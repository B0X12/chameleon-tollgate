package com.chameleon.tollgate.rest.exception;

@SuppressWarnings("serial")
public class NoUserException extends BaseException {
	public NoUserException(AuthError error) {
		super(error);
	}
}