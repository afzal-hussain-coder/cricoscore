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

import com.cricoscore.Activity.TeamListActivity;
import com.cricoscore.Activity.TeamPlayerActivity;
import com.cricoscore.Activity.YourPlayerListActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.Fragment.MatchFragment;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectTournamentType;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.MyViewHolder>{

    Context mContext;
    private ArrayList<DataModel> option_player_type = new ArrayList<>();
    private ArrayList<DataModel> option_player_role = new ArrayList<>();
    List<TeamListActivity.Team> tList = new ArrayList<>();
    int value;
    int size;
    public TeamListAdapter(Context mContext,int size,List<TeamListActivity.Team> tList){
        this.mContext = mContext;
        this.size = size;
        this.tList = tList;

        option_player_type.add(new DataModel("Bowler"));
        option_player_type.add(new DataModel("Batsman"));
        option_player_type.add(new DataModel("Wicketkeeper"));
        option_player_type.add(new DataModel("All Rounder"));

        option_player_role.add(new DataModel("Captain"));
        option_player_role.add(new DataModel("Vice Captain"));
        option_player_role.add(new DataModel("Coach"));
        option_player_role.add(new DataModel("Player"));
        option_player_role.add(new DataModel("Commentator"));
        option_player_role.add(new DataModel("Manager"));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.team_list_child, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TeamListActivity.Team tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getAddress());

        holder.image.setBorderColor(tournament.getLogo());


        AtomicInteger count= new AtomicInteger();
        holder.mb_add_player.setOnClickListener(v -> {
            if(holder.rl_player_add.getVisibility() == View.VISIBLE){
                holder.mb_add_player.setText(mContext.getResources().getString(R.string.player));
                holder.mb_add_player.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.purple_700));
                holder.rl_player_add.setVisibility(View.GONE);
                value = count.getAndIncrement();
                holder.tvtNumberOfPlayer.setText("# "+value);
                holder.edit_text_pMobile.setText("");
                holder.edit_text_pName.setText("");
                holder.drop_pRole.setText("");
                holder.drop_pType.setText("");

            }else{
                holder.mb_add_player.setText(mContext.getResources().getString(R.string.submit));
                holder.mb_add_player.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.green));

                holder.rl_player_add.setVisibility(View.VISIBLE);
            }
            if(holder.tvtNumberOfPlayer.getText().toString().equalsIgnoreCase("# 0")){
                holder.li_PlayerCount.setVisibility(View.GONE);
            }else{
                holder.li_PlayerCount.setVisibility(View.VISIBLE);
            }
        });
        holder.li_PlayerCount.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, TeamPlayerActivity.class).putExtra("Count",value));
        });



        holder.drop_pType.setOptionList(option_player_type);
        holder.drop_pType.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {

            }


            @Override
            public void onDismiss() {
            }
        });


        holder.drop_pRole.setOptionList(option_player_role);
        holder.drop_pRole.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {

            }


            @Override
            public void onDismiss() {
            }
        });

        holder.tv_select_player_from_list.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, YourPlayerListActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return size>0? size: tList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
       private MaterialButton mb_add_player;
       private RelativeLayout rl_player_add;
        private TextView tvtNumberOfPlayer;
        private LinearLayout li_PlayerCount;
        private TextView tv_select_player_from_list;
        private SelectTournamentType drop_pType;
        private SelectTournamentType drop_pRole;
        private TextInputEditText edit_text_pName;
        private TextInputEditText edit_text_pMobile;
        private TextView tvTName;
        private TextView tvtLocation;

        private CircleImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mb_add_player = itemView.findViewById(R.id.mb_add_player);
            rl_player_add = itemView.findViewById(R.id.rl_player_add);
            tvtNumberOfPlayer = itemView.findViewById(R.id.tvtNumberOfPlayer);
            li_PlayerCount = itemView.findViewById(R.id.li_PlayerCount);
            tv_select_player_from_list = itemView.findViewById(R.id.tv_select_player_from_list);
            drop_pType = itemView.findViewById(R.id.drop_pType);
            drop_pRole = itemView.findViewById(R.id.drop_pRole);
            edit_text_pName = itemView.findViewById(R.id.edit_text_pName);
            edit_text_pMobile = itemView.findViewById(R.id.edit_text_pMobile);

            tvTName = itemView.findViewById(R.id.tvTName);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            image = itemView.findViewById(R.id.image);
        }
    }
}
