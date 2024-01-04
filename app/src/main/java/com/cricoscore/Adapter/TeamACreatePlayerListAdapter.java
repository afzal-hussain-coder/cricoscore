package com.cricoscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.R;
import com.cricoscore.Utils.Toaster;

public class TeamACreatePlayerListAdapter extends RecyclerView.Adapter<TeamACreatePlayerListAdapter.MyViewHolder>{
    Context mContext;
    int posClick=0;
    itemClickListener itemClickListener;
    public TeamACreatePlayerListAdapter(Context mContext,itemClickListener itemClickListener){
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.player_child_final,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(posClick>11){
                Toaster.customToast("Select only 11 players");
            }else{
                if(isChecked==true){
                    posClick++;
                    itemClickListener.checkedItem(posClick);
                }else{
                    posClick--;
                    itemClickListener.checkedItem(posClick);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox cb;
        private TextView tvTName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cb = itemView.findViewById(R.id.cb);
            this.tvTName = itemView.findViewById(R.id.tvTNameOne);
        }
    }

    public interface itemClickListener{
        void checkedItem(int pos);
    }
}
