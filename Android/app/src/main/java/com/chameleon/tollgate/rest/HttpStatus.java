package com.chameleon.tollgate.rest;

import lombok.Getter;

public enum HttpStatus {
    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    public final int value;

    private HttpStatus(int value) {
        this.value = value;
    }
}
