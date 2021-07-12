package com.chameleon.tollgate.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chameleon.tollgate.rest.ErrorResponse;
import com.chameleon.tollgate.rest.exception.BaseException;
import com.chameleon.tollgate.rest.exception.BaseRuntimeException;
import com.chameleon.tollgate.rest.exception.CommonError;

//@RestControllerAdvice(basePackages = "com.chameleon.tollgate.pattern.controller")
//@RestControllerAdvice(basePackageClasses = AuthController.class)
@RestControllerAdvice
public class ExceptionController {
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(HttpServletRequest request, final BaseException ex){
		return new ResponseEntity<>(new ErrorResponse(ex.getException()), ex.getException().getHttpStatus());
	}
	
	@ExceptionHandler(BaseRuntimeException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(HttpServletRequest request, final BaseRuntimeException ex){
		return new ResponseEntity<>(new ErrorResponse(ex.getException()), ex.getException().getHttpStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(HttpServletRequest request, final Exception ex){
		return new ResponseEntity<>(new ErrorResponse(new CommonError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}