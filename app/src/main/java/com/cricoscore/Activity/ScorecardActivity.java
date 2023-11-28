package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cricoscore.Adapter.BatsmanScorecardAdapter;
import com.cricoscore.Adapter.BowlerScorecardAdapter;
import com.cricoscore.R;
import com.cricoscore.databinding.ActivityScorecardBinding;
import com.cricoscore.databinding.ActivityStartLiveScoringBinding;
import com.cricoscore.databinding.ToolbarBinding;

import java.util.Objects;

public class ScorecardActivity extends AppCompatActivity {

    ActivityScorecardBinding activityScorecardBinding;
    Context mContext;
    Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityScorecardBinding = ActivityScorecardBinding.inflate(getLayoutInflater());
        setContentView(activityScorecardBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityScorecardBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.scorecard));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());



        /*Team Scorecard Details*/
        /*BatsmanScore*/
        activityScorecardBinding.rvBatsmanTeam1.setLayoutManager(new LinearLayoutManager(mActivity));
        activityScorecardBinding.rvBatsmanTeam1.setHasFixedSize(true);
        activityScorecardBinding.rvBatsmanTeam1.setAdapter(new BatsmanScorecardAdapter(mContext));

        activityScorecardBinding.rlTeam1Details.setOnClickListener(v -> {
            if(activityScorecardBinding.liTeam1Details.getVisibility()==View.GONE){
                activityScorecardBinding.liTeam1Details.setVisibility(View.VISIBLE);
                activityScorecardBinding.imgDownArrow.setVisibility(View.GONE);
                activityScorecardBinding.imgUpArrow.setVisibility(View.VISIBLE);
            }else{
                activityScorecardBinding.liTeam1Details.setVisibility(View.GONE);
                activityScorecardBinding.imgDownArrow.setVisibility(View.VISIBLE);
                activityScorecardBinding.imgUpArrow.setVisibility(View.GONE);
            }

        });
        /*BowlerScore*/
        activityScorecardBinding.rvBowlerTeam1.setLayoutManager(new LinearLayoutManager(mActivity));
        activityScorecardBinding.rvBowlerTeam1.setHasFixedSize(true);
        activityScorecardBinding.rvBowlerTeam1.setAdapter(new BowlerScorecardAdapter(mContext));

        /*Team2 Scorecard Details*/
        /*BatsmanScore*/
        activityScorecardBinding.rvBatsmanTeam2.setLayoutManager(new LinearLayoutManager(mActivity));
        activityScorecardBinding.rvBatsmanTeam2.setHasFixedSize(true);
        activityScorecardBinding.rvBatsmanTeam2.setAdapter(new BatsmanScorecardAdapter(mContext));

        activityScorecardBinding.rlTeam2Details.setOnClickListener(v -> {
            if(activityScorecardBinding.liTeam2Details.getVisibility()== View.GONE){
                activityScorecardBinding.liTeam2Details.setVisibility(View.VISIBLE);
                activityScorecardBinding.imgDownArrow2.setVisibility(View.GONE);
                activityScorecardBinding.imgUpArrow2.setVisibility(View.VISIBLE);
            }else{
                activityScorecardBinding.liTeam2Details.setVisibility(View.GONE);
                activityScorecardBinding.imgDownArrow2.setVisibility(View.VISIBLE);
                activityScorecardBinding.imgUpArrow2.setVisibility(View.GONE);
            }

        });

        /*BowlerScore*/
        activityScorecardBinding.rvBowlerTeam2.setLayoutManager(new LinearLayoutManager(mActivity));
        activityScorecardBinding.rvBowlerTeam2.setHasFixedSize(true);
        activityScorecardBinding.rvBowlerTeam2.setAdapter(new BowlerScorecardAdapter(mContext));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}