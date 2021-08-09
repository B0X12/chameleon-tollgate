package com.chameleon.tollgate.viewitem;

import com.chameleon.tollgate.R;

import lombok.Getter;

@Getter
public enum HistoryResultIcon {
    SUCCESS(R.drawable.main_log_success),
    FAIL(R.drawable.main_log_failed);

    private final int resultResource;
    private HistoryResultIcon(int resultResource) {
        this.resultResource = resultResource;
    }
    public static HistoryResultIcon get(String result){
        int resultInt = Integer.parseInt(result);
        if(resultInt == 1) return HistoryResultIcon.SUCCESS;
        return HistoryResultIcon.FAIL;
    }
}
