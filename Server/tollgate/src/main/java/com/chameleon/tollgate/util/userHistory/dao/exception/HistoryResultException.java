package com.chameleon.tollgate.util.userHistory.dao.exception;

import com.chameleon.tollgate.rest.exception.BaseException;

@SuppressWarnings("serial")
public class HistoryResultException extends BaseException {

	public HistoryResultException(HistoryError error) {
		super(error);
	}
}
