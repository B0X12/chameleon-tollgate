package com.chameleon.tollgate.otp.dao;


public interface IOtpDAO 
{
	String GetUserSecretKey(final String userId);
	Long GetUserRegisterTime(final String userId); // Long = null O || long = null X
	String GetUserSid(final String userId);
	String GetUserToken(final String userId);
	boolean Register(final String userId, final String secertKey, final long registerTimeStamp);
}
