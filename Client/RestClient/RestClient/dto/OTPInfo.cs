using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RestClient.dto
{
    class OTPInfo
    {
        public string id;
        public string otp;

        public OTPInfo(in string id, in string otp = null)
        {
            this.id = id;
            this.otp = otp;
        }
    }
}
