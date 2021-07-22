package com.chameleon.tollgate.util.userHistory.dto;

import lombok.Getter;

@Getter
public enum HistoryResult {
    SUCCESS("success", 1),
    FAIL("fail", 0);

    private final String result;
    private final int code;
    private HistoryResult(String result, int code){ 
    	this.result = result; 
    	this.code = code;
    }
}
