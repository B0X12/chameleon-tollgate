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

public class authAdapter extends RecyclerView.Adapter<authAdapter.ViewHolder>{

    private ArrayList<authItem> mList = null;

    public authAdapter(){
        this.mList = new ArrayList<authItem>();
    }
    public authAdapter(ArrayList<authItem> mList){
        this.mList = mList;
    }

    public void addItem(authItem item){
        this.mList.add(item);
    }

    @NonNull
    @NotNull
    @Override
    public authAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view =inflater.inflate(R.layout.item_auth, parent, false);
        authAdapter.ViewHolder vh = new authAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull authAdapter.ViewHolder holder, int position) {
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
