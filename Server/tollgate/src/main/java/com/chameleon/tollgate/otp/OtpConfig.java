package com.chameleon.tollgate.otp;
import com.chameleon.tollgate.otp.module.HashType;

public class OtpConfig {
	public final static int CreateCycle = 60;
	public final static int OtpSize = 6;
	public final static int Hashtype = HashType.SHA256;
}
