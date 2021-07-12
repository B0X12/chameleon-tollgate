package com.chameleon.tollgate.pattern.dao.exception;

import com.chameleon.tollgate.rest.exception.BaseException;

@SuppressWarnings("serial")
public class PatternResultException extends BaseException {
	public PatternResultException(PatternError error) {
		super(error);
	}
}
