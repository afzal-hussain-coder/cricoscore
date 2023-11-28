package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cricoscore.Adapter.TeamListAdapter;
import com.cricoscore.Adapter.YourTeamListAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.Toaster;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YourTeamListActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    RecyclerView rv_teamList;
    MaterialCardView mcv_submit;
    SelectTournamentType drop_tournamentName;
    MaterialButton mb_submit;
    int position=0;
    String tournamentType ="";
    private ArrayList<DataModel> option_tournament_list = new ArrayList<>();

    YourTeamListAdapter yourTeamListAdapter;
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

        initView();
    }

    private void initView() {

        mcv_submit= findViewById(R.id.mcv_submit);
        mb_submit= findViewById(R.id.mb_submit);


        rv_teamList = findViewById(R.id.rv_teamList);
        rv_teamList.setLayoutManager(new LinearLayoutManager(mContext));
        rv_teamList.setHasFixedSize(true);
        yourTeamListAdapter = new YourTeamListAdapter(mContext,getTeamList(), (pos,string) -> {
            position = pos;
            if(position>0){
                mcv_submit.setVisibility(View.VISIBLE);
            }else{
                mcv_submit.setVisibility(View.GONE);
            }
        });
        rv_teamList.setAdapter(yourTeamListAdapter);



        drop_tournamentName= findViewById(R.id.drop_tournamentName);
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
                if(!tournamentType.isEmpty()){
                    mb_submit.setVisibility(View.VISIBLE);
                }else{
                    mb_submit.setVisibility(View.GONE);
                }

            }


            @Override
            public void onDismiss() {
            }
        });

        mb_submit.setOnClickListener(v -> {
            mb_submit.setVisibility(View.GONE);
            if(mcv_submit.getVisibility() == View.VISIBLE){
                tournamentType = "";
                yourTeamListAdapter.clearData();
                drop_tournamentName.setText(tournamentType);
                Toaster.customToast("Submitted successfully!");
                mcv_submit.setVisibility(View.GONE);
                yourTeamListAdapter.notifyDataSetChanged();
            }
        });
    }

    public List<Team> getTeamList(){
        List<Team> tList = new ArrayList<>();
        tList.add(new Team(
                Color.parseColor("#E6F587"),"Royal Challengers","Inderjit Singh Bindra Stadium"));
        tList.add(new Team(
                Color.parseColor("#BBEA54"),"Power Hitters","Dr. Y. S. Rajasekhara Reddy International Cricket Stadium"));
        tList.add(new Team(
                Color.parseColor("#F1DB9C"),"Flying Eagles","Rajiv Gandhi International Cricket Stadium"));
        tList.add(new Team(
                Color.parseColor("#F4CEC8"),"Swift Strikers","Vidarbha Cricket Association Stadium"));
        tList.add(new Team(
                Color.parseColor("#E6C2EF"),"Golden Eagles","Arun Jaitley Cricket Stadium"
        ));
        tList.add(new Team(
                Color.parseColor("#E6F587"),"Rebel Raiders","Inderjit Singh Bindra Stadium"));
        tList.add(new Team(
                Color.parseColor("#BBEA54"),"Dark Knights","Dr. Y. S. Rajasekhara Reddy International Cricket Stadium"));
        tList.add(new Team(
                Color.parseColor("#F1DB9C"),"Red Raptors","Rajiv Gandhi International Cricket Stadium"));
        tList.add(new Team(
                Color.parseColor("#F4CEC8"),"Storm Troopers","Vidarbha Cricket Association Stadium"));
        tList.add(new Team(
                Color.parseColor("#E6C2EF"),"Lightning Lancers","Arun Jaitley Cricket Stadium"
        ));
        return tList;
    }
    public static class Team{

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}