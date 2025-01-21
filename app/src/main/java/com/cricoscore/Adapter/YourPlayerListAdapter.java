package com.cricoscore.Adapter;

import android.animation.ValueAnimator;
import android.content.Context;
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
import com.cricoscore.model.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourPlayerListAdapter extends RecyclerView.Adapter<YourPlayerListAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<PlayerModel> tList;
    private final ArrayList<PlayerModel> addList;
    private final itemClickListener itemClickListener;

    public YourPlayerListAdapter(Context mContext, List<PlayerModel> tList, ArrayList<PlayerModel> selectedPlayers, itemClickListener itemClickListener) {
        this.mContext = mContext;
        this.tList = tList;
        this.itemClickListener = itemClickListener;
        this.addList = new ArrayList<>();

        // Initialize the selection state for each player
        if (selectedPlayers != null && !selectedPlayers.isEmpty()) {
            for (PlayerModel player : tList) {
                for (PlayerModel selectedPlayer : selectedPlayers) {
                    if (player.getPlayer_id() == selectedPlayer.getPlayer_id()) {
                        player.setSelected(true); // Mark as selected
                        addList.add(player);
                        break;
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_player_list_child_demo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlayerModel player = tList.get(position);

        // Set player name and avatar
        holder.tvTName.setText(Global.capitalizeFirstLatterOfString(player.getName()));
        if (player.getAvatar().isEmpty()) {
            Glide.with(mContext).load(R.drawable.placeholder_user).into(holder.image);
        } else {
            Glide.with(mContext).load(Global.BASE_URL + "/" + player.getAvatar()).into(holder.image);
        }

        // Set UI state based on the selection
        if (player.isSelected()) {
            holder.cb.setChecked(true);
            holder.rl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_border_background));
            //fadeBackground(holder.rl, 1.0f); // Fully visible
            //fadeInIcon(holder.rlChcek);
            holder.rlChcek.setVisibility(View.VISIBLE);
        } else {
            holder.cb.setChecked(false);
            holder.rl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.white_border_background));
            //fadeBackground(holder.rl, 0.6f); // Semi-transparent
            //fadeOutIcon(holder.rlChcek);
            holder.rlChcek.setVisibility(View.GONE);
        }

        // Handle item click to toggle selection
        holder.itemView.setOnClickListener(v -> {
            // Toggle the selection state
            boolean isSelected = !player.isSelected();
            player.setSelected(isSelected);

            if (isSelected) {
                addList.add(player);
            } else {
                addList.remove(player);
            }

            // Notify the listener about the updated selection
            itemClickListener.checkedItem(addList);

            // Update the UI for this item
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTName;
        private final CircleImageView image;
        private final RelativeLayout rl;
        private final CheckBox cb;
        private final RelativeLayout rlChcek;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cb = itemView.findViewById(R.id.cb);
            this.tvTName = itemView.findViewById(R.id.tvTName);
            this.image = itemView.findViewById(R.id.image);
            this.rl = itemView.findViewById(R.id.rl);
            this.rlChcek = itemView.findViewById(R.id.rlChcek); // "OK" icon above the profile
        }
    }

    public interface itemClickListener {
        void checkedItem(ArrayList<PlayerModel> playerModelArrayList);
    }

//    /**
//     * Applies a fading effect to the background of the view.
//     *
//     * @param view The view to apply the fading effect to.
//     * @param alpha The target alpha value (e.g., 0.6f for semi-transparent).
//     */
//    private void fadeBackground(View view, float alpha) {
//        if (view != null) {
//            ValueAnimator animator = ValueAnimator.ofFloat(view.getAlpha(), alpha);
//            animator.setDuration(300); // Duration in milliseconds
//            animator.addUpdateListener(animation -> view.setAlpha((float) animation.getAnimatedValue()));
//            animator.start();
//        }
//    }
//
//    /**
//     * Fades in the ok_icon.
//     */
//    private void fadeInIcon(View view) {
//        if (view != null) {
//            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
//            animator.setDuration(300); // Duration in milliseconds
//            animator.addUpdateListener(animation -> view.setAlpha((float) animation.getAnimatedValue()));
//            animator.start();
//        }
//    }
//
//    /**
//     * Fades out the ok_icon.
//     */
//    private void fadeOutIcon(View view) {
//        if (view != null) {
//            ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
//            animator.setDuration(300); // Duration in milliseconds
//            animator.addUpdateListener(animation -> view.setAlpha((float) animation.getAnimatedValue()));
//            animator.start();
//        }
//    }
}


//
//package com.cricoscore.Adapter;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.cricoscore.R;
//import com.cricoscore.Utils.Global;
//import com.cricoscore.model.PlayerModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class YourPlayerListAdapter extends RecyclerView.Adapter<YourPlayerListAdapter.MyViewHolder> {
//
//    private final Context mContext;
//    private final List<PlayerModel> tList;
//    private final ArrayList<PlayerModel> addList;
//    private final itemClickListener itemClickListener;
//
//    public YourPlayerListAdapter(Context mContext, List<PlayerModel> tList, ArrayList<PlayerModel> selectedPlayers, itemClickListener itemClickListener) {
//        this.mContext = mContext;
//        this.tList = tList;
//        this.itemClickListener = itemClickListener;
//        this.addList = new ArrayList<>();
//
//        // Initialize the selection state for each player
//        if (selectedPlayers != null && !selectedPlayers.isEmpty()) {
//            for (PlayerModel player : tList) {
//                for (PlayerModel selectedPlayer : selectedPlayers) {
//                    if (player.getPlayer_id() == selectedPlayer.getPlayer_id()) {
//                        player.setSelected(true); // Mark as selected
//                        addList.add(player);
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.your_player_list_child, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        PlayerModel player = tList.get(position);
//
//        // Set player name and avatar
//        holder.tvTName.setText(player.getName());
//        if (player.getAvatar().isEmpty()) {
//            Glide.with(mContext).load(R.drawable.placeholder_user).into(holder.image);
//        } else {
//            Glide.with(mContext).load(Global.BASE_URL + "/" + player.getAvatar()).into(holder.image);
//        }
//
//        // Set UI state based on the selection
//        if (player.isSelected()) {
//            holder.cb.setChecked(true);
//            holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_grey));
//        } else {
//            holder.cb.setChecked(false);
//            holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
//        }
//
//        // Handle item click to toggle selection
//        holder.itemView.setOnClickListener(v -> {
//            // Toggle the selection state
//            boolean isSelected = !player.isSelected();
//            player.setSelected(isSelected);
//
//            if (isSelected) {
//                addList.add(player);
//            } else {
//                addList.remove(player);
//            }
//
//            // Notify the listener about the updated selection
//            itemClickListener.checkedItem(addList);
//
//            // Update the UI for this item
//            notifyItemChanged(position);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return tList.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        private final TextView tvTName;
//        private final CircleImageView image;
//        private final RelativeLayout rl;
//        private final CheckBox cb;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.cb = itemView.findViewById(R.id.cb);
//            this.tvTName = itemView.findViewById(R.id.tvTName);
//            this.image = itemView.findViewById(R.id.image);
//            this.rl = itemView.findViewById(R.id.rl);
//        }
//    }
//
//    public interface itemClickListener {
//        void checkedItem(ArrayList<PlayerModel> playerModelArrayList);
//    }
//}
