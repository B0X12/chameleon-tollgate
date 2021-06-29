package com.chameleon.tollgate.database.exception;

@SuppressWarnings("serial")
public class DatabaseConnectException extends Exception {
	public final String message;

	public DatabaseConnectException(String message) {
		super(message);
		this.message = message;
	}

	@Override()
	public String toString() {
		return message;
	}
}
