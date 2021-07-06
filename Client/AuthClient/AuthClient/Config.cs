using AuthClient.dto;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient
{
    class Config
    {
        private const string CONFIG_FILE_PATH = "C:\\Tollgate\\server.cfg";
        private static string currentUser = "";

        internal static void SetCurrentUser(string user)
        {
            currentUser = user;
        }

        internal static string GetCurrentUser()
        {
            return currentUser;
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

                // 파일 내용 검사
                if ((secureTransmissionLine != null) && (addressLine != null) && (portLine != null))
                {
                    string secureTransmission = secureTransmissionLine.Substring(secureTransmissionLine.IndexOf('=') + 1);
                    string address = addressLine.Substring(addressLine.IndexOf('=') + 1);
                    string port = portLine.Substring(portLine.IndexOf('=') + 1);

                    if (secureTransmission.Equals("true"))
                    {
                        HttpCommunication.InitializeBaseURL(address + ":" + port, true);
                    }
                    else
                    {
                        HttpCommunication.InitializeBaseURL(address + ":" + port, false);
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

        internal static bool MappingSIDWithUser(string user)
        {
            string sid = Util.GetSystemUniqueIdentifier();
            string alias = Util.GetSystemName();
            MapPC mapPC = new MapPC(user, sid, alias);
            string result = "";

            try
            {
                result = HttpCommunication.SendRequestPOST(AccountURL.MAP_PC, mapPC);
            } 
            catch (WebException we)
            {
                result = "false";
            }


            if(result.Equals("true"))
            {
                return true;
            } 
            else
            {
                return false;
            }
        }

        /*
         *      현재 PC의 sid를 이용하여 id를 체크함
         *      id가 존재한다면, id 정보를 서버가 넘겨 주어야 함
         */
        internal static string GetRegisteredUserIDBySID()
        {
            string sid = Util.GetSystemUniqueIdentifier();
            string userID;

            try
            {
                userID = HttpCommunication.SendRequestGET(AccountURL.MAP_PC + sid);
            } catch (WebException we)
            {
                userID = "";
            }

            return userID;
        }
    }
}
