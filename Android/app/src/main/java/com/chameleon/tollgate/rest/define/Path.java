package com.chameleon.tollgate.rest.define;

import org.jetbrains.annotations.NotNull;

public enum Path {
    TOLLGATE("/"),
    USB("/auth/usb/"),
    PATTERN("/auth/pattern/"),
    FINGERPRINT("/auth/finger/"),
    FACEID("/auth/face/"),
    LOGIN("/account/android/login/"),
    LOGOUT("/account/android/logout/"),
    SET_TOKEN("/account/android/map/"),
    AD_ID("/account/android/id/"),
    DATA_OTP("/data/otp/"),
    AUTH_QR("/auth/qr/"),
    REGIST_PATTERN("/register/pattern/"),
    REGIST_FACE("/register/face/");

    private final String value;

    Path(String value){
        this.value = value;
    }

    @Override
    public @NotNull String toString() {
        return value;
    }
}
