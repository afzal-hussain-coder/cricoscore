package com.cricoscore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.Activity.MatchDetailsActivity;
import com.cricoscore.Activity.ScheduleCricketDetailsActivity;
import com.cricoscore.Activity.ScheduleMatchActivity;
import com.cricoscore.Activity.ScheduleTournamentActivity;
import com.cricoscore.Activity.TeamListActivity;
import com.cricoscore.Activity.YourMatchTeamListActivity;
import com.cricoscore.Activity.YourPlayerListActivity;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.ScheduleMatchDetailsItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleMatchAdapter extends RecyclerView.Adapter<ScheduleMatchAdapter.MyHolder> {

    Context mContext;
    List<ScheduleMatchDetailsItem> tList = null;
    getImageCallListener getImageCallListener;

    public ScheduleMatchAdapter(Context mContext, List<ScheduleMatchDetailsItem> tList, getImageCallListener getImageCallListener) {
        this.mContext = mContext;
        this.tList = tList;
        this.getImageCallListener = getImageCallListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_tournament_child, null, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        ScheduleMatchDetailsItem tournament = tList.get(position);
        holder.tvTeamName.setText(tournament.getTeamAInfo().getName());
        holder.tvTName_teamTwo.setText(tournament.getTeamBInfo().getName());

        Glide.with(mContext).load(Global.BASE_URL + "/" + tournament.getTeamAInfo().getTeamLogo()).into(holder.img_first_team_logo);
        Glide.with(mContext).load(Global.BASE_URL + "/" + tournament.getTeamBInfo().getTeamLogo()).into(holder.img_teamTwo_logo);
        holder.tvtLocation.setText(tournament.getLocation() + " ," + tournament.getGround());
        holder.tvdate.setText(tournament.getStartDateTime());

        holder.tvWinner.setText(tournament.getMessage());

        if(tournament.getIs_match_completed()==1){
            holder.tvtLocation.setVisibility(View.GONE);
            holder.tvWinner.setVisibility(View.VISIBLE);
            holder.tvLiveStatus.setVisibility(View.VISIBLE);
            holder.tvLiveStatus.setText("Compleated");
            holder.itemView.setEnabled(false);
        }else{
            holder.itemView.setEnabled(true);
            if(tournament.getInning_no() > 0){
                holder.tvLiveStatus.setVisibility(View.VISIBLE);
            }else{
                holder.tvLiveStatus.setVisibility(View.GONE);
            }
        }



//        holder.mb_add_player_first.setOnClickListener(v -> {
//         getImageCallListener.addPlayer();
//        });
//
//        holder.mb_add_player2.setOnClickListener(v -> {
//            getImageCallListener.addPlayer();
//        });
//
//
//        holder.mb_select_playing_xi.setOnClickListener(v -> {
//            mContext.startActivity(new Intent(mContext, ScheduleCricketDetailsActivity.class)
//                    .putExtra("Name",tournament.getName()).putExtra("Address",tournament.getAddress())
//                    .putExtra("Date",tournament.getDate()));
//        });

        holder.itemView.setOnClickListener(v -> {
            getImageCallListener.getDetails(tournament.getScheduleMatchId(), tournament.getScheduleMatchId(), tournament.getTeamAInfo().getName(),
                    tournament.getTeamBInfo().getName(), tournament, tournament.getInning_no());
        });

    }


    @Override
    public int getItemCount() {
        return tList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private MaterialButton mb_add_player_first, mb_add_player2, mb_select_playing_xi;

        private TextView tvPlayerFirstCount, tvPlayerSecondCount;
        private TextView tvtLocation;
        private TextView tvdate;
        private TextView tvLiveStatus;
        private TextView tvTeamName, tvTName_teamTwo;
        private TextView tvWinner;


        private CircleImageView img_first_team_logo, img_teamTwo_logo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mb_add_player_first = itemView.findViewById(R.id.mb_add_player_first);
            mb_add_player2 = itemView.findViewById(R.id.mb_add_player2);
            mb_select_playing_xi = itemView.findViewById(R.id.mb_select_playing_xi);
            tvPlayerFirstCount = itemView.findViewById(R.id.tvPlayerFirstCount);
            tvPlayerSecondCount = itemView.findViewById(R.id.tvPlayerSecondCount);
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
        //  void addPlayer();
        void getDetails(int scheduleId, int id, String teamA, String teamB, ScheduleMatchDetailsItem jsonObject, int inningNo);
    }
}
