package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cricoscore.Adapter.TeamListAdapter;
import com.cricoscore.Fragment.MatchFragment;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.TournamentModel.TournamentDetails;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamListActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    RecyclerView rv_teamList;
    MaterialButton mb_add_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        mActivity = this;
        mContext = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbartext);
        mTitle.setText(getResources().getString(R.string.teamlist));
        toolbar.setNavigationOnClickListener(v -> finish());


        initView();


    }

    private void initView() {

        rv_teamList = findViewById(R.id.rv_teamList);
        rv_teamList.setLayoutManager(new LinearLayoutManager(mContext));
        rv_teamList.setHasFixedSize(true);
        rv_teamList.setAdapter(new TeamListAdapter(mContext, getIntent().getIntExtra("Count", 0), getTeamList()));

        mb_add_player = findViewById(R.id.mb_add_player);
    }



    public List<Team> getTeamList() {
        List<Team> tList = new ArrayList<>(getIntent().getIntExtra("Count", 0));
        tList.add(new Team(
                Color.parseColor("#E6F587"), "Royal Challengers", "Inderjit Singh Bindra Stadium"));
        tList.add(new Team(
                Color.parseColor("#BBEA54"), "Power Hitters", "Dr. Y. S. Rajasekhara Reddy International Cricket Stadium"));
        tList.add(new Team(
                Color.parseColor("#F1DB9C"), "Flying Eagles", "Rajiv Gandhi International Cricket Stadium"));
        tList.add(new Team(
                Color.parseColor("#F4CEC8"), "Swift Strikers", "Vidarbha Cricket Association Stadium"));
        tList.add(new Team(
                Color.parseColor("#E6C2EF"), "Golden Eagles", "Arun Jaitley Cricket Stadium"
        ));
        return tList;
    }

    public class Team {

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
        String name = "";
        String address = "";


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