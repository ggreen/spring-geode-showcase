using System;
using StackExchange.Redis;

namespace dotnet
{
    class Program
    {
        static void Main(string[] args)
        {
            ConnectionMultiplexer redis = ConnectionMultiplexer.Connect("localhost");

            IDatabase db = redis.GetDatabase();

            db.StringSet("hello", "hello world");

            string value = db.StringGet("hello");
            Console.WriteLine(value); // writes: "abcdefg"
        }
    }
}
