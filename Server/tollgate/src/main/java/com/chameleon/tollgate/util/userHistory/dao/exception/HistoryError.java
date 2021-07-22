package com.chameleon.tollgate.util.userHistory.dao.exception;

import org.springframework.http.HttpStatus;

import com.chameleon.tollgate.rest.exception.BaseExceptionType;

import lombok.Getter;

@Getter
public enum HistoryError implements BaseExceptionType {
	NO_HISTORY("There is no Face Model Hash.", HttpStatus.NOT_FOUND);
	
	private final String message;
	private final HttpStatus httpStatus;
	
	private HistoryError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
