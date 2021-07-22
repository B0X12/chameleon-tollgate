package com.chameleon.tollgate.util.tollgateLog.dto;

import lombok.Getter;

@Getter
public class LogRecord {

	private String timestamp;
	private String level;
	private String factor;
	private String code;
	private String ip;
	
	public LogRecord(String log, String separator){
        String[] logData = log.split(separator);
        timestamp = logData[0];
        ip = logData[1];
        level = logData[2];
        factor = logData[3];
        code = logData[4];
	}
}
