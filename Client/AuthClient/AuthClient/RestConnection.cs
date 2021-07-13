using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient
{
    public enum Method
    {
        GET,
        POST,
        PUT,
        DELETE,
    }

    public class RestConnection
    {
        // 기본 구성 요소
        private string baseURL;
        private string path;

        private string queryString; 
        private object bodyJsonObj;

        // Web Request 설정 객체
        HttpWebRequest req;

        // 


        public RestConnection(string baseURL, Method method, string path)
        {
            // HTTPS 통신 세팅
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Ssl3 |
            SecurityProtocolType.Tls | SecurityProtocolType.Tls11 | SecurityProtocolType.Tls12;
            ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };

            // 기본 URL 세팅
            this.baseURL = baseURL;

            // Web Request 설정 객체 세팅
            req.UserAgent = "Tollgate-client";

            switch (method){
                case Method.GET:
                    req.Method = "GET";
                    break;

                case Method.POST:
                    req.ContentType = "application/json";
                    req.Method = "POST";
                    break;
                
                case Method.PUT:
                    req.Method = "PUT";
                    break;
                
                case Method.DELETE:
                    req.Method = "DELETE";
                    break;
            }

            this.path = path;
            this.queryString = "";
            this.bodyJsonObj = null;
        }

        public void AddQueryString(string key, string value)
        {
            if(queryString.Length == 0)
            {
                this.queryString += "?";
            } 
            else
            {
                this.queryString += "&";
            }
            this.queryString += key + "=" + value;
        }

        public void AddQueryString(string key, long value)
        {
            if (queryString.Length == 0)
            {
                this.queryString += "?";
            }
            else
            {
                this.queryString += "&";
            }
            this.queryString += key + "=" + value;
        }

        public void SetBodyJsonObj(object dto)
        {
            this.bodyJsonObj = dto;
        }


        public RestResult SendRequest()
        {
            

            //return strFromServer;
        }
    }

    public class RestResult
    {
        public HttpStatusCode statusCode;
        public string result;

        public RestResult(HttpStatusCode statusCode, string result)
        {
            this.statusCode = statusCode;
            this.result = result;
        }
    }
}
