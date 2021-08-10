using Newtonsoft.Json;
using System.IO;
using System.Net;

namespace AuthClient.tollgate.rest
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
        Method method = Method.INVALID;
        object jsonObject = null;
        string urlArguments = "";
        int timeout = 60000;            // 기본 타임아웃 시간: 1분


        // --------------- 메소드 ----------------
        public HttpCommunication(Method method, string path, QueryString query)
        {
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

        public HttpCommunication(Method method, string path, QueryString query, int timeout) : this(method, path, query)
        {
            this.timeout = timeout;
        }

        public HttpCommunication(Method method, string path, QueryString query, object jsonObject)
        {
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

        public HttpCommunication(Method method, string path, QueryString query, object jsonObject, int timeout) : this(method, path, query, jsonObject)
        {
            this.timeout = timeout;
        }

        public RestResult SendRequest()
        {
            HttpWebRequest req = (HttpWebRequest)WebRequest.Create(Config.GetBaseURL() + urlArguments);
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

        public void AddQueryString(string key, bool value)
        {
            querys += "&" + key + "=" + value;
        }

        public void AddQueryString(string key, int value)
        {
            querys += "&" + key + "=" + value;
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

        /*** 계정 관리 관련 Path ***/
        public const string LOGIN = "account/login/";
        public const string SIGNUP = "account/signup/";
        public const string MAP_PC = "account/map/pc/";
        public const string MAP_PCLIST = "account/map/pclist/";
        public const string FACTOR_FLAG = "account/factor/";
        public const string SET_PWD = "account/password/";

        public const string AUTH_USB = "auth/usb/";
        public const string AUTH_PATTERN = "auth/pattern/";
        public const string AUTH_FINGERPRINT = "auth/finger/";
        public const string AUTH_FACEID = "auth/face/";
        public const string AUTH_OTP = "auth/otp/";

        public const string REGISTER_USB = "register/usb/";
        public const string REGISTER_PATTERN = "register/pattern/";
        public const string REGISTER_FINGERPRINT = "register/finger/";
        public const string REGISTER_FACEID = "register/face/";
        public const string REGISTER_OTP = "register/otp/";

        public const string UPDATE_PC_ALIAS = "update/pc/alias/";
        public const string UPDATE_USB_ALIAS = "update/usb/alias/";

        private const string DATA = "/data/";
        public const string DATA_OTP = DATA + "otp/";
        public const string DATA_QR = DATA + "qr/";

        public const string CONF_INIT = "init/";
    }
}
