package com.chameleon.tollgate.util.userHistory.dto;

import lombok.Getter;

@Getter
public enum HistoryFactor {
    FINGERPRINT("fingerprint"),
    PATTERN("pattern"),
    OTP("otp"),
    FACE("face"),
    USB("usb");

    private final String factor;
    private HistoryFactor(String factor){ 
    	this.factor = factor; 
    }
}
