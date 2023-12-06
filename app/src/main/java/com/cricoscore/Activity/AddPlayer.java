package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityAddMatchBinding;
import com.cricoscore.databinding.ActivityAddPlayerBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class AddPlayer extends AppCompatActivity {

    ActivityAddPlayerBinding activityAddPlayerBinding;
    Context mContext;
    Activity mActivity;
    private ArrayList<DataModel> option_player_type = new ArrayList<>();
    private ArrayList<DataModel> option_player_role = new ArrayList<>();
    private ArrayList<DataModel> option_team_list = new ArrayList<>();
    private String filterType = "";
    public static Uri image_uri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddPlayerBinding = ActivityAddPlayerBinding.inflate(getLayoutInflater());
        setContentView(activityAddPlayerBinding.getRoot());

        mActivity = this;
        mContext = this;

        ToolbarBinding toolbarBinding = activityAddPlayerBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.addPlayer));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(image_uri!=null){
            activityAddPlayerBinding.profilePic.setImageURI(image_uri);
            image_uri = null;
        }
    }

    private void initView() {

        option_player_type.add(new DataModel("Bowler"));
        option_player_type.add(new DataModel("Batsman"));
        option_player_type.add(new DataModel("Wicketkeeper"));
        option_player_type.add(new DataModel("All Rounder"));
        activityAddPlayerBinding.dropPType.setOptionList(option_player_type);
        activityAddPlayerBinding.dropPType.setClickListener(new SelectTournamentType.onClickInterface() {
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

        option_player_role.add(new DataModel("Captain"));
        option_player_role.add(new DataModel("Vice Captain"));
        option_player_role.add(new DataModel("Coach"));
        option_player_role.add(new DataModel("Player"));
        option_player_role.add(new DataModel("Commentator"));
        option_player_role.add(new DataModel("Manager"));
        option_player_role.add(new DataModel("Scorer"));
        activityAddPlayerBinding.dropPRole.setOptionList(option_player_role);
        activityAddPlayerBinding.dropPRole.setClickListener(new SelectTournamentType.onClickInterface() {
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


        option_team_list.add(new DataModel("Team A"));
        option_team_list.add(new DataModel("Team B"));
        option_team_list.add(new DataModel("Team C"));
        option_team_list.add(new DataModel("Team D"));
        option_team_list.add(new DataModel("Team E"));
        option_team_list.add(new DataModel("Team CF"));
        activityAddPlayerBinding.dropPAddToTeam.setOptionList(option_team_list);
        activityAddPlayerBinding.dropPAddToTeam.setClickListener(new SelectTournamentType.onClickInterface() {
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

        activityAddPlayerBinding.mbSubmit.setOnClickListener(v -> {
            startActivity(new Intent(mContext,YourPlayerListActivity.class));
            finish();
        });

        activityAddPlayerBinding.middle.setOnClickListener(view -> {
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","AddPlayer"));
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}