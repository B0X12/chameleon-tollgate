using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Windows.Forms;
using System.Net;
using System.Net.Http;
using System.IO;

namespace AuthClient
{
    // 전역으로 참조되는 클래스 - RestUtility
    //      인증 서버와 통신하기 위한 메서드 모음
    class RestUtility
    {
        // --------------- 멤버 변수 ---------------
        private static WebClient client = new WebClient();
        private static string baseURL = "";


        // --------------- 메소드 ----------------

        public static void InitRestClient(string serverIPPort, bool useHttps=true)
        {
            // 헤더 항목 추가 : User-Agent: Chameleon
            client.Headers.Set("User-Agent", "Tollgate-client");

            // URL 요소 추가
            baseURL = "";

            if(useHttps)
            {
                baseURL += "https://";
            }
            else
            {
                baseURL += "http://";
            }

            baseURL += (serverIPPort + "/");
        }


        public static string GetURL()
        {
            return baseURL;
        }


        public static bool IsServerAlive()
        {
            Stream stream = null;

            try
            {
                stream = client.OpenRead(baseURL);
            }
            catch (WebException e)
            {
                MessageBox.Show(e.Message, "연결에 실패하였습니다", MessageBoxButtons.OK);
                return false;
            }
            
            StreamReader reader = new StreamReader(stream);
            string strFromServer = reader.ReadToEnd();

            if(strFromServer.Equals("Hello"))
            {
                return true;
            }
            
            return false;
        }
    }
}
