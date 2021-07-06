package com.chameleon.tollgate.rest.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CommonError implements BaseExceptionType {
	private final String message;
	private final HttpStatus httpStatus;
	
	public CommonError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}