package com.chameleon.tollgate.Utils.Log.dto;

import lombok.Getter;

@Getter
public enum logPriority {
    TRACE("Trace"),
    DEBUG("Debug"),
    INFO("Info"),
    WARN("Warn"),
    ERROR("Error"),
    FATAL("Fatal");

    public final String priority;
    private logPriority(String priority){
        this.priority = priority;
    }
}
