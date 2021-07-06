using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Windows.Forms;
using System.Net;
using System.Net.Http;
using System.IO;
using Newtonsoft.Json;

namespace AuthClient
{
    class HttpCommunication
    {
        // --------------- 멤버 변수 ---------------
        private static string baseURL = "";


        // --------------- 메소드 ----------------

        internal static void InitializeBaseURL(string serverIPPort, bool useHttps = true)
        {
            // baseURL 초기화
            baseURL = "";

            if (useHttps)
            {
                // HTTPS 통신 세팅
                ServicePointManager.SecurityProtocol = SecurityProtocolType.Ssl3 |
                SecurityProtocolType.Tls | SecurityProtocolType.Tls11 | SecurityProtocolType.Tls12;
                ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };

                baseURL += "https://";
            }
            else
            {
                baseURL += "http://";
            }

            baseURL += (serverIPPort + "/");
        }


        internal static string GetURL()
        {
            return baseURL;
        }


        /*
         * GET 메소드로 보내기
         */
        internal static string SendRequestGET(string path)
        {
            HttpWebRequest req;
            HttpWebResponse resp;

            // 서버와 통신 시도
            req = (HttpWebRequest)WebRequest.Create(baseURL + path);
            req.Method = "GET";
            req.UserAgent = "Tollgate-client";

            resp = (HttpWebResponse)req.GetResponse();

            Stream stream;
            StreamReader reader;
            string strFromServer = "";

            stream = resp.GetResponseStream();
            reader = new StreamReader(stream);
            strFromServer = reader.ReadToEnd();

            return strFromServer;
        }


        /*
         * POST 메소드로 보내기
         * JsonObj : 보낼 데이터 객체
         */
        internal static string SendRequestPOST(string path, object JsonObj)
        {
            HttpWebRequest req = (HttpWebRequest)WebRequest.Create(baseURL + path);

            // Request 방식 설정 - Content type, Method, User agent
            req.ContentType = "application/json";
            req.Method = "POST";
            req.UserAgent = "Tollgate-client";


            // JSON 형식의 데이터 생성
            using (StreamWriter sw = new StreamWriter(req.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(JsonObj);
                sw.Write(json);
            }


            // 서버와 통신 시도
            string ResponseResult = "";
            WebResponse resp = req.GetResponse();

            using (StreamReader sr = new StreamReader(resp.GetResponseStream()))
            {
                ResponseResult = sr.ReadToEnd();
            }

            return ResponseResult;
        }
    }
}
