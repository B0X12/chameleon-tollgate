package com.chameleon.tollgate.util.tollgateLog.dto.code;

public enum QrCode implements LogCode{
	SUCCESS("SUCCESS",0),
	FAIL("FAIL",1),
	
	START_GETDATACLIENT("Start Client Get QrData",2),
	END_GETDATACLIENT("End Client Get QrData",3),
	
	START_WAITCLIENT("Start Client Wait AndroidMessage",4),
	VERIFY_WAIT("Client Verify Wait",5),
	VERIFY_TIMEOUT("Client Verify Wait TimeOut",6),
	END_WAITCLIENT("End Client Wait AndroidMessage",7),
	
	START_AD_DATA("Start Ad Send QrData",8),
	END_AD_DATA("End Ad Send QrData",9),
	
	NOTFOUND_DATA("User Data Not Found", 10),
	NOTMATCH_DATA("User Data Not Matched",11);
	
	
	private final String log;
	private final int code;
	
	QrCode(String log, int code) 
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
