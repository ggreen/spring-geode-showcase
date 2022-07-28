using System;
using System.Collections.Generic;
using System.Net;
using Serengeti.Migration.ExampleProcessor.App.Domain;
using Serengeti.Migration.ExampleProcessor.App.Serialization;
using Serengeti.Migration.ExampleProcessor.App.StateMgmt.Cache;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using StackExchange.Redis;

namespace Serengeti.Migration.ExampleProcessor.App.Test.StateMgmt.Cache
{
    [TestClass]
    public class RedisDictionaryTest
    {
        private Mock<IDatabase> mockDb;
        private Mock<IConnectionMultiplexer> mockConnection;
        private RedisDictionary<ApplicationMessage> subject;
        private Mock<Serde<ApplicationMessage,string>> serde;
        private TimeSpan? expiry = null;
        private When when = When.Always;
        private CommandFlags flags = CommandFlags.None;

        private ApplicationMessage expectedValue;
        private string expectedId = "hello";
        private string expectedJson;

        private Mock<EndPoint> endPoint;
        private EndPoint[] endPoints;
        private List<RedisKey> expectedKeys = new List<RedisKey>();

        [TestInitialize]
        public void InitializeRedisDictionaryTest()
        {
            endPoint = new Mock<EndPoint>();
            endPoints = new EndPoint[1];
            endPoints[0] = endPoint.Object;

            mockDb = new Mock<IDatabase>();
            mockConnection = new Mock<IConnectionMultiplexer>();
            serde = new Mock<Serde<ApplicationMessage,string>>();
            mockConnection.Setup(r => r.GetDatabase(It.IsAny<int>(), null)).Returns(mockDb.Object);
            subject = new RedisDictionary<ApplicationMessage>(mockConnection.Object, serde.Object);

            expectedValue = new ApplicationMessage()
            {
                Id = expectedId
            };

            expectedJson = $"{{\"Id\": \"${expectedValue.Id}\"}}";

            serde.Setup(s => s.Serialize(expectedValue)).Returns(expectedJson);
            serde.Setup(s => s.Deserialize(It.IsAny<string>())).Returns(expectedValue);
            mockDb.Setup(db => db.StringGet(It.IsAny<RedisKey>(), flags)).Returns(expectedJson);

            this.mockConnection.Setup(connection => connection.GetEndPoints(false)).Returns(endPoints);

            expectedKeys.Add(new RedisKey());

            var mockServer = new Mock<IServer>();
            this.mockConnection.Setup(c => c.GetServer(endPoint.Object, null)).Returns(mockServer.Object);
            int database = 0;
            int pageSize = 10;
            long cursor = 0;
            int pageOffset = 0;
            mockServer.Setup(s => s.Keys(database, It.IsAny<RedisValue>(),
            pageSize, cursor, pageOffset, flags)).Returns(expectedKeys);



        }
        [TestMethod]
        public void Put()
        {

            subject[expectedValue.Id] = expectedValue;
            var actual = (ApplicationMessage)subject[expectedValue.Id];
            Assert.IsNotNull(actual);

            Assert.AreEqual(expectedValue.Id, actual.Id);

            Type type = typeof(ApplicationMessage);


            serde.Verify(s => s.Serialize(expectedValue));
            serde.Verify(s => s.Deserialize(It.IsAny<string>()));
            string jsonKeyexpectedKey = $"{type.Name}-{expectedValue.Id}";

            mockDb.Verify(db => db.StringSet(It.IsAny<RedisKey>(), It.IsAny<RedisValue>(),
            expiry, when, flags));

        }

        [TestMethod]
        public void GetExpiry()
        {
            //expiry
            Assert.IsNull(subject.Expiry);
            TimeSpan timeSpan = new TimeSpan(0, 1, 2);
            subject = new RedisDictionary<ApplicationMessage>(mockConnection.Object, serde.Object, timeSpan);

            Assert.IsNotNull(subject.Expiry);
        }

        [TestMethod]
        public void Get_Null()
        {
            string key = null;
            ApplicationMessage expectedValue = (ApplicationMessage)subject[key];
            Assert.IsNull(expectedValue);
            serde.Verify(e => e.Deserialize(It.IsAny<string>()), Times.Never);

        }

        [TestMethod]
        public void ExpiringRecords()
        {
            long timeNanoseconds = Convert.ToInt64(6 * 100000000); //1 minute
            int hour = 0;
            int minutes = 0;
            int seconds = 3;
            string key = "hello";
            var jsonSerde = new JsonSerde<ApplicationMessage>();
            expiry = new TimeSpan(hour, minutes, seconds);

            subject = subject = new RedisDictionary<ApplicationMessage>(mockConnection.Object, jsonSerde, expiry);


            string expectedJson = "{}";
            ApplicationMessage expectedValue = new ApplicationMessage();

            mockDb.Setup(db => db.StringGet(It.IsAny<RedisKey>(), flags)).Returns(expectedJson);

            subject[key] = expectedValue;


            Type type = typeof(ApplicationMessage);


            mockDb.Verify(db => db.StringSet(It.IsAny<RedisKey>(), It.IsAny<RedisValue>(),
            expiry, when, flags));


        }

        [TestMethod]
        public void Get_KeyDoesNotExist()
        {
            string key = "";
            mockDb.Setup(db => db.StringGet(It.IsAny<RedisKey>(), flags)).Returns(default(RedisValue));

            ApplicationMessage expectedValue = (ApplicationMessage)subject[key];
            Assert.IsNull(expectedValue);
            serde.Verify(e => e.Deserialize(It.IsAny<string>()), Times.Never);

        }
        [TestMethod]
        public void Add()
        {
            subject.Add(expectedValue.Id, expectedValue);

            mockDb.Verify(db => db.StringSet(It.IsAny<RedisKey>(), It.IsAny<RedisValue>(),
            expiry, when, flags));

        }

        [TestMethod]
        public void Add_KeyPair()
        {
            KeyValuePair<string, ApplicationMessage> pair = new KeyValuePair<string, ApplicationMessage>();

            subject.Add(pair);

            mockDb.Verify(db => db.StringSet(It.IsAny<RedisKey>(), It.IsAny<RedisValue>(),
            expiry, when, flags));

        }

        [TestMethod]
        public void FormatKey()
        {
            Assert.AreEqual("ApplicationMessage-1", subject.FormatKey("1"));
            Assert.IsNull(subject.FormatKey(""));
            Assert.IsNull(null, subject.FormatKey(null));
        }

        [TestMethod]
        public void Remove_By_Key_When_InvalidKey_Then_Return_False()
        {
            string keyInvalid = "Invalid";

            mockDb.Setup(db => db.KeyDelete(It.IsAny<RedisKey>(), flags)).Returns(false);
            Assert.IsFalse(subject.Remove(keyInvalid));
            mockDb.Verify(db => db.KeyDelete(It.IsAny<RedisKey>(), CommandFlags.None));
        }

        [TestMethod]
        public void Remove_By_Key_When_validKey_Then_Return_True()
        {
            mockDb.Setup(db => db.KeyDelete(It.IsAny<RedisKey>(), flags)).Returns(true);
            subject.Add(expectedId, expectedValue);
            Assert.IsTrue(subject.Remove(expectedId));
            mockDb.Verify(db => db.KeyDelete(It.IsAny<RedisKey>(), flags));
        }
        [TestMethod]
        public void Remove_By_Key_Pair_When_validKey_Then_Return_True()
        {
            mockDb.Setup(db => db.KeyDelete(It.IsAny<RedisKey>(), flags)).Returns(true);
            KeyValuePair<string, ApplicationMessage> expectedPair = new KeyValuePair<string, ApplicationMessage>
            (expectedId, expectedValue);

            Assert.IsTrue(subject.Remove(expectedPair));
            mockDb.Verify(db => db.KeyDelete(It.IsAny<RedisKey>(), flags));
        }
        [TestMethod]
        public void Keys()
        {
            ICollection<string> keys = subject.Keys;
            Assert.IsNotNull(keys);
            Assert.AreEqual(1, keys.Count);

        }

        [TestMethod]
        public void Count()
        {
            int expected = 1;
            Assert.AreEqual(expected, subject.Count);
            this.mockConnection.Verify(connection => connection.GetEndPoints(false));
        }

        [TestMethod]
        public void IsReadOnly()
        {
            Assert.IsFalse(subject.IsReadOnly);
        }

        [TestMethod]
        public void IsFixedSize()
        {
            Assert.IsFalse(subject.IsFixedSize);

        }

        [TestMethod]
        public void ContainsKey()
        {
            Assert.IsFalse(subject.ContainsKey("INVALID"));
            mockDb.Verify(db => db.KeyExists(It.IsAny<RedisKey>(), this.flags));
        }

        [TestMethod]
        public void Contains()
        {
            KeyValuePair<string, ApplicationMessage> pair = new KeyValuePair<string, ApplicationMessage>("INVALID", expectedValue);
            Assert.IsFalse(subject.Contains(pair));
            mockDb.Verify(db => db.KeyExists(It.IsAny<RedisKey>(), this.flags));
        }

        [TestMethod]
        public void Clear()
        {
            subject.Clear();
            this.mockConnection.Verify(connection => connection.GetEndPoints(false));

        }

        [TestMethod]
        public void CopyTo()
        {
            KeyValuePair<string, ApplicationMessage>[] array = null;
            int index = 0;
            subject.CopyTo(array,index);
            
            array = new KeyValuePair<string, ApplicationMessage>[1];
            
            subject.CopyTo(array,index+1); 
            Assert.IsNotNull(array[0]);
        }

        [TestMethod]
        public void Values()
        {
            var actual = subject.Values;
            Assert.IsNotNull(actual);
        }

        [TestMethod]
        public void TryGetValue()
        {
            subject.TryGetValue(expectedId,out expectedValue);

            Assert.IsNotNull(expectedValue);
            
        }
    }

}