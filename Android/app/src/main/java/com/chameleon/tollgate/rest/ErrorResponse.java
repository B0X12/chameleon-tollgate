package com.chameleon.tollgate.rest;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int httpsStatus;
    private String message;

    @Override()
    public @NotNull String toString() {
        return String.format("{ HttpStatus : %d, Message : %s }", this.httpsStatus, this.message);
    }
}