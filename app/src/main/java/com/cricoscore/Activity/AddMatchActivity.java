package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
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
    SelectTournamentType selectTournamentType, drop_tBallType, drop_tState, drop_tCity;

    TextInputEditText edit_text_tAddAwards;
    ChipGroup chip_group_awards;
    ImageView iv_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        mActivity = this;
        mContext = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbartext);
        mTitle.setText(getResources().getString(R.string.addMatch));
        toolbar.setNavigationOnClickListener(v -> finish());

        initializeView();
    }

    private void initializeView() {
        selectTournamentType = findViewById(R.id.drop_mType);

        option.add(new DataModel("Age dependent match (U-16 , U-19 etc.)"));
        option.add(new DataModel("Corporate Match"));
        option.add(new DataModel("Open Match"));

        selectTournamentType.setOptionList(option);
//        filterType = option.get(0).getName();
//        selectTournamentType.setText(filterType);

        selectTournamentType.setClickListener(new SelectTournamentType.onClickInterface() {
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


        drop_tBallType = findViewById(R.id.drop_mAwards);

        option_ball_type.add(new DataModel("Leather"));
        option_ball_type.add(new DataModel("Tennis"));
        option_ball_type.add(new DataModel("Rubber"));
        option_ball_type.add(new DataModel("Other"));


        drop_tBallType.setOptionList(option_ball_type);
        drop_tBallType.setClickListener(new SelectTournamentType.onClickInterface() {
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

        drop_tState = findViewById(R.id.drop_mState);
        option_state.add(new DataModel("Bihar"));
        option_state.add(new DataModel("Punjab"));
        option_state.add(new DataModel("Haryana"));
        option_state.add(new DataModel("Delhi"));
        option_state.add(new DataModel("Chennai"));
        drop_tState.setOptionList(option_state);
        drop_tState.setClickListener(new SelectTournamentType.onClickInterface() {
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

        drop_tCity = findViewById(R.id.drop_mCity);
        option_city.add(new DataModel("Patna"));
        option_city.add(new DataModel("Gurgaon"));
        option_city.add(new DataModel("Samastipur"));
        option_city.add(new DataModel("Dhanbad"));
        option_city.add(new DataModel("Hajipur"));
        drop_tCity.setOptionList(option_city);
        drop_tCity.setClickListener(new SelectTournamentType.onClickInterface() {
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

        tv_date = findViewById(R.id.tv_start_date);
        tv_end_date = findViewById(R.id.tv_end_date);

        findViewById(R.id.li_start_date).setOnClickListener(v -> {
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

                tv_date.setText(Global.convertUTCDateToLocall(date));
            }, year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());

            picker.show();

        });

        findViewById(R.id.li_end_date).setOnClickListener(v -> {
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

                tv_end_date.setText(Global.convertUTCDateToLocall(date));
            }, year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(twoDaysLater.getTimeInMillis());

            picker.show();

        });


        chip_group_awards = findViewById(R.id.chip_group_awards);

        iv_done = findViewById(R.id.iv_done);
        edit_text_tAddAwards = findViewById(R.id.edit_text_mAddAwards);
        edit_text_tAddAwards.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    iv_done.setVisibility(View.VISIBLE);
                    if (s.length() > 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        addChipToGroup(s.toString().replace(",", ""));
                    }
                }else{
                    iv_done.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    if (s.toString().length() == 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        edit_text_tAddAwards.setText("");
                    } else if (s.toString().length() > 1 && s.toString().charAt(s.toString().length() - 1) == ',') {
                        edit_text_tAddAwards.setText("");
                    }
                }
            }
        });

        iv_done.setOnClickListener(v -> {
            if (edit_text_tAddAwards.getText().toString().length() > 1 ) {
                addChipToGroup(edit_text_tAddAwards.getText().toString());
                edit_text_tAddAwards.setText("");
                iv_done.setVisibility(View.GONE);
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
        findViewById(R.id.mb_submit).setOnClickListener(v -> {
            startActivity(new Intent(mContext,MainActivity.class));
            finish();
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
        chip_group_awards.addView(chip);
        chip.setOnCloseIconClickListener(v -> {
            chip_group_awards.removeView(chip);
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