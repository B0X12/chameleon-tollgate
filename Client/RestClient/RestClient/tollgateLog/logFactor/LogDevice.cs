using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.util.tollgateLog.dto
{
    class LogDevice
    {
        public static LogDevice SERVER
        {
            get
            {
                return new LogDevice("Login", 10000);
            }
        }
        public static LogDevice PC
        {
            get
            {
                return new LogDevice("PC", 20000);
            }
        }
        public static LogDevice ANDROID
        {
            get
            {
                return new LogDevice("Android", 30000);
            }
        }

        private string device;
        private int code;

        LogDevice(string device, int code)
        {
            this.device = device;
            this.code = code;
        }
        public int getCode()
        {
            return code;
        }
        public string getMessage()
        {
            return device;
        }
    }
}
