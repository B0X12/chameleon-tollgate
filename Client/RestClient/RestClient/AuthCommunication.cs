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
            authServerURI = "http://192.168.0.15:8080";
            
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Ssl3 | 
                SecurityProtocolType.Tls | SecurityProtocolType.Tls11 | SecurityProtocolType.Tls12;
            ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
            
        }

        public bool IsServerAlive()
        {
            HttpWebRequest req;
            HttpWebResponse resp;
            string uri = authServerURI + "/";

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
                Console.WriteLine("CONNECTION-FAILED:");
                Console.WriteLine(e.Message);
                return false;
            }

            Stream stream = resp.GetResponseStream();
            StreamReader reader = new StreamReader(stream);
            string strFromServer = reader.ReadToEnd();
            Console.WriteLine(strFromServer);

            if (strFromServer.Equals("Hello"))
            {
                return true;
            }

            return false;
        }

        public bool VerifyUSB(string user, string usb_info)
        {
            HttpWebRequest req;
            HttpWebResponse resp;
            string uri = authServerURI + "/usb/" + user + "/" + usb_info;

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
                Console.WriteLine(e.Message);
                return false;
            }

            Stream stream = resp.GetResponseStream();
            StreamReader reader = new StreamReader(stream);
            string strFromServer = reader.ReadToEnd();
            Console.WriteLine(strFromServer);

            return true;
        }

        public bool VerifyPattern(string user)
        {
            HttpWebRequest req;
            HttpWebResponse resp;
            string uri = "https://192.168.0.72:8080" + "/auth/pattern/" + user;

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
                Console.WriteLine(e.Message);
                return false;
            }

            Stream stream = resp.GetResponseStream();
            StreamReader reader = new StreamReader(stream);
            string strFromServer = reader.ReadToEnd();
            Console.WriteLine(strFromServer);

            return true;
        }
    }
}
