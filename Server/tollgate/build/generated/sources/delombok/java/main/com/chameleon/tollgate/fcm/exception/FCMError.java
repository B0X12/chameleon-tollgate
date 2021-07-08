package com.chameleon.tollgate.fcm.exception;

import org.springframework.http.HttpStatus;

import com.chameleon.tollgate.rest.exception.BaseExceptionType;

import lombok.Getter;

@Getter
public enum FCMError implements BaseExceptionType {
	NO_TITLE("Title not set.", HttpStatus.INTERNAL_SERVER_ERROR),
	NO_BODY("Body not set.", HttpStatus.INTERNAL_SERVER_ERROR),
	NO_TOKEN("Token not set.", HttpStatus.INTERNAL_SERVER_ERROR);
	
	private final String message;
	private final HttpStatus httpStatus;
	
	private FCMError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}