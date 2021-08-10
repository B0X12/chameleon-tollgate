namespace AuthClient.tollgate.util.tollgateLog.dto
{
    class LogLevel
    {
        public static LogLevel INFO
        {
            get
            {
                return new LogLevel("Info", 100);
            }
        }
        public static LogLevel WARN
        {
            get
            {
                return new LogLevel("Warn", 200);
            }
        }
        public static LogLevel ERROR
        {
            get
            {
                return new LogLevel("Error", 300);
            }
        }

        private string level;
        private int code;
        LogLevel(string level, int code)
        {
            this.level = level;
            this.code = code;
        }
        public int getCode()
        {
            return code;
        }
        public string getMessage()
        {
            return level;
        }
    }
}
