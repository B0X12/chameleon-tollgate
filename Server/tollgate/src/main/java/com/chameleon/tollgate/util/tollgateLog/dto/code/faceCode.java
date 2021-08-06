package com.chameleon.tollgate.util.tollgateLog.dto.code;

public enum faceCode implements LogCode {
	NO_PRIVILEGE("No authority to access", 1),
	NO_FACE("Face Model Hash value mismatched", 2),
	SIGNAL_SENT("FCM Notification Sent to Mobile Device", 3),
	TIMEOUT("Authentication time limit exceeded", 4),
	START_WAIT("Waiting mobile device start", 5),
	STOP_WAIT("Waiting mobile device stop", 6),
	VERIFY("Verifying authentication information", 7);

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
