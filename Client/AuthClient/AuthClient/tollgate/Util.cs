using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate
{
    class Util
    {
        internal static string GetSystemUniqueIdentifier()
        {
            // 해당 시스템의 SID 정보를 얻고, SID에서 시스템 고유 식별 값만 추출
            var GetSid = System.Security.Principal.WindowsIdentity.GetCurrent().User;
            string stringSid = GetSid.ToString();
            string[] splitSid = stringSid.Split(new string[] { "-" }, StringSplitOptions.None);
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
    }
}
