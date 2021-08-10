package com.chameleon.tollgate.viewitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chameleon.tollgate.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class regAdapter extends RecyclerView.Adapter<regAdapter.ViewHolder> {
    private ArrayList<authItem> mList = null;

    public regAdapter(){
        this.mList = new ArrayList<authItem>();
    }
    public regAdapter(ArrayList<authItem> mList){
        this.mList = mList;
    }

    public void addItem(authItem item){
        this.mList.add(item);
    }

    @NonNull
    @NotNull
    @Override
    public regAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view =inflater.inflate(R.layout.item_reg, parent, false);
        regAdapter.ViewHolder vh = new regAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull regAdapter.ViewHolder holder, int position) {
        authItem item = mList.get(position);

        holder.imgView_item.setImageResource(mList.get(position).getViewImg());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgView_item;
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

            imgView_item = (ImageView) itemView.findViewById(R.id.auth_img);
        }
    }
}
