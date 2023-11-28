package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cricoscore.Adapter.YourTeamListAdapterHorizontal;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.databinding.ActivityTournamentDetailsBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TournamentDetailsActivity extends AppCompatActivity {

    ActivityTournamentDetailsBinding activityTournamentDetailsBinding;
    Context mContext;
    Activity mActivity;
    YourTeamListAdapterHorizontal yourTeamListAdapter;
    int position;
    private int year, month, day, hour, minute;
    String date = "";
    Date _selectedDate;
    Date _currentDate;
    String aTime = "";
    String currentDate = "";
    String teamName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTournamentDetailsBinding = ActivityTournamentDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityTournamentDetailsBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityTournamentDetailsBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.tournament_details));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        activityTournamentDetailsBinding.tvInfo.startAnimation((Animation) AnimationUtils.loadAnimation(mContext,R.anim.translate));

        activityTournamentDetailsBinding.rvTeamList.setLayoutManager(new GridLayoutManager(mContext,2));
        activityTournamentDetailsBinding.rvTeamList.setHasFixedSize(true);

        yourTeamListAdapter = new YourTeamListAdapterHorizontal(mContext,getTeamList(), (pos,string) -> {
            position = pos;
            teamName = string;
            if(position==2){
                activityTournamentDetailsBinding.mbScheduleMatch.setVisibility(View.VISIBLE);
            }else{
                activityTournamentDetailsBinding.mbScheduleMatch.setVisibility(View.GONE);
            }
        });
        activityTournamentDetailsBinding.rvTeamList.setAdapter(yourTeamListAdapter);

        activityTournamentDetailsBinding.imgBanner.setBackgroundColor(getIntent().getIntExtra("color",0));
        activityTournamentDetailsBinding.image.setBorderColor(getIntent().getIntExtra("color",0));
        activityTournamentDetailsBinding.tvTName.setText(getIntent().getStringExtra("Name"));
        activityTournamentDetailsBinding.tvtLocation.setText(getIntent().getStringExtra("Address"));
        activityTournamentDetailsBinding.tvdate.setText(getIntent().getStringExtra("Date"));
        activityTournamentDetailsBinding.mbScheduleMatch.setOnClickListener(v -> {
            showBottomSheetDialog();
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
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#E6F587"),"Rebel Raiders","Inderjit Singh Bindra Stadium"));
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#BBEA54"),"Dark Knights","Dr. Y. S. Rajasekhara Reddy International Cricket Stadium"));
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#F1DB9C"),"Red Raptors","Rajiv Gandhi International Cricket Stadium"));
        tList.add(new YourTeamListActivity.Team(
                Color.parseColor("#F4CEC8"),"Storm Troopers","Vidarbha Cricket Association Stadium"));
        tList.add(new YourTeamListActivity.Team(
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

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_schedule);

        TextView teamFirst = bottomSheetDialog.findViewById(R.id.tvTName);
        TextView teamSecond = bottomSheetDialog.findViewById(R.id.tvTName_teamTwo);
        teamFirst.setText(teamName);
        teamSecond.setText(teamName);


        bottomSheetDialog.findViewById(R.id.li_start_date).setOnClickListener(v -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final Calendar c = Calendar.getInstance();

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            currentDate = year + "-" + (month + 1) + "-" + day;

            Calendar twoDaysLater = (Calendar) c.clone();
            twoDaysLater.add(Calendar.DATE, 90);

            DatePickerDialog picker = new DatePickerDialog(mActivity, (datePicker, year, month, day) -> {
                date = year + "-" + (month + 1) + "-" + day;

                try {
                    _currentDate = simpleDateFormat.parse(currentDate);
                    _selectedDate = simpleDateFormat.parse(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextView tvSdate = bottomSheetDialog.findViewById(R.id.tv_start_date);
                tvSdate.setText(Global.convertUTCDateToLocall(date));
            }, year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());

            picker.show();
        });

        bottomSheetDialog.findViewById(R.id.li_end_date).setOnClickListener(v -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final Calendar c = Calendar.getInstance();

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            currentDate = year + "-" + (month + 1) + "-" + day;

            Calendar twoDaysLater = (Calendar) c.clone();
            twoDaysLater.add(Calendar.DATE, 90);

            DatePickerDialog picker = new DatePickerDialog(mActivity, (datePicker, year, month, day) -> {
                date = year + "-" + (month + 1) + "-" + day;

                try {
                    _currentDate = simpleDateFormat.parse(currentDate);
                    _selectedDate = simpleDateFormat.parse(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextView tvSdate = bottomSheetDialog.findViewById(R.id.tv_end_date);
                tvSdate.setText(Global.convertUTCDateToLocall(date));
            }, year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());

            picker.show();
        });

        TextView tv_start_time = bottomSheetDialog.findViewById(R.id.tv_start_time);
        TextView tv_end_time = bottomSheetDialog.findViewById(R.id.tv_end_time);

        bottomSheetDialog.findViewById(R.id.li_start_time).setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, (timePicker, hour, min) -> {
                Calendar datetime = Calendar.getInstance();
                Calendar c1 = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, min);


//                if (datetime.getTimeInMillis() < c1.getTimeInMillis()) {
//                    //it's after current
//                    updateTime(hour, min,tv_end_time);
//                } else {
//                    //it's before current'
//                    Toaster.customToast("Invalid Time");
//
//                }
                updateTime(hour, min,tv_start_time);

//                int difference = _currentDate.compareTo(_selectedDate);
//
//                if (difference == 0) {
//                    if (datetime.getTimeInMillis() > c1.getTimeInMillis()) {
//                        //it's after current
//                        updateTime(hour, min,tv_start_time);
//                    } else {
//                        //it's before current'
//                        //Toaster.customToast("Invalid Time");
//
//                    }
//                } else if (difference == -1) {
//                    updateTime(hour, min,tv_start_time);
//                }


            }, hour, minute, false);

            timePickerDialog.show();
        });

        bottomSheetDialog.findViewById(R.id.li_end_time).setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, (timePicker, hour, min) -> {
                Calendar datetime = Calendar.getInstance();
                Calendar c1 = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, min);


                updateTime(hour, min,tv_end_time);

//                if (datetime.getTimeInMillis() > c1.getTimeInMillis()) {
//                    //it's after current
//                    updateTime(hour, min,tv_end_time);
//                } else {
//                    //it's before current'
//                    Toaster.customToast("Invalid Time");
//
//                }

                //int difference = _currentDate.compareTo(_selectedDate);

//                if (difference == 0) {
//                    if (datetime.getTimeInMillis() > c1.getTimeInMillis()) {
//                        //it's after current
//                        updateTime(hour, min,tv_end_time);
//                    } else {
//                        //it's before current'
//                        //Toaster.customToast("Invalid Time");
//
//                    }
//                } else if (difference == -1) {
//                    updateTime(hour, min,tv_end_time);
//                }


            }, hour, minute, false);

            timePickerDialog.show();
        });

        bottomSheetDialog.findViewById(R.id.mb_submit).setOnClickListener(v -> {
            startActivity(new Intent(mContext,ScheduleMatchActivity.class));
            finish();
            bottomSheetDialog.hide();
        });


        bottomSheetDialog.show();
    }

    private void updateTime(int hours, int mins,TextView tv_time) {
        String timeSet;
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes;
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        aTime = String.valueOf(hours) + ':' + minutes + " " + timeSet;
        tv_time.setText(aTime);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}