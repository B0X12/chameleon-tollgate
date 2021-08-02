using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate
{
    class Config
    {
        private const string CONFIG_FILE_PATH = "C:\\Tollgate\\server.cfg";
        private const int SERVER_PORT = 8080;
        private static string currentUser = "";
        private static string signupUser = "";
        private static string baseURL = "";
        
        internal static void SetCurrentUser(string user)
        {
            currentUser = user;
        }

        internal static string GetCurrentUser()
        {
            return currentUser;
        }

        internal static void SetSignupUser(string user)
        {
            signupUser = user;
        }

        internal static string GetSignupUser()
        {
            return signupUser;
        }

        internal static void InitializeBaseURL(string serverIP, bool useHttps = true)
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

            baseURL += (serverIP + ":" + SERVER_PORT + "/");
        }

        internal static string GetBaseURL()
        {
            return baseURL;
        }

        internal static void SetBaseURL(string url)
        {
            baseURL = url;
        }

        internal static bool InitAuthServerByConfigFile()
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
                        InitializeBaseURL(address, true);
                    }
                    else
                    {
                        InitializeBaseURL(address, false);
                    }
                    return true;
                }
            }
            return false;
        }

        internal static void CreateConfigFile(string baseURL)
        {
            string str = baseURL.Replace("/", "");
            string[] urlComponents = str.Split(':');

            using (StreamWriter configFile = new StreamWriter(CONFIG_FILE_PATH, false))
            {
                // SecureTransmission 필드
                if(urlComponents[0].Equals("http"))
                {
                    configFile.WriteLine("SecureTransmission=false");
                } 
                else
                {
                    configFile.WriteLine("SecureTransmission=true");
                }

                // Address 필드
                configFile.WriteLine("Address=" + urlComponents[1]);

                // Port 필드
                configFile.WriteLine("Port=" + urlComponents[2]);
            }
        }

        internal static void DeleteConfigFile()
        {
            if (File.Exists(CONFIG_FILE_PATH))
            {
                File.Delete(CONFIG_FILE_PATH);
            }
        }    
    }
}
