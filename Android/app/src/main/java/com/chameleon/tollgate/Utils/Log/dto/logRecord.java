package com.chameleon.tollgate.Utils.Log.dto;

import lombok.Getter;

@Getter
public class logRecord {
    private String timestamp;
    private String priority;
    private String mode;
    private String type;
    private String result;
    private String message;

    public logRecord(String line, String separator){
        String[] logData = line.split(separator);
        timestamp = logData[0];
        priority = logData[1];
        mode = logData[2];
        type = logData[3];
        result = logData[4];
        message = logData[5];
    }
}
