package com.chameleon.tollgate.database.exception;

import com.chameleon.tollgate.rest.exception.BaseException;

@SuppressWarnings("serial")
public class DatabaseResultException extends BaseException {
	public DatabaseResultException(DBError error) {
		super(error);
	}
}
