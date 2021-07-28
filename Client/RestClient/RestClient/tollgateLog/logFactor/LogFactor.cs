using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.util.tollgateLog.dto
{
    class LogFactor
    {
        public static LogFactor LOGIN
        {
            get
            {
                return new LogFactor("Login", 1000);
            }
        }
        public static LogFactor REST
        {
            get
            {
                return new LogFactor("Rest", 2000);
            }
        }
        public static LogFactor USB
        {
            get
            {
                return new LogFactor("USB", 3000);
            }
        }
        public static LogFactor PATTERN
        {
            get
            {
                return new LogFactor("Pattern", 4000);
            }
        }
        public static LogFactor FINGER
        {
            get
            {
                return new LogFactor("Finger", 5000);
            }
        }
        public static LogFactor FACE
        {
            get
            {
                return new LogFactor("Face", 6000);
            }
        }
        public static LogFactor OTP
        {
            get
            {
                return new LogFactor("OTP", 7000);
            }
        }

        private int code;
        private string factor;
        LogFactor(string factor, int code)
        {
            this.factor = factor;
            this.code = code;
        }
        public int getCode()
        {
            return code;
        }
        public string getMessage()
        {
            return factor;
        }
    }
}
