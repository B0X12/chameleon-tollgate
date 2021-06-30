package com.chameleon.tollgate.rest;

public class RestResult {
    public int responseCode;
    public String result;

    public RestResult(int responseCode, String result){
        this.responseCode = responseCode;
        this.result = result;
    }

    @Override
    public String toString(){
        return String.format("{ response : %d, result : %s }", this.responseCode, this.result);
    }
}
