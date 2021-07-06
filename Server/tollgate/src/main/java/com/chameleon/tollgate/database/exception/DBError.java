package com.chameleon.tollgate.database.exception;

import org.springframework.http.HttpStatus;

import com.chameleon.tollgate.rest.exception.BaseExceptionType;

import lombok.Getter;

@Getter
public enum DBError implements BaseExceptionType {
	NO_TOKEN("There is not connected with the database.", HttpStatus.NOT_FOUND),
	MANY_TOKEN("Too many tokens.", HttpStatus.INTERNAL_SERVER_ERROR),
	NO_CONNECTION("There is not connected with the database.", HttpStatus.INTERNAL_SERVER_ERROR);
	
	private final String message;
	private final HttpStatus httpStatus;
	
	private DBError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}