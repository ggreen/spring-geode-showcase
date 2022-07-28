using System;
using StackExchange.Redis;

namespace dotnet
{
    class Program
    {
        static void Main(string[] args)
        {
            //Connection maybe slow
            ConnectionMultiplexer redis = ConnectionMultiplexer.Connect("localhost");

            IDatabase db = redis.GetDatabase();

            var writeTime = new System.Diagnostics.Stopwatch();
            string value = "";

            db.StringSet("hello", "hello world");
            writeTime.Stop();
            Console.WriteLine($"Write Time: {writeTime.ElapsedMilliseconds} ms");

            var readTime = new System.Diagnostics.Stopwatch();
            value = db.StringGet("hello");
            readTime.Stop();
            Console.WriteLine($"Read Time: {readTime.ElapsedMilliseconds} ms");



            Console.WriteLine(value);
        }
    }
}
