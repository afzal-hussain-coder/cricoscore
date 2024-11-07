package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.cricoscore.Adapter.YourTeamListAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityYourMatchTeamListBinding;
import com.cricoscore.databinding.ToolbarBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YourMatchTeamListActivity extends AppCompatActivity {

    ActivityYourMatchTeamListBinding activityYourMatchTeamListBinding;
    Context mContext;
    Activity mActivity;
    int position;
    private ArrayList<DataModel> option_tournament_list = new ArrayList<>();
    SelectTournamentType selectTournamentType;
    String matchType ="";
    YourTeamListAdapter  yourTeamListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityYourMatchTeamListBinding = ActivityYourMatchTeamListBinding.inflate(getLayoutInflater());
        setContentView(activityYourMatchTeamListBinding.getRoot());

        mContext = this;
        mActivity = this;

        ToolbarBinding  toolbarBinding = activityYourMatchTeamListBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);




        toolbarBinding.toolbartext.setText(getResources().getString(R.string.yourteamlist));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        activityYourMatchTeamListBinding.rvTeamList.setHasFixedSize(true);
        activityYourMatchTeamListBinding.rvTeamList.setLayoutManager(new LinearLayoutManager(this));

//        yourTeamListAdapter = new YourTeamListAdapter(mContext,getTeamList(), (pos,string) -> {
//            position = pos;
//            if(position>0){
//                activityYourMatchTeamListBinding.mcvSubmit.setVisibility(View.VISIBLE);
//            }else{
//                activityYourMatchTeamListBinding.mcvSubmit.setVisibility(View.GONE);
//            }
//        });
//        activityYourMatchTeamListBinding.rvTeamList.setAdapter(yourTeamListAdapter);

        selectTournamentType =activityYourMatchTeamListBinding.dropTournamentName;
        option_tournament_list.add(new DataModel("Match A"));
        option_tournament_list.add(new DataModel("Match B"));
        option_tournament_list.add(new DataModel("Match C"));
        option_tournament_list.add(new DataModel("Match D"));
        selectTournamentType.setOptionList(option_tournament_list);
        selectTournamentType.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                matchType = name;
                if(!matchType.isEmpty()){
                    activityYourMatchTeamListBinding.mbSubmit.setVisibility(View.VISIBLE);
                }else{
                    activityYourMatchTeamListBinding.mbSubmit.setVisibility(View.GONE);
                }

            }


            @Override
            public void onDismiss() {
            }
        });

        activityYourMatchTeamListBinding.mbSubmit.setOnClickListener(v -> {
            activityYourMatchTeamListBinding.mbSubmit.setVisibility(View.GONE);
            if(activityYourMatchTeamListBinding.mcvSubmit.getVisibility() == View.VISIBLE){
                matchType = "";
                yourTeamListAdapter.clearData();
                selectTournamentType.setText(matchType);
                Toaster.customToast("Submitted successfully!");
                activityYourMatchTeamListBinding.mcvSubmit.setVisibility(View.GONE);
                yourTeamListAdapter.notifyDataSetChanged();
            }
        });


    }

}