package com.chameleon.tollgate.util.tollgateLog.dto.code;

import lombok.Getter;

@Getter
public enum PatternCode implements LogCode {
	SET_PATTERN("Set pattern.", 1),
	MANY_TOKEN("Too many tokens", 2),
	NO_PATTERN("No pattern.", 3),
	MANY_PATTERN("Too many patterns.", 4);

	private final String errorMessage;
	private final int errorCode;
	
	private PatternCode(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
}