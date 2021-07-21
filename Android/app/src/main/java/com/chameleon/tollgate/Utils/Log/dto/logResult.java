package com.chameleon.tollgate.Utils.Log.dto;

import lombok.Getter;

@Getter
public enum logResult {
    SUCCESS("Success"),
    FAIL("Fail"),
    TIMEOUT("Timeout"),
    NULL("-");

    public final String authResult;
    private logResult(String result){ this.authResult = result; }
}
