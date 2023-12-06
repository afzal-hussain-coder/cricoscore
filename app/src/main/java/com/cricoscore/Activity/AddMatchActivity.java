package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.databinding.ActivityAddMatchBinding;
import com.cricoscore.databinding.ActivityAddTournamentBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddMatchActivity extends AppCompatActivity {

    ActivityAddMatchBinding activityAddMatchBinding;
    private Context mContext;
    private Activity mActivity;
    private int year, month, day, hour, minute;
    TextView tv_date,tv_end_date, tv_time;
    String date = "";
    Date _selectedDate;
    Date _currentDate;
    String aTime = "";
    String currentDate = "";
    private ArrayList<DataModel> option = new ArrayList<>();
    private ArrayList<DataModel> option_ball_type = new ArrayList<>();
    private ArrayList<DataModel> option_state = new ArrayList<>();
    private ArrayList<DataModel> option_city = new ArrayList<>();
    private String filterType = "";
    public static Uri image_uri=null;
    private int logo=0,banner=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddMatchBinding = ActivityAddMatchBinding.inflate(getLayoutInflater());
        setContentView(activityAddMatchBinding.getRoot());

        mActivity = this;
        mContext = this;

        ToolbarBinding toolbarBinding = activityAddMatchBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.addMatch));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(image_uri!=null){

            if(logo==1){
                activityAddMatchBinding.profilePic.setImageURI(image_uri);
                image_uri = null;
            }else if(banner==2){
                activityAddMatchBinding.coverImg.setImageURI(image_uri);
                image_uri = null;
            }
        }
    }

    private void initializeView() {

        option.add(new DataModel("Age dependent match (U-16 , U-19 etc.)"));
        option.add(new DataModel("Corporate Match"));
        option.add(new DataModel("Open Match"));

        activityAddMatchBinding.dropMType.setOptionList(option);
//        filterType = option.get(0).getName();
//        selectTournamentType.setText(filterType);

        activityAddMatchBinding.dropMType.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {

                if (name.equalsIgnoreCase("Cancelled booking")) {
                    filterType = "cancelled";
                } else if (name.equalsIgnoreCase("Confirmed booking")) {
                    filterType = "confirmed";
                } else {
                    filterType = name;
                }
            }


            @Override
            public void onDismiss() {
            }
        });

        option_ball_type.add(new DataModel("Leather"));
        option_ball_type.add(new DataModel("Tennis"));
        option_ball_type.add(new DataModel("Rubber"));
        option_ball_type.add(new DataModel("Other"));


        activityAddMatchBinding.dropMAwards.setOptionList(option_ball_type);
        activityAddMatchBinding.dropMAwards.setClickListener(new SelectTournamentType.onClickInterface() {
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

        option_state.add(new DataModel("Bihar"));
        option_state.add(new DataModel("Punjab"));
        option_state.add(new DataModel("Haryana"));
        option_state.add(new DataModel("Delhi"));
        option_state.add(new DataModel("Chennai"));
        activityAddMatchBinding.dropMState.setOptionList(option_state);
        activityAddMatchBinding.dropMState.setClickListener(new SelectTournamentType.onClickInterface() {
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

        option_city.add(new DataModel("Patna"));
        option_city.add(new DataModel("Gurgaon"));
        option_city.add(new DataModel("Samastipur"));
        option_city.add(new DataModel("Dhanbad"));
        option_city.add(new DataModel("Hajipur"));
        activityAddMatchBinding.dropMCity.setOptionList(option_city);
        activityAddMatchBinding.dropMCity.setClickListener(new SelectTournamentType.onClickInterface() {
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


        activityAddMatchBinding.liStartDate.setOnClickListener(v -> {
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

                activityAddMatchBinding.tvStartDate.setText(Global.convertUTCDateToMM(date));
            }, year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());

            picker.show();

        });

        activityAddMatchBinding.liEndDate.setOnClickListener(v -> {
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

                activityAddMatchBinding.tvEndDate.setText(Global.convertUTCDateToMM(date));
            }, year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());

            picker.show();

        });

        activityAddMatchBinding.editTextMAddAwards.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    activityAddMatchBinding.ivDone.setVisibility(View.VISIBLE);
                    if (s.length() > 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        addChipToGroup(s.toString().replace(",", ""));
                    }
                }else{
                    activityAddMatchBinding.ivDone.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    if (s.toString().length() == 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        activityAddMatchBinding.editTextMAddAwards.setText("");
                    } else if (s.toString().length() > 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        activityAddMatchBinding.editTextMAddAwards.setText("");
                    }
                }
            }
        });

        activityAddMatchBinding.ivDone.setOnClickListener(v -> {
            if (activityAddMatchBinding.editTextMAddAwards.getText().toString().length() > 1 ) {
                addChipToGroup(activityAddMatchBinding.editTextMAddAwards.getText().toString());
                activityAddMatchBinding.editTextMAddAwards.setText("");
                activityAddMatchBinding.ivDone.setVisibility(View.GONE);
            }
        });
       /* findViewById(R.id.li_time).setOnClickListener(v -> {
            tv_time.setText("Select Time");
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, (timePicker, hour, min) -> {
                Calendar datetime = Calendar.getInstance();
                Calendar c1 = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, min);


                int difference = _currentDate.compareTo(_selectedDate);

                if (difference == 0) {
                    if (datetime.getTimeInMillis() > c1.getTimeInMillis()) {
                        //it's after current
                        updateTime(hour, min);
                    } else {
                        //it's before current'
                        //Toaster.customToast("Invalid Time");

                    }
                } else if (difference == -1) {
                    updateTime(hour, min);
                }


            }, hour, minute, false);

            timePickerDialog.show();
        });*/
        activityAddMatchBinding.mbSubmit.setOnClickListener(v -> {
            startActivity(new Intent(mContext,MainActivity.class));
            finish();
        });

        activityAddMatchBinding.middle.setOnClickListener(view -> {
            logo = 1;
            banner = 0;
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","AddMatchActivity"));
        });

        activityAddMatchBinding.coverImg.setOnClickListener(view -> {
            logo = 0;
            banner = 2;
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","AddMatchActivity"));
        });

    }

    private void addChipToGroup(String awards) {
        Chip chip = new Chip(mContext);
        chip.setText(awards);
        chip.setChipIcon(mContext.getResources().getDrawable(R.drawable.cancel_black_24dp));
        chip.setChipIconVisible(false);
        chip.setCloseIconVisible(true);
        chip.setClickable(true);
        chip.setCheckable(false);
        chip.setTextSize(10);
        chip.setChipBackgroundColorResource(R.color.purple_700);
        chip.setCloseIconTintResource(R.color.white);
        chip.setTextColor(mContext.getResources().getColor(R.color.white));
        activityAddMatchBinding.chipGroupAwards.addView(chip);
        chip.setOnCloseIconClickListener(v -> {
            activityAddMatchBinding.chipGroupAwards.removeView(chip);
        });
    }

    private void updateTime(int hours, int mins) {
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