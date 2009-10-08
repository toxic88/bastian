using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Ext.Direct;

namespace ExtDirectSample
{
    public class DirectHandler : IHttpHandler
    {

        private const string DirectHandlerContentType = "text/javascript";

        public void ProcessRequest(HttpContext context)
        {
            DirectProvider provider = DirectProviderCache.GetInstance()["Ext.app.REMOTING_API"];
            context.Response.ContentType = DirectHandlerContentType;
            context.Response.Write(DirectProcessor.Execute(provider, context.Request));
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
