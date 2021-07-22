using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.otp.dto
{
    class AuthOtp
    {
        public string id; // private로 바꾸면 서버에서 null로 읽힘
        public long timestamp;
        public string otp;
        //int? wow=null 하면 null값 쓸수있음.
        public AuthOtp(in string id, in long timestamp, in string otp = null)
        {
            this.id = id;
            this.timestamp = timestamp;
            this.otp = otp;
        }
    }
}