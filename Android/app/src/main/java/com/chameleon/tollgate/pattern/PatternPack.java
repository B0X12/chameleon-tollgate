package com.chameleon.tollgate.pattern;

import lombok.Getter;

@Getter
public class PatternPack {
    private int timestamp;
    private String pattern;

    public PatternPack(String pattern, int timestamp) {
        this.timestamp = timestamp;
        this.pattern = pattern;
    }
}
