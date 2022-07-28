using System;
using System.Collections.Generic;
using Serengeti.Migration.ExampleProcessor.App.Domain;
using Serengeti.Migration.ExampleProcessor.App.Serialization;
using Serengeti.Migration.ExampleProcessor.App.StateMgmt.Cache;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using StackExchange.Redis;

namespace Serengeti.Migration.ExampleProcessor.App.Test.Controllers
{
    [TestClass]
    [Ignore]
    public class RedisIt
    {
        private Serde<ApplicationMessage,string> serde;
        private ConnectionMultiplexer redisConnection;
        private RedisDictionary<ApplicationMessage> dictionary;


        [TestInitialize]
        public void InitializeCacheBenchMarkControllerTest()
        {
            serde = new JsonSerde<ApplicationMessage>();
            redisConnection = ConnectionMultiplexer.Connect("localhost:6379");
            dictionary = new RedisDictionary<ApplicationMessage>(redisConnection, serde);
        }


        [TestMethod]
        public void Int_Local_Test()
        {
            string key = "1";
            ApplicationMessage expected = new ApplicationMessage()
            {
                Id = key,
                ZSeriesMessage = "Hi"
            };

            dictionary[key] = expected;

            ICollection<string> actual = dictionary.Keys;
            Assert.IsNotNull(actual);
            Assert.AreEqual(1, actual.Count);

        }

        [TestMethod]
        public void Keys()
        {
            var keys = dictionary.Keys;

            foreach (var key in keys)
            {
                Console.WriteLine($"the key is: {key}");
            }

            string expectedKey = "1";
            ApplicationMessage actual = null;
            dictionary.TryGetValue(expectedKey,out actual);

            Assert.IsTrue(actual != null && actual.Id == expectedKey);

            Assert.IsTrue(keys.Count > 0);
            Console.WriteLine($"keys:{keys}");
            
        }

    }
}