package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.cricoscore.ParamBody.ToasParamBody;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityTossBinding;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TossActivity extends AppCompatActivity {
    private ActivityTossBinding activityTossBinding;
    private AnimatorSet frontAnimation, backAnimation;
    private boolean isTeamSelected = false, isTossAction = false;
    private int TeamAId, TeamBId, scheduleId;
    private int tossWinnerTeamId = 0, battingTeamId = 0;
    private String message = "";
    private String winnerTeamName = "";
    JSONObject jsonObject;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTossBinding = ActivityTossBinding.inflate(getLayoutInflater());
        setContentView(activityTossBinding.getRoot());
        mContext = this;
        setupToolbar();
        setupAnimations();
        getIntentData();
        initView();
    }

    private void setupToolbar() {
        setSupportActionBar(activityTossBinding.toolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        activityTossBinding.toolbar.toolbartext.setText(getResources().getString(R.string.toss));
        activityTossBinding.toolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupAnimations() {
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        activityTossBinding.mbToss.setCameraDistance(8000 * scale);
        activityTossBinding.mbTossBack.setCameraDistance(8000 * scale);

        frontAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.front_animator);
        backAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.back_animator);
    }

    private void getIntentData() {
        if (getIntent() != null) {
            scheduleId = getIntent().getIntExtra("ScheduleId", 0);
            String TeamAName = getIntent().getStringExtra("TeamAName");
            String TeamBName = getIntent().getStringExtra("TeamBName");
            TeamAId = getIntent().getIntExtra("TeamAId", 0);
            TeamBId = getIntent().getIntExtra("TeamBId", 0);

            activityTossBinding.tvTeamName1.setText(TeamAName);
            activityTossBinding.tvTeamName2.setText(TeamBName);
        }
    }

    private void initView() {
        // Disable Batting and Bowling buttons initially
        activityTossBinding.tvBowling.setEnabled(false);
        activityTossBinding.tvBtting.setEnabled(false);

        // Flip button setup
        activityTossBinding.mbToss.setOnClickListener(v -> {
            flipCoinAnimation();
        });

        activityTossBinding.li1.setOnClickListener(v -> selectWinningTeam(TeamAId, activityTossBinding.tvTeamName1));
        activityTossBinding.li2.setOnClickListener(v -> selectWinningTeam(TeamBId, activityTossBinding.tvTeamName2));

        activityTossBinding.tvBowling.setOnClickListener(v -> setTossDecision("Bowling"));
        activityTossBinding.tvBtting.setOnClickListener(v -> setTossDecision("Batting"));

        activityTossBinding.mbCreate.setOnClickListener(v -> {
            if (!isTeamSelected) {
                Toaster.customToast(getString(R.string.selection_of_team_error));
            } else if (!isTossAction) {
                Toaster.customToast(getString(R.string.Decision_to_choose));
            } else {
                if ("Bowling".equals(message)) {
                    battingTeamId = (tossWinnerTeamId == TeamAId) ? TeamBId : TeamAId;
                } else {
                    battingTeamId = tossWinnerTeamId;
                }

                String tossDecisionMessage = winnerTeamName + " won the toss and decided to " + message + " first.";
                if (Global.isOnline(this)) {
                    tossApi(tossWinnerTeamId, battingTeamId, scheduleId, tossDecisionMessage);
                } else {
                    Global.showDialog(this);
                }
            }
        });
    }

    private void selectWinningTeam(int teamId, View teamView) {
        setTossDecision("");
        activityTossBinding.tvBowling.setBackgroundResource(R.drawable.tab_bacgroundindicator);
        activityTossBinding.tvBowling.setTextColor(getResources().getColor(R.color.black));
        activityTossBinding.tvBtting.setBackgroundResource(R.drawable.tab_bacgroundindicator);
        activityTossBinding.tvBtting.setTextColor(getResources().getColor(R.color.black));

        isTeamSelected = true;
        tossWinnerTeamId = teamId;

        resetTeamSelection();

        if (teamView.getId() == R.id.tvTeamName1) {
            activityTossBinding.li1.setBackgroundResource(R.drawable.tab_bacgroundindicator_red); // Highlight team A
            activityTossBinding.tvTeamName1.setTextColor(getResources().getColor(R.color.white)); // Change team A text color
        } else if (teamView.getId() == R.id.tvTeamName2) {
            activityTossBinding.li2.setBackgroundResource(R.drawable.tab_bacgroundindicator_red); // Highlight team B
            activityTossBinding.tvTeamName2.setTextColor(getResources().getColor(R.color.white)); // Change team B text color
        }

        winnerTeamName = ((androidx.appcompat.widget.AppCompatTextView) teamView).getText().toString();
        activityTossBinding.tvMessage.setText(winnerTeamName + " won the toss. Choose Batting or Bowling");

        enableTossSelectionButtons(); // Enable the buttons once a team is selected
    }


    private void resetTeamSelection() {
        activityTossBinding.li1.setBackgroundResource(R.drawable.tab_bacgroundindicator);
        activityTossBinding.tvTeamName1.setTextColor(getResources().getColor(R.color.black));

        activityTossBinding.li2.setBackgroundResource(R.drawable.tab_bacgroundindicator);
        activityTossBinding.tvTeamName2.setTextColor(getResources().getColor(R.color.black));
    }

    private void setTossDecision(String decision) {
        isTossAction = true;
        message = decision;
        resetSelection();

        if ("Bowling".equals(decision)) {
            activityTossBinding.tvBowling.setBackgroundResource(R.drawable.tab_bacgroundindicator_red);
            activityTossBinding.tvBowling.setTextColor(getResources().getColor(R.color.white));
        } else {
            activityTossBinding.tvBtting.setBackgroundResource(R.drawable.tab_bacgroundindicator_red);
            activityTossBinding.tvBtting.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void resetSelection() {
        activityTossBinding.tvBowling.setBackgroundResource(R.drawable.tab_bacgroundindicator);
        activityTossBinding.tvBtting.setBackgroundResource(R.drawable.tab_bacgroundindicator);
        activityTossBinding.tvBowling.setTextColor(getResources().getColor(R.color.black));
        activityTossBinding.tvBtting.setTextColor(getResources().getColor(R.color.black));
    }
    private void flipCoinAnimation() {
        // Step 1: Randomly set Head or Tail on the toss button
        String[] tossOptions = {"Heads", "Tails"};
        int randomIndex = new java.util.Random().nextInt(2); // Random number between 0 and 1
        String tossResult = tossOptions[randomIndex];  // Choose "Head" or "Tail"

        // Set the random toss result on the button or text view
        activityTossBinding.tvFlipMessage.setText("It's "+tossResult +"!");  // Set the result on the flip button text

        // Step 2: Set the winner team name after the toss
        if (tossWinnerTeamId == TeamAId) {
            winnerTeamName = activityTossBinding.tvTeamName1.getText().toString();
        } else if (tossWinnerTeamId == TeamBId) {
            winnerTeamName = activityTossBinding.tvTeamName2.getText().toString();
        }

        // Step 3: Update background colors immediately after toss decision
        updateTeamSelectionBackground();  // Make sure the background updates to show the winner

        // Step 4: Set message for the toss result
        //activityTossBinding.tvMessage.setText(winnerTeamName + " won the toss. Choose Batting or Bowling");

        // Step 5: Start the flip coin animation
        frontAnimation.setTarget(activityTossBinding.mbToss);
        backAnimation.setTarget(activityTossBinding.mbTossBack);

        // Start the coin flip animation
        frontAnimation.start();
        backAnimation.start();

        // Step 6: After the flip animation ends, enable the Batting and Bowling buttons
        new Handler().postDelayed(() -> {
           // enableTossSelectionButtons();  // Enable Batting and Bowling buttons after animation
        }, 2000); // Adjust delay for animation duration (2 seconds here)
    }



    private void updateTeamSelectionBackground() {
        // Set background for Team A and Team B based on toss winner
        if (tossWinnerTeamId == TeamAId) {
            // Team A is the winner
            activityTossBinding.li1.setBackgroundResource(R.drawable.tab_bacgroundindicator_red); // Team A selected
            activityTossBinding.tvTeamName1.setTextColor(getResources().getColor(R.color.white)); // Team A text color
            activityTossBinding.li2.setBackgroundResource(R.drawable.tab_bacgroundindicator); // Reset Team B background
            activityTossBinding.tvTeamName2.setTextColor(getResources().getColor(R.color.black)); // Reset Team B text color
        } else if (tossWinnerTeamId == TeamBId) {
            // Team B is the winner
            activityTossBinding.li2.setBackgroundResource(R.drawable.tab_bacgroundindicator_red); // Team B selected
            activityTossBinding.tvTeamName2.setTextColor(getResources().getColor(R.color.white)); // Team B text color
            activityTossBinding.li1.setBackgroundResource(R.drawable.tab_bacgroundindicator); // Reset Team A background
            activityTossBinding.tvTeamName1.setTextColor(getResources().getColor(R.color.black)); // Reset Team A text color
        }
    }

    private void enableTossSelectionButtons() {
        // Enable the Batting and Bowling buttons only after the coin flip animation is done
        activityTossBinding.tvBowling.setEnabled(true);
        activityTossBinding.tvBtting.setEnabled(true);
    }




    private void tossApi(int tossWinnerTeamId, int battingTeamId, int scheduleMatchId, String message) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.toss(
                SessionManager.getToken(),
                new ToasParamBody(tossWinnerTeamId, battingTeamId, scheduleMatchId, message)
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Convert ResponseBody to String
                        String responseBodyString = response.body().string();

                        // Parse the string to a JSONObject
                        jsonObject = new JSONObject(responseBodyString);

                        // Check if the status is true
                        boolean status = jsonObject.optBoolean("status", false);
                        String message = jsonObject.optString("message", "No message");

                        if (status) {
                            // Extract "data" object if needed
                            // Example: Get schedule_match_id from "data"
                            // Extract "data" object if needed
                            JSONObject dataObject = jsonObject.optJSONObject("data");

                            if (dataObject != null) {
                                // Convert JSONObject to String
                                String dataString = dataObject.toString();

                                // Create an Intent to StartInningActivity
                                Intent intent = new Intent(mContext, StartInningActivity.class);

                                // Pass the JSON data as a string extra
                                intent.putExtra("dataObject", dataString);

                                // Start the activity
                                startActivity(intent);
                            } else {
                                Toaster.customToast("Toss result saved successfully!");
                                startActivity(new Intent(mContext, StartInningActivity.class));
                            }

                        } else {
                            Toaster.customToast(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
}
