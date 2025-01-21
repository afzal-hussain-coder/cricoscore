package com.cricoscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.BowlingLeaderboatrdModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BowlingLeaderboardAdapter extends RecyclerView.Adapter<BowlingLeaderboardAdapter.ViewHolder> {
    private final List<BowlingLeaderboatrdModel> itemList;
    Context mContext;

    public BowlingLeaderboardAdapter(Context mContext,List<BowlingLeaderboatrdModel> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bowling_leaderboard_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BowlingLeaderboatrdModel bowlingLeaderboatrdModel = itemList.get(position);
        holder.tvPoints.setText(position+1+"");
        Glide.with(mContext).load(Global.BASE_URL+bowlingLeaderboatrdModel.getAvatar()).into(holder.image);
        holder.tvPlayerName.setText(Global.capitalizeFirstLatterOfString(bowlingLeaderboatrdModel.getName()));

        holder.tvACount.setText(bowlingLeaderboatrdModel.getBowlingAverage()+"");
        holder.tvEcoCount.setText(bowlingLeaderboatrdModel.getEconomyRate()+"");
        holder.tvInningCount.setText(bowlingLeaderboatrdModel.getTotalInning()+"");
        holder.tvMeddianCount.setText(bowlingLeaderboatrdModel.getTotalMaidens()+"");
        holder.tvWicketCount.setText(bowlingLeaderboatrdModel.getTotalWicketsTaken()+"");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInningCount;
        TextView tvWicketCount;
        TextView tvEcoCount;
        TextView tvSrCount;
        TextView tvMeddianCount;
        CircleImageView image;
        TextView tvACount;
        TextView tvPlayerName;
        TextView tvPoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInningCount = itemView.findViewById(R.id.tvInningCount);
            tvWicketCount = itemView.findViewById(R.id.tvWicketCount);
            tvEcoCount = itemView.findViewById(R.id.tvEcoCount);
            tvSrCount = itemView.findViewById(R.id.tvSrCount);
            tvMeddianCount = itemView.findViewById(R.id.tvMeddianCount);
            image = itemView.findViewById(R.id.image);
            tvACount = itemView.findViewById(R.id.tvACount);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvPoints = itemView.findViewById(R.id.tvPoints);
        }

    }
}

