package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cricoscore.Adapter.TeamListAdapter;
import com.cricoscore.Adapter.YourTeamListAdapter;
import com.cricoscore.Adapter.YourTeamListAdapterHorizontal;
import com.cricoscore.ParamBody.AddTeamInTournamnetBody;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.TeamModel;
import com.cricoscore.model.TournamentModel.TournamentDetails;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourTeamListActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    RecyclerView rv_teamList;
    MaterialButton mb_add_new_team;
    SelectTournamentType drop_tournamentName;
    MaterialButton mb_submit;
    int position = 0;
    String tournamentType = "";
    private ArrayList<DataModel> option_tournament_list = new ArrayList<>();
    ImageView img_add;
    public String tournamentId = "";
    public String teamId = null;

    YourTeamListAdapter yourTeamListAdapter;
    ArrayList<TeamModel>updatedPlayer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_team_list);

        mActivity = this;
        mContext = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbartext);
        mTitle.setText(getResources().getString(R.string.yourteamlist));
        toolbar.setNavigationOnClickListener(v -> finish());

        if (getIntent() != null) {
            tournamentId = getIntent().getStringExtra("Id");
        }

        initView();

        if (Global.isOnline(mContext)) {
            getTournamentDetails(tournamentId);
        } else {
            Global.showDialog(mActivity);
        }

        if (Global.isOnline(mContext)) {
            getMyTeamList();
        } else {
            Global.showDialog(mActivity);
        }
    }

    private void initView() {

        mb_add_new_team = findViewById(R.id.mb_add_new_team);
        img_add = findViewById(R.id.img_add);
        img_add.setVisibility(View.VISIBLE);

        rv_teamList = findViewById(R.id.rv_teamList);
        rv_teamList.setLayoutManager(new LinearLayoutManager(mContext));
        rv_teamList.setHasFixedSize(true);


//        drop_tournamentName= findViewById(R.id.drop_tournamentName);
//        option_tournament_list.add(new DataModel("Tournament A"));
//        option_tournament_list.add(new DataModel("Tournament B"));
//        option_tournament_list.add(new DataModel("Tournament C"));
//        option_tournament_list.add(new DataModel("Tournament D"));
//        drop_tournamentName.setOptionList(option_tournament_list);
//        drop_tournamentName.setClickListener(new SelectTournamentType.onClickInterface() {
//            @Override
//            public void onClickAction() {
//            }
//
//            @Override
//            public void onClickDone(String name) {
//                tournamentType = name;
//                if(!tournamentType.isEmpty()){
//                    mb_submit.setVisibility(View.VISIBLE);
//                }else{
//                    mb_submit.setVisibility(View.GONE);
//                }
//
//            }
//
//
//            @Override
//            public void onDismiss() {
//            }
//        });

        img_add.setOnClickListener(v -> {
            startActivity(new Intent(mContext, AddTeamActivity.class).putExtra("Id",tournamentId));
        });

        mb_add_new_team.setOnClickListener(v -> {
            if (Global.isOnline(mContext)) {
                getSubmitTeam(tournamentId, teamId);
            } else {
                Global.showDialog(mActivity);
            }
        });
    }


    public void getMyTeamList() {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getMyTeam(SessionManager.getToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject


                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        ArrayList<TeamModel> teamModelArrayList = new ArrayList<>();
                        TeamModel teamModel;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            teamModel = new TeamModel(jsonArray.getJSONObject(i));
                            teamModelArrayList.add(teamModel);
                        }


                        yourTeamListAdapter = new YourTeamListAdapter(mContext, teamModelArrayList,updatedPlayer, new YourTeamListAdapter.itemClickListener() {
                            @Override
                            public void checkedItem(ArrayList<Integer> arrylist) {


                                StringBuilder sb = new StringBuilder();

                                for (int i = 0; i < arrylist.size(); i++) {
                                    sb.append(arrylist.get(i));
                                    if (i < arrylist.size() - 1) {
                                        sb.append(",");
                                    }
                                }

                                teamId = sb.toString();

                                Log.d("Team Id", teamId);

                                //Toaster.customToast(arrylist.size()+"size");

                                if (arrylist.size() > 0) {
                                    mb_add_new_team.setVisibility(View.VISIBLE);
                                } else {
                                    mb_add_new_team.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void removeTeam(int pos,int teamId) {
                                if (Global.isOnline(mContext)) {
                                    removeTeamFromList(teamId,pos);
                                } else {
                                    Global.showDialog(mActivity);
                                }
                            }
                        });

                        rv_teamList.setAdapter(yourTeamListAdapter);
                        Log.d("Response", jsonObject.toString());


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

    public void getSubmitTeam(String tournamentId, String teamId) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.addTeamInTournamnet(SessionManager.getToken(),new AddTeamInTournamnetBody(tournamentId,teamId));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        Toaster.customToast(jsonObject.getString("message"));

                        startActivity(new Intent(mContext,TournamentDetailsActivity.class).putExtra("id",tournamentId));
                        finish();


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


    public void removeTeamFromList(int teamId,int pos) {

        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.removeTeam(SessionManager.getToken(), teamId);

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

    public void getTournamentDetails(String tournamentId) {
       // Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getTournamentDetails(SessionManager.getToken(), tournamentId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               // Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        JSONObject finalData = jsonObject.getJSONObject("data");

                        JSONArray jsonArray = finalData.getJSONArray("teams");
                        for(int i=0;i<jsonArray.length();i++){
                            updatedPlayer.add(new TeamModel(jsonArray.getJSONObject(i)));
                        }




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
              //  Global.hideLoder();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}