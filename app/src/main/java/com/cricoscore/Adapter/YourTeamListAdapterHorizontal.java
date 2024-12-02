package com.cricoscore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.Activity.TeamDetailsActivity;
import com.cricoscore.Activity.TournamentDetailsActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.TeamModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourTeamListAdapterHorizontal extends RecyclerView.Adapter<YourTeamListAdapterHorizontal.MyViewHolder> {

    Context mContext;
    itemClickListener itemClickListener;
    int posClick = 0;
    List<TeamModel> tList;
    List<TeamModel> teamIdList;

    public YourTeamListAdapterHorizontal(Context mContext, List<TeamModel> tList, itemClickListener itemClickListener) {
        this.mContext = mContext;
        this.tList = tList;
        this.itemClickListener = itemClickListener;

        teamIdList =  new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_team_list_child_card, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TeamModel tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getCity());

        if(tournament.getTeam_logo().isEmpty()){
            Glide.with(mContext).load(R.drawable.placeholder_user).into(holder.image);
        }else{
            Glide.with(mContext).load(Global.BASE_URL+"/"+tournament.getTeam_logo()).into(holder.image);
        }

        holder.cb.setVisibility(View.VISIBLE);


        // Check if the checkbox is checked based on some saved state
        // Assuming this is inside the Adapter where you handle the checkbox state change
        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Check if there are already two selected items
                if (posClick < 2) {
                    // If there are less than two items selected, increase the counter
                    posClick++;
                    teamIdList.add(tournament);
                    itemClickListener.checkedItem(posClick, teamIdList);
                } else {
                    // If there are already two selected, uncheck the current checkbox
                    buttonView.setChecked(false);
                    // Show a message that only two teams can be selected
                    Toast.makeText(mContext, "You can only select two items", Toast.LENGTH_SHORT).show();
                }
            } else {
                // If the checkbox is unchecked, decrease the counter and notify the activity
                posClick--;
                teamIdList.remove(tournament);
                itemClickListener.checkedItem(posClick, teamIdList);
            }
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

        holder.ivRemove.setOnClickListener(v -> {
            itemClickListener.itemRemove(tournament.team_id,position);
        });

        holder.itemView.setOnClickListener(v -> {



            mContext.startActivity(new Intent(mContext, TeamDetailsActivity.class).putExtra("ID"
                    , tournament.getTeam_id()+"")
            );
        });

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < tList.size()) {
            tList.remove(position); // Remove the item from the list

            // Notify the adapter that an item was removed
            //notifyItemRemoved(position);
            notifyDataSetChanged();

            // Optionally, notify the adapter that the data set has changed (if needed)
            // This might help in edge cases, but usually not necessary if notifyItemRemoved is used.
            // notifyDataSetChanged(); // Uncomment this if necessary
        } // Notify adapter about the removed item
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
        void checkedItem(int pos, List<TeamModel> teamIdList);
        void itemRemove(int id ,int pos);
    }
}
