namespace AuthClient.tollgate.define
{
    public class Define
    {
        public enum Factor
        {
            FINGER,
            FACEID,
            USB,
            OTP,
            QR,
            PATTERN
        }

        public enum AuthFactorFlag
        {
            AUTH_FACTOR_INVALID = 0x00,
            AUTH_FACTOR_PASSWORD = 0x01,
            AUTH_FACTOR_USB = 0x02,
            AUTH_FACTOR_OTP = 0x04,
            AUTH_FACTOR_PATTERN = 0x08,
            AUTH_FACTOR_FINGERPRINT = 0x10,
            AUTH_FACTOR_FACE = 0x20,
            AUTH_FACTOR_QR = 0x40,
        };
    }
}
