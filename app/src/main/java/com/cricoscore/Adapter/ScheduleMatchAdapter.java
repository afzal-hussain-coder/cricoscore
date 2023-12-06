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
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.Toaster;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleMatchAdapter extends RecyclerView.Adapter<ScheduleMatchAdapter.MyHolder>{

    Context mContext;
    List<ScheduleMatchActivity.Match> tList =null;
    getImageCallListener  getImageCallListener;

    public ScheduleMatchAdapter(Context mContext, List<ScheduleMatchActivity.Match> tList,getImageCallListener  getImageCallListener){
        this.mContext = mContext;
        this.tList = tList;
        this.getImageCallListener = getImageCallListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_tournament_child,null,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        ScheduleMatchActivity.Match tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getAddress());

        holder.mb_add_player_first.setOnClickListener(v -> {
         getImageCallListener.addPlayer();
        });

        holder.mb_add_player2.setOnClickListener(v -> {
            getImageCallListener.addPlayer();
        });


        holder.mb_select_playing_xi.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ScheduleCricketDetailsActivity.class)
                    .putExtra("Name",tournament.getName()).putExtra("Address",tournament.getAddress())
                    .putExtra("Date",tournament.getDate()));
        });

    }




    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        private MaterialButton mb_add_player_first,mb_add_player2,mb_select_playing_xi;

        private TextView tvPlayerFirstCount,tvPlayerSecondCount;
        private TextView tvtLocation;
        private TextView tvdate;
        private TextView tvtime;
        private TextView tvTName;


        private CircleImageView img_first_team_logo,img_teamTwo_logo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mb_add_player_first = itemView.findViewById(R.id.mb_add_player_first);
            mb_add_player2 = itemView.findViewById(R.id.mb_add_player2);
            mb_select_playing_xi = itemView.findViewById(R.id.mb_select_playing_xi);
            tvPlayerFirstCount = itemView.findViewById(R.id.tvPlayerFirstCount);
            tvPlayerSecondCount = itemView.findViewById(R.id.tvPlayerSecondCount);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvtime = itemView.findViewById(R.id.tvtime);
            tvTName = itemView.findViewById(R.id.tvTName);
            img_first_team_logo = itemView.findViewById(R.id.img_first_team_logo);
            img_teamTwo_logo = itemView.findViewById(R.id.img_teamTwo_logo);

        }
    }

    public interface getImageCallListener{
        void addPlayer();
    }
}
