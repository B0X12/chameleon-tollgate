package com.chameleon.tollgate.rest.exception;

import lombok.Getter;

@SuppressWarnings("serial")
public class BaseException extends Exception {
	@Getter
	private BaseExceptionType exception;
	
	public BaseException(BaseExceptionType exception) {
		super(exception.getMessage());
		this.exception = exception;
	}

	@Override()
	public String toString() {
		return String.format("{ Message : %s, HttpStatus : %d }", exception.getMessage(), exception.getHttpStatus());
	}
}
