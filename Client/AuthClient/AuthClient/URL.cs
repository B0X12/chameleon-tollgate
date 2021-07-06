using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient
{
    class AuthURL
    {
        public const string USB = "auth/usb/";
        public const string PATTERN = "auth/pattern/";
        public const string FINGERPRINT = "auth/finger/";
        public const string FACEID = "auth/face/";
        public const string OTP = "auth/otp/";
    }

    class RegisterURL
    {
        public const string USB = "register/usb/";
        public const string PATTERN = "register/pattern/";
        public const string FINGERPRINT = "register/finger/";
        public const string FACEID = "register/face/";
        public const string OTP = "register/otp/";
    }

    class AccountURL
    {
        public const string LOGIN = "account/login/";
        public const string SIGNUP = "account/signup/";
        public const string MAP_PC = "account/map/pc/";
    }

    class EtcURL
    {
        public const string SERVER_HELLO = "";
    }
}
