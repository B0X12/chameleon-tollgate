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
        public long timestamp;
        public string otp;

        public OTPInfo(in string id, in long timestamp, in string otp = null)
        {
            this.id = id;
            this.timestamp = timestamp;
            this.otp = otp;
        }
    }
}
