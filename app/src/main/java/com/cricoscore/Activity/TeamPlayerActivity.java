package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.cricoscore.Adapter.TeamPlayerListAdapter;
import com.cricoscore.R;
import com.cricoscore.databinding.ActivityTeamPlayerBinding;
import com.cricoscore.databinding.ToolbarBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeamPlayerActivity extends AppCompatActivity {
    ActivityTeamPlayerBinding activityTeamPlayerBinding;
    Context mContext;
    Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTeamPlayerBinding = ActivityTeamPlayerBinding.inflate(getLayoutInflater());
        setContentView(activityTeamPlayerBinding.getRoot());

        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding=activityTeamPlayerBinding.toolbar;

        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.playerlist));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        activityTeamPlayerBinding.rvPlayerList.setHasFixedSize(true);
        activityTeamPlayerBinding.rvPlayerList.setLayoutManager(new LinearLayoutManager(this));
        activityTeamPlayerBinding.rvPlayerList.setAdapter(new TeamPlayerListAdapter(
                mContext,getIntent().getIntExtra("Count",0),getPlayerList()));

    }

    public List<Player> getPlayerList(){
        List<Player> tList = new ArrayList<>();
        tList.add(new Player(
                Color.parseColor("#E6F587"),"Arshdeep Singh","8876542345","Captain"));
        tList.add(new Player(
                Color.parseColor("#BBEA54"),"Avesh Khan","9479482636","Vice Captain"));
        tList.add(new Player(
                Color.parseColor("#F1DB9C"),"Ashwin, R","1345323465","Wicket Keeper"));
        tList.add(new Player(
                Color.parseColor("#F4CEC8"),"Bumrah, JJ","7654344556","Captain"));
        tList.add(new Player(
                Color.parseColor("#E6C2EF"),"Chahar, DL","9199787875","Vice Captain"
        ));
        tList.add(new Player(
                Color.parseColor("#BBEA54"),"Avesh Khan","9479482636","Vice Captain"));
        tList.add(new Player(
                Color.parseColor("#F1DB9C"),"Ashwin, R","1345323465","Wicket Keeper"));
        tList.add(new Player(
                Color.parseColor("#F4CEC8"),"Bumrah, JJ","7654344556","Captain"));
        tList.add(new Player(
                Color.parseColor("#E6C2EF"),"Chahar, DL","9199787875","Vice Captain"
        ));
        return tList;
    }
    public static class Player{

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



        int logo;
        String name="";

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        String phone="";
        String role;




        public Player(int logo, String name, String phone,String role) {
            this.logo = logo;
            this.name = name;
            this.phone = phone;
            this.role = role;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}