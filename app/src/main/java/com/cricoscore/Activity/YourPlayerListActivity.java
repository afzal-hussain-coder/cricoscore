package com.cricoscore.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Adapter.YourPlayerListAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.Toaster;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YourPlayerListActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    RecyclerView rv_teamList;
    MaterialCardView mcv_submit;
    SelectTournamentType drop_tournamentName;
    MaterialButton mb_submit;
    int position=0;
    String tournamentType ="";
    private ArrayList<DataModel> option_tournament_list = new ArrayList<>();

    YourPlayerListAdapter yourPlayerListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_player_list);

        mActivity = this;
        mContext = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbartext);
        mTitle.setText(getResources().getString(R.string.yourPlayerlist));
        toolbar.setNavigationOnClickListener(v -> finish());

        initView();
    }



    private void initView() {

        mcv_submit= findViewById(R.id.mcv_submit);
        mb_submit= findViewById(R.id.mb_submit);


        rv_teamList = findViewById(R.id.rv_teamList);
        rv_teamList.setLayoutManager(new LinearLayoutManager(mContext));
        rv_teamList.setHasFixedSize(true);
        yourPlayerListAdapter = new YourPlayerListAdapter(mContext,getPlayerList(), pos -> {
            position = pos;
            if(position>0){
                mcv_submit.setVisibility(View.VISIBLE);
            }else{
                mcv_submit.setVisibility(View.GONE);
            }
        });
        rv_teamList.setAdapter(yourPlayerListAdapter);



        drop_tournamentName= findViewById(R.id.drop_tournamentName);
        option_tournament_list.add(new DataModel("Team A"));
        option_tournament_list.add(new DataModel("Team B"));
        option_tournament_list.add(new DataModel("Team C"));
        option_tournament_list.add(new DataModel("Team D"));
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
                yourPlayerListAdapter.clearData();
                drop_tournamentName.setText(tournamentType);
                Toaster.customToast("Submitted successfully!");
                mcv_submit.setVisibility(View.GONE);
                yourPlayerListAdapter.notifyDataSetChanged();
            }
        });
    }

    public List<TeamPlayerActivity.Player> getPlayerList(){
        List<TeamPlayerActivity.Player> tList = new ArrayList<>();
        tList.add(new TeamPlayerActivity.Player(
                Color.parseColor("#E6F587"),"Arshdeep Singh","8876542345","Captain"));
        tList.add(new TeamPlayerActivity.Player(
                Color.parseColor("#BBEA54"),"Avesh Khan","9479482636","Vice Captain"));
        tList.add(new TeamPlayerActivity.Player(
                Color.parseColor("#F1DB9C"),"Ashwin, R","1345323465","Wicket Keeper"));
        tList.add(new TeamPlayerActivity.Player(
                Color.parseColor("#F4CEC8"),"Bumrah, JJ","7654344556","Captain"));
        tList.add(new TeamPlayerActivity.Player(
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