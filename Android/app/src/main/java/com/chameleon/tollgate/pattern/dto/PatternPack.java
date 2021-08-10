package com.chameleon.tollgate.pattern.dto;

import lombok.Getter;

@Getter
public class PatternPack {
    private final long timestamp;
    private final String pattern;
    private final String pc;

    public PatternPack(String pattern, long timestamp, String pc) {
        this.timestamp = timestamp;
        this.pattern = pattern;
        this.pc = pc;
    }
}
