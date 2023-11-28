package com.cricoscore.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.cricoscore.Activity.ScheduleTournamentActivity;
import com.cricoscore.Activity.TeamListActivity;
import com.cricoscore.Activity.YourMatchTeamListActivity;
import com.cricoscore.Fragment.MatchFragment;
import com.cricoscore.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleTournamentAdapter extends RecyclerView.Adapter<ScheduleTournamentAdapter.MyHolder>{

    Context mContext;
    int value,value2;
    List<ScheduleTournamentActivity.Match> tList = new ArrayList<>();
    public ScheduleTournamentAdapter(Context mContext, List<ScheduleTournamentActivity.Match> tList){
        this.mContext = mContext;
        this.tList = tList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schedule_tournament_child,null,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        ScheduleTournamentActivity.Match tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getAddress());

        holder.tvdate.setText(tournament.getDate());

         AtomicInteger count= new AtomicInteger();
        holder.mb_add_player_first.setOnClickListener(v -> {
            if(holder.rl_player_add.getVisibility() == View.VISIBLE){
                holder.mb_add_player_first.setText("");
                holder.mb_add_player_first.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.purple_700));
                holder.mb_add_player_first.setCompoundDrawablesWithIntrinsicBounds(R.drawable.add_black_24dp, 0, R.drawable.player_icon_small, 0);
                holder.rl_player_add.setVisibility(View.GONE);
                value = count.getAndIncrement();
                holder.tvPlayerFirstCount.setText(String.valueOf(value));
                holder.edit_text_pMobile.setText("");
                holder.edit_text_pName.setText("");
                if(value==11){
                    holder.mb_add_player_first.setVisibility(View.GONE);
                    count.set(0);
                }else{
                    holder.mb_add_player_first.setVisibility(View.VISIBLE);
                }

            }else{
                holder.mb_add_player_first.setCompoundDrawablesWithIntrinsicBounds(R.drawable.done_black_24dp, 0, R.drawable.player_icon_small, 0);
                holder.mb_add_player_first.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.green));

                holder.rl_player_add.setVisibility(View.VISIBLE);
            }
        });

//            if(value==11){
//                holder.mb_add_player_first.setVisibility(View.GONE);
//            }else{
//                holder.mb_add_player_first.setVisibility(View.VISIBLE);
//            }


        holder.mb_add_player2.setOnClickListener(v -> {
            if(holder.rl_player_add.getVisibility() == View.VISIBLE){
                holder.mb_add_player2.setText("");
                holder.mb_add_player2.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.purple_700));
                holder.mb_add_player2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.add_black_24dp, 0, R.drawable.player_icon_small, 0);
                holder.rl_player_add.setVisibility(View.GONE);
                value2 = count.getAndIncrement();
                holder.tvPlayerSecondCount.setText(String.valueOf(value2));
                holder.edit_text_pMobile.setText("");
                holder.edit_text_pName.setText("");
                if(value2==11){
                    holder.mb_add_player2.setVisibility(View.GONE);
                }else{
                    holder.mb_add_player2.setVisibility(View.VISIBLE);
                }
            }else{
                holder.mb_add_player2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.done_black_24dp, 0, R.drawable.player_icon_small, 0);
                holder.mb_add_player2.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.green));

                holder.rl_player_add.setVisibility(View.VISIBLE);
            }
        });

//        if(value2==11){
//            holder.mb_add_player2.setVisibility(View.GONE);
//        }else{
//            holder.mb_add_player2.setVisibility(View.VISIBLE);
//        }



        holder.rl_select_player.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, YourMatchTeamListActivity.class));
        });
//
//
        holder.mb_start_tournament.setOnClickListener(v -> {
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
        private RelativeLayout rl_player_add,rl_select_player;
        private MaterialButton mb_add_player_first,mb_add_player2,mb_start_tournament;
        private TextInputEditText edit_text_pName;
        private TextInputEditText edit_text_pMobile;

        private TextView tvPlayerFirstCount,tvPlayerSecondCount;
        private TextView tvtLocation;
        private TextView tvdate;
        private TextView tvtime;
        private TextView tvTName;


        private CircleImageView img_first_team_logo,img_teamTwo_logo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            rl_player_add = itemView.findViewById(R.id.rl_player_add);
            mb_add_player_first = itemView.findViewById(R.id.mb_add_player_first);
            mb_add_player2 = itemView.findViewById(R.id.mb_add_player2);
            mb_start_tournament = itemView.findViewById(R.id.mb_start_tournament);
            rl_select_player = itemView.findViewById(R.id.rl_select_player);
            edit_text_pName = itemView.findViewById(R.id.edit_text_pName);
            edit_text_pMobile = itemView.findViewById(R.id.edit_text_pMobile);
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
}
