using System;
using System.Collections;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;

namespace Serengeti.Migration.ExampleProcessor.App.StateMgmt.Cache
{
    public abstract class DictionaryAdapter<K,V> : IDictionary<K,V>
    {
        public abstract V this[K key] { get; set; }

        public bool IsFixedSize { get { return false;}}

        public bool IsReadOnly {get{ return false;}}

        public virtual ICollection<K> Keys => throw new NotImplementedException();

        public ICollection<V> Values 
        {
            get{
                var keys = Keys;

                ICollection<V> collection = new List<V>();
                foreach (var key in keys)
                {
                    collection.Add(this[key]);
                }
                return collection;

            }
        }

        public virtual int Count => throw new NotImplementedException();

        public virtual void Add(K key, V value)
        {
            throw new NotImplementedException();
        }

        public virtual void Add(KeyValuePair<K, V> pair)
        {
            Add(pair.Key,pair.Value);
        }

        public virtual void Clear()
        {
            throw new NotImplementedException();
        }


        public virtual bool Contains(KeyValuePair<K, V> item)
        {
           return ContainsKey(item.Key);
        }

        public virtual bool ContainsKey(K key)
        {
            throw new NotImplementedException();
        }

       
        public virtual void CopyTo(KeyValuePair<K, V>[] array, int arrayIndex)
        {
            if(array == null || array.Length == 0)
                return;

            var keys = Keys;
            if(keys == null)
                return;

            var keysEnumeration = keys.GetEnumerator();
            K key = default;
            for(int i=0;i< arrayIndex && i < array.Length;i++)
            {
                if(!keysEnumeration.MoveNext())
                    return;

                key = keysEnumeration.Current;

                array[i] = new KeyValuePair<K,V>(key,this[key]);
            }
        }

        public virtual IEnumerator<KeyValuePair<K, V>> GetEnumerator()
        {
            throw new NotImplementedException();
        }

        public virtual bool Remove(K key)
        {
            throw new NotImplementedException();
        }

        public virtual bool Remove(KeyValuePair<K, V> item)
        {
            return Remove(item.Key);
        }

        public virtual bool TryGetValue(K key, [MaybeNullWhen(false)] out V value)
        {
            var output = this[key];

            if(output == null)
            {
                value = default;
                return false;
            }

             value = output;
             return true;
        }

         IEnumerator IEnumerable.GetEnumerator()
        {
            throw new NotImplementedException();
        }
    }
}