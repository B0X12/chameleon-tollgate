package com.chameleon.tollgate.rest.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum AuthError implements BaseExceptionType {
	NO_USER("There is no user with the same ID.", HttpStatus.INTERNAL_SERVER_ERROR),
	NO_SESSION("Invalid request.", HttpStatus.BAD_REQUEST);
	
	private final String message;
	private final HttpStatus httpStatus;
	
	private AuthError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
