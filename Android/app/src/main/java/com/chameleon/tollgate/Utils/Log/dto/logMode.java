package com.chameleon.tollgate.Utils.Log.dto;

import lombok.Getter;

@Getter
public enum logMode {
    LOGIN("Login"),
    AUTH("Authentication"),
    REG("Registration"),
    OTHER("Others"),
    NULL("-");

    public final String authMode;
    private logMode(String mode){
        this.authMode = mode;
    }
}
