package com.chameleon.tollgate.database.exception;

import com.chameleon.tollgate.rest.exception.BaseException;

@SuppressWarnings("serial")
public class DatabaseConnectException extends BaseException {
	public DatabaseConnectException(DBError error) {
		super(error);
	}
}
