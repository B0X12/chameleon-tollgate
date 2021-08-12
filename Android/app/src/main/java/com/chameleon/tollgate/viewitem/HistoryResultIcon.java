package com.chameleon.tollgate.viewitem;

import com.chameleon.tollgate.R;

import lombok.Getter;

@Getter
public enum HistoryResultIcon {
    SUCCESS(R.drawable.main_log_success, true),
    FAIL(R.drawable.main_log_failed, false);

    private final int resultResource;
    private final boolean result;

    private HistoryResultIcon(int resultResource, boolean result) {
        this.resultResource = resultResource;
        this.result = result;
    }
    public static HistoryResultIcon get(String result){
        int resultInt = Integer.parseInt(result);
        if(resultInt == 1) return HistoryResultIcon.SUCCESS;
        return HistoryResultIcon.FAIL;
    }

}
