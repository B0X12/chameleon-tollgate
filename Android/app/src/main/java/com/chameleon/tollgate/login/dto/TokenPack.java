package com.chameleon.tollgate.login.dto;

import lombok.Getter;

@Getter
public class TokenPack {
    private final long timestamp;
    private final String token;

    public TokenPack(String token, long timestamp) {
        this.timestamp = timestamp;
        this.token = token;
    }
}
