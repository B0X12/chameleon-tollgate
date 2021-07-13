package com.chameleon.tollgate.util.exception;

@SuppressWarnings("serial")
public class ParseException extends Exception {
	public final String message;

	public ParseException(String message) {
		super(message);
		this.message = message;
	}

	@Override()
	public String toString() {
		return message;
	}
}