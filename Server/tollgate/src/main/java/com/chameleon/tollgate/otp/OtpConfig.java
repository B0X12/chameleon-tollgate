package com.chameleon.tollgate.otp;
import com.chameleon.tollgate.otp.module.HashType;

public class OtpConfig {
	public final static int createCycle = 60;
	public final static int otpSize = 6;
	public final static int hashType = HashType.SHA256;
}
