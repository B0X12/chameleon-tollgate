package com.chameleon.tollgate.util.others;

import lombok.Getter;

@Getter
public class HistoryPack {
    private boolean result;
    private long timestamp;

    public HistoryPack(boolean result, long timestamp) {
        this.result = result;
        this.timestamp = timestamp;
    }
}
