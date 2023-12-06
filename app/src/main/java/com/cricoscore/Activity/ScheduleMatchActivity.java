package com.cricoscore.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cricoscore.Adapter.ScheduleMatchAdapter;
import com.cricoscore.Adapter.ScheduleTournamentAdapter;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.databinding.ActivityScheduleMatchBinding;
import com.cricoscore.databinding.ActivityScheduleTournamentBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleMatchActivity extends AppCompatActivity {

    ActivityScheduleMatchBinding activityScheduleMatchBinding;
    Context mContext;
    Activity mActivity;
    ScheduleMatchAdapter scheduleMatchAdapter;
    private int itemPosition;
    private ArrayList<DataModel> option_player_role = null;
    private ArrayList<DataModel> option_player_type = null;
    public static Uri image_uri=null;
    CircleImageView iv_team_logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityScheduleMatchBinding = ActivityScheduleMatchBinding.inflate(getLayoutInflater());
        setContentView(activityScheduleMatchBinding.getRoot());

        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityScheduleMatchBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.schedule_tournament_list));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());



        activityScheduleMatchBinding.rvScheduleMatchList.setHasFixedSize(true);
        activityScheduleMatchBinding.rvScheduleMatchList.setLayoutManager(new LinearLayoutManager(this));

       scheduleMatchAdapter = new ScheduleMatchAdapter(mContext, getMatchList(), () -> showBottomSheetDialog(null));
       activityScheduleMatchBinding.rvScheduleMatchList.setAdapter(scheduleMatchAdapter);
    }

    public List<ScheduleMatchActivity.Match> getMatchList() {
        List<ScheduleMatchActivity.Match> tList = new ArrayList<>();
        tList.add(new ScheduleMatchActivity.Match(Color.parseColor("#c8f5ae"),
                Color.parseColor("#c8f5ae"), "First-class Match", "Inderjit Singh Bindra Stadium",
                "30-Mar-23 to 31-Sep-23"));
        tList.add(new ScheduleMatchActivity.Match(Color.parseColor("#d7e09b"),
                Color.parseColor("#d7e09b"), "One day Match", "Dr. Y. S. Rajasekhara Reddy International Cricket Stadium",
                "01-Apr-23 to 22-july-23"));
        tList.add(new ScheduleMatchActivity.Match(Color.parseColor("#F1DB9C"),
                Color.parseColor("#F1DB9C"), "Twenty 20 (T20)", "Rajiv Gandhi International Cricket Stadium",
                "9-Jan-23 to 31-Mar-23"));
        tList.add(new ScheduleMatchActivity.Match(Color.parseColor("#F4CEC8"),
                Color.parseColor("#F4CEC8"), "One Day Internationals", "Vidarbha Cricket Association Stadium",
                "5-Feb-23 to 23-May-23"));
        tList.add(new ScheduleMatchActivity.Match(Color.parseColor("#E6C2EF"),
                Color.parseColor("#E6C2EF"), "Twenty 20 Internationals", "Arun Jaitley Cricket Stadium", "16-Nov-23 to 27-Dec-23"
        ));
        return tList;
    }

    public class Match {
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
        String name = "";
        String address = "";

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        String date = "";


        public Match(int banner, int logo, String name, String address, String date) {
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

    @Override
    protected void onResume() {
        super.onResume();
        if(image_uri!=null){
            iv_team_logo.setImageURI(image_uri);
            image_uri = null;
        }

    }

    /* player ki list pe drop down ko hidwe karna ka logic
     * playing 11 ke button ko aftre 11 player ke sel;cetionke bad on karn ahai..*/

    public void showBottomSheetDialog(Uri image_uri) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.add_player_dialog);
        SelectTournamentType drop_pType = bottomSheetDialog.findViewById(R.id.drop_pType);
        SelectTournamentType drop_pRole = bottomSheetDialog.findViewById(R.id.drop_pRole);
        drop_pRole.setOptionList(setRole());
        drop_pType.setOptionList(setType());
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
        RelativeLayout rl_team_logo = bottomSheetDialog.findViewById(R.id.rl_team_logo);
        iv_team_logo = bottomSheetDialog.findViewById(R.id.iv_team_logo);
        TextInputEditText edit_text_pName = bottomSheetDialog.findViewById(R.id.edit_text_pName);
        TextInputEditText edit_text_pMobile = bottomSheetDialog.findViewById(R.id.edit_text_pMobile);
        RelativeLayout rl_select_player = bottomSheetDialog.findViewById(R.id.rl_select_player);
        MaterialButton mb_submit = bottomSheetDialog.findViewById(R.id.mb_submit);

        rl_team_logo.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","ScheduleMatchActivity"));
        });


        rl_select_player.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, YourPlayerListActivity.class));
            bottomSheetDialog.dismiss();
        });

        mb_submit.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();

    }

    private ArrayList<DataModel> setRole() {
        option_player_role = new ArrayList<>();
        option_player_role.add(new DataModel("Captain"));
        option_player_role.add(new DataModel("Vice Captain"));
        option_player_role.add(new DataModel("Coach"));
        option_player_role.add(new DataModel("Player"));
        option_player_role.add(new DataModel("Commentator"));
        option_player_role.add(new DataModel("Manager"));
        option_player_role.add(new DataModel("Scorer"));
        return  option_player_role;
    }

    private ArrayList<DataModel> setType() {
        option_player_type = new ArrayList<>();
        option_player_type.add(new DataModel("Bowler"));
        option_player_type.add(new DataModel("Batsman"));
        option_player_type.add(new DataModel("Wicketkeeper"));
        option_player_type.add(new DataModel("All Rounder"));
        return option_player_type;
    }
}
