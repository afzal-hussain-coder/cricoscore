package com.cricoscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Activity.TeamPlayerActivity;
import com.cricoscore.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourPlayerListAdapter extends RecyclerView.Adapter<YourPlayerListAdapter.MyViewHolder>{

    Context mContext;
    itemClickListener itemClickListener;
    int posClick=0;
    List<TeamPlayerActivity.Player> tList = new ArrayList<>();
    public YourPlayerListAdapter(Context mContext,List<TeamPlayerActivity.Player> tList, itemClickListener itemClickListener){
        this.mContext = mContext;
        this.tList = tList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_player_list_child, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TeamPlayerActivity.Player tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getPhone());
        holder.tvtRole.setText(tournament.getRole());

        holder.image.setBorderColor(tournament.getLogo());
        holder.cb.setVisibility(View.VISIBLE);

        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked==true){
                posClick++;
                itemClickListener.checkedItem(posClick);
            }else{
                posClick--;
                itemClickListener.checkedItem(posClick);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public void clearData(){
        posClick =0;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTName;
        private TextView tvtLocation;
        private TextView tvtRole;

        private CircleImageView image;
        CheckBox cb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cb = itemView.findViewById(R.id.cb);
            tvTName = itemView.findViewById(R.id.tvTName);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            image = itemView.findViewById(R.id.image);
            tvtRole = itemView.findViewById(R.id.tvtRole);
        }
    }

    public interface itemClickListener{
        public void checkedItem(int pos);
    }
}
