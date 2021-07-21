package com.chameleon.tollgate.account.exception;
import com.chameleon.tollgate.rest.exception.BaseException;

@SuppressWarnings("serial")
public class UserAlreadyExistException extends BaseException {
	public UserAlreadyExistException(AccountError error) {
		super(error);
	}
}
