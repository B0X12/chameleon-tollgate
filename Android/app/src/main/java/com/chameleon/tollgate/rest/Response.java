package com.chameleon.tollgate.rest;

import lombok.Getter;

@Getter
public class Response <T> {
    private int httpStatus;
    private T result;
    private long timestamp;

    public Response(T result, int timestamp) {
        this.httpStatus = HttpStatus.OK.value;
        this.result = result;
        this.timestamp = timestamp;
    }

    @Override()
    public String toString() {
        return String.format("{ Message : %s, HttpStatus : %d, Timestamp : %d }", this.result, this.httpStatus, this.timestamp);
    }
}