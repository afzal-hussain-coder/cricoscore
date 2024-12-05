package com.cricoscore.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.model.TeamModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourTeamListAdapter extends RecyclerView.Adapter<YourTeamListAdapter.MyViewHolder> {

    private final Context mContext;
    private final itemClickListener itemClickListener;
    private final List<TeamModel> tList;
    private final ArrayList<Integer> arrayListUser;
    private final ArrayList<TeamModel> updatePlayer;

    public YourTeamListAdapter(Context mContext, List<TeamModel> tList, ArrayList<TeamModel> selectedPlayer, itemClickListener itemClickListener) {
        this.mContext = mContext;
        this.tList = tList;
        this.itemClickListener = itemClickListener;
        this.updatePlayer = selectedPlayer;
        this.arrayListUser = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_team_list_child, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TeamModel tournament = tList.get(position);

        // Set team name and location
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getCity());

        // Load the team logo
        if (tournament.getTeam_logo().isEmpty()) {
            Glide.with(mContext).load(R.drawable.placeholder_user).into(holder.image);
        } else {
            Glide.with(mContext).load(Global.BASE_URL + "/" + tournament.getTeam_logo()).into(holder.image);
        }

        // Determine if the item is already selected based on updatePlayer
        boolean isSelected = false;
        if (updatePlayer != null) {
            for (TeamModel selected : updatePlayer) {
                if (selected.getTeam_id() == tournament.getTeam_id()) {
                    isSelected = true;
                    break;
                }
            }
        }

        // Update CheckBox and background based on selection state
        holder.cb.setChecked(isSelected);
        holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, isSelected ? R.color.dark_grey : R.color.white));

        // If item was already selected, add it to arrayListUser to keep the state consistent
        if (isSelected && !arrayListUser.contains(tournament.getTeam_id())) {
            arrayListUser.add(tournament.getTeam_id());
        }

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            boolean isChecked = holder.cb.isChecked();

            // Toggle selection
            isChecked = !isChecked;
            holder.cb.setChecked(isChecked);

            // Update the selection list
            if (isChecked) {
                arrayListUser.add(tournament.getTeam_id());
                holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_grey));
            } else {
                arrayListUser.remove((Integer) tournament.getTeam_id()); // Avoid index removal
                holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            }

            // Notify the listener
            itemClickListener.checkedItem(arrayListUser);
        });

        // Handle remove button click
        holder.ivRemove.setOnClickListener(v -> {
            itemClickListener.removeTeam(position, tournament.getTeam_id());
        });
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
        arrayListUser.clear(); // Clear the selected items
        notifyDataSetChanged(); // Refresh the adapter
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;
        private final TextView tvTName;
        private final TextView tvtLocation;
        private final CircleImageView image;
        private final ImageView ivRemove;
        private final RelativeLayout rl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cb = itemView.findViewById(R.id.cb);
            this.tvTName = itemView.findViewById(R.id.tvTName);
            this.tvtLocation = itemView.findViewById(R.id.tvtLocation);
            this.image = itemView.findViewById(R.id.image);
            this.ivRemove = itemView.findViewById(R.id.ivRemove);
            this.rl = itemView.findViewById(R.id.rl);
        }
    }

    public interface itemClickListener {
        void checkedItem(ArrayList<Integer> arrayList);

        void removeTeam(int pos, int teamId);
    }
}
