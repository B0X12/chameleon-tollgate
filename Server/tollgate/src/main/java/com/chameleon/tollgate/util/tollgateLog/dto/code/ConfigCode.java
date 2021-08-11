package com.chameleon.tollgate.util.tollgateLog.dto.code;

import lombok.Getter;

@Getter
public enum ConfigCode implements LogCode {
	INIT_PATTERN("Init pattern factor.", 1);

	private final String errorMessage;
	private final int errorCode;
	
	private ConfigCode(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
}