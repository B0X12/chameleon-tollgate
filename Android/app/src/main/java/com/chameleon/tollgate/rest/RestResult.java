package com.chameleon.tollgate.rest;

import org.jetbrains.annotations.NotNull;

public class RestResult {
    public int responseCode;
    public String result;

    public RestResult(int responseCode, String result){
        this.responseCode = responseCode;
        this.result = result;
    }

    @Override
    public @NotNull String toString(){
        return String.format("{ response : %d, result : %s }", this.responseCode, this.result);
    }
}
