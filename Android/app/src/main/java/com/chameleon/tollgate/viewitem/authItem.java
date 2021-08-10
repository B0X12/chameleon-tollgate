package com.chameleon.tollgate.viewitem;

public class authItem {
    public interface OnItemClickListener{
        void onClick();
    }

    OnItemClickListener mListener = null;

    public authItem(int imgResource, OnItemClickListener clickListener){
        this.viewImg = imgResource;
        this.mListener = clickListener;
    }

    public void execute(){
        mListener.onClick();
    }

    private int viewImg;
    public int getViewImg(){
        return viewImg;
    }
    public void setViewImg(int imgName){
        this.viewImg = imgName;
    }

}
