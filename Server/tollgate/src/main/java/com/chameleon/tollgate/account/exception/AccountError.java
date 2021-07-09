package com.chameleon.tollgate.account.exception;

import org.springframework.http.HttpStatus;

import com.chameleon.tollgate.rest.exception.BaseExceptionType;

import lombok.Getter;

@Getter
public enum AccountError implements BaseExceptionType {
	NO_USER("���̵� Ʋ�Ƚ��ϴ�.", HttpStatus.BAD_REQUEST);
	
	private String message;
	private HttpStatus httpStatus;
	
	private AccountError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
