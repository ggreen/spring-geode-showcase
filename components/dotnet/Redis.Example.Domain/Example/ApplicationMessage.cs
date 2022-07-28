using System;

namespace Redis.Example.Domain
{
    public class ApplicationMessage
    {
        public string Id {get; set;}
        
        public string Text { get; set; }

       public  DateTime dataValidTime;

    }
}