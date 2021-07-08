package com.chameleon.tollgate.rest.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
	String getMessage();
	HttpStatus getHttpStatus();
}
