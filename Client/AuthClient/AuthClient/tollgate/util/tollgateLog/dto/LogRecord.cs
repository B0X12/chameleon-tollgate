using System;

namespace AuthClient.tollgate.util.tollgateLog.dto
{
    class LogRecord
    {
        public string Timestamp { get; }
        public string Level { get; }
        public string Factor { get; }
        public string Code { get; }
        public string Ip { get; }

        public LogRecord(String log, char separator)
        {
            string[] logData = log.Split(separator);
            Timestamp = logData[0];
            Ip = logData[1];
            Level = logData[2];
            Factor = logData[3];
            Code = logData[4];
        }
    }
}
