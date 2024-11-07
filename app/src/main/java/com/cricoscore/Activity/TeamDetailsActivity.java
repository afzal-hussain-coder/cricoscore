package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cricoscore.Adapter.YourPlayerListAdapterHorizontal;
import com.cricoscore.Adapter.YourTeamListAdapterHorizontal;
import com.cricoscore.ParamBody.RemovePlayerBody;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityTeamDetailsBinding;
import com.cricoscore.databinding.ActivityTournamentDetailsBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.PlayerModel;
import com.cricoscore.model.TeamModel;
import com.cricoscore.model.TournamentModel.TournamentDetails;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailsActivity extends AppCompatActivity {
    ActivityTeamDetailsBinding activityTeamDetailsBinding;
    Context mContext;
    Activity mActivity;
    String teamName = "";
    YourPlayerListAdapterHorizontal yourTeamListAdapter;
    ImageView img_add;
    Integer position = 0;
    String teamId = "";
    SharedPreferences sharedPreferences;
    ArrayList<PlayerModel> newList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTeamDetailsBinding = ActivityTeamDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityTeamDetailsBinding.getRoot());
        mContext = this;
        mActivity = this;


        ToolbarBinding toolbarBinding = activityTeamDetailsBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        if (getIntent() != null) {
            teamId = getIntent().getStringExtra("ID");
        }
        toolbarBinding.toolbartext.setText(teamName);
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        initView();

    }

    private void initView() {
        img_add = findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);


        activityTeamDetailsBinding.rvTeamList.setLayoutManager(new GridLayoutManager(mContext, 2));
        activityTeamDetailsBinding.rvTeamList.setHasFixedSize(true);


        activityTeamDetailsBinding.mbAddNewTeam.setOnClickListener(v -> {
            startActivity(new Intent(mContext, YourPlayerListActivity.class).putExtra("ID", teamId)
                    .putExtra("List",newList));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //newList.clear();
        //newList = retrieveList(sharedPreferences);

        // Log the size of the retrieved list
        //Toaster.customToast("Retrieved list size: " + newList.size());



        if (Global.isOnline(mContext)) {
            getTeamDetails(teamId);
        } else {
            Global.showDialog(mActivity);
        }

    }

    public void getTeamDetails(String teamId) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getTeamDetails(SessionManager.getToken(), teamId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        JSONObject jsonObject1Data = jsonObject.getJSONObject("data");
                        setData(jsonObject1Data);

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

    private void setData(JSONObject jsonObject) {
//        {
//            "status": true,
//                "message": "Team has been delete",
//                "data": {
//            "team_id": 1,
//                    "name": "Gurgram Team A",
//                    "city": "gurgaon",
//                    "team_logo": "uploads/team.png",
//                    "players": []
//        }
//        }

        try {
            String name = jsonObject.getString("name");
            activityTeamDetailsBinding.tvTName.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String city = jsonObject.getString("city");
            activityTeamDetailsBinding.tvtLocation.setText(city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String team_logo = jsonObject.getString("team_logo");
            if (team_logo.isEmpty()) {
                Glide.with(mContext).load(R.drawable.placeholder_user).into(activityTeamDetailsBinding.image);
            } else {
                Glide.with(mContext).load(Global.BASE_URL + "/" + team_logo).into(activityTeamDetailsBinding.image);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArrayPlayer = jsonObject.getJSONArray("players");

            newList.clear();
            for (int i = 0; i < jsonArrayPlayer.length(); i++) {
                newList.add(new PlayerModel(jsonArrayPlayer.getJSONObject(i)));
            }

            yourTeamListAdapter = new YourPlayerListAdapterHorizontal(mContext, newList, new YourPlayerListAdapterHorizontal.itemClickListener() {
                @Override
                public void checkedItem(int pos, int teamId) {

                }

                @Override
                public void removeItem(int pos, int playerId) {

                    if (Global.isOnline(mContext)) {
                        removePlayer(Integer.parseInt(teamId),String.valueOf(playerId),pos);
                    } else {
                        Global.showDialog(mActivity);
                    }



                }
            });
            activityTeamDetailsBinding.rvTeamList.setAdapter(yourTeamListAdapter);

//            if (jsonArrayPlayer.length() == 0) {
//                activityTeamDetailsBinding.mbAddNewTeam.setVisibility(View.VISIBLE);
//            } else {
//                activityTeamDetailsBinding.mbAddNewTeam.setVisibility(View.GONE);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public ArrayList<PlayerModel> retrieveList(SharedPreferences sharedPreferences) {

        ArrayList<PlayerModel> list = new ArrayList<>();
        String jsonString = sharedPreferences.getString("my_list", null);

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

    public void removePlayer(int teamId,String playerId,int pos) {

        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.removePlayer(SessionManager.getToken(), teamId,playerId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject
                        yourTeamListAdapter.deleteItem(pos);

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

}