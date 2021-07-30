using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace RestClient
{
    class Util
    {
        internal static string GetSystemUniqueIdentifier()
        {
            // 해당 시스템의 SID 정보를 얻고, SID에서 시스템 고유 식별 값만 추출
            var GetSid = System.Security.Principal.WindowsIdentity.GetCurrent().User;
            string strSid = GetSid.ToString();
            string[] splitSid = strSid.Split(new string[] { "-" }, StringSplitOptions.None);
            string sid = splitSid[4] + "-" + splitSid[5] + "-" + splitSid[6];

            return sid;
        }

        internal static string GetSystemName()
        {
            // 해당 시스템의 컴퓨터 이름을 얻어옴
            string systemName = System.Security.Principal.WindowsIdentity.GetCurrent().Name.Split('\\')[0];
            return systemName;
        }

        internal static long GetCurrentTimestamp()
        {
            var timeSpan = (DateTime.UtcNow - new DateTime(1970, 1, 1, 0, 0, 0));
            return (long)timeSpan.TotalSeconds;
        }

        public static string GetCurrentDateTime()
        {
            return DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
        }

        internal static string EncryptSHA512(string Data)
        {
            SHA512 sha = new SHA512Managed();
            byte[] hash = sha.ComputeHash(Encoding.ASCII.GetBytes(Data));
            StringBuilder sb = new StringBuilder();

            foreach (byte b in hash)
            {
                sb.AppendFormat("{0:x2}", b);
            }

            return sb.ToString();
        }
    }
}
