package com.cricoscore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.Activity.TeamDetailsActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.model.PlayerModel;
import com.cricoscore.model.TeamModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourPlayerListAdapterHorizontal extends RecyclerView.Adapter<YourPlayerListAdapterHorizontal.MyViewHolder> {

    Context mContext;
    itemClickListener itemClickListener;
    int posClick = 0;
    List<PlayerModel> tList;
    ArrayList<Integer> teamIdList;

    public YourPlayerListAdapterHorizontal(Context mContext, List<PlayerModel> tList, itemClickListener itemClickListener) {
        this.mContext = mContext;
        this.tList = tList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_team_list_child_card, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlayerModel tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());

        if(tournament.getAvatar().isEmpty()){
            Glide.with(mContext).load(R.drawable.placeholder_user).into(holder.image);
        }else{
            Glide.with(mContext).load(Global.BASE_URL+"/"+tournament.getAvatar()).into(holder.image);
        }
//
        holder.cb.setVisibility(View.GONE);
//
        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked==true){
                posClick++;
                itemClickListener.checkedItem(posClick,1);
            }else{
                posClick--;
                itemClickListener.checkedItem(posClick,1);
            }
        });

        holder.ivRemove.setOnClickListener(v -> {
            itemClickListener.removeItem(position,tournament.getPlayer_id());
        });


//        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
//
//            // Get the team ID based on the current position
//            Integer teamId = tList.get(position).getTeam_id();
//
//            if (isChecked) {
//                // Add team ID if not already present
//                if (!arrayListUser.contains(teamId)) {
//                    arrayListUser.add(teamId);
//                }
//            } else {
//                // Remove team ID if present
//                arrayListUser.remove(teamId);
//            }
//
//            // Notify the listener of the checked items
//            itemClickListener.checkedItem(arrayListUser);
//        });

//        holder.itemView.setOnClickListener(v -> {
//            mContext.startActivity(new Intent(mContext, TeamDetailsActivity.class).putExtra("Name"
//                    , tournament.getName()+"")
//            );
//        });

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public void deleteItem(int position) {
        tList.remove(position); // Remove item from the list
        notifyItemRemoved(position); // Notify adapter about the removed item
    }

    public void clearData() {
        posClick = 0;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;
        private TextView tvTName;
        private TextView tvtLocation;

        private CircleImageView image;
        ImageView ivRemove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cb = itemView.findViewById(R.id.cb);

            tvTName = itemView.findViewById(R.id.tvTName);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            image = itemView.findViewById(R.id.image);
            ivRemove = itemView.findViewById(R.id.ivRemove);
        }
    }

    public interface itemClickListener {
        void checkedItem(int pos, int teamId);
        void removeItem(int pos,int playerId);
    }
}
