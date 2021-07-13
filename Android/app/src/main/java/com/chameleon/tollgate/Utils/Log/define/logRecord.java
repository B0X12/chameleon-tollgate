package com.chameleon.tollgate.Utils.Log.define;

import lombok.Getter;

@Getter
public class logRecord {
    private String timestamp;
    private String priority;
    private String mode;
    private String type;
    private String message;

    public logRecord(String line){
        String[] logData = line.split(";");
        timestamp = logData[0];
        priority = logData[1];
        mode = logData[2];
        type = logData[3];
        message = logData[4];
    }
}
