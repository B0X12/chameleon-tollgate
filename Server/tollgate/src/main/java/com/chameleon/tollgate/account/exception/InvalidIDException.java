package com.chameleon.tollgate.account.exception;

import com.chameleon.tollgate.rest.exception.BaseException;

@SuppressWarnings("serial")
public class InvalidIDException extends BaseException {
	public InvalidIDException(AccountError error) {
		super(error);
	}
}
