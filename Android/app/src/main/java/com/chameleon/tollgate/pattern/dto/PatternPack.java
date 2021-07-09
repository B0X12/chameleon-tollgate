package com.chameleon.tollgate.pattern.dto;

import lombok.Getter;

@Getter
public class PatternPack {
    private final long timestamp;
    private final String pattern;

    public PatternPack(String pattern, long timestamp) {
        this.timestamp = timestamp;
        this.pattern = pattern;
    }
}
