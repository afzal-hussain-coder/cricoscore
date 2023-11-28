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

        yourTeamListAdapter = new YourTeamListAdapter(mContext,getTeamList(), (pos,string) -> {
            position = pos;
            if(position>0){
                activityYourMatchTeamListBinding.mcvSubmit.setVisibility(View.VISIBLE);
            }else{
                activityYourMatchTeamListBinding.mcvSubmit.setVisibility(View.GONE);
            }
        });
        activityYourMatchTeamListBinding.rvTeamList.setAdapter(yourTeamListAdapter);

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
    public List<YourTeamListActivity.Team> getTeamList(){
        List<YourTeamListActivity.Team> tList = new ArrayList<>();
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#E6F587"),"Royal Challengers","Inderjit Singh Bindra Stadium"));
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#BBEA54"),"Power Hitters","Dr. Y. S. Rajasekhara Reddy International Cricket Stadium"));
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#F1DB9C"),"Flying Eagles","Rajiv Gandhi International Cricket Stadium"));
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#F4CEC8"),"Swift Strikers","Vidarbha Cricket Association Stadium"));
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#E6C2EF"),"Golden Eagles","Arun Jaitley Cricket Stadium"
        ));
        return tList;
    }
    public class Team{

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        int logo;
        String name="";
        String address="";




        public Team(int logo, String name, String address) {
            this.logo = logo;
            this.name = name;
            this.address = address;
        }
    }
}