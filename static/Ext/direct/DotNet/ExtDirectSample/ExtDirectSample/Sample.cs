using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Ext.Direct;
using Newtonsoft.Json.Linq;

namespace ExtDirectSample
{

    [DirectAction]
    public class Sample
    {

        [DirectMethod]
        public string Echo(string name)
        {
            return "Echo: " + name;
        }

        [DirectMethod]
        public string GetTime()
        {
            return DateTime.Now.ToLongTimeString();
        }

        [DirectMethodForm]
        public void Upload(HttpRequest request)
        {
        }

        [DirectMethodForm]
        public string SaveForm(HttpRequest request)
        {
            int age = 0;
            int.TryParse(request["age"], out age);
            JObject o = new JObject(
                new JProperty("firstName", request["firstName"]),
                new JProperty("lastName", request["lastName"]),
                new JProperty("age", age));
            return o.ToString(Newtonsoft.Json.Formatting.None);
        }

    }
}
