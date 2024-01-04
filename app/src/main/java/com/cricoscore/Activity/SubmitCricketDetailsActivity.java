package com.cricoscore.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cricoscore.Adapter.TeamACreatePlayerListAdapter;
import com.cricoscore.Adapter.TeamACreatedFinalPlayerListAdapter;
import com.cricoscore.Adapter.TeamBCreatePlayerListAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectStatusType;
import com.cricoscore.databinding.ActivityScheduleCricketDetailsBinding;
import com.cricoscore.databinding.ActivitySubmitCricketDetailsBinding;
import com.cricoscore.databinding.ToolbarBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubmitCricketDetailsActivity extends AppCompatActivity {

    ActivitySubmitCricketDetailsBinding activityScheduleCricketDetailsBinding;
    Context mContext;
    Activity mActivity;
    TeamACreatePlayerListAdapter teamACreatePlayerListAdapter;
    TeamBCreatePlayerListAdapter teamBCreatePlayerListAdapter;
    private ArrayList<DataModel> option_status_list = new ArrayList<>();
    private ArrayList<DataModel> option_over_list = new ArrayList<>();
    int teamAPlayerCount,teamBPlayerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityScheduleCricketDetailsBinding = ActivitySubmitCricketDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityScheduleCricketDetailsBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityScheduleCricketDetailsBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(mContext.getResources().getString(R.string.match_details));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> {
            if(getIntent().getStringExtra("FROM").equalsIgnoreCase("2")){
                finish();
            }else{
                //startActivity(new Intent(mContext,ScheduleCricketDetailsActivity.class));
                finish();
            }
        });

        toolbarBinding.imgEdit.setVisibility(View.VISIBLE);
        toolbarBinding.imgEdit.setOnClickListener(v -> {
            //startActivity(new Intent(mContext,ScheduleCricketDetailsActivity.class));

            if(getIntent().getStringExtra("FROM").equalsIgnoreCase("2")){
               finish();
            }else{
                startActivity(new Intent(mContext,ScheduleCricketDetailsActivity.class));
            }


        });

        initView();


    }

    private void initView() {




        activityScheduleCricketDetailsBinding.liTeamOne.setOnClickListener(v -> {
            activityScheduleCricketDetailsBinding.rvPlaye2r.setVisibility(View.GONE);
            activityScheduleCricketDetailsBinding.rvPlayer.setVisibility(View.VISIBLE);

            activityScheduleCricketDetailsBinding.tvClickOne.setVisibility(View.GONE);
            activityScheduleCricketDetailsBinding.tvClicktwo.setVisibility(View.VISIBLE);
            activityScheduleCricketDetailsBinding.tvTNameTeamTwo.setTextColor(mContext.getResources().getColor(R.color.lightgrey_dim));
            activityScheduleCricketDetailsBinding.tvTNameOne.setTextColor(mContext.getResources().getColor(R.color.black));
            activityScheduleCricketDetailsBinding.liTeam1.setVisibility(View.VISIBLE);
            activityScheduleCricketDetailsBinding.liTeam2.setVisibility(View.GONE);
        });

        activityScheduleCricketDetailsBinding.liTeamTwo.setOnClickListener(v -> {
            activityScheduleCricketDetailsBinding.rvPlayer.setVisibility(View.GONE);
            activityScheduleCricketDetailsBinding.rvPlaye2r.setVisibility(View.VISIBLE);


            activityScheduleCricketDetailsBinding.tvClickOne.setVisibility(View.VISIBLE);
            activityScheduleCricketDetailsBinding.tvClicktwo.setVisibility(View.GONE);
            activityScheduleCricketDetailsBinding.tvTNameTeamTwo.setTextColor(mContext.getResources().getColor(R.color.black));
            activityScheduleCricketDetailsBinding.tvTNameOne.setTextColor(mContext.getResources().getColor(R.color.lightgrey_dim));
            activityScheduleCricketDetailsBinding.liTeam1.setVisibility(View.GONE);
            activityScheduleCricketDetailsBinding.liTeam2.setVisibility(View.VISIBLE);
        });

        activityScheduleCricketDetailsBinding.rvPlayer.setLayoutManager(new LinearLayoutManager(mContext));
        activityScheduleCricketDetailsBinding.rvPlayer.setHasFixedSize(true);


        activityScheduleCricketDetailsBinding.rvPlayer.setAdapter(new TeamACreatedFinalPlayerListAdapter(mContext,getListOfPlayer()));

        activityScheduleCricketDetailsBinding.rvPlaye2r.setLayoutManager(new LinearLayoutManager(mContext));
        activityScheduleCricketDetailsBinding.rvPlaye2r.setHasFixedSize(true);

        teamBCreatePlayerListAdapter = new TeamBCreatePlayerListAdapter(mContext, poss -> {
            teamBPlayerCount = poss;
            activityScheduleCricketDetailsBinding.tvTNameBCount.setText(String.valueOf(poss)+" "+"Selected");
        });

        activityScheduleCricketDetailsBinding.rvPlaye2r.setAdapter(teamBCreatePlayerListAdapter);





//        if(teamAPlayerCount>=11 && teamAPlayerCount>=11){
//            activityScheduleCricketDetailsBinding.rlOver.setVisibility(View.VISIBLE);
//        }else{
//            activityScheduleCricketDetailsBinding.rlOver.setVisibility(View.GONE);
//        }


        activityScheduleCricketDetailsBinding.mbCreate.setOnClickListener(v -> {
            startActivity(new Intent(mContext,TossActivity.class));
        });

        activityScheduleCricketDetailsBinding.ivMore.setOnClickListener(v -> {
            activityScheduleCricketDetailsBinding.ivMore.setImageResource(0);
            if(activityScheduleCricketDetailsBinding.liInfoDetails.getVisibility()==View.GONE){
                activityScheduleCricketDetailsBinding.liInfoDetails.setVisibility(View.VISIBLE);
                activityScheduleCricketDetailsBinding.ivMore.setBackground(mContext.getResources().getDrawable(R.drawable.expand_less_black_24dp));
                activityScheduleCricketDetailsBinding.ivMore.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(
                        getResources(), R.color.dim_grey, null)));
            }else{
                activityScheduleCricketDetailsBinding.liInfoDetails.setVisibility(View.GONE);
                activityScheduleCricketDetailsBinding.ivMore.setBackground(mContext.getResources().getDrawable(R.drawable.expand_more_black_24dp));
                activityScheduleCricketDetailsBinding.ivMore.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(
                        getResources(), R.color.dim_grey, null)));
            }

        });

        activityScheduleCricketDetailsBinding.tvCount1.setText(String.valueOf(getListOfPlayer().size()));


    }

    public List<String> getListOfPlayer(){
        List<String> list = new ArrayList<>();
        list.add("Arshdeep Singh");
        list.add("Avesh Khan");
        list.add("Ashwin, R");
        list.add("Bumrah, JJ");
        list.add("Chahar, DL");
        list.add("Virat Kohli");
        list.add("Rohit Sharma");
        list.add("Ravindra Jadeja");
        list.add("Hardik Pandya");
        list.add("Ravichandran Ashwin");
        list.add("Kuldeep Yadav");
//        list.add("Ishan Kishan");
//        list.add("Mukesh Kumar");
        return list;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getStringExtra("FROM").equalsIgnoreCase("2")){
            startActivity(new Intent(mContext,ScheduleCricketDetailsActivity.class));
        }else{
            finish();
        }
    }
}