using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RestClient.dto
{
    class OTPReturnMessage
    {
        public const string SUCCESS = "SUCCESS";
        public const string FAIL = "FAIL";

        public const string REGISTER_DATABASE = "REGISTER_DATABASE";
        public const string REGISTER_INFORMATION = "REGISTER_INFORMATION";

        public const string VERIFY_DATABASE = "VERIFY_DATABASE";
        public const string VERIFY_INFORMATION = "VERIFY_INFORMATION";
        public const string VERIFY_TIMEOUT = "VERIFY_TIMEOUT";
        public const string VERIFY_FAIL = "VERIFY_FAIL";
    }

    class OTPReturnMessageValue
    {
        public const int SUCCESS = 1;
        public const int FAIL = 0;

        public const int REGISTER_INFORMATION = 10;
        public const int REGISTER_DATABASE = 11;

        public const int VERIFY_INFORMATION = 20;
        public const int VERIFY_DATABASE = 21;
        public const int VERIFY_TIMEOUT = 22;
        public const int VERIFY_FAIL = 23;
    }
}
