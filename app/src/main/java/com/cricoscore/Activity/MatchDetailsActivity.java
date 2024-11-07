package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import com.cricoscore.Adapter.YourTeamListAdapter;
import com.cricoscore.Adapter.YourTeamListAdapterHorizontal;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityMatchDetailsBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MatchDetailsActivity extends AppCompatActivity {

    ActivityMatchDetailsBinding activityMatchDetailsBinding;
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

        activityMatchDetailsBinding = ActivityMatchDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityMatchDetailsBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityMatchDetailsBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.match_details));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        activityMatchDetailsBinding.tvInfo.startAnimation((Animation) AnimationUtils.loadAnimation(mContext,R.anim.translate));

        activityMatchDetailsBinding.rvTeamList.setLayoutManager(new GridLayoutManager(mContext,2));
        activityMatchDetailsBinding.rvTeamList.setHasFixedSize(true);

//        yourTeamListAdapter = new YourTeamListAdapterHorizontal(mContext,new ArrayList<String>(), (pos,string) -> {
//            position = pos;
//            teamName = string;
//            if(position==2){
//                activityMatchDetailsBinding.mbScheduleMatch.setVisibility(View.VISIBLE);
//            }else{
//                activityMatchDetailsBinding.mbScheduleMatch.setVisibility(View.GONE);
//            }
//        });
//        activityMatchDetailsBinding.rvTeamList.setAdapter(yourTeamListAdapter);




        activityMatchDetailsBinding.imgBanner.setBackgroundColor(getIntent().getIntExtra("color",0));
        activityMatchDetailsBinding.image.setBorderColor(getIntent().getIntExtra("color",0));
        activityMatchDetailsBinding.tvTName.setText(getIntent().getStringExtra("Name"));
        activityMatchDetailsBinding.tvtLocation.setText(getIntent().getStringExtra("Address"));
        activityMatchDetailsBinding.tvdate.setText(getIntent().getStringExtra("Date"));
        activityMatchDetailsBinding.mbScheduleMatch.setOnClickListener(v -> {
            showBottomSheetDialog();
        });


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