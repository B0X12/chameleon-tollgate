
package com.chameleon.tollgate.rest;

import com.chameleon.tollgate.rest.exception.BaseExceptionType;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private final int httpsStatus;
	private final String message;
	
	public ErrorResponse(BaseExceptionType exception) {
		this.httpsStatus = exception.getHttpStatus().value();
		this.message = exception.getMessage();
	}
	
	@Override()
	public String toString() {
		return String.format("{ Message : %s, HttpStatus : %d }", this.message, this.httpsStatus);
	}
}