using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RestClient.dto
{
    class ReturnMessage
    {
        public const string SUCCESS = "SUCCESS";
        public const string FAIL = "FAIL";
        public const string UNKNOWN = "UNKNOWN";

        public const string FAIL_REGISTER_INFORMATION = "Fail Register INFORMATION";
        public const string FAIL_REGISTER_UNKNOWN = "Fail Register UNKNOWN";

        public const string FAIL_VERIFY_INFORMATION = "Fail Verify INFORMATION";
        public const string FAIL_VERIFY_UNKNOWN = "Fail Verify UNKNOWN";
        public const string FAIL_VERIFY_TIMEOUT = "Fail Verify TIMEOUT";
        public const string FAIL_VERIFY = "Fail Verify";
    }
}
