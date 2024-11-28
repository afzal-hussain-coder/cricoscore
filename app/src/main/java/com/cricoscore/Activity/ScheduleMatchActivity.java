package com.cricoscore.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cricoscore.Adapter.ScheduleMatchAdapter;
import com.cricoscore.Adapter.ScheduleTournamentAdapter;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.databinding.ActivityScheduleMatchBinding;
import com.cricoscore.databinding.ActivityScheduleTournamentBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.PlayerModel;
import com.cricoscore.model.ScheduleMatchDetailsItem;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleMatchActivity extends AppCompatActivity {

    ActivityScheduleMatchBinding activityScheduleMatchBinding;
    Context mContext;
    Activity mActivity;
    ScheduleMatchAdapter scheduleMatchAdapter;
    private int itemPosition;
    private ArrayList<DataModel> option_player_role = null;
    private ArrayList<DataModel> option_player_type = null;
    public static Uri image_uri=null;
    CircleImageView iv_team_logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityScheduleMatchBinding = ActivityScheduleMatchBinding.inflate(getLayoutInflater());
        setContentView(activityScheduleMatchBinding.getRoot());

        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityScheduleMatchBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.schedule_tournament_list));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());



        activityScheduleMatchBinding.rvScheduleMatchList.setHasFixedSize(true);
        activityScheduleMatchBinding.rvScheduleMatchList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(image_uri!=null){
            iv_team_logo.setImageURI(image_uri);
            image_uri = null;
        }

        if (Global.isOnline(mContext)) {
            getScheduleMatchList();
        } else {
            Global.showDialog(mActivity);
        }

    }

    /* player ki list pe drop down ko hidwe karna ka logic
     * playing 11 ke button ko aftre 11 player ke sel;cetionke bad on karn ahai..*/

    public void showBottomSheetDialog(Uri image_uri) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.add_player_dialog);
        SelectTournamentType drop_pType = bottomSheetDialog.findViewById(R.id.drop_pType);
        SelectTournamentType drop_pRole = bottomSheetDialog.findViewById(R.id.drop_pRole);
        drop_pRole.setOptionList(setRole());
        drop_pType.setOptionList(setType());
        drop_pRole.setClickListener(new SelectTournamentType.onClickInterface() {
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
        drop_pType.setClickListener(new SelectTournamentType.onClickInterface() {
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
        RelativeLayout rl_team_logo = bottomSheetDialog.findViewById(R.id.rl_team_logo);
        iv_team_logo = bottomSheetDialog.findViewById(R.id.iv_team_logo);
        TextInputEditText edit_text_pName = bottomSheetDialog.findViewById(R.id.edit_text_pName);
        TextInputEditText edit_text_pMobile = bottomSheetDialog.findViewById(R.id.edit_text_pMobile);
        RelativeLayout rl_select_player = bottomSheetDialog.findViewById(R.id.rl_select_player);
        MaterialButton mb_submit = bottomSheetDialog.findViewById(R.id.mb_submit);

        rl_team_logo.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","ScheduleMatchActivity"));
        });


        rl_select_player.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, YourPlayerListActivity.class));
            bottomSheetDialog.dismiss();
        });

        mb_submit.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();

    }

    private ArrayList<DataModel> setRole() {
        option_player_role = new ArrayList<>();
        option_player_role.add(new DataModel("Captain"));
        option_player_role.add(new DataModel("Vice Captain"));
        option_player_role.add(new DataModel("Coach"));
        option_player_role.add(new DataModel("Player"));
        option_player_role.add(new DataModel("Commentator"));
        option_player_role.add(new DataModel("Manager"));
        option_player_role.add(new DataModel("Scorer"));
        return  option_player_role;
    }

    private ArrayList<DataModel> setType() {
        option_player_type = new ArrayList<>();
        option_player_type.add(new DataModel("Bowler"));
        option_player_type.add(new DataModel("Batsman"));
        option_player_type.add(new DataModel("Wicketkeeper"));
        option_player_type.add(new DataModel("All Rounder"));
        return option_player_type;
    }

    public void getScheduleMatchList() {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getScheduleMatchList(SessionManager.getToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        JSONArray finalData = jsonObject.getJSONArray("data");

                        ArrayList<ScheduleMatchDetailsItem>list =new ArrayList<>();
                        for(int i =0;i<finalData.length();i++){
                            list.add( new ScheduleMatchDetailsItem(finalData.getJSONObject(i)));
                        }

                        scheduleMatchAdapter = new ScheduleMatchAdapter(mContext, list, new ScheduleMatchAdapter.getImageCallListener() {
                            @Override
                            public void getDetails(int id,String teamA,String teamB) {
                                startActivity(new Intent(mContext,ScheduleedMatchDetaislUpdateActivity.class).putExtra("ID",id)
                                        .putExtra("TeamA",teamA)
                                        .putExtra("TeamB",teamB));
                            }
                        });
                        activityScheduleMatchBinding.rvScheduleMatchList.setAdapter(scheduleMatchAdapter);
                        Log.d("FinalResponse", finalData.length()+"");


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
