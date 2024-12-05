package com.cricoscore.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Adapter.PlayerAdapterForSelection;
import com.cricoscore.ParamBody.InningNewBody;
import com.cricoscore.ParamBody.ToasParamBody;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityStartInningBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.Player;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartInningActivity extends AppCompatActivity {
    ActivityStartInningBinding activityStartInningBinding;

    private ArrayList<Player> battingTeamPlayers = new ArrayList<>();
    private ArrayList<Player> bowlingTeamPlayers = new ArrayList<>();

    private TextView tvStriker, tvNonStriker, tvBowler, tvBatting, tvBowlingTeam, tvScheduleMatch;

    private boolean isStrikerSelected = false;
    private boolean isNonStrikerSelected = false;
    private boolean isBowlerSelected = false;

    int scheduleId,inning_id, teamIdBatting, inningNumber = 1, currentBowlerId, currentNonStrikerId, currentStrikerId, bowlingTeamId;
    JSONObject jsonObject;
    Context mContext;
    String StrikePlayerName ="",nonStrikeName ="",bowlerName ="",battingTeamName="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStartInningBinding = ActivityStartInningBinding.inflate(getLayoutInflater());
        setContentView(activityStartInningBinding.getRoot());

        mContext = this;

        setupToolbar();
        // Initialize views
        tvStriker = findViewById(R.id.tvStriker);
        tvNonStriker = findViewById(R.id.tvNonStriker);
        tvBowler = findViewById(R.id.tvBowler);
        tvBatting = findViewById(R.id.tvBatting);
        tvBowlingTeam = findViewById(R.id.tvBowlingTeam);
        tvScheduleMatch = findViewById(R.id.tvScheduleMatch);
        // Parsing JSON data (assuming you passed it via Intent)

        String dataString = getIntent().getStringExtra("dataObject");
        if (dataString != null) {
            parseData(dataString);
        }

        // Setting up click listeners for cards
        findViewById(R.id.cardStriker).setOnClickListener(v ->
                {
                    isStrikerSelected = true;
                    isNonStrikerSelected = false;
                    isBowlerSelected = false;
                    showPlayerSelectionDialog("Select Striker", battingTeamPlayers, player -> {
                        tvStriker.setText(player.getName()

                        );
                    });
                }
        );

        findViewById(R.id.cardNonStriker).setOnClickListener(v ->
                {
                    isStrikerSelected = false;
                    isNonStrikerSelected = true;
                    isBowlerSelected = false;

                    showPlayerSelectionDialog("Select Non-Striker", battingTeamPlayers, player -> {
                        tvNonStriker.setText(player.getName());
                    });
                }


        );

        findViewById(R.id.cardBowler).setOnClickListener(v ->

                {
                    isStrikerSelected = false;
                    isNonStrikerSelected = false;
                    isBowlerSelected = true;
                    showPlayerSelectionDialog("Select Bowler", bowlingTeamPlayers, player -> {
                        tvBowler.setText(player.getName());
                    });
                }
        );

        tvScheduleMatch.setOnClickListener(v -> {
            //...go to score card activity

            if (currentStrikerId == 0) {
                Toaster.customToast("Add Striker Player");
            } else if (currentNonStrikerId == 0) {
                Toaster.customToast("Add Non-Striker Player");
            } else if (currentBowlerId == 0) {
                Toaster.customToast("Add Bowler");
            } else {
                if (Global.isOnline(this)) {
                    startInningNew(scheduleId, teamIdBatting, inningNumber, currentBowlerId, currentNonStrikerId, currentStrikerId,
                            bowlingTeamId);
                } else {
                    Global.showDialog(this);
                }
            }


        });
    }

    private void setupToolbar() {
        ToolbarBinding toolbarBinding = activityStartInningBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarBinding.toolbartext.setText(getResources().getString(R.string.start_inning));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void parseData(String dataString) {
        try {
            JSONObject dataObject = new JSONObject(dataString);


            JSONObject jsonObjectBattingTeamId = dataObject.getJSONObject("batting_team");
            tvBatting.setText("Batting -" + jsonObjectBattingTeamId.getString("name"));
            battingTeamName = jsonObjectBattingTeamId.getString("name");

            inningNumber = dataObject.getInt("inning_no");



           // Toaster.customToast(inningNumber+"/");

            JSONObject jsonObjectBowlingTeamId = dataObject.getJSONObject("bowling_team");
            tvBowlingTeam.setText("Bowling -" + jsonObjectBowlingTeamId.getString("name"));

            scheduleId = dataObject.getInt("schedule_match_id");

            teamIdBatting = jsonObjectBattingTeamId.getInt("team_id");
            bowlingTeamId = jsonObjectBowlingTeamId.getInt("team_id");

            // Parsing Batting Team Players
            JSONArray selectedTeamAPlayers = jsonObjectBattingTeamId.optJSONArray("selected_players");

            if (selectedTeamAPlayers != null) {
                for (int i = 0; i < selectedTeamAPlayers.length(); i++) {
                    JSONObject playerObj = selectedTeamAPlayers.getJSONObject(i);
                    battingTeamPlayers.add(new Player(
                            playerObj.getInt("player_id"),
                            playerObj.getString("name"),
                            playerObj.getString("avatar")
                    ));
                }
            }

            // Parsing Bowling Team Players
            JSONArray selectedTeamBPlayers = jsonObjectBowlingTeamId.optJSONArray("selected_players");
            if (selectedTeamBPlayers != null) {
                for (int i = 0; i < selectedTeamBPlayers.length(); i++) {
                    JSONObject playerObj = selectedTeamBPlayers.getJSONObject(i);
                    bowlingTeamPlayers.add(new Player(
                            playerObj.getInt("player_id"),
                            playerObj.getString("name"),
                            playerObj.getString("avatar")
                    ));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse data", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPlayerSelectionDialog(String title, ArrayList<Player> players, PlayerAdapterForSelection.OnPlayerSelectListener listener) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_player_selection, null);

        RecyclerView rvPlayers = sheetView.findViewById(R.id.rvPlayers);
        TextView tvTitle = sheetView.findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        rvPlayers.setLayoutManager(new LinearLayoutManager(this));

        PlayerAdapterForSelection adapter = new PlayerAdapterForSelection(players, new PlayerAdapterForSelection.OnPlayerSelectListener() {
            @Override
            public void onSelect(Player player) {
                // Handle the selected player (Striker, Non-Striker, Bowler, etc.)
                // For example, update the UI or pass the selected player to another activity
                if (isStrikerSelected) {
                    tvStriker.setText("Striker: " + player.getName());
                    isStrikerSelected = false;  // Reset selection
                    currentStrikerId = player.getPlayerId();
                    StrikePlayerName = player.getName();
                } else if (isNonStrikerSelected) {
                    tvNonStriker.setText("Non-Striker: " + player.getName());
                    isNonStrikerSelected = false; // Reset selection
                    currentNonStrikerId = player.getPlayerId();
                    nonStrikeName = player.getName();
                } else if (isBowlerSelected) {
                    tvBowler.setText("Bowler: " + player.getName());
                    isBowlerSelected = false; // Reset selection
                    currentBowlerId = player.getPlayerId();
                    bowlerName = player.getName();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomSheetDialog.dismiss();
                    }
                }, 1000);

            }
        });

        rvPlayers.setAdapter(adapter);

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    private void startInningNew(int schedule_match_id, int team_id, int inning_number, int current_bowler_id,
                                int current_non_striker_id, int current_striker_id, int bowling_team_id) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.inningNew(
                SessionManager.getToken(),
                new InningNewBody(schedule_match_id, team_id, inning_number, current_bowler_id, current_non_striker_id, current_striker_id, bowling_team_id)
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
                                scheduleId = dataObject.getInt("schedule_match_id");
                                inning_id = dataObject.getInt("inning_id");
                                currentStrikerId = dataObject.getJSONObject("striker").getInt("player_id");
                                currentNonStrikerId = dataObject.getJSONObject("non_striker").getInt("player_id");
                                StrikePlayerName = dataObject.getJSONObject("striker").getString("name");
                                nonStrikeName = dataObject.getJSONObject("non_striker").getString("name");
                                bowlerName = dataObject.getJSONObject("bowler").getString("name");
                                currentBowlerId = dataObject.getJSONObject("bowler").getInt("player_id");
                                battingTeamName = dataObject.getString("team_name");;
                                bowlingTeamId = dataObject.getInt("bowling_team_id");;
                                teamIdBatting = dataObject.getInt("batting_team_id");


                                Intent intent = new Intent(mContext, ScoringDashBordActivity.class)
                                        .putExtra("StrikePlayerName",StrikePlayerName)
                                        .putExtra("NonStrikerName",nonStrikeName)
                                        .putExtra("BattingTeamName",battingTeamName)
                                        .putExtra("BowlerName",bowlerName)
                                        .putExtra("BowlerId",current_bowler_id)
                                        .putExtra("BowlingTeamId",bowling_team_id)
                                        .putExtra("StrikerPlayerId",currentStrikerId)
                                        .putExtra("NonStrikerPlayerId",currentNonStrikerId)
                                        .putExtra("inning_id",inning_id)
                                        .putExtra("scheduleId",scheduleId)
                                        .putExtra("teamIdBatting",teamIdBatting);


                                // Pass the JSON data as a string extra
                                intent.putExtra("dataObject", dataString);

                                // Start the activity
                                startActivity(intent);
                            } else {
                                Toaster.customToast("Toss result saved successfully!");
                                startActivity(new Intent(mContext, ScoringDashBordActivity.class));
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
