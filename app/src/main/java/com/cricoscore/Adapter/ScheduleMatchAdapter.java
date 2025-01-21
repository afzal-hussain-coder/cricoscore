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
import com.cricoscore.model.ScheduleMatchDetailsItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleMatchAdapter extends RecyclerView.Adapter<ScheduleMatchAdapter.MyHolder> {

    Context mContext;
    List<ScheduleMatchDetailsItem> tList;
    getImageCallListener getImageCallListener;

    public ScheduleMatchAdapter(Context mContext, List<ScheduleMatchDetailsItem> tList, getImageCallListener getImageCallListener) {
        this.mContext = mContext;
        this.tList = tList;
        this.getImageCallListener = getImageCallListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_tournament_child, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ScheduleMatchDetailsItem tournament = tList.get(position);

        // Reset views to default state to avoid incorrect data due to recycling
        holder.tvtLocation.setVisibility(View.VISIBLE);
        holder.tvWinner.setVisibility(View.GONE);
        holder.tvLiveStatus.setVisibility(View.GONE);
        holder.itemView.setEnabled(true);

        // Bind data to views
        holder.tvTeamName.setText(Global.capitalizeFirstLatterOfString(tournament.getTeamAInfo().getName()));
        holder.tvTName_teamTwo.setText(Global.capitalizeFirstLatterOfString(tournament.getTeamBInfo().getName()));

        Glide.with(mContext)
                .load(Global.BASE_URL + "/" + tournament.getTeamAInfo().getTeamLogo())
                .into(holder.img_first_team_logo);
        Glide.with(mContext)
                .load(Global.BASE_URL + "/" + tournament.getTeamBInfo().getTeamLogo())
                .into(holder.img_teamTwo_logo);

        holder.tvtLocation.setText(tournament.getLocation() + " ," + tournament.getGround());
        holder.tvdate.setText(tournament.getStartDateTime());
        holder.tvWinner.setText(tournament.getMessage());

        // Handle match completion state
        if (tournament.getIs_match_completed() == 1) {
            holder.tvtLocation.setVisibility(View.GONE);
            holder.tvWinner.setVisibility(View.VISIBLE);
            holder.tvLiveStatus.setVisibility(View.VISIBLE);
            holder.tvLiveStatus.setText("Completed");
            holder.itemView.setEnabled(false);
        } else {
            holder.itemView.setEnabled(true);
            if (tournament.getInning_no() > 0) {
                holder.tvLiveStatus.setVisibility(View.VISIBLE);
                holder.tvLiveStatus.setText("Live");
            } else {
                holder.tvLiveStatus.setVisibility(View.GONE);
            }
        }

        // Item click listener
        holder.itemView.setOnClickListener(v -> getImageCallListener.getDetails(
                tournament.getScheduleMatchId(),
                tournament.getScheduleMatchId(),
                tournament.getTeamAInfo().getName(),
                tournament.getTeamBInfo().getName(),
                tournament,
                tournament.getInning_no()
        ));
    }

    @Override
    public int getItemCount() {
        return tList != null ? tList.size() : 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvtLocation, tvdate, tvLiveStatus, tvTeamName, tvTName_teamTwo, tvWinner;
        private CircleImageView img_first_team_logo, img_teamTwo_logo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvLiveStatus = itemView.findViewById(R.id.tvLiveStatus);
            tvTeamName = itemView.findViewById(R.id.tvTeamName);
            tvTName_teamTwo = itemView.findViewById(R.id.tvTName_teamTwo);
            img_first_team_logo = itemView.findViewById(R.id.img_first_team_logo);
            img_teamTwo_logo = itemView.findViewById(R.id.img_teamTwo_logo);
            tvWinner = itemView.findViewById(R.id.tvWinner);
        }
    }

    public interface getImageCallListener {
        void getDetails(int scheduleId, int id, String teamA, String teamB, ScheduleMatchDetailsItem jsonObject, int inningNo);
    }
}
