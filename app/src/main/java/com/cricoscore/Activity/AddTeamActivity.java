package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityAddTeamBinding;
import com.cricoscore.databinding.ToolbarBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AddTeamActivity extends AppCompatActivity {

    ActivityAddTeamBinding activityAddTeamBinding;
    Context mContext;
    Activity mActivity;
    SelectTournamentType drop_tournamentName,drop_matchName;
    String tournamentType ="";
    private ArrayList<DataModel> option_tournament_list = new ArrayList<>();
    private ArrayList<DataModel> option_match_list = new ArrayList<>();
    String matchType ="";
    public static Uri image_uri=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAddTeamBinding = ActivityAddTeamBinding.inflate(getLayoutInflater());
        setContentView(activityAddTeamBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding=activityAddTeamBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.add_team));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        drop_matchName =activityAddTeamBinding.dropMatchName;
        option_match_list.add(new DataModel("Match A"));
        option_match_list.add(new DataModel("Match B"));
        option_match_list.add(new DataModel("Match C"));
        option_match_list.add(new DataModel("Match D"));
        drop_matchName.setOptionList(option_match_list);
        drop_matchName.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                matchType = name;
            }


            @Override
            public void onDismiss() {
            }
        });

        drop_tournamentName =activityAddTeamBinding.dropTournamentName;
        option_tournament_list.add(new DataModel("Tournament A"));
        option_tournament_list.add(new DataModel("Tournament B"));
        option_tournament_list.add(new DataModel("Tournament C"));
        option_tournament_list.add(new DataModel("Tournament D"));
        drop_tournamentName.setOptionList(option_tournament_list);
        drop_tournamentName.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                tournamentType = name;
            }


            @Override
            public void onDismiss() {
            }
        });

        activityAddTeamBinding.mbSubmit.setOnClickListener(v -> {
            drop_tournamentName.setText(tournamentType);
            drop_matchName.setText(matchType);
            activityAddTeamBinding.editTextTeamName.setText("");
            activityAddTeamBinding.editTextCity.setText("");
            //Toaster.customToast("Submitted successfully!");

            startActivity(new Intent(mContext,YourTeamListActivity.class));
            finish();
        });

        activityAddTeamBinding.middle.setOnClickListener(view -> {
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","AddTeamActivity"));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(image_uri!=null){
            activityAddTeamBinding.profilePic.setImageURI(image_uri);
            image_uri = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}