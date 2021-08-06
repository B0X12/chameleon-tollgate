using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using AuthClient.tollgate.util.tollgateLog.dto;
using AuthClient.tollgate.util.tollgateLog.dto.code;
using RestClient;

namespace AuthClient.tollgate.util.tollgateLog
{
    class tollgateLog
    {
        private static StreamWriter logFile = null;
        private const string separator = ";";
        private static string filePath = "";
        private static string dirPath = "";

        public static void setLogPath(string path)
        {
            dirPath = path;
            filePath = path + @"\clog.txt";

            if (!Directory.Exists(dirPath))
            {
                Directory.CreateDirectory(dirPath);
            }

            if (!File.Exists(filePath))
            {
                using (File.Create(filePath))
                {
                    if (!File.Exists(filePath))
                    {
                        Console.WriteLine("failed to create file");
                        return;
                    }
                    Console.WriteLine("file created");
                }
            }
            else
            {
                Console.WriteLine("file already exists");
            }
        }

        public static void i(LogFactor factor, LogCode code)
        {
            StringBuilder sb = new StringBuilder();
            string timestamp = DateTime.Now.ToString("yyyy-MM-dd hh:mm:ss");

            int codeNum = LogDevice.ANDROID.getCode() + factor.getCode() + LogLevel.INFO.getCode() + code.getCode();
            string ip = Config.GetServerIPAddress();

            sb.Append(timestamp).Append(separator)
                    .Append(ip == "" ? "-" : ip).Append(separator)
                    .Append(LogLevel.INFO.getMessage()).Append(separator)
                    .Append(factor.getMessage()).Append(separator)
                    .Append(codeNum).Append(separator);

            if (File.Exists(filePath))
            {
                using (logFile = File.AppendText(filePath))
                {
                    logFile.WriteLine(sb.ToString());
                }
            }

        }
        public static void w(LogFactor factor, LogCode code)
        {
            StringBuilder sb = new StringBuilder();
            string timestamp = DateTime.Now.ToString("yyyy-MM-dd hh:mm:ss");

            int codeNum = LogDevice.ANDROID.getCode() + factor.getCode() + LogLevel.INFO.getCode() + code.getCode();
            string ip = Config.GetServerIPAddress();


            sb.Append(timestamp).Append(separator)
                .Append(ip == "" ? "-" : ip).Append(separator)
                .Append(LogLevel.WARN.getMessage()).Append(separator)
                .Append(factor.getMessage()).Append(separator)
                .Append(codeNum).Append(separator);

            if (File.Exists(filePath))
            {
                using (logFile = File.AppendText(filePath))
                {
                    logFile.WriteLine(sb.ToString());
                }
            }

        }
        public static void e(LogFactor factor, LogCode code)
        {
            StringBuilder sb = new StringBuilder();
            string timestamp = DateTime.Now.ToString("yyyy-MM-dd hh:mm:ss");

            int codeNum = LogDevice.ANDROID.getCode() + factor.getCode() + LogLevel.INFO.getCode() + code.getCode();
            string ip = Config.GetServerIPAddress();

            sb.Append(timestamp).Append(separator)
                    .Append(ip == "" ? "-" : ip).Append(separator)
                    .Append(LogLevel.ERROR.getMessage()).Append(separator)
                    .Append(factor.getMessage()).Append(separator)
                    .Append(codeNum).Append(separator);

            if (File.Exists(filePath))
            {
                using (logFile = File.AppendText(filePath))
                {
                    logFile.WriteLine(sb.ToString());
                }
            }

        }

    }
}
