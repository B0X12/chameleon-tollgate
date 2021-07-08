package com.chameleon.tollgate.database.exception;

import com.chameleon.tollgate.rest.exception.BaseException;

@SuppressWarnings("serial")
public class InitFactorException extends BaseException {
	public InitFactorException(DBError error) {
		super(error);
	}
}