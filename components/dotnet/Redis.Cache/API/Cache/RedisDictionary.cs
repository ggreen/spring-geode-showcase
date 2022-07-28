using System;
using System.Collections.Generic;
using System.Net;
using Serengeti.Migration.ExampleProcessor.App.Domain;
using Serengeti.Migration.ExampleProcessor.App.Serialization;
using StackExchange.Redis;

namespace Serengeti.Migration.ExampleProcessor.App.StateMgmt.Cache
{
    public class RedisDictionary<V> : DictionaryAdapter<string, V>, ICache<string, V>
    {
        private readonly IConnectionMultiplexer connection;
        private readonly IDatabase db;

        private readonly Serde<V,string> serde;
        private readonly string typeName;

        private readonly TimeSpan? expiry = null;

        public TimeSpan? Expiry { get { return expiry; } }
        public RedisDictionary(IConnectionMultiplexer connection, Serde<V,string> serde) : this(connection, serde, null)
        {   
        }
        public RedisDictionary(IConnectionMultiplexer connection, Serde<V,string> serde, TimeSpan? expiry)
        {
            this.connection = connection;
            this.db = connection.GetDatabase();
            this.serde = serde;
            this.typeName = typeof(V).Name;

            this.expiry = expiry;
        }

        public override void Add(string key, V value)
        {
            string serialized = this.serde.Serialize((V)value);

            this.db.StringSet(FormatKey(key), serialized, expiry);
        }



        public override V this[string key] { get => get(key); set => Add(key, value); }

        public override bool Remove(string key)
        {
            return db.KeyDelete(FormatKey(key));
        }


        private V get(object key)
        {
            if (key == null)
                return default(V);

            var serialized = this.db.StringGet(FormatKey(key));
            if (serialized.Length() == 0)
                return default(V);

            return this.serde.Deserialize(serialized);
        }

        internal string FormatKey(object key)
        {
            if (key == null)
                return null;

            var keyText = key.ToString();
            if (keyText.Length == 0)
                return null;

            return $"{typeName}-{keyText}";
        }


        public override ICollection<string> Keys
        {
            //TODO: must be optimized
            get
            {
                string expression = $"{typeName}-*";
                EndPoint[] endPoints = connection.GetEndPoints();

                if (endPoints == null || endPoints.Length == 0)
                    return null;

                EndPoint endPoint = endPoints[0];
                IEnumerable<RedisKey> keys = connection.GetServer(endPoint).Keys(pattern: expression);

                if (keys == null)
                    return null;

                ISet<string> set = new HashSet<string>();
                foreach (RedisKey key in keys)
                {
                    set.Add(key.ToString());
                }

                return set;
            }
        }

        public override int Count
        {
            get
            {
                var keys = Keys;
                if (keys == null)
                    return 0;

                return Keys.Count;
            }
        }
        public override bool ContainsKey(string key)
        {
            return this.db.KeyExists(FormatKey(key));
        }

        public override void Clear()
        {
            var keys = Keys;

            if (keys == null || keys.Count == 0)
                return;

            foreach (var key in keys)
            {
                this.Remove(key);
            }
        }
    }

}