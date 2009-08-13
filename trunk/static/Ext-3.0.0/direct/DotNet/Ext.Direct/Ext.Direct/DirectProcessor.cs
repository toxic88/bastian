using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using Newtonsoft.Json.Serialization;
using Newtonsoft.Json.Linq;

namespace Ext.Direct
{
    /// <summary>
    /// Class for processing Ext Direct requests.
    /// </summary>
    public class DirectProcessor
    {

        /// <summary>
        /// Processes an incoming request.
        /// </summary>
        /// <param name="provider">The provider that triggered the request.</param>
        /// <param name="httpRequest">The http information.</param>
        /// <returns>The result from the client method.</returns>
        public static string Execute(DirectProvider provider, HttpRequest httpRequest)
        {
            List<DirectResponse> responses = new List<DirectResponse>();
            if (!String.IsNullOrEmpty(httpRequest[DirectRequest.RequestFormAction]))
            {
                DirectRequest request = new DirectRequest();
                request.Action = httpRequest[DirectRequest.RequestFormAction] ?? string.Empty;
                request.Method = httpRequest[DirectRequest.RequestFormMethod] ?? string.Empty;
                request.Type = httpRequest[DirectRequest.RequestFormType] ?? string.Empty;
                request.IsUpload = Convert.ToBoolean(httpRequest[DirectRequest.RequestFormUpload]);
                request.TransactionId = Convert.ToInt32(httpRequest[DirectRequest.RequestFormTransactionId]);
                request.Data = new object[] { httpRequest };
                responses.Add(DirectProcessor.ProcessRequest(provider, request));
            }
            else
            {
                UTF8Encoding encoding = new UTF8Encoding();
                string json = encoding.GetString(httpRequest.BinaryRead(httpRequest.TotalBytes));
                List<DirectRequest> requests = JsonConvert.DeserializeObject<List<DirectRequest>>(json);
                if (requests.Count > 0)
                {
                    foreach (DirectRequest request in requests)
                    {
                        responses.Add(DirectProcessor.ProcessRequest(provider, request));
                    }
                }
                else
                {
                    responses.Add(DirectProcessor.ProcessRequest(provider, JsonConvert.DeserializeObject<DirectRequest>(json)));
                }
            }
            if (responses.Count > 1)
            {
                return JsonConvert.SerializeObject(responses);
            }
            else
            {
                DirectResponse response = responses[0];
                if (response.IsUpload)
                {
                    return String.Format("<html><body><textarea>{0}</textarea></body></html>", JsonConvert.SerializeObject(response).Replace("&quot;", "\\&quot;"));
                }
                else
                {
                    return JsonConvert.SerializeObject(response);
                }
                
            }
        }

        private static DirectResponse ProcessRequest(DirectProvider provider, DirectRequest request)
        {
            return new DirectResponse(request, provider.Execute(request));
        }

    }
}
