package com.chameleon.tollgate.util.tollgateLog.dto;

import lombok.Getter;

@Getter
public enum LogDevice {
	SERVER("Server", 10000),
	PC("PC", 20000),
	ANDROID("Android", 30000);
	
	
	private final String device;
	private final int code;
	
	private LogDevice(String device, int code) {
		this.device = device;
		this.code = code;
	}
}
