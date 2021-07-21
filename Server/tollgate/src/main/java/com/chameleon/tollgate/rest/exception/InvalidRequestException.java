package com.chameleon.tollgate.rest.exception;

@SuppressWarnings("serial")
public class InvalidRequestException extends BaseException {
	public InvalidRequestException(AuthError error) {
		super(error);
	}
}
