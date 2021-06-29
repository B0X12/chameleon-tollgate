package com.chameleon.tollgate.database.exception;

@SuppressWarnings("serial")
public class InitFactorException extends Exception {
	public final String message;

	public InitFactorException(String message) {
		super(message);
		this.message = message;
	}

	@Override()
	public String toString() {
		return message;
	}
}
