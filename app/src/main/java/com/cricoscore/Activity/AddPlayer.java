package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class AddPlayer extends AppCompatActivity {

    Context mContext;
    Activity mActivity;
    TextInputEditText edit_text_pName;
    TextInputEditText edit_text_pMobile;
    private ArrayList<DataModel> option_player_type = new ArrayList<>();
    private ArrayList<DataModel> option_player_role = new ArrayList<>();
    private ArrayList<DataModel> option_team_list = new ArrayList<>();
    private String filterType = "";
    SelectTournamentType drop_pType, drop_pRole,drop_p_Add_To_Team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        mActivity = this;
        mContext = this;

        Toaster.customToast(SessionManager.getUserId()+"//"+SessionManager.getToken()+"?"+SessionManager.getEmail());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbartext);
        mTitle.setText(getResources().getString(R.string.addPlayer));
        toolbar.setNavigationOnClickListener(v -> finish());

        initView();
    }

    private void initView() {

        edit_text_pName = findViewById(R.id.edit_text_pName);
        edit_text_pMobile = findViewById(R.id.edit_text_pMobile);

        drop_pType = findViewById(R.id.drop_pType);
        drop_pRole = findViewById(R.id.drop_pRole);
        drop_p_Add_To_Team = findViewById(R.id.drop_p_Add_To_Team);

        option_player_type.add(new DataModel("Bowler"));
        option_player_type.add(new DataModel("Batsman"));
        option_player_type.add(new DataModel("Wicketkeeper"));
        option_player_type.add(new DataModel("All Rounder"));
        drop_pType.setOptionList(option_player_type);
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

        option_player_role.add(new DataModel("Captain"));
        option_player_role.add(new DataModel("Vice Captain"));
        option_player_role.add(new DataModel("Coach"));
        option_player_role.add(new DataModel("Player"));
        option_player_role.add(new DataModel("Commentator"));
        option_player_role.add(new DataModel("Manager"));
        option_player_role.add(new DataModel("Scorer"));
        drop_pRole.setOptionList(option_player_role);
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


        option_team_list.add(new DataModel("Team A"));
        option_team_list.add(new DataModel("Team B"));
        option_team_list.add(new DataModel("Team C"));
        option_team_list.add(new DataModel("Team D"));
        option_team_list.add(new DataModel("Team E"));
        option_team_list.add(new DataModel("Team CF"));
        drop_p_Add_To_Team.setOptionList(option_team_list);
        drop_p_Add_To_Team.setClickListener(new SelectTournamentType.onClickInterface() {
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

        findViewById(R.id.mb_submit).setOnClickListener(v -> {
            startActivity(new Intent(mContext,YourPlayerListActivity.class));
            finish();
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}