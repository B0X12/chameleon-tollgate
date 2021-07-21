package com.chameleon.tollgate.rest.exception;

@SuppressWarnings("serial")
public class UnauthorizedUserAgentException extends BaseException {
	public UnauthorizedUserAgentException(UnauthorizedUserAgentError error) {
		super(error);
	}
}
