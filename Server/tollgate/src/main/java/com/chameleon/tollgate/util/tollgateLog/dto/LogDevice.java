package com.chameleon.tollgate.util.tollgateLog.dto;

import lombok.Getter;

@Getter
public enum LogDevice {
	SERVER("Server", 100000),
	PC("PC", 200000),
	ANDROID("Android", 300000);
	
	
	private final String device;
	private final int code;
	
	private LogDevice(String device, int code) {
		this.device = device;
		this.code = code;
	}
}
