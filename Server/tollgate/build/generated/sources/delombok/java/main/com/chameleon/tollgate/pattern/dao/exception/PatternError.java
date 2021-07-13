package com.chameleon.tollgate.pattern.dao.exception;

import org.springframework.http.HttpStatus;

import com.chameleon.tollgate.rest.exception.BaseExceptionType;

import lombok.Getter;

@Getter
public enum PatternError implements BaseExceptionType {
	NO_PATTERN("There is no pattern.", HttpStatus.NOT_FOUND),
	MANY_PATTERN("Too many patterns", HttpStatus.INTERNAL_SERVER_ERROR);
	
	private final String message;
	private final HttpStatus httpStatus;
	
	private PatternError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}