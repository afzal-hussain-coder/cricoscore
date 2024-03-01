package com.cricoscore.Activity;

import androidx.activity.OnBackPressedDispatcherKt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import com.cricoscore.Adapter.BatsmanScorecardAdapter;
import com.cricoscore.Adapter.BattingStyleAdapter;
import com.cricoscore.Adapter.BowlerScorecardAdapter;
import com.cricoscore.Adapter.BowlingStyleAdapter;
import com.cricoscore.Adapter.CommentaryAdapter;
import com.cricoscore.Adapter.ScoringBallAdapter;
import com.cricoscore.Adapter.ScoringRunAdapter;
import com.cricoscore.Adapter.ShortAreaSubCategoryAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectStatusType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityDashboardLiveScoringBinding;
import com.cricoscore.databinding.BottomStartscoringDialogSelectPalyerBinding;
import com.cricoscore.databinding.DashboardToolbarBinding;
import com.cricoscore.model.BallModel;
import com.cricoscore.model.RunModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DashboardLiveScoringActivity extends AppCompatActivity {

    ActivityDashboardLiveScoringBinding activityDashboardLiveScoringBinding;
    Context mContext;
    Activity mActivity;

    TextToSpeech textToSpeech;
    int typedRun = 0;
    String wayOfOut = "";
    int totalOver = 14;
    int increaseOver = 0;
    int increaseBall = 0;
    float typedBall = 0.1f;
    ScoringBallAdapter scoringBallAdapter;
    ScoringRunAdapter scoringRunAdapter;
    ArrayList<BallModel> listBall = new ArrayList<>();
    ArrayList<RunModel> listRun = new ArrayList<>();
    BallModel ballModel;
    RunModel runModel;
    boolean isEmptyRun;
    boolean isBallLayoutVisible;
    //    int WDIncrease =0;
//    int  WDIncrease2=0;
//    int  WDIncrease3=0;
//    int  WDIncrease4=0;
//    int  WDIncrease5=0;
//    int  WDIncrease6=0;
//    int NBIncrease =0;
//    int NBIncrease2 =0;
//    int NBIncrease3 =0;
//    int NBIncrease4 =0;
//    int NBIncrease5 =0;
//    int NBIncrease6 =0;
//    int xtra=0;
//    int xtra2=0;
//    int xtra3=0;
//    int xtra4=0;
//    int xtra5=0;
//    int xtra6=0;
    int number = 1;

    String bowlingStyleType = "";
    String battingStyle = "";
    boolean checkBoxStatus;
    boolean checkBoxDitBallStatus;

    private ArrayList<DataModel> option_teamType_list = new ArrayList<>();
    private ArrayList<DataModel> option_commentaryType_list = new ArrayList<>();
    private String teamTypeSelection = "";
    private String commentaryTypeSelection = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardLiveScoringBinding = ActivityDashboardLiveScoringBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardLiveScoringBinding.getRoot());

        mContext = this;
        mActivity = this;

        textToSpeech = new TextToSpeech(getApplicationContext(), i -> {

            // if No error is found then only it will run
            if (i != TextToSpeech.ERROR) {
                // To Choose language of speech
                textToSpeech.setLanguage(Locale.US);
            }
        });

        DashboardToolbarBinding toolbarBinding = activityDashboardLiveScoringBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbardahsboard);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText("CHENNAI SUPER KING VS ROYAL CHALLENGERS");
        toolbarBinding.toolbardahsboard.setNavigationOnClickListener(v ->
                {
                    showBottomStopMatchSheetDialog();
                    TossActivity.setIsSaveButtonStatus(true);
                    // finish();
                }

        );

        Animation animationBlink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        activityDashboardLiveScoringBinding.imgBatting.startAnimation(animationBlink);
        activityDashboardLiveScoringBinding.imgBowling.startAnimation(animationBlink);
        activityDashboardLiveScoringBinding.tvLive.startAnimation(animationBlink);


        BottomStartscoringDialogSelectPalyerBinding bottomStartscoringDialogSelectPalyerBinding
                = activityDashboardLiveScoringBinding.bottomSheetParent;
        showStartScoringBottomSheetDialog(bottomStartscoringDialogSelectPalyerBinding.liBottomScoring);

        activityDashboardLiveScoringBinding.rlBattingMain.setOnClickListener(v -> showBottomBattingSheetDialog());
        activityDashboardLiveScoringBinding.rlBowlingMain.setOnClickListener(v -> showBottomBowlingSheetDialog(""));

        activityDashboardLiveScoringBinding.tvStartScoring.setOnClickListener(v -> {
            activityDashboardLiveScoringBinding.liTopScorecard.setVisibility(View.GONE);
            activityDashboardLiveScoringBinding.liScorecard.setVisibility(View.GONE);
            activityDashboardLiveScoringBinding.rlStartScoring.setVisibility(View.VISIBLE);
            activityDashboardLiveScoringBinding.tvScorecard.setBackgroundColor(getResources().getColor(R.color.verified_user_color));
            activityDashboardLiveScoringBinding.tvStartScoring.setBackgroundColor(getResources().getColor(R.color.dim_sky));

        });

        activityDashboardLiveScoringBinding.imgSwipe.setOnClickListener(v -> {
            String name1 = activityDashboardLiveScoringBinding.tvBatsmanName.getText().toString();
            String name2 = activityDashboardLiveScoringBinding.tvBatsmanName2.getText().toString();
            activityDashboardLiveScoringBinding.tvBatsmanName.setText(name2);
            activityDashboardLiveScoringBinding.tvBatsmanName2.setText(name1);
        });


        activityDashboardLiveScoringBinding.tvBatsmanName2.setOnClickListener(v -> showBottomBatting2SheetDialog(activityDashboardLiveScoringBinding.tvBatsmanName2.getText().toString()));

        activityDashboardLiveScoringBinding.tvBatsmanName.setOnClickListener(v -> showBottomBatting1SheetDialog(activityDashboardLiveScoringBinding.tvBatsmanName.getText().toString()));

        activityDashboardLiveScoringBinding.tvBowlerName.setOnClickListener(v -> showBottomBowlingSheetDialog(activityDashboardLiveScoringBinding.tvBowlerName.getText().toString()));

        /*Team Scorecard Details*/
        /*BatsmanScore*/
        activityDashboardLiveScoringBinding.rvBatsmanTeam1.setLayoutManager(new LinearLayoutManager(mActivity));
        activityDashboardLiveScoringBinding.rvBatsmanTeam1.setHasFixedSize(true);
        activityDashboardLiveScoringBinding.rvBatsmanTeam1.setAdapter(new BatsmanScorecardAdapter(mContext));

        activityDashboardLiveScoringBinding.rlTeam1Details.setOnClickListener(v -> {
            if (activityDashboardLiveScoringBinding.liTeam1Details.getVisibility() == View.GONE) {
                activityDashboardLiveScoringBinding.liTeam1Details.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.imgDownArrow.setVisibility(View.GONE);
                activityDashboardLiveScoringBinding.imgUpArrow.setVisibility(View.VISIBLE);
            } else {
                activityDashboardLiveScoringBinding.liTeam1Details.setVisibility(View.GONE);
                activityDashboardLiveScoringBinding.imgDownArrow.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.imgUpArrow.setVisibility(View.GONE);
            }

        });

        /*BowlerScore*/
        activityDashboardLiveScoringBinding.rvBowlerTeam1.setLayoutManager(new LinearLayoutManager(mActivity));
        activityDashboardLiveScoringBinding.rvBowlerTeam1.setHasFixedSize(true);
        activityDashboardLiveScoringBinding.rvBowlerTeam1.setAdapter(new BowlerScorecardAdapter(mContext));

        /*Team2 Scorecard Details*/
        /*BatsmanScore*/
        activityDashboardLiveScoringBinding.rvBatsmanTeam2.setLayoutManager(new LinearLayoutManager(mActivity));
        activityDashboardLiveScoringBinding.rvBatsmanTeam2.setHasFixedSize(true);
        activityDashboardLiveScoringBinding.rvBatsmanTeam2.setAdapter(new BatsmanScorecardAdapter(mContext));

        activityDashboardLiveScoringBinding.rlTeam2Details.setOnClickListener(v -> {
            if (activityDashboardLiveScoringBinding.liTeam2Details.getVisibility() == View.GONE) {
                activityDashboardLiveScoringBinding.liTeam2Details.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.imgDownArrow2.setVisibility(View.GONE);
                activityDashboardLiveScoringBinding.imgUpArrow2.setVisibility(View.VISIBLE);
            } else {
                activityDashboardLiveScoringBinding.liTeam2Details.setVisibility(View.GONE);
                activityDashboardLiveScoringBinding.imgDownArrow2.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.imgUpArrow2.setVisibility(View.GONE);
            }

        });

        /*BowlerScore*/
        activityDashboardLiveScoringBinding.rvBowlerTeam2.setLayoutManager(new LinearLayoutManager(mActivity));
        activityDashboardLiveScoringBinding.rvBowlerTeam2.setHasFixedSize(true);
        activityDashboardLiveScoringBinding.rvBowlerTeam2.setAdapter(new BowlerScorecardAdapter(mContext));


        activityDashboardLiveScoringBinding.tvToss.startAnimation(animationBlink);

        activityDashboardLiveScoringBinding.tvScorecard.setOnClickListener(v -> {
            activityDashboardLiveScoringBinding.liTopScorecard.setVisibility(View.VISIBLE);
            activityDashboardLiveScoringBinding.liScorecard.setVisibility(View.VISIBLE);
            activityDashboardLiveScoringBinding.rlStartScoring.setVisibility(View.GONE);
            activityDashboardLiveScoringBinding.tvStartScoring.setBackgroundColor(getResources().getColor(R.color.verified_user_color));
            activityDashboardLiveScoringBinding.tvScorecard.setBackgroundColor(getResources().getColor(R.color.dim_sky));
        });

        if (getIntent() != null) {
            if (getIntent().getStringExtra("FROM").equalsIgnoreCase("SCORECARD")) {
                activityDashboardLiveScoringBinding.liScorecard.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.rlStartScoring.setVisibility(View.GONE);
                activityDashboardLiveScoringBinding.tvStartScoring.setBackgroundColor(getResources().getColor(R.color.verified_user_color));
                activityDashboardLiveScoringBinding.tvScorecard.setBackgroundColor(getResources().getColor(R.color.dim_sky));
            }
        }




        /*Commentary*/
        option_teamType_list.add(new DataModel("Team A"));
        option_teamType_list.add(new DataModel("Team B"));
        activityDashboardLiveScoringBinding.dropTeamSelection.setOptionList(option_teamType_list);
        teamTypeSelection = option_teamType_list.get(0).getName();
        activityDashboardLiveScoringBinding.dropTeamSelection.setText(teamTypeSelection);
        activityDashboardLiveScoringBinding.dropTeamSelection.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activityDashboardLiveScoringBinding.dropTeamSelection.setCompoundDrawableTintList(mContext.getResources().getColorStateList(R.color.black));
                }
            }


            @Override
            public void onDismiss() {
            }
        });

        option_commentaryType_list.add(new DataModel(mActivity.getResources().getString(R.string.full_commentary)));
        option_commentaryType_list.add(new DataModel(mActivity.getResources().getString(R.string.wickets)));
        option_commentaryType_list.add(new DataModel(mActivity.getResources().getString(R.string.boundaries)));
        activityDashboardLiveScoringBinding.dropCommentaryType.setOptionList(option_commentaryType_list);
        commentaryTypeSelection = option_commentaryType_list.get(0).getName();
        activityDashboardLiveScoringBinding.dropCommentaryType.setText(commentaryTypeSelection);
        activityDashboardLiveScoringBinding.dropCommentaryType.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activityDashboardLiveScoringBinding.dropCommentaryType.setCompoundDrawableTintList(mContext.getResources().getColorStateList(R.color.black));
                }
            }


            @Override
            public void onDismiss() {
            }
        });

        activityDashboardLiveScoringBinding.rcvCommentary.setHasFixedSize(true);
        activityDashboardLiveScoringBinding.rcvCommentary.setLayoutManager(new LinearLayoutManager(mActivity));
        activityDashboardLiveScoringBinding.rcvCommentary.setAdapter(new CommentaryAdapter(mActivity));


        /*scorecard Reopen all views*/

        activityDashboardLiveScoringBinding.tvScorecard1.setOnClickListener(view -> {
            activityDashboardLiveScoringBinding.liScorcarddOpen.setVisibility(View.VISIBLE);
            activityDashboardLiveScoringBinding.liCommentaryReopen.setVisibility(View.GONE);

            activityDashboardLiveScoringBinding.tvScorecard1.setBackgroundColor(mContext.getResources().getColor(R.color.light_sky));
            activityDashboardLiveScoringBinding.tvScorecard1.setTextColor(mContext.getResources().getColor(R.color.purple_500));

            activityDashboardLiveScoringBinding.tvCommentary.setBackgroundColor(mContext.getResources().getColor(R.color.light_gray));
            activityDashboardLiveScoringBinding.tvCommentary.setTextColor(mContext.getResources().getColor(R.color.dark_grey));
        });

        activityDashboardLiveScoringBinding.tvCommentary.setOnClickListener(view -> {
            activityDashboardLiveScoringBinding.liScorcarddOpen.setVisibility(View.GONE);
            activityDashboardLiveScoringBinding.liCommentaryReopen.setVisibility(View.VISIBLE);
            activityDashboardLiveScoringBinding.tvCommentary.setBackgroundColor(mContext.getResources().getColor(R.color.light_sky));
            activityDashboardLiveScoringBinding.tvCommentary.setTextColor(mContext.getResources().getColor(R.color.purple_500));

            activityDashboardLiveScoringBinding.tvScorecard1.setBackgroundColor(mContext.getResources().getColor(R.color.light_gray));
            activityDashboardLiveScoringBinding.tvScorecard1.setTextColor(mContext.getResources().getColor(R.color.dark_grey));
        });


    }

    private void showBottomBattingSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_select_palyer);
        if (bottomSheetDialog.getWindow() != null)
            bottomSheetDialog.getWindow().setDimAmount(0);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        LinearLayout liBattingStyle1 = bottomSheetDialog.findViewById(R.id.liBattingStyle1);
        RecyclerView rcvBattingStyle1 = bottomSheetDialog.findViewById(R.id.rcvBattingStyle1);

        rcvBattingStyle1.setLayoutManager(new GridLayoutManager(mContext, 2));
        rcvBattingStyle1.setHasFixedSize(true);
        rcvBattingStyle1.setAdapter(new BattingStyleAdapter(mContext, Global.getBattingStyleList(mContext), value -> {
            battingStyle = value;

        }));

        LinearLayout liBattingStyle2 = bottomSheetDialog.findViewById(R.id.liBattingStyle2);
        RecyclerView rcvBattingStyle2 = bottomSheetDialog.findViewById(R.id.rcvBattingStyle2);

        rcvBattingStyle2.setLayoutManager(new GridLayoutManager(mContext, 2));
        rcvBattingStyle2.setHasFixedSize(true);
        rcvBattingStyle2.setAdapter(new BattingStyleAdapter(mContext, Global.getBattingStyleList(mContext), value -> {
            battingStyle = value;

        }));


        ArrayList<DataModel> option_status_list = new ArrayList<>();
        option_status_list.add(new DataModel("Ahuja, KS"));
        option_status_list.add(new DataModel("Ashwin, R"));
        option_status_list.add(new DataModel("Arshdeep Singh"));
        option_status_list.add(new DataModel("Avesh Khan"));
        option_status_list.add(new DataModel("Bharat, KS"));
        option_status_list.add(new DataModel("Chahar, DL"));
        SelectStatusType drop_tvSelectBatman1 = bottomSheetDialog.findViewById(R.id.drop_tvSelectBatman1);
        SelectStatusType drop_tvSelectBatman2 = bottomSheetDialog.findViewById(R.id.drop_tvSelectBatman2);
        SelectStatusType drop_tvSelectBowler = bottomSheetDialog.findViewById(R.id.drop_tvSelectBowler);

        assert drop_tvSelectBatman1 != null;
        drop_tvSelectBatman1.setOptionList(option_status_list);
        drop_tvSelectBatman1.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                activityDashboardLiveScoringBinding.rlBattingPlayer1.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.tvBatsmanName.setText(name);

            }


            @Override
            public void onDismiss() {
            }
        });

        assert drop_tvSelectBatman2 != null;
        drop_tvSelectBatman2.setOptionList(option_status_list);
        drop_tvSelectBatman2.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                activityDashboardLiveScoringBinding.rlBattingPlayer2.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.tvBatsmanName2.setText(name);
            }


            @Override
            public void onDismiss() {
            }
        });

        assert drop_tvSelectBowler != null;
        drop_tvSelectBowler.setOptionList(option_status_list);
        drop_tvSelectBowler.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                activityDashboardLiveScoringBinding.rlBowlingPlayer.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.tvBowlerName.setText(name);
            }


            @Override
            public void onDismiss() {
            }
        });
        drop_tvSelectBowler.setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.rl_bowler_main).setVisibility(View.GONE);

        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(v -> {
            bottomSheetDialog.hide();
        });
        bottomSheetDialog.findViewById(R.id.mb_save).setOnClickListener(v -> {
            bottomSheetDialog.hide();
        });

        bottomSheetDialog.show();
    }

    private void showBottomBatting1SheetDialog(String name) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_select_palyer);
        if (bottomSheetDialog.getWindow() != null)
            bottomSheetDialog.getWindow().setDimAmount(0);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        LinearLayout liBattingStyle1 = bottomSheetDialog.findViewById(R.id.liBattingStyle1);
        RecyclerView rcvBattingStyle1 = bottomSheetDialog.findViewById(R.id.rcvBattingStyle1);

        rcvBattingStyle1.setLayoutManager(new GridLayoutManager(mContext, 2));
        rcvBattingStyle1.setHasFixedSize(true);
        rcvBattingStyle1.setAdapter(new BattingStyleAdapter(mContext, Global.getBattingStyleList(mContext), value -> {
            battingStyle = value;

        }));


        ArrayList<DataModel> option_status_list = new ArrayList<>();
        option_status_list.add(new DataModel("Ahuja, KS"));
        option_status_list.add(new DataModel("Ashwin, R"));
        option_status_list.add(new DataModel("Arshdeep Singh"));
        option_status_list.add(new DataModel("Avesh Khan"));
        option_status_list.add(new DataModel("Bharat, KS"));
        option_status_list.add(new DataModel("Chahar, DL"));
        SelectStatusType drop_tvSelectBatman1 = bottomSheetDialog.findViewById(R.id.drop_tvSelectBatman1);


        Objects.requireNonNull(drop_tvSelectBatman1).setText(name);
        drop_tvSelectBatman1.setOptionList(option_status_list);
        drop_tvSelectBatman1.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                activityDashboardLiveScoringBinding.rlBattingPlayer1.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.tvBatsmanName.setText(name);
                liBattingStyle1.setVisibility(View.VISIBLE);


            }


            @Override
            public void onDismiss() {
            }
        });

        bottomSheetDialog.findViewById(R.id.rl_bowlingmain).setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.rl_bowler_main).setVisibility(View.GONE);

        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(v -> {
            bottomSheetDialog.hide();
        });
        bottomSheetDialog.findViewById(R.id.mb_save).setOnClickListener(v -> {
            liBattingStyle1.setVisibility(View.GONE);
            bottomSheetDialog.hide();
        });
        TextView tv_header = bottomSheetDialog.findViewById(R.id.tv_header);
        tv_header.setText("Replace Batsman");
        TextView tvSelectBatman1 = bottomSheetDialog.findViewById(R.id.tvSelectBatman1);
        tvSelectBatman1.setText("Replace Batsman");


        bottomSheetDialog.show();
    }

    private void showBottomBatting2SheetDialog(String name2) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_select_palyer);
        if (bottomSheetDialog.getWindow() != null)
            bottomSheetDialog.getWindow().setDimAmount(0);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        LinearLayout liBattingStyle2 = bottomSheetDialog.findViewById(R.id.liBattingStyle2);
        RecyclerView rcvBattingStyle2 = bottomSheetDialog.findViewById(R.id.rcvBattingStyle2);

        rcvBattingStyle2.setLayoutManager(new GridLayoutManager(mContext, 2));
        rcvBattingStyle2.setHasFixedSize(true);
        rcvBattingStyle2.setAdapter(new BattingStyleAdapter(mContext, Global.getBattingStyleList(mContext), value -> {
            battingStyle = value;

        }));

        ArrayList<DataModel> option_status_list = new ArrayList<>();
        option_status_list.add(new DataModel("Ahuja, KS"));
        option_status_list.add(new DataModel("Ashwin, R"));
        option_status_list.add(new DataModel("Arshdeep Singh"));
        option_status_list.add(new DataModel("Avesh Khan"));
        option_status_list.add(new DataModel("Bharat, KS"));
        option_status_list.add(new DataModel("Chahar, DL"));

        SelectStatusType drop_tvSelectBatman2 = bottomSheetDialog.findViewById(R.id.drop_tvSelectBatman2);
        drop_tvSelectBatman2.setText(name2);
        drop_tvSelectBatman2.setOptionList(option_status_list);
        drop_tvSelectBatman2.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                activityDashboardLiveScoringBinding.rlBattingPlayer2.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.tvBatsmanName2.setText(name);
                liBattingStyle2.setVisibility(View.VISIBLE);
            }


            @Override
            public void onDismiss() {
            }
        });

        bottomSheetDialog.findViewById(R.id.rl_batingmain).setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.rl_bowler_main).setVisibility(View.GONE);


        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(v -> {
            bottomSheetDialog.hide();
        });
        bottomSheetDialog.findViewById(R.id.mb_save).setOnClickListener(v -> {
            liBattingStyle2.setVisibility(View.GONE);
            bottomSheetDialog.hide();
        });

        TextView tv_header = bottomSheetDialog.findViewById(R.id.tv_header);
        tv_header.setText("Replace Batsman");
        TextView tvSelectBatman2 = bottomSheetDialog.findViewById(R.id.tvSelectBatman2);
        tvSelectBatman2.setText("Replace Batsman");
        bottomSheetDialog.show();
    }

    private void showBottomBowlingSheetDialog(String name) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_select_palyer);
        if (bottomSheetDialog.getWindow() != null)
            bottomSheetDialog.getWindow().setDimAmount(0);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        LinearLayout liBowlingStyle = bottomSheetDialog.findViewById(R.id.liBowlingStyle);
        RecyclerView rcvBowlingStyle = bottomSheetDialog.findViewById(R.id.rcvBowlingStyle);

        rcvBowlingStyle.setLayoutManager(new GridLayoutManager(mContext, 3));
        rcvBowlingStyle.setHasFixedSize(true);
        rcvBowlingStyle.setAdapter(new BowlingStyleAdapter(mContext, Global.getBowlingStyleList(mContext), value -> {
            bowlingStyleType = value;

        }));


        ArrayList<DataModel> option_status_list = new ArrayList<>();
        option_status_list.add(new DataModel("Ahuja, KS"));
        option_status_list.add(new DataModel("Ashwin, R"));
        option_status_list.add(new DataModel("Arshdeep Singh"));
        option_status_list.add(new DataModel("Avesh Khan"));
        option_status_list.add(new DataModel("Bharat, KS"));
        option_status_list.add(new DataModel("Chahar, DL"));
        SelectStatusType drop_tvSelectBatman1 = bottomSheetDialog.findViewById(R.id.drop_tvSelectBatman1);
        SelectStatusType drop_tvSelectBatman2 = bottomSheetDialog.findViewById(R.id.drop_tvSelectBatman2);
        SelectStatusType drop_tvSelectBowler = bottomSheetDialog.findViewById(R.id.drop_tvSelectBowler);

        assert drop_tvSelectBatman1 != null;
        drop_tvSelectBatman1.setOptionList(option_status_list);
        drop_tvSelectBatman1.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                activityDashboardLiveScoringBinding.rlBattingPlayer1.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.tvBatsmanName.setText(name);

            }


            @Override
            public void onDismiss() {
            }
        });
        drop_tvSelectBatman1.setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.rl_batingmain).setVisibility(View.GONE);

        assert drop_tvSelectBatman2 != null;
        drop_tvSelectBatman2.setOptionList(option_status_list);
        drop_tvSelectBatman2.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                activityDashboardLiveScoringBinding.rlBattingPlayer2.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.tvBatsmanName2.setText(name);
            }


            @Override
            public void onDismiss() {
            }
        });
        drop_tvSelectBatman2.setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.rl_bowlingmain).setVisibility(View.GONE);

        assert drop_tvSelectBowler != null;
        drop_tvSelectBowler.setOptionList(option_status_list);

        TextView tv_header = bottomSheetDialog.findViewById(R.id.tv_header);

        if (!name.isEmpty()) {
            if (name.equalsIgnoreCase("ChangeBowler")) {
                tv_header.setText("Change Bowler");
            } else {
                drop_tvSelectBowler.setText(name);
                tv_header.setText("Replace Bowler");
            }
        } else {
            tv_header.setText("Select Bowler");
        }

        drop_tvSelectBowler.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                activityDashboardLiveScoringBinding.rlBowlingPlayer.setVisibility(View.VISIBLE);
                activityDashboardLiveScoringBinding.tvBowlerName.setText(name);
                liBowlingStyle.setVisibility(View.VISIBLE);
            }


            @Override
            public void onDismiss() {
            }
        });

        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(v -> bottomSheetDialog.hide());
        bottomSheetDialog.findViewById(R.id.mb_save).setOnClickListener(v -> {
                    liBowlingStyle.setVisibility(View.GONE);
                    bottomSheetDialog.hide();
                }
        );

        bottomSheetDialog.show();
    }

    private void showStartScoringBottomSheetDialog(LinearLayout bottomSheetDialog) {

        LinearLayout li_keyboard = bottomSheetDialog.findViewById(R.id.li_keyboard);
        LinearLayout li_startInningEndSession = bottomSheetDialog.findViewById(R.id.li_startInningEndSession);
        LinearLayout li_startSecondInning = bottomSheetDialog.findViewById(R.id.li_startSecondInning);
        LinearLayout li_endScoringSession = bottomSheetDialog.findViewById(R.id.li_endScoringSession);
        TextView tv_StartsecondInning = bottomSheetDialog.findViewById(R.id.tv_StartsecondInning);

        MaterialButton mb_end_over = bottomSheetDialog.findViewById(R.id.mb_end_over);

        LinearLayout li1 = bottomSheetDialog.findViewById(R.id.li1);
        LinearLayout li2 = bottomSheetDialog.findViewById(R.id.li2);
        LinearLayout li3 = bottomSheetDialog.findViewById(R.id.li3);
        LinearLayout li4 = bottomSheetDialog.findViewById(R.id.li4);
        LinearLayout li5 = bottomSheetDialog.findViewById(R.id.li5);
        LinearLayout li6 = bottomSheetDialog.findViewById(R.id.li6);

        TextView tv1Ball = bottomSheetDialog.findViewById(R.id.tv1Ball);
        TextView tv2Ball = bottomSheetDialog.findViewById(R.id.tv2ball);
        TextView tv3Ball = bottomSheetDialog.findViewById(R.id.tv3Ball);
        TextView tv4Ball = bottomSheetDialog.findViewById(R.id.tv4Ball);
        TextView tv5Ball = bottomSheetDialog.findViewById(R.id.tv5Ball);
        TextView tv6Ball = bottomSheetDialog.findViewById(R.id.tv6Ball);

        TextView tv1Run = bottomSheetDialog.findViewById(R.id.tv1Run);
        TextView tv1_WD = bottomSheetDialog.findViewById(R.id.tv1_WD);
        TextView tv1_xtra = bottomSheetDialog.findViewById(R.id.tv1_xtra);
        TextView tv1_NB = bottomSheetDialog.findViewById(R.id.tv1_NB);

        TextView tv2Run = bottomSheetDialog.findViewById(R.id.tv2Run);
        TextView tv2_WD = bottomSheetDialog.findViewById(R.id.tv2_WD);
        TextView tv2_xtra = bottomSheetDialog.findViewById(R.id.tv2_xtra);
        TextView tv2_NB = bottomSheetDialog.findViewById(R.id.tv2_NB);

        TextView tv3Run = bottomSheetDialog.findViewById(R.id.tv3Run);
        TextView tv3_WD = bottomSheetDialog.findViewById(R.id.tv3_WD);
        TextView tv3_xtra = bottomSheetDialog.findViewById(R.id.tv3_xtra);
        TextView tv3_NB = bottomSheetDialog.findViewById(R.id.tv3_NB);

        TextView tv4Run = bottomSheetDialog.findViewById(R.id.tv4Run);
        TextView tv4_WD = bottomSheetDialog.findViewById(R.id.tv4_WD);
        TextView tv4_xtra = bottomSheetDialog.findViewById(R.id.tv4_xtra);
        TextView tv4_NB = bottomSheetDialog.findViewById(R.id.tv4_NB);

        TextView tv5Run = bottomSheetDialog.findViewById(R.id.tv5Run);
        TextView tv5_WD = bottomSheetDialog.findViewById(R.id.tv5_WD);
        TextView tv5_xtra = bottomSheetDialog.findViewById(R.id.tv5_xtra);
        TextView tv5_NB = bottomSheetDialog.findViewById(R.id.tv5_NB);

        TextView tv6Run = bottomSheetDialog.findViewById(R.id.tv6Run);
        TextView tv6_WD = bottomSheetDialog.findViewById(R.id.tv6_WD);
        TextView tv6_xtra = bottomSheetDialog.findViewById(R.id.tv6_xtra);
        TextView tv6_NB = bottomSheetDialog.findViewById(R.id.tv6_NB);

        Animation animationBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlink1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlink2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlink3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlink4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlink6 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlinkwb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlinknb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlinkwi = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Animation animationBlinkex = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);


        TextView tv_one = bottomSheetDialog.findViewById(R.id.tv_one);
        TextView tv_zero = bottomSheetDialog.findViewById(R.id.tv_zero);
        TextView tv_two = bottomSheetDialog.findViewById(R.id.tv_two);
        TextView tv_three = bottomSheetDialog.findViewById(R.id.tv_three);
        TextView tv_four = bottomSheetDialog.findViewById(R.id.tv_four);
        TextView tv_six = bottomSheetDialog.findViewById(R.id.tv_six);
        TextView tv_WD = bottomSheetDialog.findViewById(R.id.tv_WD);
        TextView tv_NB = bottomSheetDialog.findViewById(R.id.tv_NB);
        TextView tv_wicket = bottomSheetDialog.findViewById(R.id.tv_wicket);
        TextView tv_extras = bottomSheetDialog.findViewById(R.id.tv_extras);


        ArrayList<DataModel> option_status_list = new ArrayList<>();
        option_status_list.add(new DataModel("Bowled"));
        option_status_list.add(new DataModel("Caught"));
        option_status_list.add(new DataModel("Caught Behind"));
        option_status_list.add(new DataModel("Caught & Bowled"));
        option_status_list.add(new DataModel("Stumped"));
        option_status_list.add(new DataModel("Run Out"));
        option_status_list.add(new DataModel("LBW"));
        option_status_list.add(new DataModel("Hit Wicket"));
        option_status_list.add(new DataModel("Retired Hurt"));
        option_status_list.add(new DataModel("Retired Out"));
        option_status_list.add(new DataModel("Run Out (Mankaded)"));
        option_status_list.add(new DataModel("Absent Hurt"));
        option_status_list.add(new DataModel("Hit the Ball Twice"));
        option_status_list.add(new DataModel("Obstr,the Field"));
        option_status_list.add(new DataModel("Timed Out"));
        option_status_list.add(new DataModel("Retired"));
        SelectStatusType drop_tvSelectWicketType = bottomSheetDialog.findViewById(R.id.drop_tvSelectWicketType);
        drop_tvSelectWicketType.setOptionList(option_status_list);
        drop_tvSelectWicketType.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                wayOfOut = name;
            }


            @Override
            public void onDismiss() {
            }
        });


        LinearLayout li_forCommon = bottomSheetDialog.findViewById(R.id.li_forCommon);
        LinearLayout li_wi = bottomSheetDialog.findViewById(R.id.li_wi);
        LinearLayout li_nb = bottomSheetDialog.findViewById(R.id.li_nb);

        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = (RadioButton) bottomSheetDialog.findViewById(checkedId);
            Toaster.customToast(rb.getText().toString());
        });

        LinearLayout li_wd = bottomSheetDialog.findViewById(R.id.li_wd);

        TextView tv_extraRunWD = bottomSheetDialog.findViewById(R.id.tv_extraRunWD);
        TextView tv_finalRunWD = bottomSheetDialog.findViewById(R.id.tv_finalRunWD);
        TextView tv_extraNoBallRun = bottomSheetDialog.findViewById(R.id.tv_extraNoBallRun);
        TextView tv_extraNoBulFinalRun = bottomSheetDialog.findViewById(R.id.tv_extraNoBulFinalRun);

        MaterialButton mb_cancel_wd = bottomSheetDialog.findViewById(R.id.mb_cancel_wd);
        MaterialButton mb_submit_wd = bottomSheetDialog.findViewById(R.id.mb_submit_wd);
        MaterialButton mb_add_cancel_nb = bottomSheetDialog.findViewById(R.id.mb_add_cancel_nb);
        MaterialButton mb_submit_nb = bottomSheetDialog.findViewById(R.id.mb_submit_nb);
        MaterialButton mb_cancel_wi = bottomSheetDialog.findViewById(R.id.mb_cancel_wi);
        MaterialButton mb_submit_wi = bottomSheetDialog.findViewById(R.id.mb_submit_wi);

        mb_cancel_wd.setOnClickListener(v -> {
            tv_extraRunWD.setText(String.valueOf(0));
            tv_finalRunWD.setText(String.valueOf(1));
            if (li_forCommon.getVisibility() == View.GONE) {
                li_forCommon.setVisibility(View.VISIBLE);
            }
            if (li_wd.getVisibility() == View.VISIBLE) {
                li_wd.setVisibility(View.GONE);
                tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
                tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));

                tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
                tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_one.setBackgroundResource(R.drawable.add_post_background_left);
                tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_two.setBackgroundResource(R.drawable.add_post_background_left);
                tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_three.setBackgroundResource(R.drawable.add_post_background_left);
                tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_four.setBackgroundResource(R.drawable.add_post_background_left);
                tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_six.setBackgroundResource(R.drawable.add_post_background_left);
                tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));

            }
        });
        mb_submit_wd.setOnClickListener(v -> {

            if (li_forCommon.getVisibility() == View.GONE) {
                li_forCommon.setVisibility(View.VISIBLE);
            }
            if (li_wd.getVisibility() == View.VISIBLE) {
                li_wd.setVisibility(View.GONE);
                tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
                tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));

                tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
                tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_one.setBackgroundResource(R.drawable.add_post_background_left);
                tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_two.setBackgroundResource(R.drawable.add_post_background_left);
                tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_three.setBackgroundResource(R.drawable.add_post_background_left);
                tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_four.setBackgroundResource(R.drawable.add_post_background_left);
                tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_six.setBackgroundResource(R.drawable.add_post_background_left);
                tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            }

            tv_extraRunWD.setText(String.valueOf(0));
            tv_finalRunWD.setText(String.valueOf(1));

        });

        mb_add_cancel_nb.setOnClickListener(v -> {
            tv_extraNoBallRun.setText(String.valueOf(0));
            tv_extraNoBulFinalRun.setText(String.valueOf(1));
            if (li_forCommon.getVisibility() == View.GONE) {
                li_forCommon.setVisibility(View.VISIBLE);
            }
            if (li_nb.getVisibility() == View.VISIBLE) {
                li_nb.setVisibility(View.GONE);
                tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
                tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));

                tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
                tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_one.setBackgroundResource(R.drawable.add_post_background_left);
                tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_two.setBackgroundResource(R.drawable.add_post_background_left);
                tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_three.setBackgroundResource(R.drawable.add_post_background_left);
                tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_four.setBackgroundResource(R.drawable.add_post_background_left);
                tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_six.setBackgroundResource(R.drawable.add_post_background_left);
                tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));

            }
        });
        mb_submit_nb.setOnClickListener(v -> {
            if (li_forCommon.getVisibility() == View.GONE) {
                li_forCommon.setVisibility(View.VISIBLE);
            }


            if (li_nb.getVisibility() == View.VISIBLE) {
                li_nb.setVisibility(View.GONE);
                tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
                tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));

                tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
                tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_one.setBackgroundResource(R.drawable.add_post_background_left);
                tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_two.setBackgroundResource(R.drawable.add_post_background_left);
                tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_three.setBackgroundResource(R.drawable.add_post_background_left);
                tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_four.setBackgroundResource(R.drawable.add_post_background_left);
                tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_six.setBackgroundResource(R.drawable.add_post_background_left);
                tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));

            }


            tv_extraNoBallRun.setText(String.valueOf(0));
            tv_extraNoBulFinalRun.setText(String.valueOf(1));
        });

        mb_cancel_wi.setOnClickListener(v -> {
            drop_tvSelectWicketType.setText("");
            if (li_forCommon.getVisibility() == View.GONE) {
                li_forCommon.setVisibility(View.VISIBLE);
            }
            if (li_wi.getVisibility() == View.VISIBLE) {
                li_wi.setVisibility(View.GONE);
                tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
                tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));

                tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
                tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_one.setBackgroundResource(R.drawable.add_post_background_left);
                tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_two.setBackgroundResource(R.drawable.add_post_background_left);
                tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_three.setBackgroundResource(R.drawable.add_post_background_left);
                tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_four.setBackgroundResource(R.drawable.add_post_background_left);
                tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_six.setBackgroundResource(R.drawable.add_post_background_left);
                tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));

            }
        });
        mb_submit_wi.setOnClickListener(v -> {
            drop_tvSelectWicketType.setText("");
            Toaster.customToast(wayOfOut);
            if (li_forCommon.getVisibility() == View.GONE) {
                li_forCommon.setVisibility(View.VISIBLE);
            }
            if (li_wi.getVisibility() == View.VISIBLE) {
                li_wi.setVisibility(View.GONE);
                tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
                tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));

                tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
                tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_one.setBackgroundResource(R.drawable.add_post_background_left);
                tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_two.setBackgroundResource(R.drawable.add_post_background_left);
                tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_three.setBackgroundResource(R.drawable.add_post_background_left);
                tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_four.setBackgroundResource(R.drawable.add_post_background_left);
                tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
                tv_six.setBackgroundResource(R.drawable.add_post_background_left);
                tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));

            }
        });

        tv_zero.setOnClickListener(v -> {

            if (SessionManager.getWWDotBoolean() == false) {
                startActivity(new Intent(mContext, WagonWheelActivity.class));
            }


            typedRun = 0;

            typedBall = typedBall + 0.1f;

            if (li_wd.getVisibility() == View.VISIBLE) {
                tv_extraRunWD.setText(String.valueOf(typedRun));
                tv_finalRunWD.setText(String.valueOf(1 + typedRun));
            } else if (li_nb.getVisibility() == View.VISIBLE) {
                tv_extraNoBallRun.setText(String.valueOf(typedRun));
                tv_extraNoBulFinalRun.setText(String.valueOf(1 + typedRun));
            } else {
                if (tv1Run.getText().toString().isEmpty()) {
                    tv1Run.setText(String.valueOf(typedRun));
                    li2.setVisibility(View.VISIBLE);
                    tv2Ball.setText(String.valueOf(typedBall));
                } else if (tv2Run.getText().toString().isEmpty()) {
                    tv2Run.setText(String.valueOf(typedRun));
                    li3.setVisibility(View.VISIBLE);
                    tv3Ball.setText(String.valueOf(typedBall));
                } else if (tv3Run.getText().toString().isEmpty()) {
                    tv3Run.setText(String.valueOf(typedRun));
                    li4.setVisibility(View.VISIBLE);
                    tv4Ball.setText(String.valueOf(typedBall));
                } else if (tv4Run.getText().toString().isEmpty()) {
                    tv4Run.setText(String.valueOf(typedRun));
                    li5.setVisibility(View.VISIBLE);
                    tv5Ball.setText(String.valueOf(typedBall));
                } else if (tv5Run.getText().toString().isEmpty()) {
                    tv5Run.setText(String.valueOf(typedRun));
                    li6.setVisibility(View.VISIBLE);
                    tv6Ball.setText(String.valueOf(typedBall));
                } else if (tv6Run.getText().toString().isEmpty()) {
                    tv6Run.setText(String.valueOf(typedRun));
                }
            }

            textToSpeech.speak("No Run Scored", TextToSpeech.QUEUE_FLUSH, null);


            tv_zero.startAnimation(animationBlink);
            tv_zero.setBackgroundResource(R.drawable.rectangle_gradient);
            tv_zero.setTextColor(getResources().getColor(R.color.black));

            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));
        });

        tv_one.setOnClickListener(v -> {
            if (SessionManager.getWW1sBoolean() == false) {
                startActivity(new Intent(mContext, WagonWheelActivity.class));
            }

            typedRun = 1;
            typedBall = typedBall + 0.1f;
            if (li_wd.getVisibility() == View.VISIBLE) {
                tv_extraRunWD.setText(String.valueOf(typedRun));
                tv_finalRunWD.setText(String.valueOf(1 + typedRun));
            } else if (li_nb.getVisibility() == View.VISIBLE) {
                tv_extraNoBallRun.setText(String.valueOf(typedRun));
                tv_extraNoBulFinalRun.setText(String.valueOf(1 + typedRun));
            } else {
                if (tv1Run.getText().toString().isEmpty()) {
                    tv1Run.setText(String.valueOf(typedRun));
                    li2.setVisibility(View.VISIBLE);
                    tv2Ball.setText(String.valueOf(typedBall));
                } else if (tv2Run.getText().toString().isEmpty()) {
                    tv2Run.setText(String.valueOf(typedRun));
                    li3.setVisibility(View.VISIBLE);
                    tv3Ball.setText(String.valueOf(typedBall));
                } else if (tv3Run.getText().toString().isEmpty()) {
                    tv3Run.setText(String.valueOf(typedRun));
                    li4.setVisibility(View.VISIBLE);
                    tv4Ball.setText(String.valueOf(typedBall));
                } else if (tv4Run.getText().toString().isEmpty()) {
                    tv4Run.setText(String.valueOf(typedRun));
                    li5.setVisibility(View.VISIBLE);
                    tv5Ball.setText(String.valueOf(typedBall));
                } else if (tv5Run.getText().toString().isEmpty()) {
                    tv5Run.setText(String.valueOf(typedRun));
                    li6.setVisibility(View.VISIBLE);
                    tv6Ball.setText(String.valueOf(typedBall));
                } else if (tv6Run.getText().toString().isEmpty()) {
                    tv6Run.setText(String.valueOf(typedRun));
                }
            }
            textToSpeech.speak(tv_one.getText().toString() + " " + "Run", TextToSpeech.QUEUE_FLUSH, null);
            tv_one.startAnimation(animationBlink1);
            tv_one.setBackgroundResource(R.drawable.rectangle_gradient);
            tv_one.setTextColor(getResources().getColor(R.color.black));


            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));


        });

        tv_two.setOnClickListener(v -> {
            if (SessionManager.getWW1sBoolean() == false) {
                startActivity(new Intent(mContext, WagonWheelActivity.class));
            }
            typedRun = 2;
            typedBall = typedBall + 0.1f;

            if (li_wd.getVisibility() == View.VISIBLE) {
                tv_extraRunWD.setText(String.valueOf(typedRun));
                tv_finalRunWD.setText(String.valueOf(1 + typedRun));
            } else if (li_nb.getVisibility() == View.VISIBLE) {
                tv_extraNoBallRun.setText(String.valueOf(typedRun));
                tv_extraNoBulFinalRun.setText(String.valueOf(1 + typedRun));
            } else {
                if (tv1Run.getText().toString().isEmpty()) {
                    tv1Run.setText(String.valueOf(typedRun));
                    li2.setVisibility(View.VISIBLE);
                    tv2Ball.setText(String.valueOf(typedBall));
                } else if (tv2Run.getText().toString().isEmpty()) {
                    tv2Run.setText(String.valueOf(typedRun));
                    li3.setVisibility(View.VISIBLE);
                    tv3Ball.setText(String.valueOf(typedBall));
                } else if (tv3Run.getText().toString().isEmpty()) {
                    tv3Run.setText(String.valueOf(typedRun));
                    li4.setVisibility(View.VISIBLE);
                    tv4Ball.setText(String.valueOf(typedBall));
                } else if (tv4Run.getText().toString().isEmpty()) {
                    tv4Run.setText(String.valueOf(typedRun));
                    li5.setVisibility(View.VISIBLE);
                    tv5Ball.setText(String.valueOf(typedBall));
                } else if (tv5Run.getText().toString().isEmpty()) {
                    tv5Run.setText(String.valueOf(typedRun));
                    li6.setVisibility(View.VISIBLE);
                    tv6Ball.setText(String.valueOf(typedBall));
                } else if (tv6Run.getText().toString().isEmpty()) {
                    tv6Run.setText(String.valueOf(typedRun));
                }
            }

            textToSpeech.speak(tv_two.getText().toString() + " " + "Runs", TextToSpeech.QUEUE_FLUSH, null);
            tv_two.startAnimation(animationBlink2);
            tv_two.setBackgroundDrawable(getDrawable(R.drawable.rectangle_gradient));
            tv_two.setTextColor(getResources().getColor(R.color.black));

            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));
        });

        tv_three.setOnClickListener(v -> {
            if (SessionManager.getWW1sBoolean() == false) {
                startActivity(new Intent(mContext, WagonWheelActivity.class));
            }
            typedRun = 3;
            typedBall = typedBall + 0.1f;

            if (li_wd.getVisibility() == View.VISIBLE) {
                tv_extraRunWD.setText(String.valueOf(typedRun));
                tv_finalRunWD.setText(String.valueOf(1 + typedRun));
            } else if (li_nb.getVisibility() == View.VISIBLE) {
                tv_extraNoBallRun.setText(String.valueOf(typedRun));
                tv_extraNoBulFinalRun.setText(String.valueOf(1 + typedRun));
            } else {
                if (tv1Run.getText().toString().isEmpty()) {
                    tv1Run.setText(String.valueOf(typedRun));
                    li2.setVisibility(View.VISIBLE);
                    tv2Ball.setText(String.valueOf(typedBall));
                } else if (tv2Run.getText().toString().isEmpty()) {
                    tv2Run.setText(String.valueOf(typedRun));
                    li3.setVisibility(View.VISIBLE);
                    tv3Ball.setText(String.valueOf(typedBall));
                } else if (tv3Run.getText().toString().isEmpty()) {
                    tv3Run.setText(String.valueOf(typedRun));
                    li4.setVisibility(View.VISIBLE);
                    tv4Ball.setText(String.valueOf(typedBall));
                } else if (tv4Run.getText().toString().isEmpty()) {
                    tv4Run.setText(String.valueOf(typedRun));
                    li5.setVisibility(View.VISIBLE);
                    tv5Ball.setText(String.valueOf(typedBall));
                } else if (tv5Run.getText().toString().isEmpty()) {
                    tv5Run.setText(String.valueOf(typedRun));
                    li6.setVisibility(View.VISIBLE);
                    tv6Ball.setText(String.valueOf(typedBall));
                } else if (tv6Run.getText().toString().isEmpty()) {
                    tv6Run.setText(String.valueOf(typedRun));
                }
            }

            textToSpeech.speak(tv_three.getText().toString() + " " + "Runs", TextToSpeech.QUEUE_FLUSH, null);
            tv_three.startAnimation(animationBlink3);
            tv_three.setBackgroundResource(R.drawable.rectangle_gradient);
            tv_three.setTextColor(getResources().getColor(R.color.black));


            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));
        });

        tv_four.setOnClickListener(v -> {
            startActivity(new Intent(mContext, WagonWheelActivity.class));
            typedRun = 4;
            typedBall = typedBall + 0.1f;

            if (li_wd.getVisibility() == View.VISIBLE) {
                tv_extraRunWD.setText(String.valueOf(typedRun));
                tv_finalRunWD.setText(String.valueOf(1 + typedRun));
            } else if (li_nb.getVisibility() == View.VISIBLE) {
                tv_extraNoBallRun.setText(String.valueOf(typedRun));
                tv_extraNoBulFinalRun.setText(String.valueOf(1 + typedRun));
            } else {
                if (tv1Run.getText().toString().isEmpty()) {
                    tv1Run.setText(String.valueOf(typedRun));
                    li2.setVisibility(View.VISIBLE);
                    tv2Ball.setText(String.valueOf(typedBall));
                } else if (tv2Run.getText().toString().isEmpty()) {
                    tv2Run.setText(String.valueOf(typedRun));
                    li3.setVisibility(View.VISIBLE);
                    tv3Ball.setText(String.valueOf(typedBall));
                } else if (tv3Run.getText().toString().isEmpty()) {
                    tv3Run.setText(String.valueOf(typedRun));
                    li4.setVisibility(View.VISIBLE);
                    tv4Ball.setText(String.valueOf(typedBall));
                } else if (tv4Run.getText().toString().isEmpty()) {
                    tv4Run.setText(String.valueOf(typedRun));
                    li5.setVisibility(View.VISIBLE);
                    tv5Ball.setText(String.valueOf(typedBall));
                } else if (tv5Run.getText().toString().isEmpty()) {
                    tv5Run.setText(String.valueOf(typedRun));
                    li6.setVisibility(View.VISIBLE);
                    tv6Ball.setText(String.valueOf(typedBall));
                } else if (tv6Run.getText().toString().isEmpty()) {
                    tv6Run.setText(String.valueOf(typedRun));
                }
            }


            textToSpeech.speak(tv_four.getText().toString() + " " + "Runs", TextToSpeech.QUEUE_FLUSH, null);
            tv_four.startAnimation(animationBlink4);

            tv_four.setBackgroundResource(R.drawable.rectangle_gradient);
            tv_four.setTextColor(getResources().getColor(R.color.black));

            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));
        });

        tv_six.setOnClickListener(v -> {
            startActivity(new Intent(mContext, WagonWheelActivity.class));
            typedRun = 6;
            typedBall = typedBall + 0.1f;

            if (li_wd.getVisibility() == View.VISIBLE) {
                tv_extraRunWD.setText(String.valueOf(typedRun));
                tv_finalRunWD.setText(String.valueOf(1 + typedRun));
            } else if (li_nb.getVisibility() == View.VISIBLE) {
                tv_extraNoBallRun.setText(String.valueOf(typedRun));
                tv_extraNoBulFinalRun.setText(String.valueOf(1 + typedRun));
            } else {
                if (tv1Run.getText().toString().isEmpty()) {
                    tv1Run.setText(String.valueOf(typedRun));
                    li2.setVisibility(View.VISIBLE);
                    tv2Ball.setText(String.valueOf(typedBall));
                } else if (tv2Run.getText().toString().isEmpty()) {
                    tv2Run.setText(String.valueOf(typedRun));
                    li3.setVisibility(View.VISIBLE);
                    tv3Ball.setText(String.valueOf(typedBall));
                } else if (tv3Run.getText().toString().isEmpty()) {
                    tv3Run.setText(String.valueOf(typedRun));
                    li4.setVisibility(View.VISIBLE);
                    tv4Ball.setText(String.valueOf(typedBall));
                } else if (tv4Run.getText().toString().isEmpty()) {
                    tv4Run.setText(String.valueOf(typedRun));
                    li5.setVisibility(View.VISIBLE);
                    tv5Ball.setText(String.valueOf(typedBall));
                } else if (tv5Run.getText().toString().isEmpty()) {
                    tv5Run.setText(String.valueOf(typedRun));
                    li6.setVisibility(View.VISIBLE);
                    tv6Ball.setText(String.valueOf(typedBall));
                } else if (tv6Run.getText().toString().isEmpty()) {
                    tv6Run.setText(String.valueOf(typedRun));
                }
            }


            textToSpeech.speak(tv_six.getText().toString() + " " + "Runs", TextToSpeech.QUEUE_FLUSH, null);
            tv_six.startAnimation(animationBlink6);
            tv_six.setBackgroundResource(R.drawable.rectangle_gradient);
            tv_six.setTextColor(getResources().getColor(R.color.black));

            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));
        });

        tv_WD.setOnClickListener(v -> {

            textToSpeech.speak("Its a Wide Ball", TextToSpeech.QUEUE_FLUSH, null);
            tv_WD.startAnimation(animationBlinkwb);
            tv_WD.setBackgroundResource(R.drawable.rectangle_gradient_large);
            tv_WD.setTextColor(getResources().getColor(R.color.black));

            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));

            if (li_forCommon.getVisibility() == View.VISIBLE) {
                li_forCommon.setVisibility(View.GONE);
            }
            if (li_wd.getVisibility() == View.GONE) {
                li_wd.setVisibility(View.VISIBLE);
            }
        });

        tv_NB.setOnClickListener(v -> {

            textToSpeech.speak("Its a No Ball", TextToSpeech.QUEUE_FLUSH, null);
            tv_NB.startAnimation(animationBlinknb);
            tv_NB.setBackgroundResource(R.drawable.rectangle_gradient_large);
            tv_NB.setTextColor(getResources().getColor(R.color.black));

            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));

            if (li_forCommon.getVisibility() == View.VISIBLE) {
                li_forCommon.setVisibility(View.GONE);
            }
            if (li_nb.getVisibility() == View.GONE) {
                li_nb.setVisibility(View.VISIBLE);
            }
        });

        tv_wicket.setOnClickListener(v -> {
            textToSpeech.speak("That's a wicket", TextToSpeech.QUEUE_FLUSH, null);
            tv_wicket.startAnimation(animationBlinkwi);
            tv_wicket.setBackgroundResource(R.drawable.rectangle_gradient_large);
            tv_wicket.setTextColor(getResources().getColor(R.color.black));

            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_extras.setBackgroundResource(R.drawable.add_post_background_left);
            tv_extras.setTextColor(getResources().getColor(R.color.verified_user_color));

            if (li_forCommon.getVisibility() == View.VISIBLE) {
                li_forCommon.setVisibility(View.GONE);
            }
            if (li_wi.getVisibility() == View.GONE) {
                li_wi.setVisibility(View.VISIBLE);
            }
        });

        tv_extras.setOnClickListener(v -> {
            tv_extras.startAnimation(animationBlinkex);
            tv_extras.setBackgroundResource(R.drawable.rectangle_gradient_large);
            tv_extras.setTextColor(getResources().getColor(R.color.black));


            tv_two.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_two.setBackgroundResource(R.drawable.add_post_background_left);
            tv_one.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_one.setBackgroundResource(R.drawable.add_post_background_left);
            tv_zero.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_zero.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setBackgroundResource(R.drawable.add_post_background_left);
            tv_four.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_six.setBackgroundResource(R.drawable.add_post_background_left);
            tv_six.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_three.setBackgroundResource(R.drawable.add_post_background_left);
            tv_three.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_WD.setBackgroundResource(R.drawable.add_post_background_left);
            tv_WD.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_NB.setBackgroundResource(R.drawable.add_post_background_left);
            tv_NB.setTextColor(getResources().getColor(R.color.verified_user_color));
            tv_wicket.setBackgroundResource(R.drawable.add_post_background_left);
            tv_wicket.setTextColor(getResources().getColor(R.color.verified_user_color));
        });

        li_startSecondInning.setOnClickListener(v -> {
            mb_end_over.setVisibility(View.VISIBLE);
            li_keyboard.setVisibility(View.VISIBLE);
            li_startInningEndSession.setVisibility(View.GONE);
        });
        li_endScoringSession.setOnClickListener(v -> {
            activityDashboardLiveScoringBinding.tvStartScoring.setBackgroundColor(getResources().getColor(R.color.verified_user_color));
            activityDashboardLiveScoringBinding.tvScorecard.setBackgroundColor(getResources().getColor(R.color.dim_sky));
            activityDashboardLiveScoringBinding.liScorecard.setVisibility(View.VISIBLE);
            activityDashboardLiveScoringBinding.rlStartScoring.setVisibility(View.GONE);
            li_keyboard.setVisibility(View.VISIBLE);
            li_startInningEndSession.setVisibility(View.GONE);
//            startActivity(new Intent(mContext,ScorecardActivity.class));
//            finish();
        });

        mb_end_over.setVisibility(View.VISIBLE);
        mb_end_over.setOnClickListener(v -> {

            li2.setVisibility(View.GONE);
            li3.setVisibility(View.GONE);
            li4.setVisibility(View.GONE);
            li5.setVisibility(View.GONE);
            li6.setVisibility(View.GONE);
            tv1Run.setText("");

//            if(tv6Ball.getText().toString().equalsIgnoreCase("0.6")){
//                typedBall= typedBall+0.1f;
//                li1.setVisibility(View.VISIBLE);
//                tv1Ball.setText(String.valueOf(typedBall));
//                Log.d("typedBal",typedBall+"");
//            }


//            for(float j = 0.1f ; j<0.6f;j = j + 0.1f){
//                Log.d("size",j+1.0f+"");
//            }

//           number++;
//            if(number == 14){
//                mb_end_over.setVisibility(View.GONE);
//                li_keyboard.setVisibility(View.GONE);
//                li_startInningEndSession.setVisibility(View.VISIBLE);
//                tv_StartsecondInning.setText("RCB won by 14 wkts");
//            }

            showBottomBowlingSheetDialog("ChangeBowler");
        });

    }

    private void changeScorerShowBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
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

    private void showBottomStopMatchSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_stop_scoring_of_match);
        if (bottomSheetDialog.getWindow() != null)
            bottomSheetDialog.getWindow().setDimAmount(0);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);


        MaterialCardView mcv_drinks = bottomSheetDialog.findViewById(R.id.mcv_drinks);
        ImageView iv_drinks = bottomSheetDialog.findViewById(R.id.iv_drinks);
        TextView tvDrinks = bottomSheetDialog.findViewById(R.id.tvDrinks);

        MaterialCardView mcv_rain = bottomSheetDialog.findViewById(R.id.mcv_rain);
        ImageView ivRain = bottomSheetDialog.findViewById(R.id.ivRain);
        TextView tvRain = bottomSheetDialog.findViewById(R.id.tvRain);

        MaterialCardView mcv_stumps = bottomSheetDialog.findViewById(R.id.mcv_stumps);
        ImageView ivStumps = bottomSheetDialog.findViewById(R.id.ivStumps);
        TextView tvStumps = bottomSheetDialog.findViewById(R.id.tvStumps);

        MaterialCardView mcv_timed_out = bottomSheetDialog.findViewById(R.id.mcv_timed_out);
        ImageView ivTimedOut = bottomSheetDialog.findViewById(R.id.ivTimedOut);
        TextView tvTimedOut = bottomSheetDialog.findViewById(R.id.tvTimedOut);

        MaterialCardView mcv_lunch = bottomSheetDialog.findViewById(R.id.mcv_lunch);
        ImageView ivLunch = bottomSheetDialog.findViewById(R.id.ivLunch);
        TextView tvLunch = bottomSheetDialog.findViewById(R.id.tvLunch);

        MaterialCardView mcv_others = bottomSheetDialog.findViewById(R.id.mcv_others);
        ImageView ivOthers = bottomSheetDialog.findViewById(R.id.ivOthers);
        TextView tvOthers = bottomSheetDialog.findViewById(R.id.tvOthers);

        MaterialCardView mcv_scoring_mistake = bottomSheetDialog.findViewById(R.id.mcv_scoring_mistake);
        ImageView ivScoringMistake = bottomSheetDialog.findViewById(R.id.ivScoringMistake);
        TextView tvScoringMistake = bottomSheetDialog.findViewById(R.id.tvScoringMistake);

        MaterialCardView mcv_change_scorer = bottomSheetDialog.findViewById(R.id.mcv_change_scorer);
        ImageView ivChangeScorer = bottomSheetDialog.findViewById(R.id.ivChangeScorer);
        TextView tvChangeScorer = bottomSheetDialog.findViewById(R.id.tvChangeScorer);

        mcv_drinks.setOnClickListener(view -> {

            iv_drinks.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDrinks.setTextColor(mContext.getColor(R.color.lemon_color));
                tvDrinks.setTextSize(20);
            }

            ivRain.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRain.setTextColor(mContext.getColor(R.color.dark_grey));
                tvRain.setTextSize(14);
            }

            ivStumps.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStumps.setTextColor(mContext.getColor(R.color.dark_grey));
                tvStumps.setTextSize(14);
            }
            ivLunch.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvLunch.setTextColor(mContext.getColor(R.color.dark_grey));
                tvLunch.setTextSize(14);
            }
            ivTimedOut.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTimedOut.setTextColor(mContext.getColor(R.color.dark_grey));
                tvTimedOut.setTextSize(14);
            }
            ivOthers.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvOthers.setTextColor(mContext.getColor(R.color.dark_grey));
                tvOthers.setTextSize(14);
            }
            ivScoringMistake.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvScoringMistake.setTextColor(mContext.getColor(R.color.dark_grey));
                tvScoringMistake.setTextSize(14);
            }
            ivChangeScorer.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvChangeScorer.setTextColor(mContext.getColor(R.color.dark_grey));
                tvChangeScorer.setTextSize(14);
            }


            mcv_drinks.setBackgroundColor(getResources().getColor(R.color.green));
            mcv_rain.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_stumps.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_timed_out.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_lunch.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_others.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_scoring_mistake.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_change_scorer.setBackgroundColor(getResources().getColor(R.color.white));
        });
        mcv_rain.setOnClickListener(view -> {

            ivRain.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRain.setTextColor(mContext.getColor(R.color.lemon_color));
                tvRain.setTextSize(20);
            }


            iv_drinks.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDrinks.setTextColor(mContext.getColor(R.color.dark_grey));
                tvDrinks.setTextSize(14);
            }

            ivStumps.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStumps.setTextColor(mContext.getColor(R.color.dark_grey));
                tvStumps.setTextSize(14);
            }
            ivLunch.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvLunch.setTextColor(mContext.getColor(R.color.dark_grey));
                tvLunch.setTextSize(14);
            }
            ivTimedOut.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTimedOut.setTextColor(mContext.getColor(R.color.dark_grey));
                tvTimedOut.setTextSize(14);
            }
            ivOthers.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvOthers.setTextColor(mContext.getColor(R.color.dark_grey));
                tvOthers.setTextSize(14);
            }
            ivScoringMistake.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvScoringMistake.setTextColor(mContext.getColor(R.color.dark_grey));
                tvScoringMistake.setTextSize(14);
            }
            ivChangeScorer.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvChangeScorer.setTextColor(mContext.getColor(R.color.dark_grey));
                tvChangeScorer.setTextSize(14);
            }

            mcv_rain.setBackgroundColor(getResources().getColor(R.color.green));
            mcv_drinks.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_stumps.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_timed_out.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_lunch.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_others.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_scoring_mistake.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_change_scorer.setBackgroundColor(getResources().getColor(R.color.white));
        });
        mcv_stumps.setOnClickListener(view -> {

            ivRain.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRain.setTextColor(mContext.getColor(R.color.dark_grey));
                tvRain.setTextSize(14);
            }


            iv_drinks.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDrinks.setTextColor(mContext.getColor(R.color.dark_grey));
                tvDrinks.setTextSize(14);
            }

            ivStumps.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStumps.setTextColor(mContext.getColor(R.color.lemon_color));
                tvStumps.setTextSize(20);
            }
            ivLunch.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvLunch.setTextColor(mContext.getColor(R.color.dark_grey));
                tvLunch.setTextSize(14);
            }
            ivTimedOut.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTimedOut.setTextColor(mContext.getColor(R.color.dark_grey));
                tvTimedOut.setTextSize(14);
            }
            ivOthers.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvOthers.setTextColor(mContext.getColor(R.color.dark_grey));
                tvOthers.setTextSize(14);
            }
            ivScoringMistake.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvScoringMistake.setTextColor(mContext.getColor(R.color.dark_grey));
                tvScoringMistake.setTextSize(14);
            }
            ivChangeScorer.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvChangeScorer.setTextColor(mContext.getColor(R.color.dark_grey));
                tvChangeScorer.setTextSize(14);
            }

            mcv_stumps.setBackgroundColor(getResources().getColor(R.color.green));
            mcv_drinks.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_rain.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_timed_out.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_lunch.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_others.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_scoring_mistake.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_change_scorer.setBackgroundColor(getResources().getColor(R.color.white));
        });
        mcv_timed_out.setOnClickListener(view -> {
            ivRain.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRain.setTextColor(mContext.getColor(R.color.dark_grey));
                tvRain.setTextSize(14);
            }


            iv_drinks.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDrinks.setTextColor(mContext.getColor(R.color.dark_grey));
                tvDrinks.setTextSize(14);
            }

            ivStumps.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStumps.setTextColor(mContext.getColor(R.color.dark_grey));
                tvStumps.setTextSize(14);
            }
            ivLunch.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvLunch.setTextColor(mContext.getColor(R.color.dark_grey));
                tvLunch.setTextSize(14);
            }
            ivTimedOut.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTimedOut.setTextColor(mContext.getColor(R.color.lemon_color));
                tvTimedOut.setTextSize(20);
            }
            ivOthers.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvOthers.setTextColor(mContext.getColor(R.color.dark_grey));
                tvOthers.setTextSize(14);
            }
            ivScoringMistake.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvScoringMistake.setTextColor(mContext.getColor(R.color.dark_grey));
                tvScoringMistake.setTextSize(14);
            }
            ivChangeScorer.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvChangeScorer.setTextColor(mContext.getColor(R.color.dark_grey));
                tvChangeScorer.setTextSize(14);
            }

            mcv_timed_out.setBackgroundColor(getResources().getColor(R.color.green));
            mcv_drinks.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_rain.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_stumps.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_lunch.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_others.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_scoring_mistake.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_change_scorer.setBackgroundColor(getResources().getColor(R.color.white));
        });
        mcv_lunch.setOnClickListener(view -> {

            ivRain.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRain.setTextColor(mContext.getColor(R.color.dark_grey));
                tvRain.setTextSize(14);
            }


            iv_drinks.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDrinks.setTextColor(mContext.getColor(R.color.dark_grey));
                tvDrinks.setTextSize(14);
            }

            ivStumps.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStumps.setTextColor(mContext.getColor(R.color.dark_grey));
                tvStumps.setTextSize(14);
            }
            ivLunch.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvLunch.setTextColor(mContext.getColor(R.color.lemon_color));
                tvLunch.setTextSize(20);
            }
            ivTimedOut.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTimedOut.setTextColor(mContext.getColor(R.color.dark_grey));
                tvTimedOut.setTextSize(14);
            }
            ivOthers.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvOthers.setTextColor(mContext.getColor(R.color.dark_grey));
                tvOthers.setTextSize(14);
            }
            ivScoringMistake.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvScoringMistake.setTextColor(mContext.getColor(R.color.dark_grey));
                tvScoringMistake.setTextSize(14);
            }
            ivChangeScorer.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvChangeScorer.setTextColor(mContext.getColor(R.color.dark_grey));
                tvChangeScorer.setTextSize(14);
            }

            mcv_lunch.setBackgroundColor(getResources().getColor(R.color.green));
            mcv_drinks.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_rain.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_stumps.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_timed_out.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_others.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_scoring_mistake.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_change_scorer.setBackgroundColor(getResources().getColor(R.color.white));
        });
        mcv_others.setOnClickListener(view -> {
            ivRain.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRain.setTextColor(mContext.getColor(R.color.dark_grey));
                tvRain.setTextSize(14);
            }


            iv_drinks.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDrinks.setTextColor(mContext.getColor(R.color.dark_grey));
                tvDrinks.setTextSize(14);
            }

            ivStumps.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStumps.setTextColor(mContext.getColor(R.color.dark_grey));
                tvStumps.setTextSize(14);
            }
            ivLunch.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvLunch.setTextColor(mContext.getColor(R.color.dark_grey));
                tvLunch.setTextSize(14);
            }
            ivTimedOut.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTimedOut.setTextColor(mContext.getColor(R.color.dark_grey));
                tvTimedOut.setTextSize(14);
            }
            ivOthers.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvOthers.setTextColor(mContext.getColor(R.color.lemon_color));
                tvOthers.setTextSize(20);
            }
            ivScoringMistake.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvScoringMistake.setTextColor(mContext.getColor(R.color.dark_grey));
                tvScoringMistake.setTextSize(14);
            }
            ivChangeScorer.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvChangeScorer.setTextColor(mContext.getColor(R.color.dark_grey));
                tvChangeScorer.setTextSize(14);
            }

            mcv_others.setBackgroundColor(getResources().getColor(R.color.green));
            mcv_drinks.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_rain.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_stumps.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_timed_out.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_lunch.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_scoring_mistake.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_change_scorer.setBackgroundColor(getResources().getColor(R.color.white));
        });
        mcv_scoring_mistake.setOnClickListener(view -> {

            ivRain.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRain.setTextColor(mContext.getColor(R.color.dark_grey));
                tvRain.setTextSize(14);
            }

            iv_drinks.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDrinks.setTextColor(mContext.getColor(R.color.dark_grey));
                tvDrinks.setTextSize(14);
            }

            ivStumps.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStumps.setTextColor(mContext.getColor(R.color.dark_grey));
                tvStumps.setTextSize(14);
            }
            ivLunch.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvLunch.setTextColor(mContext.getColor(R.color.dark_grey));
                tvLunch.setTextSize(14);
            }
            ivTimedOut.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTimedOut.setTextColor(mContext.getColor(R.color.dark_grey));
                tvTimedOut.setTextSize(14);
            }
            ivOthers.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvOthers.setTextColor(mContext.getColor(R.color.dark_grey));
                tvOthers.setTextSize(14);
            }
            ivScoringMistake.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvScoringMistake.setTextColor(mContext.getColor(R.color.lemon_color));
                tvScoringMistake.setTextSize(20);
            }
            ivChangeScorer.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvChangeScorer.setTextColor(mContext.getColor(R.color.dark_grey));
                tvChangeScorer.setTextSize(14);
            }

            mcv_scoring_mistake.setBackgroundColor(getResources().getColor(R.color.green));
            mcv_drinks.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_rain.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_stumps.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_timed_out.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_lunch.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_others.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_change_scorer.setBackgroundColor(getResources().getColor(R.color.white));
        });

        mcv_change_scorer.setOnClickListener(view -> {
            bottomSheetDialog.hide();
            changeScorerShowBottomSheetDialog();

            ivRain.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRain.setTextColor(mContext.getColor(R.color.dark_grey));
                tvRain.setTextSize(14);
            }


            iv_drinks.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDrinks.setTextColor(mContext.getColor(R.color.dark_grey));
                tvDrinks.setTextSize(14);
            }

            ivStumps.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStumps.setTextColor(mContext.getColor(R.color.dark_grey));
                tvStumps.setTextSize(14);
            }
            ivLunch.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvLunch.setTextColor(mContext.getColor(R.color.dark_grey));
                tvLunch.setTextSize(14);
            }
            ivTimedOut.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTimedOut.setTextColor(mContext.getColor(R.color.dark_grey));
                tvTimedOut.setTextSize(14);
            }
            ivOthers.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvOthers.setTextColor(mContext.getColor(R.color.dark_grey));
                tvOthers.setTextSize(14);
            }
            ivScoringMistake.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvScoringMistake.setTextColor(mContext.getColor(R.color.dark_grey));
                tvScoringMistake.setTextSize(14);
            }
            ivChangeScorer.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvChangeScorer.setTextColor(mContext.getColor(R.color.lemon_color));
                tvChangeScorer.setTextSize(20);
            }

            mcv_change_scorer.setBackgroundColor(getResources().getColor(R.color.green));
            mcv_drinks.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_rain.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_stumps.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_timed_out.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_lunch.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_others.setBackgroundColor(getResources().getColor(R.color.white));
            mcv_scoring_mistake.setBackgroundColor(getResources().getColor(R.color.white));
        });

        bottomSheetDialog.findViewById(R.id.mb_save).setOnClickListener(view -> {
            startActivity(new Intent(mContext, StartLiveScoringActivity.class));
            finish();
        });
        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(view -> {
            bottomSheetDialog.hide();
        });

        bottomSheetDialog.show();


    }

}