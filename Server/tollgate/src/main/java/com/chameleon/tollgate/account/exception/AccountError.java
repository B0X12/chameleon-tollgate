package com.chameleon.tollgate.account.exception;

import org.springframework.http.HttpStatus;

import com.chameleon.tollgate.rest.exception.BaseExceptionType;

import lombok.Getter;

@Getter
public enum AccountError implements BaseExceptionType {
	NO_USER("���̵� Ʋ�Ƚ��ϴ�.", HttpStatus.BAD_REQUEST),
	USER_ALREADY_EXIST("�̹� ���� ������ �����մϴ�", HttpStatus.OK);
	
	private final String message;
	private final HttpStatus httpStatus;
	
	private AccountError(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
