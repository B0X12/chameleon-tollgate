package com.chameleon.tollgate.Utils.tollgateLog.dto;

import lombok.Getter;

@Getter
public class LogRecord {

	private String timestamp;
	private String level;
	private String factor;
	private String code;
	private String message;
	
	public LogRecord(String log, String separator){
        String[] logData = log.split(separator);
        timestamp = logData[0];
        level = logData[1];
        factor = logData[2];
        code = logData[3];
        message = logData[4];
	}
}
