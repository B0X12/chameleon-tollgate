package com.chameleon.tollgate.Utils.Log.dto;

import lombok.Getter;

@Getter
public enum logType {
    LOGIN("LogIn"),
    USB("USB"),
    PATTERN("Pattern"),
    FINGERPRINT("Finger"),
    FACE("Face"),
    OTP("OTP"),
    NULL("-");

    public final String authType;
    private logType(String type){ this.authType = type; }
}