package com.chameleon.tollgate.util.tollgateLog.dto.code;

import lombok.Getter;

@Getter
public enum FaceCode implements LogCode {
	NO_PRIVILEGE("No authority to access", 1),
	NO_FACE("Face Model Hash value mismatched", 2),
	SIGNAL_SENT("FCM Notification Sent to Mobile Device", 3),
	TIMEOUT("Authentication time limit exceeded", 4),
	START_WAIT("Waiting mobile device start", 5),
	STOP_WAIT("Waiting mobile device stop", 6),
	VERIFY("Verifying authentication information", 7);

	private final String errorMessage;
	private final int errorCode;
	
	private FaceCode(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
}