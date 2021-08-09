package com.chameleon.tollgate.util.tollgateLog.dto.code;

import lombok.Getter;

@Getter
public enum RestCode implements LogCode {
	INVALID_REQUEST("Invalid user-agent.", 1);

	private final String errorMessage;
	private final int errorCode;
	
	private RestCode(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
}