package com.chameleon.tollgate.Utils.Log.define;

import lombok.Getter;

@Getter
public enum logType {
    USB("usb"),
    PATTERN("pattern"),
    FINGERPRINT("finger"),
    FACE("face"),
    OTP("otp");

    public final String authType;
    private logType(String type){ this.authType = type; }
}