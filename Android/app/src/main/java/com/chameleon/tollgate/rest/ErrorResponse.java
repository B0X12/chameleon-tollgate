package com.chameleon.tollgate.rest;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int httpsStatus;
    private String message;

    @Override()
    public String toString() {
        return String.format("{ HttpStatus : %d, Message : %s }", this.httpsStatus, this.message);
    }
}