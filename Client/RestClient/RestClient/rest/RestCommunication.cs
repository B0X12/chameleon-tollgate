using Newtonsoft.Json;
using System;
using System.IO;
using System.Net;

namespace RestClient.rest
{
    public enum Method
    {
        INVALID,
        GET,
        POST,
        PUT,
        DELETE,
    }

    class HttpCommunication
    {
        // --------------- 멤버 변수 ---------------
        private string baseURL = "";
        private Method method = Method.INVALID;
        private object jsonObject = null;
        private string urlArguments = "";
        private int timeout = 60000;            // 기본 타임아웃 시간: 1분


        // --------------- 메소드 ----------------
        public HttpCommunication(string baseURL, Method method, string path, QueryString query)
        {
            this.baseURL = baseURL;

            if (method.Equals(Method.POST) || method.Equals(Method.PUT))
            {
                throw new InvalidArgumentException("POST, PUT 메소드에는 jsonObject 객체를 지정해야 합니다")
                {
                    method = this.method
                };
            }

            this.method = method;
            this.urlArguments += path;

            if (query != null)
            {
                urlArguments += query.GetQueryString();
            }
        }

        public HttpCommunication(string baseURL, Method method, string path, QueryString query, int timeout) : this(baseURL, method, path, query)
        {
            this.timeout = timeout;
        }

        public HttpCommunication(string baseURL, Method method, string path, QueryString query, object jsonObject)
        {
            this.baseURL = baseURL;

            if (method.Equals(Method.GET) || method.Equals(Method.DELETE))
            {
                throw new InvalidArgumentException("GET, DELETE 메소드에는 jsonObject 객체를 할당할 수 없습니다")
                {
                    method = this.method
                };
            }

            this.method = method;
            this.urlArguments += path;

            if (query != null)
            {
                urlArguments += query.GetQueryString();
            }

            this.jsonObject = jsonObject;
        }

        public HttpCommunication(string baseURL, Method method, string path, QueryString query, object jsonObject, int timeout) 
            : this(baseURL, method, path, query, jsonObject)
        {
            this.timeout = timeout;
        }

        public RestResult SendRequest()
        {
            HttpWebRequest req = (HttpWebRequest)WebRequest.Create(baseURL + urlArguments);
            req.UserAgent = "Tollgate-client";
            req.Method = method.ToString();

            // POST 또는 PUT 메소드일 때만 Body 적용 
            if ((this.method == Method.POST) || (this.method == Method.PUT))
            {
                req.ContentType = "application/json";

                // JSON 형식의 데이터 생성
                using (StreamWriter sw = new StreamWriter(req.GetRequestStream()))
                {
                    string serializedJson = JsonConvert.SerializeObject(jsonObject);
                    sw.Write(serializedJson);
                }
            }

            HttpWebResponse resp = (HttpWebResponse)req.GetResponse();
            StreamReader reader = new StreamReader(resp.GetResponseStream());
            string strFromServer = reader.ReadToEnd();

            RestResult result = new RestResult(resp.StatusCode, strFromServer);
            return result;
        }
    }

    public class RestResult
    {
        public HttpStatusCode statusCode;
        public string jsonResult;

        public RestResult(HttpStatusCode statusCode, string result)
        {
            this.statusCode = statusCode;
            this.jsonResult = result;
        }
    }

    class QueryString
    {
        private string querys = "";

        public QueryString(string key, string value)
        {
            querys += "?" + key + "=" + value;
        }

        public QueryString(string key, long value)
        {
            querys += "?" + key + "=" + value;
        }

        public string GetQueryString()
        {
            return querys;
        }

        public void AddQueryString(string key, string value)
        {
            querys += "&" + key + "=" + value;
        }

        public void AddQueryString(string key, long value)
        {
            querys += "&" + key + "=" + value;
        }
    }

    class URLPath
    {
        public const string SERVER_HELLO = "";

        public const string ACCOUNT_USER = "account/map/pc/";
        public const string ACCOUNT_FACTOR = "account/factor/";

        public const string VERIFY_USB = "auth/usb/";
        public const string REQUEST_PATTERN = "auth/pattern/";
        public const string REQUEST_FINGERPRINT = "auth/finger/";
        public const string REQUEST_FACE = "auth/face/";
        public const string REQUEST_OTP = "register/otp/";
        public const string VERIFY_OTP = "auth/otp/";
        public const string DATA_QR = "data/qr/";
        public const string AUTH_QR = "auth/qr/";
    }
}
