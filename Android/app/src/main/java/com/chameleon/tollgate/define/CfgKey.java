package com.chameleon.tollgate.define;

import lombok.Getter;

@Getter
public enum CfgKey {
    SERVER_IP("server_ip");

    private final String value;

    CfgKey(String value) {
        this.value = value;
    }
}