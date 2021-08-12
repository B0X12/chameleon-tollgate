package com.chameleon.tollgate.util.tollgateLog.dto.code;

public enum OtpCode implements LogCode {
	SUCCESS("SUCCESS",0),
	FAIL("FAIL",1),
	START_REGISTER("Start Register",2),
	RESULT_REGISTER("Result Register",3),
	
	START_VERIFY("Start Verify",4),
	RESULT_VERIFY("Result Register",5),
	
	START_AD_DATA("Start AD Data",6),
	RESULT_AD_DATA("Result AD Data",7),

	NOTFOUND_DATA("User Data Not Found", 10),
	NOTMATCH_DATA("User Data Not Matched",11);
	
	private final String log;
	private final int code;
	
	OtpCode(String log, int code) 
	{
		this.log = log;
		this.code = code;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public int getErrorCode() {
		return 0;
	}
	

}
