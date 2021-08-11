package com.chameleon.tollgate.otp.module;

import android.util.Log;

import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.otp.Activity.OtpActivity;
import com.chameleon.tollgate.otp.OtpConfig;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TOtp{
    final static long ticksToSeconds = 1000; // System.currentTimeMillis()/1000
    private final IKeyProvider secretKey;
    private final int CreateCycle;
    private final int totpSize;
    private final int hashType;

    public TOtp(final String SecretKey,final int CreateCycle,final int totpSize, final int hashType)
    {
        final byte[] ByteGenderSecretKey = SecretKey.getBytes(Charset.forName("UTF-8"));

        if(OtpActivity.DEBUGOTP == true)
        {
            Log.d(LogTag.AUTH_OTP, "\r\n #TOTP - SecretKey : " + SecretKey);
            Log.d(LogTag.AUTH_OTP, "#TOTP - ByteGenderSecretKey : " + ByteGenderSecretKey);
        }
        this.secretKey = new KeyHash(ByteGenderSecretKey);
        this.CreateCycle = CreateCycle; this.totpSize = totpSize; this.hashType = hashType;
    }

    public String ComputeTotp(final long timestamp)
    {
        return CreateTotpValue(timestamp);
    }

    private String CreateTotpValue(final long timestamp)
    {
        final long CalculateTimeStamp = CalculateTimeFromTimestamp(timestamp); //정상

        return Compute(CalculateTimeStamp);
    }

    private long CalculateTimeFromTimestamp(final long timestamp)
    {
        final long Result = (timestamp / ticksToSeconds) / CreateCycle;
        if(OtpActivity.DEBUGOTP == true)
            Log.d(LogTag.AUTH_OTP, "#TOTP - CalculateTimeFromTimestamp : " + Result);
        return Result;
    }

    private String Compute(final long CalculateTimeStamp)
    {
        final byte[] TimestampLittleBytes = KeyUtil.GetLittleEndianBytes(CalculateTimeStamp);

        if(OtpActivity.DEBUGOTP == true)
            Log.d(LogTag.AUTH_OTP, "#TOTP - Compute = Bytes : " + TimestampLittleBytes);

        long HashOtp = CalculateHashOtp(TimestampLittleBytes);

        if(OtpActivity.DEBUGOTP == true)
            Log.d(LogTag.AUTH_OTP, "#TOTP - Compute = HashOtp : " + HashOtp);

        return CalculateOtp(HashOtp);
    }

    private long CalculateHashOtp(final byte[] data)
    {
        byte[] hmacComputedHash = this.secretKey.ComputeHmac(this.hashType,data);

        int offset = hmacComputedHash[hmacComputedHash.length - 1] & 0x0F;

        return (hmacComputedHash[offset] & 0x7f) << 24
                | (hmacComputedHash[offset + 1] & 0xff) << 16
                | (hmacComputedHash[offset + 2] & 0xff) << 8
                | (hmacComputedHash[offset + 3] & 0xff) % 1000000;
    }

    private String CalculateOtp(final long input)
    {
        //Math.Pow = involution
        //long truncatedValue = ((int)input % (int)Math.pow(10, this.totpSize)); //EX:) 2116646656 % 1000000(DigitCount) = 646656 -> OTP VALUE
        String stringToLong = String.valueOf(input);

        return stringToLong.substring(stringToLong.length()- OtpConfig.otpSize, stringToLong.length());
        //return String.valueOf(truncatedValue);
    }
}
