package com.chameleon.tollgate.Utils.tollgateLog.dto.code;

public enum faceCode implements LogCode {
	NO_FACE("No Face", 1),
	WRONG_HASH("Wrong Hash", 2);

	private final String log;
	private final int code;
	private faceCode(String log, int code) {
		this.log = log;
		this.code = code;
	}
	
	@Override
	public int getErrorCode() {
		return code;
	}
	
	@Override
	public String getErrorMessage() {
		return log;
	}

}
