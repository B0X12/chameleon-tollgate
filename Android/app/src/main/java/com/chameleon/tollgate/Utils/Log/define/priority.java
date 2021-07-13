package com.chameleon.tollgate.Utils.Log.define;

import lombok.Getter;

@Getter
public enum priority {
    TRACE("trace"),
    DEBUG("debug"),
    INFO("info"),
    WARN("warn"),
    ERROR("error"),
    FATAL("fatal");

    public final String priority;
    private priority(String priority){
        this.priority = priority;
    }
}
