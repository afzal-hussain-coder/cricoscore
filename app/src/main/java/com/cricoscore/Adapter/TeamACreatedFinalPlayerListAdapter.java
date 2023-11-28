package com.cricoscore.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.R;

import java.util.List;
import java.util.Random;

public class TeamACreatedFinalPlayerListAdapter extends RecyclerView.Adapter<TeamACreatedFinalPlayerListAdapter.MyViewHolder>{
    Context mContext;
    int posClick=0;
    itemClickListener itemClickListener;
    List<String> list;
    public TeamACreatedFinalPlayerListAdapter(Context mContext,List<String> list){
        this.mContext = mContext;
        this.list= list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.player_child_final_submitted,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Random rand = new Random();
        int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        holder.tvTName.setText(list.get(position));
        holder.tvTName.setTextColor(color);

        ImageViewCompat.setImageTintList(holder.iv_done, ColorStateList.valueOf(color));
        ImageViewCompat.setImageTintList(holder.iv_player, ColorStateList.valueOf(color));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_done,iv_player;
        private TextView tvTName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_done = itemView.findViewById(R.id.iv_done);
            this.iv_player = itemView.findViewById(R.id.iv_player);
            this.tvTName = itemView.findViewById(R.id.tvTNameOne);
        }
    }

    public interface itemClickListener{
        void checkedItem(int pos);
    }
}
