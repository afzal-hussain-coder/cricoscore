package com.cricoscore.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Adapter.YourPlayerListAdapter;
import com.cricoscore.ParamBody.AddPlayerIntoTeamBody;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.PlayerModel;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourPlayerListActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    RecyclerView rv_teamList;
    MaterialCardView mcv_submit;
    SelectTournamentType drop_tournamentName;
    MaterialButton mb_submit;
    int position = 0;
    String tournamentType = "";
    private ArrayList<DataModel> option_tournament_list = new ArrayList<>();
    ImageView img_add;

    YourPlayerListAdapter yourPlayerListAdapter;
    SharedPreferences sharedPreferences;
    ArrayList<PlayerModel>addDtaList = new ArrayList<>();
    String playerId="";
    int teamId=0;
    ArrayList<PlayerModel> receivedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_player_list);

        mActivity = this;
        mContext = this;

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        if (getIntent() != null) {
            teamId = Integer.parseInt(getIntent().getStringExtra("ID"));
            Toaster.customToast(teamId+"//");
        }
        receivedList = (ArrayList<PlayerModel>) getIntent().getSerializableExtra("List");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbartext);
        mTitle.setText(getResources().getString(R.string.yourPlayerlist));
        toolbar.setNavigationOnClickListener(v -> finish());

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

            if (Global.isOnline(mContext)) {
                addPlayerIntoTeam(playerId,teamId);
            } else {
                Global.showDialog(mActivity);
            }

       // saveList(sharedPreferences,addDtaList);
        finish();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Global.isOnline(mContext)) {
            getPlayerList();
        } else {
            Global.showDialog(mActivity);
        }
    }

    public void getPlayerList() {
        Global.showLoader(getSupportFragmentManager());

        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getMyPlayer(SessionManager.getToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        ArrayList<PlayerModel> playerModelArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            playerModelArrayList.add(new PlayerModel(jsonArray.getJSONObject(i)));
                        }

                        yourPlayerListAdapter = new YourPlayerListAdapter(mContext, playerModelArrayList,receivedList ,playerModelArrayList1 -> {

                            addDtaList = playerModelArrayList1;

                            StringBuilder sb = new StringBuilder();

                            for (int i = 0; i < addDtaList.size(); i++) {
                                sb.append(addDtaList.get(i).getPlayer_id());
                                if (i < addDtaList.size() - 1) {
                                    sb.append(",");
                                }
                            }

                            playerId = sb.toString();


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

    public void addPlayerIntoTeam(String playerId,int teamId) {
        Global.showLoader(getSupportFragmentManager());

        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.addPlayerInTeam(SessionManager.getToken(),new AddPlayerIntoTeamBody(playerId,teamId));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject
                        JSONArray jsonArray = jsonObject.getJSONArray("data");


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


    public void saveList(SharedPreferences sharedPreferences, ArrayList<PlayerModel> list) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();

        editor.clear();
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

        editor.putString("my_list", jsonArray.toString());
        editor.commit();

        // Log the size of the saved list
        //Toaster.customToast("Saved list size: " + list.get(0).getPlayer_id());
    }
    public ArrayList<PlayerModel> retrieveList(SharedPreferences sharedPreferences) {
        ArrayList<PlayerModel> list = new ArrayList<>();
        String jsonString = sharedPreferences.getString("my_list", null);

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(new PlayerModel(jsonArray.getJSONObject(i)));

                   // Toaster.customToast(jsonArray.getJSONObject(i).getInt("player_id")+"////");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}