package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cricoscore.Adapter.PlayingsquadAdapter;
import com.cricoscore.ParamBody.InningNewBody;
import com.cricoscore.ParamBody.PlayingSqudUpdateBody;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityPlayingSquadBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.BattingPlayersResponse;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayingSquadActivity extends AppCompatActivity {

    ActivityPlayingSquadBinding activityPlayingSquadBinding;
    Context mContext;
    Activity mActivity;
    String PlayerName = "";
    int playingId;
    int scheduleMatchId;
    int inningId;
    int teamId;
    int current_striker_id;
    int current_non_striker_id;
    int current_bowler_id;
    String Strike ="";
    String type ="";
    String bowlerType ="";
    int lastRun =0;
    PlayingsquadAdapter playingsquadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPlayingSquadBinding = ActivityPlayingSquadBinding.inflate(getLayoutInflater());
        setContentView(activityPlayingSquadBinding.getRoot());
        mContext = this;
        mActivity = this;

        setupToolbar();
        initView();

        if (getIntent() != null) {
            scheduleMatchId = getIntent().getIntExtra("ScheduleId", 0);
            inningId = getIntent().getIntExtra("InningId", 0);
            teamId = getIntent().getIntExtra("TeamId", 0);
            current_striker_id = getIntent().getIntExtra("current_striker_id",0);
            current_non_striker_id = getIntent().getIntExtra("current_non_striker_id",0);
            current_bowler_id = getIntent().getIntExtra("current_bowler_id",0);
            Strike = getIntent().getStringExtra("Strike");
            bowlerType = getIntent().getStringExtra("Bowler");
            lastRun = getIntent().getIntExtra("lastRun",0);
            if(Strike.equalsIgnoreCase("Strike")){
                type = Strike;
            } else if(Strike.equalsIgnoreCase("NonStrike")){
                type = Strike;
            }else if(bowlerType.equalsIgnoreCase("Bowler")){
                type = bowlerType;
            }

            Log.d("Data",current_bowler_id+"/"+scheduleMatchId+"/"+inningId+"/"+teamId+"/"+current_striker_id+"?"+current_non_striker_id+"/"+type);

            if (Global.isOnline(this)) {
                getSquadPlayer(scheduleMatchId, inningId, teamId);
            } else {
                Global.showDialog(this);
            }
        }

    }

    private void initView() {

        activityPlayingSquadBinding.rvPlayingSquad.setHasFixedSize(true);
        activityPlayingSquadBinding.rvPlayingSquad.setLayoutManager(new LinearLayoutManager(mContext));

        activityPlayingSquadBinding.mbSubmit.setOnClickListener(v -> {


            if(lastRun % 2==0 || lastRun ==0){
                if (Global.isOnline(this)) {
                    updateSquad(current_non_striker_id,current_striker_id,current_bowler_id,inningId,type);
                } else {
                    Global.showDialog(this);
                }
            }else{
                if (Global.isOnline(this)) {
                    updateSquad(current_striker_id,current_non_striker_id,current_bowler_id,inningId,type);
                } else {
                    Global.showDialog(this);
                }
            }

        });

    }

    private void setupToolbar() {
        ToolbarBinding toolbarBinding = activityPlayingSquadBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarBinding.toolbartext.setText(getResources().getString(R.string.playing_squad));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());
    }


    public void getSquadPlayer(int scheduleMatchId, int inningId, int teamId) {
        Global.showLoader(getSupportFragmentManager());

        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getBattingPlayers(SessionManager.getToken(), scheduleMatchId, inningId, teamId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Convert ResponseBody to a JSON string
                        String responseBodyString = response.body().string();
                        Log.d("Raw ResponseBody", responseBodyString);

                        // Convert the JSON string into a JSONObject
                        JSONObject jsonObject = new JSONObject(responseBodyString);
                        Log.d("Parsed JSON", jsonObject.toString());

                        // Example: Accessing fields from the JSON object
                        boolean status = jsonObject.getBoolean("status");
                        String message = jsonObject.getString("message");

                        if (status) {
                            //JSONArray playersArray = jsonObject.getJSONArray("data");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            ArrayList<BattingPlayersResponse.Player> playerList = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                playerList.add(new BattingPlayersResponse.Player(jsonArray.getJSONObject(i)));
                            }

                            if (playerList != null && !playerList.isEmpty()) {
                                // Bind data to RecyclerView

                                playingsquadAdapter =new PlayingsquadAdapter(mContext,current_bowler_id, playerList, player -> {
                                    PlayerName = player.getName();


                                    playingId = player.getPlayer_id();

                                    if(Strike.equalsIgnoreCase("Strike")){
                                        current_striker_id = playingId;
                                    }else if(Strike.equalsIgnoreCase("NonStrike")){
                                        current_non_striker_id = playingId;
                                    }else{
                                        current_bowler_id = playingId;
                                    }
                                });
                                playingsquadAdapter.filterPlayers();
                                activityPlayingSquadBinding.rvPlayingSquad.setAdapter(playingsquadAdapter);

                            } else {
                                Log.d("Debug", "Players list is empty.");
                            }

                        } else {
                            Log.e("Error", "API returned status false with message: " + message);
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Log.e("Error", "Failed to parse response: " + e.getMessage());
                    }
                } else {
                    try {
                        // Handle error body
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Log.e("Error Body", errorBody);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void updateSquad(int current_striker_id, int current_non_striker_id, int current_bowler_id, int inning_id,String type) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.inningUpdate(
                SessionManager.getToken(),
                new PlayingSqudUpdateBody(current_striker_id, current_non_striker_id, current_bowler_id, inning_id,type));


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Convert ResponseBody to String
                        String responseBodyString = response.body().string();
                        finish();

                        // Parse the string to a JSONObject
//                        jsonObject = new JSONObject(responseBodyString);
//
//                        // Check if the status is true
//                        boolean status = jsonObject.optBoolean("status", false);
//                        String message = jsonObject.optString("message", "No message");
//
//                        if (status) {
//                            // Extract "data" object if needed
//                            // Example: Get schedule_match_id from "data"
//                            // Extract "data" object if needed
//                            JSONObject dataObject = jsonObject.optJSONObject("data");
//
//                            if (dataObject != null) {
//                                // Convert JSONObject to String
//                                String dataString = dataObject.toString();
//
//                                // Create an Intent to StartInningActivity
//                                scheduleId = dataObject.getInt("schedule_match_id");
//                                inning_id = dataObject.getInt("inning_id");
//                                currentStrikerId = dataObject.getJSONObject("striker").getInt("player_id");
//                                currentNonStrikerId = dataObject.getJSONObject("non_striker").getInt("player_id");
//                                StrikePlayerName = dataObject.getJSONObject("striker").getString("name");
//                                nonStrikeName = dataObject.getJSONObject("non_striker").getString("name");
//                                bowlerName = dataObject.getJSONObject("bowler").getString("name");
//                                currentBowlerId = dataObject.getJSONObject("bowler").getInt("player_id");
//                                battingTeamName = dataObject.getString("team_name");;
//                                bowlingTeamId = dataObject.getInt("bowling_team_id");;
//                                teamIdBatting = dataObject.getInt("batting_team_id");
//
//
//                                Intent intent = new Intent(mContext, ScoringDashBordActivity.class)
//                                        .putExtra("StrikePlayerName",StrikePlayerName)
//                                        .putExtra("NonStrikerName",nonStrikeName)
//                                        .putExtra("BattingTeamName",battingTeamName)
//                                        .putExtra("BowlerName",bowlerName)
//                                        .putExtra("BowlerId",current_bowler_id)
//                                        .putExtra("BowlingTeamId",bowling_team_id)
//                                        .putExtra("StrikerPlayerId",current_striker_id)
//                                        .putExtra("NonStrikerPlayerId",current_non_striker_id)
//                                        .putExtra("inning_id",inning_id)
//                                        .putExtra("scheduleId",scheduleId)
//                                        .putExtra("teamIdBatting",teamIdBatting);
//
//
//                                // Pass the JSON data as a string extra
//                                intent.putExtra("dataObject", dataString);
//
//                                // Start the activity
//                                startActivity(intent);
//                            } else {
//                                Toaster.customToast("Toss result saved successfully!");
//                                startActivity(new Intent(mContext, ScoringDashBordActivity.class));
//                            }
//
//                        } else {
//                            Toaster.customToast(message);
//                        }
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