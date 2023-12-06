package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.cricoscore.Adapter.TeamACreatePlayerListAdapter;
import com.cricoscore.Adapter.TeamBCreatePlayerListAdapter;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectStatusType;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityMatchDetailsBinding;
import com.cricoscore.databinding.ActivityScheduleCricketDetailsBinding;
import com.cricoscore.databinding.ToolbarBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ScheduleCricketDetailsActivity extends AppCompatActivity {

    ActivityScheduleCricketDetailsBinding activityScheduleCricketDetailsBinding;
    Context mContext;
    Activity mActivity;
    TeamACreatePlayerListAdapter teamACreatePlayerListAdapter;
    TeamBCreatePlayerListAdapter teamBCreatePlayerListAdapter;
    private ArrayList<DataModel> option_status_list = new ArrayList<>();
    private ArrayList<DataModel> option_over_list = new ArrayList<>();
    int teamAPlayerCount,teamBPlayerCount;
    public static Uri image_uri=null;
    private int team1=0,team2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityScheduleCricketDetailsBinding = ActivityScheduleCricketDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityScheduleCricketDetailsBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityScheduleCricketDetailsBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(mContext.getResources().getString(R.string.select_final_eleven));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        initView();


    }

    private void initView() {


        activityScheduleCricketDetailsBinding.tvInfo.startAnimation((Animation) AnimationUtils.loadAnimation(mContext,R.anim.translate));

        activityScheduleCricketDetailsBinding.liTeamOne.setOnClickListener(v -> {
            activityScheduleCricketDetailsBinding.rvPlaye2r.setVisibility(View.GONE);
            activityScheduleCricketDetailsBinding.rvPlayer.setVisibility(View.VISIBLE);
            activityScheduleCricketDetailsBinding.tvTNameAEnable.setText(activityScheduleCricketDetailsBinding.tvTNameOne.getText());
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

            activityScheduleCricketDetailsBinding.tvTNameAEnable2.setText(activityScheduleCricketDetailsBinding.tvTNameTeamTwo.getText());
            activityScheduleCricketDetailsBinding.tvClickOne.setVisibility(View.VISIBLE);
            activityScheduleCricketDetailsBinding.tvClicktwo.setVisibility(View.GONE);
            activityScheduleCricketDetailsBinding.tvTNameTeamTwo.setTextColor(mContext.getResources().getColor(R.color.black));
            activityScheduleCricketDetailsBinding.tvTNameOne.setTextColor(mContext.getResources().getColor(R.color.lightgrey_dim));
            activityScheduleCricketDetailsBinding.liTeam1.setVisibility(View.GONE);
            activityScheduleCricketDetailsBinding.liTeam2.setVisibility(View.VISIBLE);
        });

        activityScheduleCricketDetailsBinding.rvPlayer.setLayoutManager(new LinearLayoutManager(mContext));
        activityScheduleCricketDetailsBinding.rvPlayer.setHasFixedSize(true);

        teamACreatePlayerListAdapter = new TeamACreatePlayerListAdapter(mContext, pos -> {
            teamAPlayerCount = pos;
            activityScheduleCricketDetailsBinding.tvTNameACount.setText(String.valueOf(pos)+" "+"Selected");
        });

        activityScheduleCricketDetailsBinding.rvPlayer.setAdapter(teamACreatePlayerListAdapter);

        activityScheduleCricketDetailsBinding.rvPlaye2r.setLayoutManager(new LinearLayoutManager(mContext));
        activityScheduleCricketDetailsBinding.rvPlaye2r.setHasFixedSize(true);

        teamBCreatePlayerListAdapter = new TeamBCreatePlayerListAdapter(mContext, poss -> {
            teamBPlayerCount = poss;
            activityScheduleCricketDetailsBinding.tvTNameBCount.setText(String.valueOf(poss)+" "+"Selected");
        });

        activityScheduleCricketDetailsBinding.rvPlaye2r.setAdapter(teamBCreatePlayerListAdapter);


        activityScheduleCricketDetailsBinding.mbAddPlayer.setOnClickListener(v -> {
            if(activityScheduleCricketDetailsBinding.rlTeamAdd.getVisibility() == View.VISIBLE){
                activityScheduleCricketDetailsBinding.mbAddPlayer.setText("Add New Player");
                activityScheduleCricketDetailsBinding.mbAddPlayer.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.purple_700));
                activityScheduleCricketDetailsBinding.rlTeamAdd.setVisibility(View.GONE);
                activityScheduleCricketDetailsBinding.editTextPMobile.setText("");
                activityScheduleCricketDetailsBinding.editTextPName.setText("");
                image_uri = null;
                activityScheduleCricketDetailsBinding.ivTeamLogo.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_user));

            }else{
                activityScheduleCricketDetailsBinding.mbAddPlayer.setText(mContext.getResources().getString(R.string.submit));
                activityScheduleCricketDetailsBinding.mbAddPlayer.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.green));

                activityScheduleCricketDetailsBinding.rlTeamAdd.setVisibility(View.VISIBLE);
            }
        });
        activityScheduleCricketDetailsBinding.mbAddPlayer2.setOnClickListener(v -> {
            if(activityScheduleCricketDetailsBinding.rlTeamAdd2.getVisibility() == View.VISIBLE){
                activityScheduleCricketDetailsBinding.mbAddPlayer2.setText("Add New Player");
                activityScheduleCricketDetailsBinding.mbAddPlayer2.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.purple_700));
                activityScheduleCricketDetailsBinding.rlTeamAdd2.setVisibility(View.GONE);
                activityScheduleCricketDetailsBinding.editTextPMobile2.setText("");
                activityScheduleCricketDetailsBinding.editTextPName2.setText("");
                image_uri = null;
                activityScheduleCricketDetailsBinding.ivTeamLogo2.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_user));

            }else{
                activityScheduleCricketDetailsBinding.mbAddPlayer2.setText(mContext.getResources().getString(R.string.submit));
                activityScheduleCricketDetailsBinding.mbAddPlayer2.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.green));

                activityScheduleCricketDetailsBinding.rlTeamAdd2.setVisibility(View.VISIBLE);
            }
        });

        option_status_list.add(new DataModel("Ahuja, KS"));
        option_status_list.add(new DataModel("Ashwin, R"));
        option_status_list.add(new DataModel("Arshdeep Singh"));
        option_status_list.add(new DataModel("Avesh Khan"));
        option_status_list.add(new DataModel("Bharat, KS"));
        option_status_list.add(new DataModel("Chahar, DL"));
        activityScheduleCricketDetailsBinding.dropSelectCaptain.setOptionList(option_status_list);
        activityScheduleCricketDetailsBinding.dropSelectCaptain.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activityScheduleCricketDetailsBinding.dropSelectCaptain.setCompoundDrawableTintList(mContext.getResources().getColorStateList(R.color.black));
                }
            }


            @Override
            public void onDismiss() {
            }
        });


        option_status_list.add(new DataModel("Hooda, DJ"));
        option_status_list.add(new DataModel("Iyer, SS"));
        option_status_list.add(new DataModel("Ishan Kishan"));
        option_status_list.add(new DataModel("Avesh Khan"));
        option_status_list.add(new DataModel("Bharat, KS"));
        option_status_list.add(new DataModel("Chahar, DL"));
        activityScheduleCricketDetailsBinding.dropSelectCaptain2.setOptionList(option_status_list);
        activityScheduleCricketDetailsBinding.dropSelectCaptain2.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activityScheduleCricketDetailsBinding.dropSelectCaptain2.setCompoundDrawableTintList(mContext.getResources().getColorStateList(R.color.black));
                }
            }


            @Override
            public void onDismiss() {
            }
        });



//        if(teamAPlayerCount>=11 && teamAPlayerCount>=11){
//            activityScheduleCricketDetailsBinding.rlOver.setVisibility(View.VISIBLE);
//        }else{
//            activityScheduleCricketDetailsBinding.rlOver.setVisibility(View.GONE);
//        }

        option_over_list.add(new DataModel("10"));
        option_over_list.add(new DataModel("20"));
        option_over_list.add(new DataModel("50"));
        option_over_list.add(new DataModel("90"));
        activityScheduleCricketDetailsBinding.dropSelectOver.setOptionList(option_over_list);
        activityScheduleCricketDetailsBinding.dropSelectOver.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activityScheduleCricketDetailsBinding.dropSelectOver.setCompoundDrawableTintList(mContext.getResources().getColorStateList(R.color.black));
                }

                activityScheduleCricketDetailsBinding.mbSubmitFinal.setVisibility(View.VISIBLE);
            }


            @Override
            public void onDismiss() {
            }
        });


        activityScheduleCricketDetailsBinding.mbSubmitFinal.setOnClickListener(v -> {

            if(teamAPlayerCount<11){
                 Toaster.customToast("Select at least 11 players");
            }else if(teamBPlayerCount<11){
                Toaster.customToast("Select at least 11 players");
            }else{
                startActivity(new Intent(mContext,SubmitCricketDetailsActivity.class).putExtra("FROM","2"));
            }


        });

        activityScheduleCricketDetailsBinding.rlTeamLogo.setOnClickListener(view -> {
            team2 = 0;
            team1 = 1;
            mContext.startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","ScheduleCricketDetailsActivity"));
        });

        activityScheduleCricketDetailsBinding.rlTeamLogo2.setOnClickListener(view -> {
            team1 = 0;
            team2 = 2;
            mContext.startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","ScheduleCricketDetailsActivity"));
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(image_uri!=null){

            if(team1==1){
                activityScheduleCricketDetailsBinding.ivTeamLogo.setImageURI(image_uri);
                image_uri = null;
            }else if(team2==2){
                activityScheduleCricketDetailsBinding.ivTeamLogo2.setImageURI(image_uri);
                image_uri = null;
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}