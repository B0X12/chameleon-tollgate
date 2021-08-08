package com.chameleon.tollgate.otp.module;

import java.nio.charset.Charset;

public class TOtp{
	private static final boolean TestDebug = true;
	
	final static long ticksToSeconds = 1000; // System.currentTimeMillis()/1000
	private IKeyProvider secretKey;
	private int CreateCycle;
	private int totpSize;
	private int hashType;
	
	public TOtp(final String SecretKey, final int CreateCycle, final int totpSize, final int hashType)
	{
		byte[] ByteGenderSecretKey = SecretKey.getBytes(Charset.forName("UTF-8")); //Android Studio : SecretKey.getBytes(Charset.forName("UTF-8"));
	
		if(TestDebug)
		{
			System.out.println("\r\n #TOTP(Module) -  SecretKey : " + SecretKey);
			System.out.println("#TOTP(Module) -  ByteGenderSecretKey : " + ByteGenderSecretKey);
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
		long CalculateTimeStamp = CalculateTimeFromTimestamp(timestamp);
		
		return Compute(CalculateTimeStamp, hashType);
	}
	
	private long CalculateTimeFromTimestamp(final long timestamp)
	{
		if(TestDebug)
			System.out.println("#TOTP(Module) -  CalculateTimeFromTimestamp : " + (timestamp / ticksToSeconds) / CreateCycle);
		return (timestamp / ticksToSeconds) / CreateCycle;
	}
	
	private String Compute(final long CalculateTimeStamp, final int hashType)
	{
		byte[] TimestampLittleBytes = KeyUtil.GetLittleEndianBytes(CalculateTimeStamp);
		
		if(TestDebug)
			System.out.println("#TOTP(Module) -  Compute = TimestampLittleBytes : " + TimestampLittleBytes);

		long Hashotp = CalculateHashOtp(TimestampLittleBytes);
		
		if(TestDebug)
			System.out.println("#TOTP(Module) -  Compute = Hashotp : " + Hashotp);
		
		return CalculateOtp(Hashotp);
	}
	
	private long CalculateHashOtp(final byte[] data)
	{
		byte[] hmacComputedHash = this.secretKey.ComputeHmac(this.hashType, data);
		
        int offset = hmacComputedHash[hmacComputedHash.length - 1] & 0x0F;
        
        return (hmacComputedHash[offset] & 0x7f) << 24
            | (hmacComputedHash[offset + 1] & 0xff) << 16
            | (hmacComputedHash[offset + 2] & 0xff) << 8
            | (hmacComputedHash[offset + 3] & 0xff) % 1000000;
	}
	
    private String CalculateOtp(final long input)
    {
        //Math.Pow = involution
        long truncatedValue = ((int)input % (int)Math.pow(10, this.totpSize)); //EX:) 2116646656 % 1000000(DigitCount) = 646656 -> OTP VALUE
        
        String otp = String.valueOf(truncatedValue);

        return otp; 
    }
}

