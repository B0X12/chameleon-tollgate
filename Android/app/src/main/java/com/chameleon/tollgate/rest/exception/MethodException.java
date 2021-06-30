package com.chameleon.tollgate.rest.exception;

public class MethodException extends RuntimeException {
    public final String message;

    public MethodException(String message) {
        super(message);
        this.message = message;
    }

    @Override()
    public String toString() {
        return message;
    }
}