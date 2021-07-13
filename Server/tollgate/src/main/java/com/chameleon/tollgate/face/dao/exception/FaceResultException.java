package com.chameleon.tollgate.face.dao.exception;

import com.chameleon.tollgate.rest.exception.BaseException;

@SuppressWarnings("serial")
public class FaceResultException extends BaseException {
	public FaceResultException(FaceError error) {
		super(error);
	}
}
