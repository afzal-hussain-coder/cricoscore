package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

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
import android.os.Handler;
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
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityAddTournamentBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.view_model.AddTournamentViewModel;
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
    TextView tv_date, tv_end_date, tv_time;
    String startdate = "";
    String enddate = "";
    Date _selectedDate;
    Date _currentDate;
    String aTime = "";
    String currentDate = "";
    private ArrayList<DataModel> option = new ArrayList<>();
    private ArrayList<DataModel> option_ball_type = new ArrayList<>();
    private ArrayList<DataModel> option_state = new ArrayList<>();
    private ArrayList<DataModel> option_city = new ArrayList<>();
    public static Uri image_uri = null;
    Uri image_logo_uri ,image_banner_uri =null;
    private int logo = 0, banner = 0;
    private String filterType = "";
    private String tournamentType = "";
    private String selectedBallType = "";
    private String selectedState = "";
    private String selectedCity = "";
    int discount;


    ActivityAddTournamentBinding activityAddTournamentBinding;

    AddTournamentViewModel addTournamentViewModel;


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

        /**
         * @AddTournament Result
         * @s --> Check result
         */

        addTournamentViewModel = new ViewModelProvider(this).get(AddTournamentViewModel.class);
        addTournamentViewModel.getAddTournamentResult().observe(this, responseStatus -> {
            if (responseStatus.isStatus()) {

                new Handler().postDelayed(() -> {
                    Toaster.customToast(responseStatus.getMessage());
                }, 100);

            }
        });

        addTournamentViewModel.getSubmitProfileProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });


        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (image_uri != null) {

            if (logo == 1) {
                activityAddTournamentBinding.profilePic.setImageURI(image_uri);
                image_logo_uri = image_uri;
            } else if (banner == 2) {
                activityAddTournamentBinding.coverImg.setImageURI(image_uri);
               image_banner_uri = image_uri;
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

                tournamentType = name;
            }


            @Override
            public void onDismiss() {
            }
        });


        option_ball_type.add(new DataModel("Leather"));
        option_ball_type.add(new DataModel("Tennis"));
        option_ball_type.add(new DataModel("Rubber"));
        option_ball_type.add(new DataModel("Other"));
        activityAddTournamentBinding.dropBallType.setOptionList(option_ball_type);
        activityAddTournamentBinding.dropBallType.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                selectedBallType = name;
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
                selectedState = name;
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
                selectedCity = name;
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
                startdate = year + "-" + (month + 1) + "-" + day;

                try {
                    _currentDate = simpleDateFormat.parse(currentDate);
                    _selectedDate = simpleDateFormat.parse(startdate);


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                activityAddTournamentBinding.tvStartDate.setText(Global.convertUTCDateToMM(startdate));
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
                enddate = year + "-" + (month + 1) + "-" + day;


                try {
                    _currentDate = simpleDateFormat.parse(currentDate);
                    _selectedDate = simpleDateFormat.parse(enddate);


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                activityAddTournamentBinding.tvEndDate.setText(Global.convertUTCDateToMM(enddate));
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
                } else {
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
            if (activityAddTournamentBinding.editTextTAddAwards.getText().toString().length() > 1) {
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


        activityAddTournamentBinding.editTextTAddAwards.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledInputAwards.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextTAddAwards.getText()).length() == 0) {
                    activityAddTournamentBinding.filledInputAwards.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledInputAwards.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextTName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldTname.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldTname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldTname.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldTname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldTname.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldTname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextTName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldTname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextTName.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldTname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldTname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldLocation.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldLocation.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldLocation.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldLocation.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldLocation.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldLocation.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextLocation.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldLocation.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextLocation.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldLocation.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldLocation.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextGroundName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldGroundName.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldGroundName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldGroundName.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldGroundName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldGroundName.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldGroundName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextGroundName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldGroundName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextGroundName.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldGroundName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldGroundName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextTfees.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldFees.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldFees.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldFees.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldFees.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldFees.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldFees.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextTfees.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldFees.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextTfees.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldFees.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldFees.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextTDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldDiscount.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldDiscount.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldDiscount.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldDiscount.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldDiscount.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldDiscount.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextTDiscount.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldDiscount.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextTDiscount.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldDiscount.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldDiscount.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextTNoOfTeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldNumberOfTeam.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldNumberOfTeam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextTNoOfTeam.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextTNoOfTeam.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldNumberOfTeam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldNumberOfTeam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextTSponsored.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldSponsore.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldSponsore.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldSponsore.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldSponsore.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldSponsore.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldSponsore.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextTSponsored.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldSponsore.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextTSponsored.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldSponsore.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldSponsore.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextTOrganizerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldOrganizerNam.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldOrganizerNam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldOrganizerNam.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldOrganizerNam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldOrganizerNam.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldOrganizerNam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextTOrganizerName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldOrganizerNam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextTOrganizerName.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldOrganizerNam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldOrganizerNam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextTOrganizerPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldOrganizerPhone.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldOrganizerPhone.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextTOrganizerPhone.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextTOrganizerPhone.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldOrganizerPhone.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldOrganizerPhone.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTournamentBinding.editTextOrganizerEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTournamentBinding.filledTextFieldOrganizerEmail.setErrorEnabled(false);
                    activityAddTournamentBinding.filledTextFieldOrganizerEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setErrorEnabled(false);
                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });
        activityAddTournamentBinding.editTextOrganizerEmail.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityAddTournamentBinding.editTextOrganizerEmail.getText()).length() == 0) {
                    activityAddTournamentBinding.filledTextFieldOrganizerEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTournamentBinding.filledTextFieldOrganizerEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });


        activityAddTournamentBinding.mbSubmit.setOnClickListener(v -> {


            if (tournamentType.isEmpty()) {
                Toaster.customToast("Select Tournament Type");
            } else if (Objects.requireNonNull(activityAddTournamentBinding.editTextTAddAwards.getText()).toString().isEmpty()) {
                activityAddTournamentBinding.filledInputAwards.setErrorEnabled(true);
                activityAddTournamentBinding.filledInputAwards.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledInputAwards.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledInputAwards.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledInputAwards.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledInputAwards.setError(getResources().getString(R.string.add_awards_message));
            } else if (selectedBallType.isEmpty()) {
                Toaster.customToast("Select Ball Type");
            } else if (Objects.requireNonNull(activityAddTournamentBinding.editTextTName.getText()).toString().isEmpty()
                    || activityAddTournamentBinding.editTextTName.getText().toString().length() < 3
                    || activityAddTournamentBinding.editTextTName.getText().toString().length() > 21) {
                activityAddTournamentBinding.filledTextFieldTname.setErrorEnabled(true);
                activityAddTournamentBinding.filledTextFieldTname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldTname.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldTname.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldTname.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldTname.setError(getResources().getString(R.string.tournamentName_msg));
            } else if (activityAddTournamentBinding.tvStartDate.getText().toString().isEmpty()) {
                Toaster.customToast("Choose Start Date");
            } else if (activityAddTournamentBinding.tvEndDate.getText().toString().isEmpty()) {
                Toaster.customToast("Choose End Date");
            } else if (selectedState.isEmpty()) {
                Toaster.customToast("Select State");
            } else if (selectedCity.isEmpty()) {
                Toaster.customToast("Select City");
            } else if (Objects.requireNonNull(activityAddTournamentBinding.editTextLocation.getText()).toString().isEmpty()
                    || activityAddTournamentBinding.editTextLocation.getText().toString().length() < 3
                    || activityAddTournamentBinding.editTextLocation.getText().toString().length() > 21) {
                activityAddTournamentBinding.filledTextFieldLocation.setErrorEnabled(true);
                activityAddTournamentBinding.filledTextFieldLocation.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldLocation.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldLocation.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldLocation.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldLocation.setError(getResources().getString(R.string.location_msg));
            } else if (Objects.requireNonNull(activityAddTournamentBinding.editTextGroundName.getText()).toString().isEmpty()
                    || activityAddTournamentBinding.editTextGroundName.getText().toString().length() < 3
                    || activityAddTournamentBinding.editTextGroundName.getText().toString().length() > 21) {
                activityAddTournamentBinding.filledTextFieldGroundName.setErrorEnabled(true);
                activityAddTournamentBinding.filledTextFieldGroundName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldGroundName.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldGroundName.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldGroundName.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldGroundName.setError(getResources().getString(R.string.groundName_msg));
            } else if (Objects.requireNonNull(activityAddTournamentBinding.editTextTfees.getText()).toString().isEmpty()) {
                activityAddTournamentBinding.filledTextFieldFees.setErrorEnabled(true);
                activityAddTournamentBinding.filledTextFieldFees.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldFees.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldFees.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldFees.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldFees.setError(getResources().getString(R.string.tFess_msg));
            } else if (Objects.requireNonNull(activityAddTournamentBinding.editTextTNoOfTeam.getText()).toString().isEmpty()) {
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setErrorEnabled(true);
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTournamentBinding.filledTextFieldNumberOfTeam.setError(getResources().getString(R.string.numberOfTeams_msg));
            }
//            else if (Objects.requireNonNull(activityAddTournamentBinding.editTextTOrganizerName.getText()).toString().isEmpty()|| activityAddTournamentBinding.editTextGroundName.getText().toString().length() < 3) {
//                activityAddTournamentBinding.filledTextFieldOrganizerNam.setErrorEnabled(true);
//                activityAddTournamentBinding.filledTextFieldOrganizerNam.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerNam.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerNam.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerNam.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerNam.setError(getResources().getString(R.string.organizerName_msg));
//            }else if (!Global.isValidPhoneNumber(activityAddTournamentBinding.editTextTOrganizerPhone.getText().toString())) {
//                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setErrorEnabled(true);
//                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerPhone.setError(getResources().getString(R.string.phone_msg));
//            }else if (!Global.isValidEmail(activityAddTournamentBinding.editTextOrganizerEmail.getText().toString())) {
//                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setErrorEnabled(true);
//                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityAddTournamentBinding.filledTextFieldOrganizerEmail.setError(getResources().getString(R.string.email_msg));
//            }
            else {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if(!activityAddTournamentBinding.editTextTDiscount.getText().toString().isEmpty()){
                        discount= Integer.parseInt(activityAddTournamentBinding.editTextTDiscount.getText().toString());
                    }

                    if (Global.isOnline(mContext)) {

                        addTournamentViewModel.getAddTournamentResponse(SessionManager.getToken(), tournamentType,
                                activityAddTournamentBinding.editTextTAddAwards.getText().toString(), selectedBallType,
                                activityAddTournamentBinding.editTextTName.getText().toString(),
                                startdate,
                                enddate, selectedState, selectedCity,
                                activityAddTournamentBinding.editTextLocation.getText().toString(),
                                activityAddTournamentBinding.editTextGroundName.getText().toString(),
                                Float.parseFloat(activityAddTournamentBinding.editTextTfees.getText().toString()),
                                discount,
                                Integer.parseInt(activityAddTournamentBinding.editTextTNoOfTeam.getText().toString()),
                                activityAddTournamentBinding.editTextTSponsored.getText().toString(),
                                SessionManager.getUserId(), 0,image_logo_uri,image_banner_uri);
                    } else {
                        Global.showDialog(mActivity);
                    }
                }
            }

        });

        activityAddTournamentBinding.middle.setOnClickListener(view -> {
            logo = 1;
            banner = 0;
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM", "AddTournamentActivity"));
        });

        activityAddTournamentBinding.coverImg.setOnClickListener(view -> {
            logo = 0;
            banner = 2;
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM", "AddTournamentActivity"));
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