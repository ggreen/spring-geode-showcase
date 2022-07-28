using System;

namespace Serengeti.Migration.ExampleProcessor.App.Serialization
{
    public interface Serde<T,S>
    {
        public  S Serialize(T deserialized);
       
        public  T Deserialize(S serialized);
        
    }
}