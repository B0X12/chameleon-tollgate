using System;

namespace AuthClient.tollgate.define
{
    public class Define
    {
        [Flags]
        public enum Factor : short
        {
            USB = 1 << 1,
            OTP = USB << 1,
            PATTERN = OTP << 1,
            FINGER = PATTERN << 1,
            FACEID = FINGER << 1,
            QR = FACEID << 1
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
