package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
import com.cricoscore.databinding.ActivityAddTournamentBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddTournamentActivity extends AppCompatActivity {

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
    public static Uri image_uri=null;
    private int logo=0,banner=0;
    private String filterType = "";


    ActivityAddTournamentBinding activityAddTournamentBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAddTournamentBinding = ActivityAddTournamentBinding.inflate(getLayoutInflater());
        setContentView(activityAddTournamentBinding.getRoot());

        mActivity = this;
        mContext = this;

        ToolbarBinding toolbarBinding = activityAddTournamentBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.addTournament));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(image_uri!=null){

            if(logo==1){
                activityAddTournamentBinding.profilePic.setImageURI(image_uri);
                image_uri = null;
            }else if(banner==2){
                activityAddTournamentBinding.coverImg.setImageURI(image_uri);
                image_uri = null;
            }
        }
    }

    private void initializeView() {

        option.add(new DataModel("Age dependent tournament (U-16 , U-19 etc.)"));
        option.add(new DataModel("Corporate Tournament"));
        option.add(new DataModel("Open Tournament"));

        activityAddTournamentBinding.dropTType.setOptionList(option);
//        filterType = option.get(0).getName();
//        activityAddTournamentBinding.dropTType.setText(filterType);

        activityAddTournamentBinding.dropTType.setClickListener(new SelectTournamentType.onClickInterface() {
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
        activityAddTournamentBinding.dropTAwards.setOptionList(option_ball_type);
        activityAddTournamentBinding.dropTAwards.setClickListener(new SelectTournamentType.onClickInterface() {
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
        activityAddTournamentBinding.dropTState.setOptionList(option_state);
        activityAddTournamentBinding.dropTState.setClickListener(new SelectTournamentType.onClickInterface() {
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
        activityAddTournamentBinding.dropTCity.setOptionList(option_city);
        activityAddTournamentBinding.dropTCity.setClickListener(new SelectTournamentType.onClickInterface() {
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

        activityAddTournamentBinding.liStartDate.setOnClickListener(v -> {
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

                activityAddTournamentBinding.tvStartDate.setText(Global.convertUTCDateToMM(date));
            }, year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());

            picker.show();

        });

        activityAddTournamentBinding.liEndDate.setOnClickListener(v -> {
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

                activityAddTournamentBinding.tvEndDate.setText(Global.convertUTCDateToMM(date));
            }, year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());

            picker.show();

        });

        activityAddTournamentBinding.editTextTAddAwards.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    activityAddTournamentBinding.ivDone.setVisibility(View.VISIBLE);
                    if (s.length() > 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        addChipToGroup(s.toString().replace(",", ""));
                    }
                }else{
                    activityAddTournamentBinding.ivDone.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    if (s.toString().length() == 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        activityAddTournamentBinding.editTextTAddAwards.setText("");
                    } else if (s.toString().length() > 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        activityAddTournamentBinding.editTextTAddAwards.setText("");
                    }
                }
            }
        });

        activityAddTournamentBinding.ivDone.setOnClickListener(v -> {
            if (activityAddTournamentBinding.editTextTAddAwards.getText().toString().length() > 1 ) {
                addChipToGroup(activityAddTournamentBinding.editTextTAddAwards.getText().toString());
                activityAddTournamentBinding.editTextTAddAwards.setText("");
                activityAddTournamentBinding.ivDone.setVisibility(View.GONE);
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

        activityAddTournamentBinding.mbSubmit.setOnClickListener(v -> {
            startActivity(new Intent(mContext,MainActivity.class));
            finish();
        });

        activityAddTournamentBinding.middle.setOnClickListener(view -> {
            logo = 1;
            banner = 0;
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","AddTournamentActivity"));
        });

        activityAddTournamentBinding.coverImg.setOnClickListener(view -> {
            logo = 0;
            banner = 2;
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM","AddTournamentActivity"));
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
        activityAddTournamentBinding.chipGroupAwards.addView(chip);
        chip.setOnCloseIconClickListener(v -> {
            activityAddTournamentBinding.chipGroupAwards.removeView(chip);
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