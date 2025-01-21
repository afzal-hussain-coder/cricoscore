package com.cricoscore.Activity;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cricoscore.Adapter.ShortAreaSubCategoryAdapter;
import com.cricoscore.ParamBody.InningBallParamBody;
import com.cricoscore.ParamBody.InningNewBody;
import com.cricoscore.ParamBody.PlayingSqudUpdateBody;
import com.cricoscore.ParamBody.UpdateScheduleInningParamBody;
import com.cricoscore.R;
import com.cricoscore.Utils.CustomLoaderView;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.BottomDialogShowWagonWheelBinding;
import com.cricoscore.model.Balled;
import com.cricoscore.model.ShortAreaSubCategoryModel;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoringDashBordActivity extends AppCompatActivity {
    CustomLoaderView loaderView;
    private TextView scoreTextView, player1ScoreView, player2ScoreView, bowlerStatsView,
            playerNameStrike, playerNameNonStrike, tvBattingTeamName, tvbowlerName, tossInfo;
    private Button undoButton, btOut, btn57, btnWideBall, btnNoBall, btnLegBye, btnBye;
    private GridLayout scoringGrid;
    ImageView imgBack;
    int firstInningScore = 0;
    int firstInningToatlOver = 0;
    int firstInningTotalWicket = 0;
    String firstInningTeamName = "";

    // Variables to store scores
    private int totalRuns = 0, wickets = 0, overs = 0, balls = 0, actualBall, extraBall;
    private int player1Score = 0, player2Score = 0;
    private boolean isPlayer1OnStrike = true;
    private int lastRun = 0;
    String overValue = "";
    private boolean wasLastWicket = false;
    // Declare variables
    private TextView[] ballIndicators;
    private int currentBall = 0; // 0-based index for current ball
    private int totalOvers = 0;
    private int ballsInCurrentOver = 0; // Reset after 6 balls
    //private int[] runsInCurrentOver = new int[6];
    private List<Integer> runsInCurrentOver = new ArrayList<>();// Stores runs for balls in the current over
    private int currentOverRuns = 0; // Sum of runs in the current over

    int scheduleId, teamIdBatting, inningNumber, currentBowlerId, currentNonStrikerId, currentStrikerId, bowlingTeamId;
    String StrikePlayerName = "", nonStrikeName = "", bowlerName = "", battingTeamName = "";

    private boolean isDialogOpen = false;
    int inning_id;
    Context mContext;
    Activity mActivity;
    int is_boundry = 0;
    String extras = "";
    String wicketType = "";
    int filederId = 0;
    String strike = "";
    String isWicket = "0";
    int lastRunn = 0;
    int current_over_total_ball = 0;

    LinearLayout ballIndicatorsLayout;
    int over_limit_inning = 0;
    int is_inning_completed = 0;
    int totalOversInggingCompleted;
    int bowling_team_id = 0;
    int batting_team_id = 0;
    int targetRun = 0;
    int targetOver = 0;
    String target_msg = "";
    int is_match_completed = 0;
    int selected = 0;
    int runOutPlayerId = 0;
    int finalStrikeId;
    int finalNonStrikeId;

    private String selectedSegment = "";
    private String selectedShortArea = "";
    boolean checkBoxStatus;
    boolean checkBoxDitBallStatus;

    private boolean isShowDialogForRuns = true; // Global variable for 1, 2, 3 runs
    private boolean isShowDialogForBoundaries = true;  // Always true for boundaries
    String short_area="";
    String short_type ="";
    ImageView imgSetting;
    TextView tvStrikerReplace;
    TextView tvNonStrikerReplace;
    String strikeChangeMessage="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring_dash_bord);
        mContext = this;
        mActivity = this;
        // Initialize UI components
        loaderView = CustomLoaderView.initialize(this);
        tossInfo = findViewById(R.id.tossInfo);
        tvBattingTeamName = findViewById(R.id.tvBattingTeamName);
        playerNameStrike = findViewById(R.id.playerNameStrike);
        playerNameNonStrike = findViewById(R.id.playerNameNonStrike);
        tvbowlerName = findViewById(R.id.bowlerName);
        imgBack = findViewById(R.id.imgBack);

        checkBoxStatus = SessionManager.getWW1sBoolean();
        checkBoxDitBallStatus = SessionManager.getWWDotBoolean();


        // Set up the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showBottomStopMatchSheetDialog();
            }
        });

        imgBack.setOnClickListener(v -> {
            showBottomStopMatchSheetDialog();
        });
        //endMatchDialog();
        imgSetting = findViewById(R.id.imgSetting);
        tvStrikerReplace = findViewById(R.id.tvStrikerReplace);
        tvNonStrikerReplace = findViewById(R.id.tvNonStrikerReplace);

        scoreTextView = findViewById(R.id.score);
        player1ScoreView = findViewById(R.id.player1Score);
        player2ScoreView = findViewById(R.id.player2Score);
        playerNameNonStrike = findViewById(R.id.playerNameNonStrike);
        playerNameStrike = findViewById(R.id.playerNameStrike);
        bowlerStatsView = findViewById(R.id.bowlerStats);
        scoringGrid = findViewById(R.id.scoringGrid);
        undoButton = findViewById(R.id.undoButton);
        btOut = findViewById(R.id.btOut);
        btn57 = findViewById(R.id.btnFiveSeven);
        btnWideBall = findViewById(R.id.btnWideBall);
        btnNoBall = findViewById(R.id.btnNoBall);
        btnLegBye = findViewById(R.id.btnLegBye);
        btnBye = findViewById(R.id.btnBye);

        setUpScoringGrid();

        // Initialize ball indicators
//        ballIndicators = new TextView[]{
//                findViewById(R.id.ball1),
//                findViewById(R.id.ball2),
//                findViewById(R.id.ball3),
//                findViewById(R.id.ball4),
//                findViewById(R.id.ball5),
//                findViewById(R.id.ball6)
//        };

        ballIndicatorsLayout = findViewById(R.id.ballIndicators);

        // Set initial score
        // updateScoreDisplay();

        // Set up scoring buttons
        //setupScoringButtons();

        // Set up undo button
        undoButton.setOnClickListener(v -> undoLastAction());

        // Set up wicket button
        btOut.setOnClickListener(v -> {

            outType();


        });

        // Set up "5, 7" button for special input
        btn57.setOnClickListener(v -> showUpdateScoreDialog());

        // Set up wide ball and no-ball buttons
        btnWideBall.setOnClickListener(v -> showWideBallDialog());
        btnNoBall.setOnClickListener(v -> showNoBallDialog());

        // Set up leg bye button
        btnLegBye.setOnClickListener(v -> showLegByeDialog());

        btnBye.setOnClickListener(v -> {
            showByeDialog();
        });

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        tvStrikerReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRunOutSelectionDialog("Strike");
            }
        });
        tvNonStrikerReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRunOutSelectionDialog("NonStrike");
            }
        });

        player2ScoreView.setOnClickListener(v -> {
            showChangeStrikerDialog();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent() != null) {
            currentBowlerId = getIntent().getIntExtra("BowlerId", 0);
            currentStrikerId = getIntent().getIntExtra("StrikerPlayerId", 0);
            currentNonStrikerId = getIntent().getIntExtra("NonStrikerPlayerId", 0);
            bowlingTeamId = getIntent().getIntExtra("BowlingTeamId", 0);
            StrikePlayerName = getIntent().getStringExtra("StrikePlayerName");
            nonStrikeName = getIntent().getStringExtra("NonStrikerName");
            bowlerName = getIntent().getStringExtra("BowlerName");
            battingTeamName = getIntent().getStringExtra("BattingTeamName");
            inningNumber = getIntent().getIntExtra("inning_id", 0);
            scheduleId = getIntent().getIntExtra("scheduleId", 0);
            teamIdBatting = getIntent().getIntExtra("teamIdBatting", 0);

            // Toaster.customToast(currentBowlerId + "//" + currentStrikerId + "/" + currentNonStrikerId);

            tossInfo.setText(Global.capitalizeFirstLatterOfString(battingTeamName) + " " + "elected to bat");
            tvBattingTeamName.setText(Global.capitalizeFirstLatterOfString(battingTeamName));
            //playerNameStrike.setText(StrikePlayerName);
            //playerNameNonStrike.setText(nonStrikeName);
            tvbowlerName.setText(Global.capitalizeFirstLatterOfString(bowlerName));
        }
        if (Global.isOnline(this)) {
            getInningDetails(inningNumber);
        } else {
            Global.showDialog(this);
        }


    }

    private void showPopupMenu(View view) {
        // PopupMenu banayein
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_settings, popupMenu.getMenu());

        // Item click listener set karein
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
//                case R.id.change_batsman:
//                    return true;
                case R.id.change_bowler:

                    startActivity(new Intent(this, PlayingSquadActivity.class)
                            .putExtra("ScheduleId", scheduleId)
                            .putExtra("InningId", inning_id)
                            .putExtra("TeamId", bowlingTeamId)
                            .putExtra("BowlingTeamId", bowling_team_id)
                            .putExtra("BattingTeamId", batting_team_id)
                            .putExtra("current_striker_id", currentStrikerId)
                            .putExtra("current_non_striker_id", currentNonStrikerId)
                            .putExtra("current_bowler_id", currentBowlerId)
                            .putExtra("Strike", "")
                            .putExtra("lastRun", lastRunn)
                            .putExtra("Bowler", "Bowler"));

                    return true;
                default:
                    return false;
            }
        });

        // PopupMenu dikhayein
        popupMenu.show();
    }

    // Call this method whenever a ball is bowled
    // Call this method whenever a ball is bowled
    private void handleScoringEvent(int runs) {
        // Update total runs
        lastRun = 0;
        extras = "";
        totalRuns += runs;
        balls++;

        lastRun = runs;


         boolean shouldShowDialog = false;

        // Always show dialog for boundaries (4 and 6)
        if (runs >= 4) {
            shouldShowDialog = true;
      } else if (runs > 0 && runs < 4) {
           // Show dialog for 1, 2, 3 runs if allowed by the checkbox
            shouldShowDialog = isShowDialogForRuns;
       }

        // Log to check if dialog should be shown
        //Log.d("ScoringEvent", "Runs: " + runs + ", ShouldShowDialog: " + shouldShowDialog);

        // Show the dialog based on the condition


       if (shouldShowDialog==true) {

           if (runs % 2 != 0) {
               changeStrike();
           }

           if (balls == 6) {
               balls = 0;
               overs++;
           }
           showWagonWheelDialog();
        }else{
           if (Global.isOnline(this)) {
               dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                       filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
           } else {
               Global.showDialog(this);
           }

           if (runs % 2 != 0) {
               changeStrike();
           }

           if (balls == 6) {
               balls = 0;
               overs++;
           }
       }

        //startActivity(new Intent(mContext,WagonWheelActivity.class));



    }

    // Show Over Completed Dialog

    private void setUpScoringGrid() {
        GridLayout scoringGrid = findViewById(R.id.scoringGrid);

        // Iterate through all children in the GridLayout
        for (int i = 0; i < scoringGrid.getChildCount(); i++) {
            View child = scoringGrid.getChildAt(i);

            // Check if the child is a Button
            if (child instanceof Button) {
                Button button = (Button) child;

                // Set the click listener for each button
                button.setOnClickListener(v -> onScoreButtonClick(button));
            }
        }
    }

    private void onScoreButtonClick(Button button) {
        String buttonText = button.getText().toString();
        try {
            // Extract the run value from the button text (handles cases like "4\nFOUR")
            lastRun = Integer.parseInt(buttonText.split("\n")[0]);

            // Determine if the run is a boundary (4 or 6)
            if (buttonText.contains("4\nFOUR")) {
                is_boundry = 1; // It's a boundary
                lastRun = 4;
            } else if (buttonText.contains("6\nSIX")) {
                is_boundry = 1; // It's a boundary
                lastRun = 6;
            } else {
                is_boundry = 0; // Not a boundary
            }

            // Log to check the run value
            Log.d("ScoreButtonClick", "LastRun: " + lastRun + ", IsBoundary: " + is_boundry);

            // Call the scoring event handler
            handleScoringEvent(lastRun); // This method updates balls, overs, and runs.
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /*...Update Squar when strike will be chnage*/
    private void changeStrike() {
        isPlayer1OnStrike = !isPlayer1OnStrike;

        if (Global.isOnline(this)) {
            updateSquad(currentNonStrikerId, currentStrikerId, currentBowlerId, inningNumber);
        } else {
            Global.showDialog(this);
        }
    }

    private void undoLastAction() {
        if (wasLastWicket && wickets > 0) {
            //wickets--;
        } else {
            totalRuns = Math.max(0, totalRuns - lastRun);
            if (isPlayer1OnStrike) {
                player1Score = Math.max(0, player1Score - lastRun);
            } else {
                player2Score = Math.max(0, player2Score - lastRun);
            }
        }

        if (balls > 0) {
            balls--;
        } else if (overs > 0) {
            balls = 5;
            overs--;
        }
        //   updateScoreDisplay();
    }

    // Dialog to enter custom score like 5 or 7
    private void showUpdateScoreDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_update_score);

        EditText inputScore = bottomSheetDialog.findViewById(R.id.inputScore);
        Button btnConfirm = bottomSheetDialog.findViewById(R.id.btnConfirm);
        Button btnCancel = bottomSheetDialog.findViewById(R.id.btnCancel);

        btnConfirm.setOnClickListener(v -> {
            String scoreText = inputScore.getText().toString();
            try {
                int score = Integer.parseInt(scoreText);
                handleScoringEvent(score);
                // updateScoreDisplay();
                bottomSheetDialog.dismiss();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid score", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }

    // Dialog for wide ball
    private void showWideBallDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_wide_ball);

        Button[] buttons = {
                bottomSheetDialog.findViewById(R.id.btn_wd_0),
                bottomSheetDialog.findViewById(R.id.btn_wd_1),
                bottomSheetDialog.findViewById(R.id.btn_wd_2),
                bottomSheetDialog.findViewById(R.id.btn_wd_3),
                bottomSheetDialog.findViewById(R.id.btn_wd_4),
                bottomSheetDialog.findViewById(R.id.btn_wd_5),
                bottomSheetDialog.findViewById(R.id.btn_wd_6),
        };

        for (Button btn : buttons) {
            btn.setOnClickListener(v -> {
                String buttonText = btn.getText().toString();

                // Extract the numeric part from the button text
                String[] parts = buttonText.split("\\+"); // Split by '+'
                int extraRuns = 0;

                // Ensure that the numeric part exists after splitting
                if (parts.length > 1) {
                    try {
                        extraRuns = Integer.parseInt(parts[1].trim()); // Convert the extracted number
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                        return; // Exit the click listener in case of an error
                    }
                }

                // Call handleExtras with the correct extra runs
                handleExtras("WB", extraRuns); // 1 for the wide ball + additional runs
                // updateScoreDisplay();
                bottomSheetDialog.dismiss();
            });
        }


        bottomSheetDialog.show();
    }

    // Dialog for no-ball
    private void showNoBallDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_no_ball);

        Button[] noBallButtons = {
                bottomSheetDialog.findViewById(R.id.btn_nb_0),
                bottomSheetDialog.findViewById(R.id.btn_nb_1),
                bottomSheetDialog.findViewById(R.id.btn_nb_2)
        };

        for (Button btn : noBallButtons) {
            btn.setOnClickListener(v -> {
                String buttonText = btn.getText().toString();

                // Extract digits from the button text
                String numericPart = buttonText.replaceAll("\\D+", ""); // Remove all non-digit characters

                try {
                    int extraRuns = Integer.parseInt(numericPart); // Parse the extracted number
                    handleExtras("NB", extraRuns);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                }

                //  updateScoreDisplay();
                bottomSheetDialog.dismiss();
            });
        }

        bottomSheetDialog.show();
    }

    private void showLegByeDialog() {
        // Create a dialog
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_leg_bye);

        // Find views in the dialog
        Button btnLegBye1 = bottomSheetDialog.findViewById(R.id.btnLegBye1);
        Button btnLegBye2 = bottomSheetDialog.findViewById(R.id.btnLegBye2);
        Button btnLegBye3 = bottomSheetDialog.findViewById(R.id.btnLegBye3);
        Button btnLegBye4 = bottomSheetDialog.findViewById(R.id.btnLegBye4);
        Button btnLegByeCustom = bottomSheetDialog.findViewById(R.id.btnLegByeCustom);
        EditText etCustomRuns = bottomSheetDialog.findViewById(R.id.etLegByeCustomRuns);
        CheckBox chkBoundary = bottomSheetDialog.findViewById(R.id.chkBoundary);
        Button btnCancel = bottomSheetDialog.findViewById(R.id.btnCancel);
        Button btnOK = bottomSheetDialog.findViewById(R.id.btnOK);


        // Set click listeners for predefined buttons
        View.OnClickListener runClickListener = v -> {
            Button btn = (Button) v;
            int runs = Integer.parseInt(btn.getText().toString());
            handleLegByeRuns(runs);
            // updateScoreDisplay();
            bottomSheetDialog.dismiss();
        };

        chkBoundary.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });

        btnLegBye1.setOnClickListener(runClickListener);
        btnLegBye2.setOnClickListener(runClickListener);
        btnLegBye3.setOnClickListener(runClickListener);
        btnLegBye4.setOnClickListener(runClickListener);

        // Show custom input field when "+" button is clicked
        btnLegByeCustom.setOnClickListener(v -> {
            etCustomRuns.setVisibility(View.VISIBLE);
            etCustomRuns.requestFocus();
        });

        // OK Button listener for custom runs
        btnOK.setOnClickListener(v -> {
            int runs = 0;
            if (etCustomRuns.getVisibility() == View.VISIBLE) {
                try {
                    runs = Integer.parseInt(etCustomRuns.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            handleLegByeRuns(runs);
            ///// updateScoreDisplay();
            bottomSheetDialog.dismiss();
        });

        // Cancel button to dismiss the dialog
        btnCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        // Show the dialog
        bottomSheetDialog.show();
    }

    private void showByeDialog() {
        // Create a dialog
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_bye);

        // Find views in the dialog
        Button btnLegBye1 = bottomSheetDialog.findViewById(R.id.btnLegBye1);
        Button btnLegBye2 = bottomSheetDialog.findViewById(R.id.btnLegBye2);
        Button btnLegBye3 = bottomSheetDialog.findViewById(R.id.btnLegBye3);
        Button btnLegBye4 = bottomSheetDialog.findViewById(R.id.btnLegBye4);
        Button btnLegByeCustom = bottomSheetDialog.findViewById(R.id.btnLegByeCustom);
        EditText etCustomRuns = bottomSheetDialog.findViewById(R.id.etLegByeCustomRuns);
        CheckBox chkBoundary = bottomSheetDialog.findViewById(R.id.chkBoundary);
        Button btnCancel = bottomSheetDialog.findViewById(R.id.btnCancel);
        Button btnOK = bottomSheetDialog.findViewById(R.id.btnOK);

        // Set click listeners for predefined buttons
        View.OnClickListener runClickListener = v -> {
            Button btn = (Button) v;
            int runs = Integer.parseInt(btn.getText().toString());
            handleBye(runs);
            //  updateScoreDisplay();
            bottomSheetDialog.dismiss();
        };

        btnLegBye1.setOnClickListener(runClickListener);
        btnLegBye2.setOnClickListener(runClickListener);
        btnLegBye3.setOnClickListener(runClickListener);
        btnLegBye4.setOnClickListener(runClickListener);

        // Show custom input field when "+" button is clicked
        btnLegByeCustom.setOnClickListener(v -> {
            etCustomRuns.setVisibility(View.VISIBLE);
            etCustomRuns.requestFocus();
        });

        // OK Button listener for custom runs
        btnOK.setOnClickListener(v -> {
            int runs = 0;
            if (etCustomRuns.getVisibility() == View.VISIBLE) {
                try {
                    runs = Integer.parseInt(etCustomRuns.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            handleBye(runs);
            // updateScoreDisplay();
            bottomSheetDialog.dismiss();
        });

        // Cancel button to dismiss the dialog
        btnCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        // Show the dialog
        bottomSheetDialog.show();
    }

    private void handleBye(int runs) {
        is_boundry = 0;
        lastRun = runs;
        extras = "BYE";
        balls++;
        if (Global.isOnline(this)) {
            dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                    filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
        } else {
            Global.showDialog(this);
        }
        // updateScoreDisplay();
    }

    private void handleExtras(String type, int runs) {
        is_boundry = 0;
        lastRun = runs;
        extras = type;
//        if (type.equalsIgnoreCase("NB")) {
//            //balls++;
//            if (balls == 6) {
//                overs++;
//                balls = 0;
//                // changeStrike();
//            }
//        }else if(type.equalsIgnoreCase("WD")){
//            if (balls == 6) {
//                overs++;
//                balls = 0;
//                // changeStrike();
//            }
//        }
        wasLastWicket = false;

        if (Global.isOnline(this)) {
            dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                    filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
        } else {
            Global.showDialog(this);
        }

        lastRun = 0;
    }

    private void handleLegByeRuns(int runs) {
        is_boundry = 0;
        extras = "LB";
        lastRun = runs;
        //lastRun = totalRuns;
        balls++;
//        if (balls == 6) {
//            balls = 0;
//            overs++;
//            // showOverCompletedDialog();
//        }

        // Call updateBallIndicators here
        //  updateBallIndicators(runs);

        if (Global.isOnline(this)) {
            dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                    filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
        } else {
            Global.showDialog(this);
        }


        //  updateScoreDisplay();
    }

    private void outType() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.out_type);

        Button bowled = bottomSheetDialog.findViewById(R.id.bowled);
        Button caught = bottomSheetDialog.findViewById(R.id.caught);
        Button runOut = bottomSheetDialog.findViewById(R.id.runOut);
        Button lbw = bottomSheetDialog.findViewById(R.id.lbw);

        MaterialCardView mcvStrike = bottomSheetDialog.findViewById(R.id.mcvStrike);
        MaterialCardView mcvNonStrike = bottomSheetDialog.findViewById(R.id.mcvNonStrike);
        TextView tvStrikeName = bottomSheetDialog.findViewById(R.id.tvStrikeName);
        TextView tvNonStrikeName = bottomSheetDialog.findViewById(R.id.tvNonStrikeName);
        ImageView imgStrike = bottomSheetDialog.findViewById(R.id.imgStrike);
        ImageView imgNonStrike = bottomSheetDialog.findViewById(R.id.imgNonStrike);

        tvStrikeName.setText(Global.capitalizeFirstLatterOfString(StrikePlayerName));
        tvNonStrikeName.setText(Global.capitalizeFirstLatterOfString(nonStrikeName));


        mcvStrike.setOnClickListener(v -> {
            runOutPlayerId = currentStrikerId;

            selected = 0;
            selected = 1;
            mcvStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_selection));
            mcvNonStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        });

        mcvNonStrike.setOnClickListener(v -> {
            runOutPlayerId = currentNonStrikerId;

            selected = 0;
            selected = 1;
            mcvStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            mcvNonStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_selection));
        });

        EditText etLegByeCustomRuns = bottomSheetDialog.findViewById(R.id.etLegByeCustomRuns);
        LinearLayout lirun = bottomSheetDialog.findViewById(R.id.lirun);
        MaterialButton mb_create = bottomSheetDialog.findViewById(R.id.mb_create);

        bowled.setOnClickListener(v -> {
            balls++;
            lirun.setVisibility(View.GONE);
            wicketType = "bowled";
            lastRun = 0;
            isWicket = "1";
            extras = "";
            if (Global.isOnline(this)) {
                dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                        filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
            } else {
                Global.showDialog(this);
            }

            bottomSheetDialog.dismiss();

        });
        caught.setOnClickListener(v -> {
            balls++;
            lirun.setVisibility(View.GONE);
            lastRun = 0;
            wicketType = "caught";
            isWicket = "1";
            extras = "";
            if (Global.isOnline(this)) {
                dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                        filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
            } else {
                Global.showDialog(this);
            }
            bottomSheetDialog.dismiss();

        });
        runOut.setOnClickListener(v -> {
            runOut.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_selection));
            lirun.setVisibility(View.VISIBLE);

            //bottomSheetDialog.dismiss();


        });
        lbw.setOnClickListener(v -> {
            extras = "";
            balls++;
            lirun.setVisibility(View.GONE);
            lastRun = 0;
            wicketType = "lbw";
            isWicket = "1";
            if (Global.isOnline(this)) {
                dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                        filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
            } else {
                Global.showDialog(this);
            }


            bottomSheetDialog.dismiss();

        });


        mb_create.setOnClickListener(v -> {
            extras = "";
            balls++;
            wicketType = "runOut";
            isWicket = "0";

            if (!etLegByeCustomRuns.getText().toString().trim().isEmpty()) {
                lastRun = Integer.parseInt(etLegByeCustomRuns.getText().toString().trim());
            }

            if (selected == 0) {
                // No selection made
                Toast.makeText(mContext, "Please select a Striker or Non-Striker", Toast.LENGTH_SHORT).show();
            } else {
                if (Global.isOnline(this)) {
                    dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                            filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
                } else {
                    Global.showDialog(this);
                }

                bottomSheetDialog.dismiss();
            }


        });

        bottomSheetDialog.show();
    }

    private void getInningDetails(int inning_number) {
        loaderView.showLoader();
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getInningDetails(
                SessionManager.getToken(),
                inning_number
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loaderView.hideLoader();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Convert ResponseBody to String
                        String responseBodyString = response.body().string();

                        Log.d("responseDetails", responseBodyString);

                        // Parse the string to a JSONObject
                        JSONObject jsonObject = new JSONObject(responseBodyString);

                        // Check if the status is true
                        boolean status = jsonObject.optBoolean("status", false);
                        String message = jsonObject.optString("message", "No message");


                        if (status) {
                            // Extract "data" object if needed
                            // Example: Get schedule_match_id from "data"
                            // Extract "data" object if needed
                            JSONObject dataObject = jsonObject.optJSONObject("data");
                            extractAndLoadData(dataObject);

                        } else {
                            Toaster.customToast(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ///Toaster.customToast("Failed to parse response!");
                    }
                } else {
                    Toaster.customToast("Failed to save result!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loaderView.hideLoader();
                Toaster.customToast("Network error: " + t.getMessage());
            }
        });
    }

    private void updateSquad(int current_striker_id, int current_non_striker_id, int current_bowler_id, int inning_id) {
        loaderView.showLoader();
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.inningUpdate(
                SessionManager.getToken(),
                new PlayingSqudUpdateBody(current_striker_id, current_non_striker_id, current_bowler_id, inning_id, strike));


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loaderView.hideLoader();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Parse Response
                        String responseBodyString = response.body().string();
                        Log.d("ResponseDetails", responseBodyString);

                        JSONObject jsonObject = new JSONObject(responseBodyString);
                        boolean status = jsonObject.optBoolean("status", false);
                        String message = jsonObject.optString("message", "No message");

                        if (status) {
                            JSONObject dataObject = jsonObject.optJSONObject("data");
                            if (dataObject != null) {

                                // Extract data
                                // currentStrikerId=0;
                                //  currentNonStrikerId=0;
                                //  extractAndLoadData(dataObject);
                            } else {
                                Log.e("Error", "Data object is null");
                            }
                        } else {
                            Toaster.customToast(message);
                        }
                    } catch (Exception e) {
                        loaderView.hideLoader();
                        Log.e("Error", "Response parsing failed", e);
                        Toaster.customToast("Failed to parse response!");
                    }
                } else {
                    Toaster.customToast("Failed to process response!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loaderView.hideLoader();
                Toaster.customToast("Network error: " + t.getMessage());
            }
        });
    }

    private void dataHitBallByBall(int inning_idd, int overss, int balls, int currentBowlerIdd, int current_striker_id, int lastRun,
                                   int is_boundry, String extras, String wicketss, String wicketType, int fielderId,
                                   int runOutPlayerId,String short_area,String short_type,String currentStrikerName,String currentBowlerName) {

        loaderView.showLoader();
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.inningBall(
                SessionManager.getToken(),
                new InningBallParamBody(inning_idd, overss, balls, currentBowlerIdd, current_striker_id, lastRun,
                        String.valueOf(is_boundry), extras, wicketss, wicketType, fielderId, runOutPlayerId,short_area,short_type,currentStrikerName,currentBowlerName)
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Parse Response
                        String responseBodyString = response.body().string();
                        Log.d("ResponseBall", responseBodyString);

                        JSONObject jsonObject = new JSONObject(responseBodyString);
                        boolean status = jsonObject.optBoolean("status", false);
                        String message = jsonObject.optString("message", "No message");

                        if (status) {
                            JSONObject dataObject = jsonObject.optJSONObject("data");
                            if (dataObject != null) {
                                // Extract data here ...
                                extractAndLoadData(dataObject);
                            } else {
                                Log.e("Error", "Data object is null");
                            }
                        } else {
                            Toaster.customToast(message);
                        }
                    } catch (Exception e) {
                        loaderView.hideLoader();
                        Log.e("Error", "Response parsing failed", e);
                        Toaster.customToast("Failed to parse response!");
                    }
                } else {
                    Toaster.customToast("Failed to process response!");
                }

                loaderView.hideLoader();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loaderView.hideLoader();
                Log.e("NetworkError", "Request failed", t);
                Toaster.customToast("Network error: " + t.getMessage());
            }
        });
    }

    // Extract data and update the UI
    private void extractAndLoadData(JSONObject dataObject) {
        try {
            List<Balled> balledList = new ArrayList<>();
            // Extract match and player data
            scheduleId = dataObject.getInt("schedule_match_id");
            inning_id = dataObject.getInt("inning_id");
            currentStrikerId = dataObject.getJSONObject("striker").getInt("player_id");
            currentNonStrikerId = dataObject.getJSONObject("non_striker").getInt("player_id");

            over_limit_inning = dataObject.getInt("over_limit_inning");

            batting_team_id = dataObject.getInt("batting_team_id");
            bowling_team_id = dataObject.getInt("bowling_team_id");


            StrikePlayerName = dataObject.getJSONObject("striker").getString("name");
            nonStrikeName = dataObject.getJSONObject("non_striker").getString("name");
            bowlerName = dataObject.getJSONObject("bowler").getString("name");
            player1Score = dataObject.getJSONObject("striker").getInt("runs_scored");
            player2Score = dataObject.getJSONObject("non_striker").getInt("runs_scored");


            currentBowlerId = dataObject.getJSONObject("bowler").getInt("player_id");
            battingTeamName = dataObject.getString("team_name");

            tvBattingTeamName.setText(Global.capitalizeFirstLatterOfString(battingTeamName));
            bowlingTeamId = dataObject.getInt("bowling_team_id");
            teamIdBatting = dataObject.getInt("batting_team_id");

            totalRuns = dataObject.getInt("total_runs");
            currentOverRuns = dataObject.getJSONObject("bowler").getInt("runs_conceded");
            wickets = dataObject.getInt("wickets");
            overValue = dataObject.getString("over");
            overs = dataObject.getInt("overs");

            current_over_total_ball = dataObject.getInt("current_over_total_ball");
            is_inning_completed = dataObject.getInt("is_inning_completed");

            target_msg = dataObject.getString("target_msg");
            tossInfo.setText(target_msg);
            is_match_completed = dataObject.getInt("is_match_completed");

            if (dataObject.getJSONObject("first_inning").length() > 0) {
                firstInningScore = dataObject.getJSONObject("first_inning").getInt("total_runs");
                firstInningToatlOver = dataObject.getJSONObject("first_inning").getInt("overs");
                firstInningTotalWicket = dataObject.getJSONObject("first_inning").getInt("wickets");
                firstInningTeamName = dataObject.getJSONObject("first_inning").getString("team_name");

            }


            JSONArray bolledArray = dataObject.getJSONArray("balled");


            // wicketType = bolledArray.getJSONObject(bolledArray.length()-1).getString("wicket_type");

//            if(wicketType.equalsIgnoreCase("runOut")){
//                showRunOutSelectionDialog();
//            }

            if (isWicket.equalsIgnoreCase("1") || runOutPlayerId > 0) {
                Toast.makeText(this, "Wicket fallen!", Toast.LENGTH_SHORT).show();
                balledList.clear();
                //  updateScoreDisplay();

                if (isPlayer1OnStrike == true) {
                    strike = "Strike";
                } else {
                    strike = "NonStrike";
                }

                if (wicketType.equalsIgnoreCase("runOut")) {
                    lastRunn = lastRun;
                } else {
                    lastRunn = 0;
                }

                new Handler().postDelayed(() -> {
                    isWicket = "0";
                    runOutPlayerId = 0;
                    if (is_inning_completed != 1 && is_match_completed != 1) {
                        startActivity(new Intent(mContext, PlayingSquadActivity.class)
                                .putExtra("ScheduleId", scheduleId)
                                .putExtra("InningId", inning_id)
                                .putExtra("TeamId", teamIdBatting)
                                .putExtra("BowlingTeamId", bowling_team_id)
                                .putExtra("BattingTeamId", batting_team_id)
                                .putExtra("current_striker_id", currentStrikerId)
                                .putExtra("current_non_striker_id", currentNonStrikerId)
                                .putExtra("current_bowler_id", currentBowlerId)
                                .putExtra("Strike", strike)
                                .putExtra("lastRun", lastRunn)
                                .putExtra("Bowler", ""));
                    }


                }, 2000);
            } else {
                isWicket = "0";
            }

            for (int i = 0; i < bolledArray.length(); i++) {
                JSONObject ballObject = bolledArray.getJSONObject(i);

                Log.d("DEBUG", "Ball JSON: " + ballObject.toString());

                Balled ball = new Balled(ballObject);

                Log.d("DEBUG", "Runs Scored from Model: " + ball.getRunsScored());

                balledList.add(ball);
            }

            updateBallIndicatorsFromResponse(balledList, current_over_total_ball, totalRuns, totalOversInggingCompleted, wickets, battingTeamName);

            updateScoreDisplay();
        } catch (JSONException e) {
            Log.e("Error", "Data extraction failed", e);
            Toaster.customToast("Failed to extract data!");
        }
    }


    private void updateBallIndicatorsFromResponse(List<Balled> balledList, int current_over_total_ball, int totalRuns, int overs, int wicket, String teamName) {
        // Reset the indicators and counters for the current over
        runsInCurrentOver.clear();  // Clear previous runs
        ballsInCurrentOver = 0;
        currentOverRuns = 0;

        // Log the size of the balledList
        Log.d("DEBUG", "Size of balledList: " + balledList.size());

        // Clear existing indicators from the layout
        ballIndicatorsLayout.removeAllViews();

        // Loop through the list and update ball indicators based on the actual size of the list
        for (Balled ballData : balledList) {
            Log.d("DEBUG", "Processing ball: " + ballsInCurrentOver); // Log the count of balls being processed

            // Extract the runs scored from the API response
            int runs = ballData.getRunsScored();
            String extras = ballData.getExtras();  // "WB" or "NB" for wide or no-ball

            // Add the runs for the current ball to the list
            runsInCurrentOver.add(runs);  // Dynamically add runs to the list
            currentOverRuns += runs;

            // Create a parent layout for each ball (this will hold both run indicators)
            LinearLayout ballIndicatorWrapper = new LinearLayout(this);
            ballIndicatorWrapper.setOrientation(LinearLayout.VERTICAL); // Stack run circle and extra ball circle vertically
            ballIndicatorWrapper.setGravity(Gravity.CENTER);

            // Create the normal run circle TextView
            TextView ballIndicator = new TextView(this);
            ballIndicator.setTextSize(14.5f);
            ballIndicator.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ballIndicator.setGravity(Gravity.CENTER);
            ballIndicator.setTextColor(Color.BLACK);
            ballIndicator.setBackgroundResource(R.drawable.circle_background); // Add your circle background

            // Set the run text for the ball (either the normal run or the extra ball)
            String ballText;
            if ("WB".equalsIgnoreCase(extras)) {
                ballText = "WB+" + runs;  // Wide ball runs
                ballIndicator.setBackgroundResource(R.drawable.circular_gradient);
            } else if ("NB".equalsIgnoreCase(extras)) {
                ballText = "NB+" + runs;  // No-ball runs
                ballIndicator.setBackgroundResource(R.drawable.circular_gradient);
            } else {
                ballText = String.valueOf(runs);  // Normal run
            }
            ballIndicator.setText(ballText);

            // Set layout params for the ball indicator
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(45), dpToPx(45)); // Convert 90 dp to px
            params.setMargins(dpToPx(4), 0, dpToPx(4), 0);  // Set margin around circle
            ballIndicator.setLayoutParams(params);

            // Add the normal run circle to the wrapper
            ballIndicatorWrapper.addView(ballIndicator);

            // Check if it is a wide or no-ball, and add an extra ball indicator below it
            if ("WB".equalsIgnoreCase(extras) || "NB".equalsIgnoreCase(extras)) {
                TextView extraBallIndicator = new TextView(this);
                extraBallIndicator.setGravity(Gravity.CENTER);
                extraBallIndicator.setTextColor(Color.WHITE);
                extraBallIndicator.setBackgroundResource(R.drawable.circle_background);  // Same background for extra ball

                // Set text for the extra ball (just the type, without runs)
                extraBallIndicator.setText(extras);

                // Set layout params for the extra ball indicator
                LinearLayout.LayoutParams extraBallParams = new LinearLayout.LayoutParams(
                        dpToPx(60), dpToPx(60));  // Smaller circle for extra ball
                extraBallParams.setMargins(0, dpToPx(8), 0, 0);  // Margin between the circles
                extraBallIndicator.setLayoutParams(extraBallParams);

                // Add the extra ball indicator to the wrapper
                // ballIndicatorWrapper.addView(extraBallIndicator);
            }

            // Add the wrapper to the main layout
            ballIndicatorsLayout.addView(ballIndicatorWrapper);

            ballsInCurrentOver++;  // Move to the next ball
        }

        // Log after the loop to check the final count of balls
        Log.d("DEBUG", "Balls in current over after loop: " + ballsInCurrentOver);

        // Trigger the over completion dialog if the over is finished
        if (is_match_completed == 1) {
            endMatchDialog();
        } else if (is_inning_completed == 1) {
            inningCompletedDialog(totalRuns, overs, wicket, teamName);
        } else {
            if (current_over_total_ball == 6) {
                Log.d("DEBUG", "Triggering over completion dialog...");

                // Ensure that the handler and dialog show are triggered on the main thread
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    runOnUiThread(() -> {
                        Log.d("DEBUG", "Showing Over Completed Dialog");
                        showOverCompletedDialog(balledList);  // Show the dialog
                    });

                    // Reset for the next over after showing the dialog
                    ballsInCurrentOver = 0;
                    totalOvers++;
                    currentOverRuns = 0;

                    // Optionally reset or clear the layout if needed
                    ballIndicatorsLayout.removeAllViews();
                }, 1000);  // Delay in milliseconds before showing the dialog
            }
        }


    }

    // Helper method to convert dp to pixels for consistent sizing
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }


    private void showOverCompletedDialog(List<Balled> balledList) {

        if (isDialogOpen) return;  // Prevent reopening if already open
        isDialogOpen = true;

        // Create and set up the BottomSheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_over_complete, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        LinearLayout ballIndicatorsLayout = bottomSheetDialog.findViewById(R.id.ballIndicators);
        ballIndicatorsLayout.removeAllViews();
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetBehavior<?> behavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
            behavior.setHideable(false); // Prevent hiding by sliding down
            behavior.setDraggable(false); // Disable drag gestures
        });

        // Fetching views for the dialog
        TextView tvCurrentBowler = bottomSheetDialog.findViewById(R.id.tvCurrentBowler);
        TextView tvCurrentBowlerTotalRun = bottomSheetDialog.findViewById(R.id.tvCurrentBowlerTotalRun);
        TextView tvTotalRuns = bottomSheetDialog.findViewById(R.id.tvTotalRuns);
        TextView tvTotalOvers = bottomSheetDialog.findViewById(R.id.tvTotalOvers);
        TextView tvTotalWickets = bottomSheetDialog.findViewById(R.id.tvTotalWickets);

        // Update dialog fields with the current data
        tvCurrentBowler.setText(bowlerName);
        tvCurrentBowlerTotalRun.setText(String.valueOf(currentOverRuns));  // Total runs for the current over
        tvTotalRuns.setText(String.valueOf(totalRuns));  // Update total runs
        tvTotalOvers.setText(String.valueOf(overs));  // Update total overs
        tvTotalWickets.setText(String.valueOf(wickets));  // Update total wickets

        // Set runs for each ball, ensuring that only the relevant balls are shown
        runOnUiThread(() -> {

            Log.d("DEBUG", "Dialog showing for over: ");

            for (Balled ballData : balledList) {
                Log.d("DEBUG", "Processing ball: " + ballsInCurrentOver); // Log the count of balls being processed

                // Extract the runs scored from the API response
                int runs = ballData.getRunsScored();
                String extras = ballData.getExtras();  // "WB" or "NB" for wide or no-ball

                // Store the runs for the current ball
                runsInCurrentOver.add(runs);  // Dynamically add runs to the list
                currentOverRuns += runs;

                // Create a parent layout for each ball (this will hold both run indicators)
                LinearLayout ballIndicatorWrapper = new LinearLayout(this);
                ballIndicatorWrapper.setOrientation(LinearLayout.VERTICAL); // Stack run circle and extra ball circle vertically
                ballIndicatorWrapper.setGravity(Gravity.CENTER);

                // Create the normal run circle TextView
                TextView ballIndicator = new TextView(this);
                ballIndicator.setTextSize(14.5f);
                ballIndicator.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                ballIndicator.setGravity(Gravity.CENTER);
                ballIndicator.setTextColor(Color.BLACK);
                ballIndicator.setBackgroundResource(R.drawable.circle_background); // Add your circle background

                // Set the run text for the ball (either the normal run or the extra ball)
                String ballText;
                if ("WB".equalsIgnoreCase(extras)) {
                    ballText = "WB+" + runs;  // Wide ball runs
                    ballIndicator.setBackgroundResource(R.drawable.circular_gradient);
                } else if ("NB".equalsIgnoreCase(extras)) {
                    ballText = "NB+" + runs;  // No-ball runs
                    ballIndicator.setBackgroundResource(R.drawable.circular_gradient);
                } else {
                    ballText = String.valueOf(runs);  // Normal run
                }
                ballIndicator.setText(ballText);

                // Set layout params for the ball indicator
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        dpToPx(50), dpToPx(50)); // Convert 90 dp to px
                params.setMargins(dpToPx(6), 0, dpToPx(6), 0);  // Set margin around circle
                ballIndicator.setLayoutParams(params);

                // Add the normal run circle to the wrapper
                ballIndicatorWrapper.addView(ballIndicator);

                // Check if it is a wide or no-ball, and add an extra ball indicator below it
                if ("WB".equalsIgnoreCase(extras) || "NB".equalsIgnoreCase(extras)) {
                    TextView extraBallIndicator = new TextView(this);
                    extraBallIndicator.setGravity(Gravity.CENTER);
                    extraBallIndicator.setTextColor(Color.WHITE);
                    extraBallIndicator.setBackgroundResource(R.drawable.circle_background);  // Same background for extra ball

                    // Set text for the extra ball (just the type, without runs)
                    extraBallIndicator.setText(extras);

                    // Set layout params for the extra ball indicator
                    LinearLayout.LayoutParams extraBallParams = new LinearLayout.LayoutParams(
                            dpToPx(60), dpToPx(60));  // Smaller circle for extra ball
                    extraBallParams.setMargins(0, dpToPx(8), 0, 0);  // Margin between the circles
                    extraBallIndicator.setLayoutParams(extraBallParams);

                    // Add the extra ball indicator to the wrapper
                    // ballIndicatorWrapper.addView(extraBallIndicator);
                }

                // Add the wrapper to the main layout
                ballIndicatorsLayout.addView(ballIndicatorWrapper);

                ballsInCurrentOver++;  // Move to the next ball
            }
        });

        // Add listeners for dialog buttons
        Button btnStartNextOver = bottomSheetDialog.findViewById(R.id.btnStartNextOver);
        btnStartNextOver.setOnClickListener(v -> {
            // Reset for the next over
            ballsInCurrentOver = 0;
            totalOvers++;
            currentOverRuns = 0;

            // Dismiss the dialog
            bottomSheetDialog.dismiss();

            // Start new activity (for next over)
            startActivity(new Intent(this, PlayingSquadActivity.class)
                    .putExtra("ScheduleId", scheduleId)
                    .putExtra("InningId", inning_id)
                    .putExtra("TeamId", bowlingTeamId)
                    .putExtra("BowlingTeamId", bowling_team_id)
                    .putExtra("BattingTeamId", batting_team_id)
                    .putExtra("current_striker_id", currentStrikerId)
                    .putExtra("current_non_striker_id", currentNonStrikerId)
                    .putExtra("current_bowler_id", currentBowlerId)
                    .putExtra("Strike", "")
                    .putExtra("lastRun", lastRunn)
                    .putExtra("Bowler", "Bowler"));
        });

        Button btnContinueThisOver = bottomSheetDialog.findViewById(R.id.btnContinueThisOver);
        btnContinueThisOver.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.setOnDismissListener(dialog -> {
            isDialogOpen = false;  // Reset flag when the dialog is dismissed
        });


        bottomSheetDialog.show();
    }

    private void updateScoreDisplay() {

        // Extract integer and decimal parts of the overs
        // int wholeOvers = (int) overs; // Extracts the integer part, e.g., 12
        // int fractionalPart = Math.round((overs - wholeOvers) * 10); //

        // Update the main scoreboard text
        scoreTextView.setText(totalRuns + "/" + wickets + "(" + overValue + " : " + over_limit_inning + ")");

        // Update individual player scores
        player1ScoreView.setText(Global.capitalizeFirstLatterOfString(StrikePlayerName) + ": " + player1Score);
        player2ScoreView.setText(Global.capitalizeFirstLatterOfString(nonStrikeName) + " : " + player2Score);

        // Update bowler stats
        bowlerStatsView.setText("Over :" + " " + overValue + "  -  " + "Run : " + " " + totalRuns + " " + "  -  " + "Wicket : " + " " + wickets);
        tvbowlerName.setText(Global.capitalizeFirstLatterOfString(bowlerName));
    }

    private void inningCompletedDialog(int totalRunss, int overss, int wickett, String name) {

        if (isDialogOpen) return;  // Prevent reopening if already open
        isDialogOpen = true;

        // Create and set up the BottomSheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_inning_complete, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        TextView tvRuns = bottomSheetDialog.findViewById(R.id.tvRuns);
        TextView tvOvers = bottomSheetDialog.findViewById(R.id.tvOvers);
        TextView tvWicket = bottomSheetDialog.findViewById(R.id.tvWicket);
        TextView tvTeamName = bottomSheetDialog.findViewById(R.id.tvTeamName);
        tvTeamName.setText("End of innings for " + "" + name);

        tvRuns.setText(totalRunss + "");
        tvOvers.setText(overs + "");
        tvWicket.setText(wickett + "");

        /**
         "bowling_team_id": 2,
         "batting_team_id": 1,
         "schedule_match_id": 13,
         "inning_id": 62,
         +*/

        Button btnStartNextOver = bottomSheetDialog.findViewById(R.id.btnStartNextOver);
        btnStartNextOver.setOnClickListener(v -> {

            if (Global.isOnline(this)) {
                updateInning(batting_team_id, bowling_team_id, scheduleId);
            } else {
                Global.showDialog(this);
            }
        });

        bottomSheetDialog.show();
    }

    private void updateInning(int batting_team_id, int bowlling_team_id, int schedule_match_id) {

        //loaderView.showLoader();
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.updateScheduleInning(
                SessionManager.getToken(),
                new UpdateScheduleInningParamBody(batting_team_id, bowlling_team_id, schedule_match_id)
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Parse Response
                        String responseBodyString = response.body().string();
                        Log.d("ResponseDetails", responseBodyString);

                        JSONObject jsonObject = new JSONObject(responseBodyString);
                        boolean status = jsonObject.optBoolean("status", false);
                        String message = jsonObject.optString("message", "No message");

                        if (status) {
                            // Extract "data" object if needed
                            // Example: Get schedule_match_id from "data"
                            // Extract "data" object if needed
                            startActivity(new Intent(mContext, ScheduleMatchActivity.class)
                                    .putExtra("FROM", "1"));
                            finish();

                        } else {
                            Toaster.customToast(message);
                        }
                    } catch (Exception e) {
                        //loaderView.hideLoader();
                        Log.e("Error", "Response parsing failed", e);
                        Toaster.customToast("Failed to parse response!");
                    }
                } else {
                    Toaster.customToast("Failed to process response!");
                }

                //loaderView.hideLoader();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // loaderView.hideLoader();
                Log.e("NetworkError", "Request failed", t);
                Toaster.customToast("Network error: " + t.getMessage());
            }
        });
    }

    private void endMatchDialog() {

        if (isDialogOpen) return;  // Prevent reopening if already open
        isDialogOpen = true;

        // Create and set up the BottomSheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_end_match, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        TextView tvTeamNameWinningHeader = bottomSheetDialog.findViewById(R.id.tvTeamNameWinningHeader);
        tvTeamNameWinningHeader.setText(target_msg);
        TextView tvLossingTeam = bottomSheetDialog.findViewById(R.id.tvLossingTeam);
        tvLossingTeam.setText(Global.capitalizeFirstLatterOfString(firstInningTeamName));
        TextView tvLoosingTeamWicket = bottomSheetDialog.findViewById(R.id.tvLoosingTeamWicket);
        tvLoosingTeamWicket.setText(firstInningTotalWicket + "");
        TextView tvLoosingTeamOver = bottomSheetDialog.findViewById(R.id.tvLoosingTeamOver);
        tvLoosingTeamOver.setText(firstInningToatlOver + "");
        TextView tvRunLoosingTeam = bottomSheetDialog.findViewById(R.id.tvRunLoosingTeam);
        tvRunLoosingTeam.setText(firstInningScore + "");

        TextView tvWinningTeamName = bottomSheetDialog.findViewById(R.id.tvWinningTeamName);
        tvWinningTeamName.setText(Global.capitalizeFirstLatterOfString(battingTeamName + ""));
        TextView tvWinningTeamRun = bottomSheetDialog.findViewById(R.id.tvWinningTeamRun);
        tvWinningTeamRun.setText(totalRuns + "");
        TextView tvWinningTeamWicket = bottomSheetDialog.findViewById(R.id.tvWinningTeamWicket);
        tvWinningTeamWicket.setText(wickets + "");
        TextView tvWinningTeamOver = bottomSheetDialog.findViewById(R.id.tvWinningTeamOver);
        tvWinningTeamOver.setText(overs + "");


        Button btnStartNextOver = bottomSheetDialog.findViewById(R.id.btnStartNextOver);
        btnStartNextOver.setOnClickListener(v -> {

            startActivity(new Intent(mContext, ScheduleMatchActivity.class)
                    .putExtra("FROM", "1"));
            finish();
            bottomSheetDialog.dismiss();

//            if (Global.isOnline(this)) {
//                updateInning(batting_team_id, bowling_team_id, scheduleId);
//            } else {
//                Global.showDialog(this);
//            }
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
            //changeScorerShowBottomSheetDialog();

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
            /**
             * This will redirected after getting the Api calling response
             */

            startActivity(new Intent(mContext, ScheduleMatchActivity.class));
            finish();
        });
        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(view -> {
            bottomSheetDialog.hide();
        });

        bottomSheetDialog.show();

    }

    // Method to display the Wagon Wheel dialog
    private void showWagonWheelDialog() {



        // Initialize the ViewBinding for the BottomSheet layout
        BottomDialogShowWagonWheelBinding bottomSheetBinding =
                BottomDialogShowWagonWheelBinding.inflate(getLayoutInflater());

        // Create the BottomSheetDialog and set the content view using ViewBinding
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

        // Adjust the dialog appearance
        if (bottomSheetDialog.getWindow() != null)
            bottomSheetDialog.getWindow().setDimAmount(0);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);


        bottomSheetDialog.setCancelable(false);

        // Wagon Wheel segment click listener
        bottomSheetBinding.wagonWheel.setOnSegmentClickListener(segmentText -> {

            switch (segmentText) {
                case "Deep\n Mid Wicket":
                    updateShortAreaRecyclerView(
                            bottomSheetBinding, Global.getMidWicketSubShortArea(mContext)
                    );
                    break;
                case "Long On":
                    updateShortAreaRecyclerView(
                            bottomSheetBinding, Global.getLongOnSubShortArea(mContext)
                    );
                    break;
                case "Long Off":
                    updateShortAreaRecyclerView(
                            bottomSheetBinding, Global.getLongOffSubShortArea(mContext)
                    );
                    break;
                case "Deep Cover":
                    updateShortAreaRecyclerView(
                            bottomSheetBinding,
                            Global.getDeepCoverSubShortArea(mContext)
                    );
                    break;
                case "Deep Point":
                    updateShortAreaRecyclerView(
                            bottomSheetBinding, Global.getDeepPointSubShortArea(mContext)
                    );
                    break;
                case "Third Man":
                    updateShortAreaRecyclerView(
                            bottomSheetBinding,
                            Global.getThirdManSubShortArea(mContext)
                    );
                    break;
                case "Deep\n Fine Leg":
                    updateShortAreaRecyclerView(
                            bottomSheetBinding,
                            Global.getDeepFineLegSubShortArea(mContext)
                    );
                    break;
                case "Deep\n Square Leg":
                    updateShortAreaRecyclerView(
                            bottomSheetBinding,
                            Global.getDeepSquareLegSubShortArea(mContext)
                    );
                    break;
            }

            if (segmentText.contains("\n")) {
                segmentText = segmentText.replace("\n", "");
            }
            selectedSegment = segmentText;
            bottomSheetBinding.tvShortArea.setText(segmentText);
        });

        // Click listeners for the dialog buttons
        bottomSheetBinding.tvNonOfAbove.setOnClickListener(view -> {
            bottomSheetBinding.rlButtonGroup.setVisibility(View.GONE);
            Toaster.customToast(selectedSegment);
            bottomSheetDialog.dismiss();
        });

        bottomSheetBinding.mbSave.setOnClickListener(view -> {

           //Toaster.customToast(selectedSegment + "  " + selectedShortArea);

            isShowDialogForRuns = bottomSheetBinding.cbDontShowWWFor1s.isChecked();


            short_area =selectedSegment;
            short_type = selectedShortArea;

            if (Global.isOnline(this)) {
                dataHitBallByBall(inning_id, overs, balls, currentBowlerId, currentStrikerId, lastRun, is_boundry, extras, isWicket, wicketType,
                        filederId, runOutPlayerId,short_area,short_type,StrikePlayerName,bowlerName);
            } else {
                Global.showDialog(this);
            }

            short_type ="";
            short_area ="";

            Log.d("Checkbox", "isShowDialogForRuns updated: " + isShowDialogForRuns);

            bottomSheetDialog.dismiss();
        });


        bottomSheetBinding.mbCancel.setOnClickListener(view -> bottomSheetDialog.dismiss());

        // Checkbox to enable/disable dialog for 1, 2, 3 runs
        bottomSheetBinding.cbDontShowWWFor1s.setChecked(isShowDialogForRuns); // Set initial state from global variable

        bottomSheetBinding.cbDontShowWWFor1s.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update the checkbox state live
            isShowDialogForRuns = isChecked;
            Log.d("Checkbox", "isShowDialogForRuns changed to: " + isChecked);
        });

        // Show the dialog
        bottomSheetDialog.show();
    }

    // Helper method to update RecyclerView
    private void updateShortAreaRecyclerView(BottomDialogShowWagonWheelBinding binding, ArrayList<ShortAreaSubCategoryModel> subShortAreas) {
        // Make liShortAreaDetails visible

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.liWagonWheel.setVisibility(View.GONE);

                TranslateAnimation slideIn = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, -1.0f, // Start from left (-1.0f)
                        Animation.RELATIVE_TO_SELF, 0.0f, // End at original position (0.0f)
                        Animation.RELATIVE_TO_SELF, 0.0f, // Keep vertical position fixed
                        Animation.RELATIVE_TO_SELF, 0.0f  // Keep vertical position fixed
                );
                slideIn.setDuration(500); // Slide duration in milliseconds
                slideIn.setInterpolator(new DecelerateInterpolator()); // Optional smooth effect
                binding.liShortAreaDetails.startAnimation(slideIn);

                binding.liShortAreaDetails.setVisibility(View.VISIBLE);
            }
        },500);


        // Apply left-to-right slide-in animation


        // Set up the RecyclerView with a GridLayoutManager
        binding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.rcvSubCategoryShortArea.setHasFixedSize(true);

        // Set the adapter with the subShortAreas list
        ShortAreaSubCategoryAdapter adapter = new ShortAreaSubCategoryAdapter(mContext, subShortAreas, value -> {
            selectedShortArea = value;
            // Show the button group when a selection is made
            binding.rlButtonGroup.setVisibility(View.VISIBLE);
        });

        // Set the adapter to the RecyclerView
        binding.rcvSubCategoryShortArea.setAdapter(adapter);

        // If you want to make sure the RecyclerView fits perfectly into the layout without needing scrolling
        // Ensure the RecyclerView's layout takes up the available space
      //  binding.rcvSubCategoryShortArea.setVisibility(View.VISIBLE); // Make RecyclerView visible
    }


    // Helper method to map List<String> to ArrayList<ShortAreaSubCategoryModel>


    private void showRunOutSelectionDialog(String type) {

        // Create and set up the BottomSheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_out_selection, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        MaterialCardView mcvStrike = bottomSheetDialog.findViewById(R.id.mcvStrike);
        MaterialCardView mcvNonStrike = bottomSheetDialog.findViewById(R.id.mcvNonStrike);
        TextView tvStrikeName = bottomSheetDialog.findViewById(R.id.tvStrikeName);
        TextView tvNonStrikeName = bottomSheetDialog.findViewById(R.id.tvNonStrikeName);
        ImageView imgStrike = bottomSheetDialog.findViewById(R.id.imgStrike);
        ImageView imgNonStrike = bottomSheetDialog.findViewById(R.id.imgNonStrike);
        EditText etLegByeCustomRuns = bottomSheetDialog.findViewById(R.id.etLegByeCustomRuns);



        tvStrikeName.setText(StrikePlayerName);
        tvNonStrikeName.setText(nonStrikeName);

        if(type.equalsIgnoreCase("Strike")){
            strike = "Strike";
            mcvStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_selection));
        }else if(type.equalsIgnoreCase("NonStrike")){
            strike = "NonStrike";
            mcvNonStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_selection));
        }


        mcvStrike.setOnClickListener(v -> {
            finalStrikeId = currentStrikerId;
            strike = "Strike";
            mcvStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_selection));
            mcvNonStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        });

        mcvNonStrike.setOnClickListener(v -> {
            strike = "NonStrike";
            finalNonStrikeId = currentNonStrikerId;
            mcvStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            mcvNonStrike.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_selection));
        });

        Button btnOK = bottomSheetDialog.findViewById(R.id.btnOK);
        Button btnCancel = bottomSheetDialog.findViewById(R.id.btnCancel);


        btnOK.setOnClickListener(v -> {


            if (is_inning_completed != 1 && is_match_completed != 1) {
                startActivity(new Intent(mContext, PlayingSquadActivity.class)
                        .putExtra("ScheduleId", scheduleId)
                        .putExtra("InningId", inning_id)
                        .putExtra("TeamId", teamIdBatting)
                        .putExtra("BowlingTeamId", bowling_team_id)
                        .putExtra("BattingTeamId", batting_team_id)
                        .putExtra("current_striker_id", currentStrikerId)
                        .putExtra("current_non_striker_id", currentNonStrikerId)
                        .putExtra("current_bowler_id", currentBowlerId)
                        .putExtra("Strike", strike)
                        .putExtra("lastRun", lastRunn)
                        .putExtra("Bowler", ""));
            }

            bottomSheetDialog.dismiss();
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.show();
    }

    private void updateSquadStrike(int current_striker_id, int current_non_striker_id, int current_bowler_id, int inning_id, String type) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.inningUpdate(
                SessionManager.getToken(),
                new PlayingSqudUpdateBody(current_striker_id, current_non_striker_id, current_bowler_id, inning_id, type));


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Parse Response
                        String responseBodyString = response.body().string();
                        Log.d("ResponseStrikerChange", responseBodyString);

                        JSONObject jsonObject = new JSONObject(responseBodyString);
                        boolean status = jsonObject.optBoolean("status", false);
                        String message = jsonObject.optString("message", "No message");

                        if (status) {
                            JSONObject dataObject = jsonObject.optJSONObject("data");
                            if (dataObject != null) {
                                // Extract data
                                // currentStrikerId=0;
                                //  currentNonStrikerId=0;
                                //  extractAndLoadData(dataObject);
                            } else {
                                Log.e("Error", "Data object is null");
                            }
                        } else {
                            Toaster.customToast(message);
                        }
                    } catch (Exception e) {
                        loaderView.hideLoader();
                        Log.e("Error", "Response parsing failed", e);
                        Toaster.customToast("Failed to parse response!");
                    }
                } else {
                    Toaster.customToast("Failed to save toss result!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Global.hideLoder();
                Toaster.customToast("Network error: " + t.getMessage());
            }
        });
    }

    private void updateSquadChnage(int current_striker_id, int current_non_striker_id, int current_bowler_id, int inning_id) {
        loaderView.showLoader();
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.inningUpdate(
                SessionManager.getToken(),
                new PlayingSqudUpdateBody(current_striker_id, current_non_striker_id, current_bowler_id, inning_id, strike));


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loaderView.hideLoader();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Parse Response
                        String responseBodyString = response.body().string();
                        Log.d("ResponseDetails", responseBodyString);

                        JSONObject jsonObject = new JSONObject(responseBodyString);
                        boolean status = jsonObject.optBoolean("status", false);
                        String message = jsonObject.optString("message", "No message");

                        if (status) {
                            JSONObject dataObject = jsonObject.optJSONObject("data");
                            if (dataObject != null) {

                                if (Global.isOnline(mContext)) {
                                    getInningDetails(inningNumber);
                                } else {
                                    Global.showDialog(mActivity);
                                }

                                // Extract data
                                // currentStrikerId=0;
                                //  currentNonStrikerId=0;
                                //  extractAndLoadData(dataObject);
                            } else {
                                Log.e("Error", "Data object is null");
                            }
                        } else {
                            Toaster.customToast(message);
                        }
                    } catch (Exception e) {
                        loaderView.hideLoader();
                        Log.e("Error", "Response parsing failed", e);
                        Toaster.customToast("Failed to parse response!");
                    }
                } else {
                    Toaster.customToast("Failed to process response!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loaderView.hideLoader();
                Toaster.customToast("Network error: " + t.getMessage());
            }
        });
    }


    private void showChangeStrikerDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_change_striker);

        CheckBox cbDeclared = bottomSheetDialog.findViewById(R.id.cbDeclared);
        CheckBox cbShort = bottomSheetDialog.findViewById(R.id.cbShort);

        TextView tvNotNow = bottomSheetDialog.findViewById(R.id.tvNotNow);
        TextView tvYesSure = bottomSheetDialog.findViewById(R.id.tvYesSure);


        cbDeclared.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                cbShort.setChecked(false);
                strikeChangeMessage = cbDeclared.getText().toString();

               // Toaster.customToast(strikeChangeMessage);
            }
        });
        cbShort.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                cbDeclared.setChecked(false);
                strikeChangeMessage = cbShort.getText().toString();
              //  Toaster.customToast(strikeChangeMessage);
            }
        });


        tvNotNow.setOnClickListener(v -> bottomSheetDialog.dismiss());

        tvYesSure.setOnClickListener(v -> {

            if (Global.isOnline(this)) {
                updateSquadChnage(currentNonStrikerId, currentStrikerId, currentBowlerId, inningNumber);
            } else {
                Global.showDialog(this);
            }

            bottomSheetDialog.dismiss();

//            if(strikeChangeMessage.isEmpty()){
//                Toaster.customToast("Tell the players what happened in the last ball");
//            }else{
//                if (Global.isOnline(this)) {
//                    updateSquadChnage(currentNonStrikerId, currentStrikerId, currentBowlerId, inningNumber);
//                } else {
//                    Global.showDialog(this);
//                }
//
//                bottomSheetDialog.dismiss();
//            }
        });


        bottomSheetDialog.show();
    }



}
