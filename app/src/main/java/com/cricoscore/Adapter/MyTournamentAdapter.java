package com.cricoscore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.Activity.TeamListActivity;
import com.cricoscore.ApiResponse.Tournament;
import com.cricoscore.R;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTournamentAdapter extends RecyclerView.Adapter<MyTournamentAdapter.MyHolder> {

    Context mContext;
    int value;
    List<Tournament> tList;
    getImageCallListener getImageCallListener;

    public MyTournamentAdapter(Context mContext, getImageCallListener getImageCallListener) {
        this.mContext = mContext;
        //this.tList = tList;
        this.getImageCallListener = getImageCallListener;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tournament_child, null, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

//        Tournament _tournamentdata = tList.get(position);
//        holder.tvTName.setText(_tournamentdata.getName());
//        holder.tvtLocation.setText(_tournamentdata.getLocation());
//
//
//        Glide.with(mContext).load(_tournamentdata.getTournamentLogo())
//                .into(holder.image);
//
//        Glide.with(mContext).load(_tournamentdata.getTournamentBanner())
//                .into(holder.img_banner);
//
//
//        holder.tvdate.setText(_tournamentdata.getStartDate());
//
        holder.mb_add_team.setOnClickListener(v -> {
            getImageCallListener.addTeamLogo();
        });
//        holder.li_teamCount.setOnClickListener(v -> {
//            if(value<=5){
//                mContext.startActivity(new Intent(mContext, TeamListActivity.class).putExtra("Count",value));
//            }else{
//                mContext.startActivity(new Intent(mContext, TeamListActivity.class).putExtra("Count",5));
//            }
//
//        });


//        holder.itemView.setOnClickListener(v -> {
//            mContext.startActivity(new Intent(mContext, TournamentDetailsActivity.class).putExtra("color"
//                            ,tournament.getBanner())
//                    .putExtra("Name",tournament.getName()).putExtra("Address",tournament.getAddress())
//                    .putExtra("Date",tournament.getDate()));
//        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private MaterialButton mb_add_team;
        private TextView tvtNumberOfTeam;
        private LinearLayout li_teamCount;

        private TextView tvTName;
        private TextView tvtLocation;
        private TextView tvdate;

        private RoundedImageView img_banner;
        private CircleImageView image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mb_add_team = itemView.findViewById(R.id.mb_add_team);
            tvtNumberOfTeam = itemView.findViewById(R.id.tvtNumberOfTeam);
            li_teamCount = itemView.findViewById(R.id.li_teamCount);
            tvTName = itemView.findViewById(R.id.tvTName);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            tvdate = itemView.findViewById(R.id.tvdate);

            img_banner = itemView.findViewById(R.id.img_banner);
            image = itemView.findViewById(R.id.image);
        }
    }

    public interface getImageCallListener {
        void addTeamLogo();
    }
}
