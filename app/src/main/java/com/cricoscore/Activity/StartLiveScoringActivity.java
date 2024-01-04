package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cricoscore.Adapter.StartLiveScoringAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectStatusType;
import com.cricoscore.databinding.ActivityStartLiveScoringBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Objects;

public class StartLiveScoringActivity extends AppCompatActivity {
    ActivityStartLiveScoringBinding activityStartLiveScoringBinding;
    Context mContext;
    Activity mActivity;
    String radioSelectionText;


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

        activityStartLiveScoringBinding.rvScoreList.setAdapter(new StartLiveScoringAdapter(mContext, new StartLiveScoringAdapter.ItemClickListener() {
            @Override
            public void itemClick(int pos) {
                showBottomSheetResumeScoringDialog();
            }

            @Override
            public void itemView(int pos) {
                showBottomSheetDialog();
            }
        }));
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext,R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_live_scoring);

        TextView tvLiveScoring = bottomSheetDialog.findViewById(R.id.tv_Live_Scoring);
        TextView tvScoreCard = bottomSheetDialog.findViewById(R.id.tv_Score_Card);
        TextView tvChangeScorer = bottomSheetDialog.findViewById(R.id.tv_Change_Scorer);
        tvLiveScoring.setOnClickListener(v -> {
            startActivity(new Intent(mContext, TossActivity.class));
            bottomSheetDialog.hide();
        });
        tvScoreCard.setOnClickListener(v -> {
         startActivity(new Intent(mContext,ScorecardActivity.class));
         bottomSheetDialog.dismiss();
        });
        tvChangeScorer.setOnClickListener(v -> {
            changeScorerShowBottomSheetDialog();
            bottomSheetDialog.dismiss();
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

    private void changeScorerShowBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.change_scorere_dialog);
        ArrayList<DataModel> option_status_list = new ArrayList<>();
        option_status_list.add(new DataModel("Ahuja, KS"));
        option_status_list.add(new DataModel("Ashwin, R"));
        option_status_list.add(new DataModel("Arshdeep Singh"));
        option_status_list.add(new DataModel("Avesh Khan"));
        option_status_list.add(new DataModel("Bharat, KS"));
        option_status_list.add(new DataModel("Chahar, DL"));
        SelectStatusType drop_tvSelectBatman1 = bottomSheetDialog.findViewById(R.id.drop_tvSelectBatman1);


        assert drop_tvSelectBatman1 != null;
        drop_tvSelectBatman1.setOptionList(option_status_list);
        drop_tvSelectBatman1.setClickListener(new SelectStatusType.onClickInterface() {
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



        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(v -> {
            bottomSheetDialog.hide();
        });
        bottomSheetDialog.findViewById(R.id.mb_save).setOnClickListener(v -> {
            bottomSheetDialog.hide();
        });

        bottomSheetDialog.show();
    }

    private void showBottomSheetResumeScoringDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext,R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_resume_scoring_area);

        RadioButton rb_resumeScoring = bottomSheetDialog.findViewById(R.id.rb_resumeScoring);
        RadioButton rb_startStreaming = bottomSheetDialog.findViewById(R.id.rb_startStreaming);
        RadioButton rbViewFullScorecard = bottomSheetDialog.findViewById(R.id.rbViewFullScorecard);



        rb_resumeScoring.setOnCheckedChangeListener((compoundButton, b) -> {
            rb_startStreaming.setChecked(false);
            rbViewFullScorecard.setChecked(false);
            radioSelectionText = rb_resumeScoring.getText().toString().trim();

        });
        rb_startStreaming.setOnCheckedChangeListener((compoundButton, b) -> {
            rbViewFullScorecard.setChecked(false);
            rb_resumeScoring.setChecked(false);
            radioSelectionText = rb_startStreaming.getText().toString().trim();
        });
        rbViewFullScorecard.setOnCheckedChangeListener((compoundButton, b) -> {
            rb_startStreaming.setChecked(false);
            rb_resumeScoring.setChecked(false);
            radioSelectionText = rbViewFullScorecard.getText().toString().trim();
        });


        bottomSheetDialog.findViewById(R.id.mb_save).setOnClickListener(v -> {
            if(radioSelectionText.equalsIgnoreCase(mContext.getResources().getString(R.string.resume_scoring))){
                startActivity(new Intent(mContext, DashboardLiveScoringActivity.class).putExtra("FROM","DETAILS"));
                finish();
            }else{
                startActivity(new Intent(mContext, DashboardLiveScoringActivity.class).putExtra("FROM","SCORECARD"));
                finish();
            }


            bottomSheetDialog.hide();
        });
        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(v -> {

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