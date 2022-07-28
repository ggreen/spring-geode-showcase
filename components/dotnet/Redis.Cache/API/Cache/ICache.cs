namespace Serengeti.Migration.ExampleProcessor.App.StateMgmt.Cache
{
    public interface ICache<K,V>
    {
        //public object this[object key] { get; set; }

        public abstract V this[K key] { get; set; }
    }
}