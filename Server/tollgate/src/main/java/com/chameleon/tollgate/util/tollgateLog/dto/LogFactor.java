package com.chameleon.tollgate.util.tollgateLog.dto;

import lombok.Getter;

@Getter
public enum LogFactor {
    LOGIN("Login", 1000),
    REST("Rest", 2000),
    USB("USB", 3000),
    PATTERN("Pattern", 4000),
    FINGER("Finger", 5000),
    FACE("Face", 6000),
    OTP("OTP", 7000),
	CONFIG("Config", 8000),
	COMMON("Common", 9000),
	QR("QR",10000);

    private final String factor;
    private final int code;
    private LogFactor(String factor, int code){ 
    	this.factor = factor; 
    	this.code = code;
    }
}
