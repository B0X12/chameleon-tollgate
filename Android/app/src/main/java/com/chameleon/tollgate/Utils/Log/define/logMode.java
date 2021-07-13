package com.chameleon.tollgate.Utils.Log.define;

import lombok.Getter;

@Getter
public enum logMode {
    AUTH("auth"),REG("register");

    public final String authMode;
    private logMode(String mode){
        this.authMode = mode;
    }
}

