package com.chameleon.tollgate.faceauth;

import lombok.Getter;

@Getter
public class FacePack {
    private String hashValue;
    private String mode;
    private boolean result;
    private long timestamp;

    public FacePack(String hashValue, String mode, boolean result, int timestamp) {
        this.hashValue = hashValue;
        this.mode = mode;
        this.result = result;
        this.timestamp = timestamp;
    }
}
