package com.chameleon.tollgate.face.dao.exception;

import org.springframework.http.HttpStatus;

import com.chameleon.tollgate.rest.exception.BaseExceptionType;

import lombok.Getter;

@Getter
public enum FaceError implements BaseExceptionType {
	NO_FACE("There is no Face Model Hash.", HttpStatus.NOT_FOUND),
	MANY_FACE("Too many hash Values", HttpStatus.INTERNAL_SERVER_ERROR);
	
	private final String message;
	private final HttpStatus httpStatus;
	
	private FaceError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}