using System;
using System.Text.Json;

namespace Serengeti.Migration.ExampleProcessor.App.Serialization
{
    public class JsonSerde<T> : Serde<T,string>
{
        public T Deserialize(string serialized)
        {
            T output = (T)JsonSerializer.Deserialize(serialized,typeof(T));
            return output;
        }

        public string Serialize(T deserialized)
        {
           return JsonSerializer.Serialize(deserialized);
        }
    }
}