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
import com.cricoscore.model.BattingPlayersResponse;

import java.util.ArrayList;

public class PlayingsquadAdapter extends RecyclerView.Adapter<PlayingsquadAdapter.ViewHolder> {

    private ArrayList<BattingPlayersResponse.Player> playerList;
    private OnPlayerSelectListener onPlayerSelectListener;
    private CheckBox currentCheckedCheckbox = null;
    private Context mContext;

    public PlayingsquadAdapter(Context mContext, ArrayList<BattingPlayersResponse.Player> playerList, OnPlayerSelectListener onPlayerSelectListener) {
        this.mContext = mContext;
        this.playerList = playerList;
        this.onPlayerSelectListener = onPlayerSelectListener;
    }

    @NonNull
    @Override
    public PlayingsquadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BattingPlayersResponse.Player player = playerList.get(position);
        holder.tvPlayerName.setText(player.getName());

        holder.cbPlayer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Uncheck the previous checkbox
                if (currentCheckedCheckbox != null && currentCheckedCheckbox != holder.cbPlayer) {
                    currentCheckedCheckbox.setChecked(false);
                }
                // Save the reference to the newly checked checkbox
                currentCheckedCheckbox = holder.cbPlayer;
                // Notify the listener that a player is selected
                onPlayerSelectListener.onSelect(player);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    // **Filter the players excluding both is_out = "0" and is_out = "1"**
    public void filterPlayers() {
        ArrayList<BattingPlayersResponse.Player> filteredList = new ArrayList<>();
        for (BattingPlayersResponse.Player player : playerList) {
            // Exclude players where is_out is "0" or "1"
            if (!"0".equals(player.getIs_out()) && !"1".equals(player.getIs_out())) {
                filteredList.add(player);
            }
        }
        playerList = filteredList;
        notifyDataSetChanged(); // Notify the adapter about the changes
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayerName;
        CheckBox cbPlayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            cbPlayer = itemView.findViewById(R.id.cbPlayer);
        }
    }

    public interface OnPlayerSelectListener {
        void onSelect(BattingPlayersResponse.Player player);
    }
}
