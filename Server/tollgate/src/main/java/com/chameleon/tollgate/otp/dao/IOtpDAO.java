package com.chameleon.tollgate.otp.dao;

import com.chameleon.tollgate.otp.dto.AuthOtp;

public interface IOtpDAO {
	long GetUserTimestamp(String UserID);
	String GetUserOtp(String UserID);
	int GetUserProcedure(String UserID);
	String GetUserSid(String UserID);
	String GetUserToken(String UserID);
	boolean Register(AuthOtp OCD);
	boolean Delete(AuthOtp OCD);

}
