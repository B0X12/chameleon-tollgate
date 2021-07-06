package com.chameleon.tollgate.rest.exception;

import lombok.Getter;

@SuppressWarnings("serial")
public class BaseRuntimeException extends RuntimeException {
	@Getter
	private BaseExceptionType exception;
	
	public BaseRuntimeException(BaseExceptionType exception) {
		super(exception.getMessage());
		this.exception = exception;
	}

	@Override()
	public String toString() {
		return String.format("{ Message : %s, HttpStatus : %d }", exception.getMessage(), exception.getHttpStatus());
	}
}
