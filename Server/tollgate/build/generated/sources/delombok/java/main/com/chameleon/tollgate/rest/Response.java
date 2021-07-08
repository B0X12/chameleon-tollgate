package com.chameleon.tollgate.rest;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class Response <T> {
	private int httpStatus;
	private T result;
	private int timestamp;
	
	public Response(HttpStatus httpStatus, T result, int timestamp) {
		this.httpStatus = httpStatus.value();
		this.result = result;
		this.timestamp = timestamp;
	}
	
	@Override()
	public String toString() {
		return String.format("{ Message : %s, HttpStatus : %d, Timestamp : %d }", this.result, this.httpStatus, this.timestamp);
	}
}