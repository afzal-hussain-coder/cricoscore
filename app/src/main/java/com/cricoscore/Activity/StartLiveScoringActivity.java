package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cricoscore.Adapter.StartLiveScoringAdapter;
import com.cricoscore.Adapter.YourTeamListAdapterHorizontal;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.databinding.ActivityMatchDetailsBinding;
import com.cricoscore.databinding.ActivityStartLiveScoringBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class StartLiveScoringActivity extends AppCompatActivity {
    ActivityStartLiveScoringBinding activityStartLiveScoringBinding;
    Context mContext;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStartLiveScoringBinding = ActivityStartLiveScoringBinding.inflate(getLayoutInflater());
        setContentView(activityStartLiveScoringBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityStartLiveScoringBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.live_scoring));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        activityStartLiveScoringBinding.rvScoreList.setLayoutManager(new LinearLayoutManager(mContext));
        activityStartLiveScoringBinding.rvScoreList.setHasFixedSize(true);

        activityStartLiveScoringBinding.rvScoreList.setAdapter(new StartLiveScoringAdapter(mContext, pos -> {
            showBottomSheetDialog();
        }));
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_live_scoring);

        TextView tvLiveScoring = bottomSheetDialog.findViewById(R.id.tv_Live_Scoring);
        TextView tvScoreCard = bottomSheetDialog.findViewById(R.id.tv_Score_Card);
        TextView tvChangeScorer = bottomSheetDialog.findViewById(R.id.tv_Change_Scorer);
        tvLiveScoring.setOnClickListener(v -> {
            startActivity(new Intent(mContext, TossActivity.class));
            bottomSheetDialog.hide();
        });
        tvScoreCard.setOnClickListener(v -> {

        });
        tvChangeScorer.setOnClickListener(v -> {

        });

        bottomSheetDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            bottomSheetDialog.hide();
        });
        bottomSheetDialog.findViewById(R.id.tv_viewDetails).setOnClickListener(v -> {
            startActivity(new Intent(mContext, SubmitCricketDetailsActivity.class).putExtra("FROM","1"));
            bottomSheetDialog.hide();
        });

        bottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}