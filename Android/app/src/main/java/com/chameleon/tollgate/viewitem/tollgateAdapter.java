package com.chameleon.tollgate.viewitem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chameleon.tollgate.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class tollgateAdapter extends RecyclerView.Adapter<tollgateAdapter.ViewHolder> {
    private ArrayList<HistoryItem> mList = null;
    private int itemLayout;

    public tollgateAdapter(){
        this.mList = new ArrayList<HistoryItem>();
        this.itemLayout = 0;
    }
    public tollgateAdapter(ArrayList<HistoryItem> mList, int itemLayout){
        this.mList = mList;
        this.itemLayout = itemLayout;
    }

    public void addItem(HistoryItem item){
        this.mList.add(item);
    }

    @NonNull
    @NotNull
    @Override
    public tollgateAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view =inflater.inflate(this.itemLayout, parent, false);
        tollgateAdapter.ViewHolder vh = new tollgateAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull tollgateAdapter.ViewHolder holder, int position) {
        HistoryItem item = mList.get(position);

        holder.historyIcon.setImageResource(item.getAuthIcon().getAuthResource());
        holder.historyResultIcon.setImageResource(item.getResultIcon().getResultResource());
        holder.historyLayout.setBackgroundColor(item.getResultIcon().isResult() ? Color.parseColor("#ffffff") : Color.parseColor("#f8f8f8"));
        holder.historyTitle.setText(item.getTitle().getText());
        holder.historySubTitle.setText(item.getAlias());
        holder.historyTime.setText(item.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView historyIcon, historyResultIcon;
        TextView historyTitle, historyTime, historySubTitle;
        ConstraintLayout historyLayout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        mList.get(pos).execute();
                    }
                }
            });

            historyIcon = (ImageView) itemView.findViewById(R.id.auth_ico);
            historyResultIcon = (ImageView) itemView.findViewById(R.id.auth_result);
            historyTitle = (TextView) itemView.findViewById(R.id.auth_title);
            historySubTitle = (TextView) itemView.findViewById(R.id.auth_subtitle);
            historyTime = (TextView) itemView.findViewById(R.id.auth_time);
            historyLayout = (ConstraintLayout)itemView.findViewById(R.id.history_item_layout);
        }
    }
}
