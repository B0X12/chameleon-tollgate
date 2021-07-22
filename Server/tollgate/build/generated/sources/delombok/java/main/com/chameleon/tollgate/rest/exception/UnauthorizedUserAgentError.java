package com.chameleon.tollgate.rest.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum UnauthorizedUserAgentError implements BaseExceptionType {
	UNAUTHERIZED_USER_AGENT("Illegal Access", HttpStatus.BAD_REQUEST);

	private final String message;
	private final HttpStatus httpStatus;

	private UnauthorizedUserAgentError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
