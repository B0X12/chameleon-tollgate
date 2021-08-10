namespace AuthClient.tollgate.util.tollgateLog.dto.code
{
    class FaceCode : LogCode
    {

        public static FaceCode TEST
        {
            get
            {
                return new FaceCode("Login Process", 1);
            }
        }


        private string log;
        private int code;
        FaceCode(string log, int code)
        {
            this.log = log;
            this.code = code;
        }

        public int getCode()
        {
            return code;
        }

        public string getMessage()
        {
            return log;
        }
    }
}
