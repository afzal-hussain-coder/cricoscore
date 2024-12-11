package com.cricoscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.R;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.BattingPlayersResponse;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayingsquadAdapter extends RecyclerView.Adapter<PlayingsquadAdapter.ViewHolder> {

    private ArrayList<BattingPlayersResponse.Player> playerList;
    private OnPlayerSelectListener onPlayerSelectListener;
    private CheckBox currentCheckedCheckbox = null;
    private Context mContext;
    private int selectedPosition = RecyclerView.NO_POSITION;
    int currentBowlerId=0;

    public PlayingsquadAdapter(Context mContext,int currentBowlerId, ArrayList<BattingPlayersResponse.Player> playerList, OnPlayerSelectListener onPlayerSelectListener) {
        this.mContext = mContext;
        this.currentBowlerId = currentBowlerId;
        this.playerList = playerList;
        this.onPlayerSelectListener = onPlayerSelectListener;

      // Toaster.customToast("Size"+playerList.size());
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

        // Disable selection if the player's ID matches the current bowler ID

       if (player.getPlayer_id() == currentBowlerId) {

           if(player.getIs_over_completed()==1){
               //holder.rl.setEnabled(false);
               holder.tvStatus.setVisibility(View.VISIBLE);
               holder.tvStatus.setText("Over Limit Exceeded");
           }

           // holder.tvStatus.setVisibility(View.GONE);
            holder.rl.setEnabled(false);
            holder.rl.setBackgroundColor(mContext.getResources().getColor(R.color.light_gray));
           // holder.rl.setVisibility(View.GONE);// Disabled color
        } else {
           if(player.getIs_over_completed()==1){
               //holder.rl.setEnabled(false);
               holder.tvStatus.setVisibility(View.VISIBLE);
               holder.tvStatus.setText("Over Limit Exceeded");
           }
           // holder.tvStatus.setVisibility(View.GONE);
           // holder.rl.setVisibility(View.VISIBLE);
            holder.rl.setEnabled(true);
            // Set the background based on the selection
            if (position == selectedPosition) {
                holder.rl.setBackgroundColor(mContext.getResources().getColor(R.color.dark_grey)); // Selected color
            } else {
                holder.rl.setBackgroundColor(mContext.getResources().getColor(R.color.white)); // Default color
            }
        }

        // Handle itemView click to change the background color and notify the listener
        holder.itemView.setOnClickListener(v -> {
            if (player.getPlayer_id() != currentBowlerId) { // Ensure it's not disabled
                // Update selected position
                int previousPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();

                // Notify changes for the old and new selected positions
                notifyItemChanged(previousPosition);
                notifyItemChanged(selectedPosition);

                // Notify the listener about the selected player
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
        CircleImageView image;
        RelativeLayout rl;
        TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            image = itemView.findViewById(R.id.image);
            this.rl = itemView.findViewById(R.id.rl);
            this.tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

    public interface OnPlayerSelectListener {
        void onSelect(BattingPlayersResponse.Player player);
    }
}
