package com.chameleon.tollgate.database.exception;

@SuppressWarnings("serial")
public class DatabaseResultException extends Exception {
	public final String message;

	public DatabaseResultException(String message) {
		super(message);
		this.message = message;
	}

	@Override()
	public String toString() {
		return message;
	}
}
