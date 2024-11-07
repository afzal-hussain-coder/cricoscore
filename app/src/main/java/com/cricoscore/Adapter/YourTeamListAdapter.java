package com.cricoscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.model.TeamModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourTeamListAdapter extends RecyclerView.Adapter<YourTeamListAdapter.MyViewHolder> {

    Context mContext;
    itemClickListener itemClickListener;
    int posClick = 0;
    List<TeamModel> tList;
    ArrayList<Integer> arrayListUser;

    public YourTeamListAdapter(Context mContext, List<TeamModel> tList, itemClickListener itemClickListener) {
        this.mContext = mContext;
        this.tList = tList;
        this.itemClickListener = itemClickListener;

        arrayListUser = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_team_list_child, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TeamModel tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getCity());

        if (tournament.getTeam_logo().isEmpty()) {
            Glide.with(mContext).load(R.drawable.placeholder_user).into(holder.image);
        } else {
            Glide.with(mContext).load(Global.BASE_URL + "/" + tournament.getTeam_logo()).into(holder.image);
        }


        holder.cb.setVisibility(View.VISIBLE);

        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {

            // Get the team ID based on the current position
            Integer teamId = tList.get(position).getTeam_id();

            if (isChecked) {
                // Add team ID if not already present
                if (!arrayListUser.contains(teamId)) {
                    arrayListUser.add(teamId);
                }
            } else {
                // Remove team ID if present
                arrayListUser.remove(teamId);
            }

            // Notify the listener of the checked items
            itemClickListener.checkedItem(arrayListUser);
        });

//        holder.itemView.setOnClickListener(v -> {
//            boolean isChecked = holder.circleCheckBox.isChecked();
//
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
//
//            // Handle the checkbox state
//        });

        holder.itemView.setOnClickListener(v -> {

            boolean isChecked = holder.cb.isChecked();

            // Toggle the checked state
            holder.cb.setChecked(!isChecked);
            isChecked = !isChecked; // Update the local variable

            // Get the team ID based on the current position
            Integer teamId = tList.get(position).getTeam_id();

            if (isChecked) {
                // Add team ID if not already present
                if (!arrayListUser.contains(teamId)) {
                    arrayListUser.add(teamId);
                }
            } else {
                // Remove team ID if present
                arrayListUser.remove(teamId);
            }

//            holder.itemView.setBackgroundColor(holder.cb.isSelected() ? mContext.getResources().getColor(R.color.purple_500) :
//                    mContext.getResources().getColor(R.color.white));

            // Notify the listener of the checked items
            itemClickListener.checkedItem(arrayListUser);

        });


        holder.ivRemove.setOnClickListener(v -> {
            itemClickListener.removeTeam(position, tournament.getTeam_id());
        });

        // Make sure to set the listener to null before re-binding to avoid duplicates
        // holder.cb.setOnCheckedChangeListener(null);
        // holder.cb.setChecked(arrayListUser.contains(tList.get(position).getTeam_id()));

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
        void checkedItem(ArrayList<Integer> arrylist);

        void removeTeam(int pos, int teamId);
    }
}
