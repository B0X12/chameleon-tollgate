package com.chameleon.tollgate.rest.define;

import org.jetbrains.annotations.NotNull;

public enum Path {
    TOLLGATE("/"),
    USB("/auth/usb/"),
    PATTERN("/auth/pattern/"),
    FINGERPRINT("/auth/finger/"),
    FACEID("/auth/face/"),
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    FACEID_REG("/register/face/"),
    OTP("/auth/otp/"),
=======
    OTP("/auth/otp/"),

>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
=======
    OTP("/auth/otp/"),

>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
=======
    OTP("/auth/otp/"),

>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
    LOGIN("/account/android/login/"),
    LOGOUT("/account/android/logout/"),
    SET_TOKEN("/account/android/map/"),
    AD_ID("/account/android/id/");

    private final String value;

    Path(String value){
        this.value = value;
    }

    @Override
    public @NotNull String toString() {
        return value;
    }
}
