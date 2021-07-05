using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace RestClient
{
    class AuthCommunication
    {
        private string authServerURI = "";

        public AuthCommunication()
        {
            authServerURI = "https://192.168.7.108:8080";
            
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Ssl3 | 
                SecurityProtocolType.Tls | SecurityProtocolType.Tls11 | SecurityProtocolType.Tls12;
            ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
            
        }

        public bool IsServerAlive()
        {
            string uri = authServerURI + "/";

            if (RequestAuthAndRecordResponse(uri))
            {
                return true;
            }

            return false;
        }

        public bool VerifyUSB(string user, string usb_info)
        {
            string uri = authServerURI + "/auth/usb/" + user + "/" + usb_info;

            if (RequestAuthAndRecordResponse(uri))
            {
                return true;
            }

            return false;
        }

        public bool VerifyPattern(string user)
        {
            string uri = authServerURI + "/auth/pattern/" + user;

            if(RequestAuthAndRecordResponse(uri))
            {
                return true;
            }

            return false;
        }

        private bool RequestAuthAndRecordResponse(string uri)
        {
            HttpWebRequest req;
            HttpWebResponse resp;
            Stream stream;
            StreamReader reader;

            try
            {
                req = (HttpWebRequest)WebRequest.Create(uri);
                req.Method = "GET";
                req.UserAgent = "Tollgate-client";

                resp = (HttpWebResponse)req.GetResponse();
                Console.WriteLine(resp.StatusCode);
            }
            catch (WebException e)
            {
                if (e.Status == WebExceptionStatus.ProtocolError)
                {
                    Console.WriteLine(((HttpWebResponse)e.Response).StatusCode);
                    Console.WriteLine("Error occured");
                    /*
                    stream = e.Response.GetResponseStream();
                    reader = new StreamReader(stream);
                    Console.WriteLine(reader.ReadToEnd());
                    */
                }
                else
                {
                    Console.WriteLine(e.Message);
                }
                return false;
            }

            stream = resp.GetResponseStream();
            reader = new StreamReader(stream);
            string strFromServer = reader.ReadToEnd();
            Console.WriteLine(strFromServer);

            return true;
        }
    }
}
