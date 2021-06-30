package com.chameleon.tollgate.rest.define;

public enum Method {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    PATCH("PATCH");

    private final String value;

    Method(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
