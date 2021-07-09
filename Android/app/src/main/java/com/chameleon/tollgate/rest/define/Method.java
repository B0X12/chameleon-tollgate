package com.chameleon.tollgate.rest.define;

import org.jetbrains.annotations.NotNull;

public enum Method {
    GET("GET"),
    PUT("PUT"),
    POST("POST"),
    DELETE("DELETE"),
    PATCH("PATCH");

    private final String value;

    Method(String value){
        this.value = value;
    }

    @Override
    public @NotNull String toString() {
        return value;
    }
}
