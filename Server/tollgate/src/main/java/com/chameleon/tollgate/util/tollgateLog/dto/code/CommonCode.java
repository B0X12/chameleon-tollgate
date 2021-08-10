package com.chameleon.tollgate.util.tollgateLog.dto.code;

import lombok.Getter;

@Getter
public enum CommonCode implements LogCode {
	FCM_FAILED("Failed send fcm.", 1),
	NO_SESSION("No session.", 2),
	DB_CONECTION("Can't connect to database.", 3);

	private final String errorMessage;
	private final int errorCode;
	
	private CommonCode(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
}