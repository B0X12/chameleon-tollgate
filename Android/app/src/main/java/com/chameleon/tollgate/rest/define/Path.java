package com.chameleon.tollgate.rest.define;

public enum Path {
    USB("/auth/usb/"),
    PATTERN("/auth/pattern/"),
    FINGERPRINT("/auth/finger/"),
    FACEID("/auth/face/"),
    OTP("/auth/otp/");

    private final String value;

    Path(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
