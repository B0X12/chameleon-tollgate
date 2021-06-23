package com.chameleon.tollgate.fcm.exception;

@SuppressWarnings("serial")
public class MessageBuildException extends RuntimeException {
	public final String message;
	
	public MessageBuildException(String message) {
		super(message);
		this.message = message;
	}
}