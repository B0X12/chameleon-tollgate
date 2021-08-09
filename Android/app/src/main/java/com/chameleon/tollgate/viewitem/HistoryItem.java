package com.chameleon.tollgate.viewitem;

import lombok.Getter;

@Getter
public class HistoryItem {
    public interface OnItemClickListener{
        void onClick();
    }

    authItem.OnItemClickListener mListener = null;

    public HistoryItem(HistoryIcon authIcon, HistoryResultIcon resultIcon, HistoryTitle title, String alias, String timestamp, authItem.OnItemClickListener clickListener){
        this.authIcon = authIcon;
        this.resultIcon = resultIcon;
        this.title = title;
        this.alias = alias;
        this.timestamp = timestamp;
        this.mListener = clickListener;
    }

    public void execute(){
        mListener.onClick();
    }

    private final HistoryIcon authIcon;
    private final HistoryResultIcon resultIcon;
    private final HistoryTitle title;
    private final String timestamp;
    private final String alias;
}
