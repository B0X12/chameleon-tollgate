using System.IO;
using System.Net;

namespace RestClient
{
    class Config
    {
        private const string CONFIG_FILE_PATH = @"C:\Tollgate\server.cfg";
        

        private string GetBaseURLAddress(string serverIPPort, bool useHttps = true)
        {
            // baseURL 초기화
            string baseURL = "";

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

            return baseURL;
        }


        public string InitAuthServerByConfigFile()
        {
            // 파일 존재 유무 검사
            if (File.Exists(CONFIG_FILE_PATH))
            {
                // 파일 내용 읽기
                StreamReader file = new StreamReader(CONFIG_FILE_PATH);
                string secureTransmissionLine = file.ReadLine();
                string addressLine = file.ReadLine();
                string portLine = file.ReadLine();
                file.Close();

                // 파일 내용 검사
                if ((secureTransmissionLine != null) && (addressLine != null) && (portLine != null))
                {
                    string secureTransmission = secureTransmissionLine.Substring(secureTransmissionLine.IndexOf('=') + 1);
                    string address = addressLine.Substring(addressLine.IndexOf('=') + 1);
                    string port = portLine.Substring(portLine.IndexOf('=') + 1);

                    if (secureTransmission.Equals("true"))
                    {
                        return GetBaseURLAddress(address + ":" + port, true);
                    }
                    else
                    {
                        return GetBaseURLAddress(address + ":" + port, false);
                    }
                }
            }

            return "";
        }
    }
}
