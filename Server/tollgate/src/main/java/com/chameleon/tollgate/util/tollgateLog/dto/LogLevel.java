package com.chameleon.tollgate.util.tollgateLog.dto;

import lombok.Getter;

@Getter
public enum LogLevel {
    INFO("Info", 100),
    WARN("Warn", 200),
    ERROR("Error", 300);

    private final String level;
    private final int code;
    private LogLevel(String level, int code){ 
    	this.level = level; 
    	this.code = code;
    }
}
		