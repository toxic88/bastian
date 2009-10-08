using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Ext.Direct;

namespace ExtDirectSample
{
    public class ApiHandler : IHttpHandler
    {
        private const string DirectHandlerContentType = "text/javascript";

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = DirectHandlerContentType;

            DirectProviderCache cache = DirectProviderCache.GetInstance();
            DirectProvider provider;
            //After being configured, the provider should be cached.
            if(!cache.ContainsKey("Ext.app.REMOTING_API"))
            {
                provider = new DirectProvider("Ext.app.REMOTING_API", "/DirectHandler.ashx");
                provider.Configure(System.Reflection.Assembly.GetExecutingAssembly());
                cache.Add("Ext.app.REMOTING_API", provider);
            }
            else
            {
                provider = cache["Ext.app.REMOTING_API"];
            }

            context.Response.Write(provider.ToString());
        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}
