package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cricoscore.Adapter.CretaedScheduleTeamPlayerListAdapter;
import com.cricoscore.Adapter.YourPlayerListAdapter;
import com.cricoscore.ParamBody.AddPlayerIntoTeamBody;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityScheduleMatchBinding;
import com.cricoscore.databinding.ActivityTeamPlayerlistBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.PlayerModel;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamPlayerlistActivity extends AppCompatActivity {

    ActivityTeamPlayerlistBinding activityTeamPlayerlistBinding;
    Context mContext;
    Activity mActivity;

    RecyclerView rv_teamList;
    MaterialCardView mcv_submit;
    SelectTournamentType drop_tournamentName;
    MaterialButton mb_submit;
    int position = 0;
    String tournamentType = "";
    private ArrayList<DataModel> option_tournament_list = new ArrayList<>();
    ImageView img_add;

    CretaedScheduleTeamPlayerListAdapter yourPlayerListAdapter;
    SharedPreferences sharedPreferences;
    ArrayList<PlayerModel>addDtaList = new ArrayList<>();
    String playerId="";
    int teamId=0;
    ArrayList<PlayerModel> receivedList = new ArrayList<>();
    //ArrayList<Integer> receivedList = new ArrayList<>();
    String TeamName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTeamPlayerlistBinding = ActivityTeamPlayerlistBinding.inflate(getLayoutInflater());
        setContentView(activityTeamPlayerlistBinding.getRoot());

        mContext = this;
        mActivity = this;

       // sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        ToolbarBinding toolbarBinding = activityTeamPlayerlistBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //toolbarBinding.toolbartext.setText(getResources().getString(R.string.schedule_tournament_list));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        if (getIntent() != null) {
            teamId = getIntent().getIntExtra("ID",0);
            TeamName = getIntent().getStringExtra("From");

            Toaster.customToast(teamId+"//");
        }

        initView();
    }

    private void initView() {

        img_add = findViewById(R.id.img_add);
        img_add.setVisibility(View.VISIBLE);

        mcv_submit = findViewById(R.id.mcv_submit);
        mb_submit = findViewById(R.id.mb_submit);


        rv_teamList = findViewById(R.id.rv_teamList);
        rv_teamList.setLayoutManager(new LinearLayoutManager(mContext));
        rv_teamList.setHasFixedSize(true);


        drop_tournamentName = findViewById(R.id.drop_tournamentName);
        option_tournament_list.add(new DataModel("Team A"));
        option_tournament_list.add(new DataModel("Team B"));
        option_tournament_list.add(new DataModel("Team C"));
        option_tournament_list.add(new DataModel("Team D"));
        drop_tournamentName.setOptionList(option_tournament_list);
        drop_tournamentName.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                tournamentType = name;
                if (!tournamentType.isEmpty()) {
                    mb_submit.setVisibility(View.VISIBLE);
                } else {
                    mb_submit.setVisibility(View.GONE);
                }

            }


            @Override
            public void onDismiss() {
            }
        });


        img_add.setOnClickListener(v -> {
            startActivity(new Intent(mContext, AddPlayer.class));
        });

        mb_submit.setOnClickListener(v -> {

            if (TeamName.equals("A")) {
                saveList("TeamA_List", addDtaList); // Save for Team A
            } else if (TeamName.equals("B")) {
                saveList("TeamB_List", addDtaList); // Save for Team B
            }
            // Indicate that data has been saved
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        receivedList.clear();

        // Load data based on the team type
        if (TeamName.equals("A")) {
            receivedList = retrieveList("TeamA_List");
        } else if (TeamName.equals("B")) {
            receivedList = retrieveList("TeamB_List");
        }

        // Log the size of the retrieved list
        //Toaster.customToast("Retrieved list size: " + receivedList.size());

        if (Global.isOnline(mContext)) {
            getPlayerList(teamId);
        } else {
            Global.showDialog(mActivity);
        }
    }


    public void getPlayerList(int teamId) {
        Global.showLoader(getSupportFragmentManager());

        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getTeamDetails(SessionManager.getToken(),String.valueOf(teamId));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                        JSONArray jsonArray = jsonObject1.getJSONArray("players");
                        ArrayList<PlayerModel> playerModelArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            playerModelArrayList.add(new PlayerModel(jsonArray.getJSONObject(i)));
                        }

                        yourPlayerListAdapter = new CretaedScheduleTeamPlayerListAdapter(mContext, playerModelArrayList,receivedList , playerModelArrayList1 -> {

                            addDtaList = playerModelArrayList1;

                            Log.d("DataSize",addDtaList.size()+"");

                            StringBuilder sb = new StringBuilder();

                            for (int i = 0; i < addDtaList.size(); i++) {
                                sb.append(addDtaList.get(i).getPlayer_id());
                                if (i < addDtaList.size() - 1) {
                                    sb.append(",");
                                }
                            }

                            playerId = sb.toString();

                            Log.d("playerId",addDtaList.size()+"");


                            if (addDtaList.size() > 0) {
                                mb_submit.setVisibility(View.VISIBLE);
                            } else {
                                mb_submit.setVisibility(View.GONE);
                            }
                        });

                        rv_teamList.setAdapter(yourPlayerListAdapter);


                    } catch (Exception e) {
                        Log.e("Error", "JSON parsing error: " + e.getMessage());
                    }
                } else {
                    Log.e("Error", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", "Failed to fetch data: " + t.getMessage());
                Global.hideLoder();
            }
        });
    }

    public void saveList(String teamName, ArrayList<PlayerModel> list) {
        // Use different SharedPreferences files for different teams
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();

        // Clear the existing data for that specific team
        editor.remove(teamName);

        for (PlayerModel player : list) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", player.getName());
                jsonObject.put("avatar", player.getAvatar());
                jsonObject.put("player_id", player.getPlayer_id());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Save the player list under the specified team name
        editor.putString(teamName, jsonArray.toString());
        editor.apply(); // Use apply() for asynchronous saving
    }

    public ArrayList<PlayerModel> retrieveList(String teamName) {
        ArrayList<PlayerModel> list = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(teamName, null);

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(new PlayerModel(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<PlayerModel> retrieveListB(SharedPreferences sharedPreferences) {
        ArrayList<PlayerModel> list = new ArrayList<>();
        String jsonString = sharedPreferences.getString("TeamB_List", null); // Key for Team B

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(new PlayerModel(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}