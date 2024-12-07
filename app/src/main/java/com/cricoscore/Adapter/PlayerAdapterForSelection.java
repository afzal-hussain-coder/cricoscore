package com.cricoscore.Adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.cricoscore.model.Player;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerAdapterForSelection extends RecyclerView.Adapter<PlayerAdapterForSelection.PlayerViewHolder> {

    private final ArrayList<Player> players;
    private final OnPlayerSelectListener listener;
    private int selectedPlayerId; // ID of the currently selected player
    private List<Integer> disabledPlayerIds; // List of disabled player IDs
    Context mContext;

    public PlayerAdapterForSelection(Context mContext,ArrayList<Player> players, OnPlayerSelectListener listener, int selectedPlayerId, List<Integer> disabledPlayerIds) {
        this.mContext =mContext;
        this.players = players;
        this.listener = listener;
        this.selectedPlayerId = selectedPlayerId;
        this.disabledPlayerIds = disabledPlayerIds;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_selcttion, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.tvPlayerName.setText(player.getName());
        if (player.getAvatar().isEmpty()) {
            Glide.with(mContext).load(R.drawable.placeholder_user).into(holder.image);
        } else {
            Glide.with(mContext).load(Global.BASE_URL + "/" + player.getAvatar()).into(holder.image);
        }

        // Check if this player is disabled
        boolean isDisabled = disabledPlayerIds != null && disabledPlayerIds.contains(player.getPlayerId());

        // Highlight the selected player
        if (player.getPlayerId() == selectedPlayerId) {
            holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_grey));
            //holder.itemView.setBackgroundColor(Color.LTGRAY); // Highlight the selected player
        } else if (isDisabled) {
            holder.tvPlayerName.setTextColor(Color.GRAY); // Disabled player text
            holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.black_light_tranparent));
           // holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        } else {
            holder.tvPlayerName.setTextColor(Color.BLACK); // Default text color
            holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

        // Handle click events
        holder.itemView.setOnClickListener(v -> {
            if (isDisabled) {
                // Do nothing if the player is disabled
                return;
            }
            selectedPlayerId = player.getPlayerId(); // Update the selected player ID
            notifyDataSetChanged(); // Refresh the adapter to show updated selection
            listener.onSelect(player); // Notify listener
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public interface OnPlayerSelectListener {
        void onSelect(Player player);
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayerName;
        CircleImageView image;
        RelativeLayout rl;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            image = itemView.findViewById(R.id.image);
            this.rl = itemView.findViewById(R.id.rl);
        }
    }
}




