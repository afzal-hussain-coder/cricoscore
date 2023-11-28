package com.cricoscore.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.cricoscore.Adapter.ScheduleTournamentAdapter;
import com.cricoscore.R;
import com.cricoscore.databinding.ActivityScheduleTournamentBinding;
import com.cricoscore.databinding.ToolbarBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScheduleTournamentActivity extends AppCompatActivity {

    ActivityScheduleTournamentBinding activityScheduleTournamentBinding;
    Context mContext;
    Activity mActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityScheduleTournamentBinding = ActivityScheduleTournamentBinding.inflate(getLayoutInflater());
        setContentView(activityScheduleTournamentBinding.getRoot());

        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityScheduleTournamentBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.schedule_tournament_list));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        activityScheduleTournamentBinding.rvScheduleTournamentList.setHasFixedSize(true);
        activityScheduleTournamentBinding.rvScheduleTournamentList.setLayoutManager(new LinearLayoutManager(this));

        activityScheduleTournamentBinding.rvScheduleTournamentList.setAdapter(new ScheduleTournamentAdapter(
                mContext,getMatchList()));
    }

    public List<ScheduleTournamentActivity.Match> getMatchList(){
        List<ScheduleTournamentActivity.Match> tList = new ArrayList<>();
        tList.add(new ScheduleTournamentActivity.Match(Color.parseColor("#c8f5ae"),
                Color.parseColor("#c8f5ae"),"First-class Match","Inderjit Singh Bindra Stadium",
                "30-Mar-23 to 31-Sep-23"));
        tList.add(new ScheduleTournamentActivity.Match(Color.parseColor("#d7e09b"),
                Color.parseColor("#d7e09b"),"One day Match","Dr. Y. S. Rajasekhara Reddy International Cricket Stadium",
                "01-Apr-23 to 22-july-23"));
        tList.add(new ScheduleTournamentActivity.Match(Color.parseColor("#F1DB9C"),
                Color.parseColor("#F1DB9C"),"Twenty 20 (T20)","Rajiv Gandhi International Cricket Stadium",
                "9-Jan-23 to 31-Mar-23"));
        tList.add(new ScheduleTournamentActivity.Match(Color.parseColor("#F4CEC8"),
                Color.parseColor("#F4CEC8"),"One Day Internationals","Vidarbha Cricket Association Stadium",
                "5-Feb-23 to 23-May-23"));
        tList.add(new ScheduleTournamentActivity.Match(Color.parseColor("#E6C2EF"),
                Color.parseColor("#E6C2EF"),"Twenty 20 Internationals","Arun Jaitley Cricket Stadium","16-Nov-23 to 27-Dec-23"
        ));
        return tList;
    }
    public class Match{
        public int getBanner() {
            return banner;
        }

        public void setBanner(int banner) {
            this.banner = banner;
        }

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

        int banner;
        int logo;
        String name="";
        String address="";

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        String date="";


        public Match(int banner, int logo, String name, String address,String date) {
            this.banner = banner;
            this.logo = logo;
            this.name = name;
            this.address = address;
            this.date = date;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
   
}
