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
import com.cricoscore.model.BattingPlayersResponse;
import com.cricoscore.model.Player;

import java.util.ArrayList;

public class PlayingsquadAdapter extends RecyclerView.Adapter<PlayingsquadAdapter.ViewHolder>{

    private ArrayList<BattingPlayersResponse.Player> playerList;
    private OnPlayerSelectListener onPlayerSelectListener;
    private CheckBox currentCheckedCheckbox = null;
    Context mContext;

    public PlayingsquadAdapter(Context mContext,ArrayList<BattingPlayersResponse.Player> playerList,OnPlayerSelectListener onPlayerSelectListener){
        this.mContext = mContext;
        this.playerList = playerList;
        this.onPlayerSelectListener = onPlayerSelectListener;

        //Toaster.customToast(playerList.size()+"/Size");
    }

    @NonNull
    @Override
    public PlayingsquadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new PlayingsquadAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayingsquadAdapter.ViewHolder holder, int position) {
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
