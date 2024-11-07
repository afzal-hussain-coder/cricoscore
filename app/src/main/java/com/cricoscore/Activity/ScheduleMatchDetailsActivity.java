package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cricoscore.Adapter.YourTeamListAdapterHorizontal;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityScheduleMatchBinding;
import com.cricoscore.databinding.ActivityScheduleMatchDetailsBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.TeamModel;
import com.cricoscore.model.TournamentModel.TournamentDetails;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleMatchDetailsActivity extends AppCompatActivity {

    ActivityScheduleMatchDetailsBinding activityScheduleMatchDetailsBinding;
    Context mContext;
    Activity mActivity;

    String tournamentId = "";
    String[] categoriesArray = {"Limited Over", "Box/Turf Cricket", "Pair Cricket", "Test Match", "The Hundred"};
    private Chip selectedChip; // Variable to track the currently selected chip

    String[] pitchTypeArray = {"Rough", "Cement", "Turf", "Astroturf", "Matting"};
    private Chip selectedChipPitchType;

    private int year, month, day, hour, minute;
    String date = "";
    Date _selectedDate;
    Date _currentDate;
    String aTime = "";
    String currentDate = "";

    String city = "";
    String groundName = "";
    String dateAndTime = "";
    String numberOfOvers = "";
    String overPerBowler = "";
    String matchType = "";
    String ballType = "";
    String pitch_type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityScheduleMatchDetailsBinding = ActivityScheduleMatchDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityScheduleMatchDetailsBinding.getRoot());
        mContext = this;
        mActivity = this;
        ToolbarBinding toolbarBinding = activityScheduleMatchDetailsBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.schedule_match));
        toolbarBinding.toolbar.setNavigationOnClickListener(v ->
                {
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();
                }


        );

        if (getIntent() != null) {
            tournamentId = getIntent().getStringExtra("id");
        }

        // Get the data passed from the previous activity
        Intent intent = getIntent();
        String teamName1 = intent.getStringExtra("teamName1");
        activityScheduleMatchDetailsBinding.tvTeamName.setText(teamName1);
        int teamId1 = intent.getIntExtra("teamId1", -1);  // Default value -1 if not found


        String teamLogo1 = intent.getStringExtra("teamLogo1");
        Glide.with(mContext).load(Global.BASE_URL + "/" + teamLogo1).into(activityScheduleMatchDetailsBinding.imgFirstTeamLogo);

        String teamName2 = intent.getStringExtra("teamName2");
        activityScheduleMatchDetailsBinding.tvTNameTeamTwo.setText(teamName2);
        int teamId2 = intent.getIntExtra("teamId2", -1);
        //Toaster.customToast(teamId2+"");// Default value -1 if not found
        String teamLogo2 = intent.getStringExtra("teamLogo2");
        Glide.with(mContext).load(Global.BASE_URL + "/" + teamLogo2).into(activityScheduleMatchDetailsBinding.imgTeamTwoLogo);


//        if (Global.isOnline(mContext)) {
//            getTournamentDetails(tournamentId);
//        } else {
//            Global.showDialog(mActivity);
//        }


        activityScheduleMatchDetailsBinding.chipSpecializationGroupList.removeAllViews();

        ArrayList<String> categoriesList = new ArrayList<>(Arrays.asList(categoriesArray));

        for (String chipText : categoriesList) {
            Chip chip = new Chip(mContext);
            chip.setText(chipText);
            chip.setChipBackgroundColorResource(R.color.platinum_gray);
            chip.setChipStrokeWidth(2.0f);
            chip.setTextAppearance(R.style.MyChipTextAppearanceList);

            // Set a click listener for each chip
            chip.setOnClickListener(v -> {
                // Check if the clicked chip is already selected
                if (selectedChip != null) {
                    // Deselect the previously selected chip
                    selectedChip.setChipBackgroundColorResource(R.color.platinum_gray); // Reset previous chip color
                    selectedChip.setTextColor(getResources().getColor(R.color.dim_grey)); // Reset previous chip text color
                }

                // Select the new chip
                selectedChip = chip; // Update the reference to the newly selected chip
                chip.setChipBackgroundColorResource(R.color.purple_500); // Change to selected color
                chip.setTextColor(getResources().getColor(R.color.white)); // Change to selected text color

                matchType = chipText;
                // Optional: Display a toast message
                //Toast.makeText(this, "Selected: " + matchType, Toast.LENGTH_SHORT).show();
            });


            activityScheduleMatchDetailsBinding.chipSpecializationGroupList.addView(chip);
        }


        activityScheduleMatchDetailsBinding.chipSpecializationGroupListPitchType.removeAllViews();

        ArrayList<String> pitchType = new ArrayList<>(Arrays.asList(pitchTypeArray));

        for (String chipText : pitchType) {
            Chip chip = new Chip(mContext);
            chip.setText(chipText);
            chip.setChipBackgroundColorResource(R.color.platinum_gray);
            chip.setChipStrokeWidth(2.0f);
            chip.setTextAppearance(R.style.MyChipTextAppearanceList);

            // Set a click listener for each chip
            chip.setOnClickListener(v -> {
                // Check if the clicked chip is already selected
                if (selectedChipPitchType != null) {
                    // Deselect the previously selected chip
                    selectedChipPitchType.setChipBackgroundColorResource(R.color.platinum_gray); // Reset previous chip color
                    selectedChipPitchType.setTextColor(getResources().getColor(R.color.dim_grey)); // Reset previous chip text color
                }

                // Select the new chip
                selectedChipPitchType = chip; // Update the reference to the newly selected chip
                chip.setChipBackgroundColorResource(R.color.purple_500); // Change to selected color
                chip.setTextColor(getResources().getColor(R.color.white)); // Change to selected text color

                pitch_type = chipText;
                // Optional: Display a toast message
                //Toast.makeText(this, "Selected: " + pitch_type, Toast.LENGTH_SHORT).show();
            });


            activityScheduleMatchDetailsBinding.chipSpecializationGroupListPitchType.addView(chip);
        }

        activityScheduleMatchDetailsBinding.rlTennis.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityScheduleMatchDetailsBinding.rlTennis.setForeground(getResources().getDrawable(R.drawable.circle_red));
                activityScheduleMatchDetailsBinding.rlWhiteLether.setForeground(null);
                activityScheduleMatchDetailsBinding.rlRedLeather.setForeground(null);
                activityScheduleMatchDetailsBinding.rlOther.setForeground(null);
            }

            activityScheduleMatchDetailsBinding.tvTennis.setTextColor(getResources().getColor(R.color.purple_500));
            activityScheduleMatchDetailsBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));

            ballType = activityScheduleMatchDetailsBinding.tvTennis.getText().toString();

        });
        activityScheduleMatchDetailsBinding.rlRedLeather.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityScheduleMatchDetailsBinding.rlRedLeather.setForeground(getResources().getDrawable(R.drawable.circle_red));

                activityScheduleMatchDetailsBinding.rlWhiteLether.setForeground(null);
                activityScheduleMatchDetailsBinding.rlTennis.setForeground(null);
                activityScheduleMatchDetailsBinding.rlOther.setForeground(null);
            }

            activityScheduleMatchDetailsBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvRedLether.setTextColor(getResources().getColor(R.color.purple_500));
            activityScheduleMatchDetailsBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));

            ballType = activityScheduleMatchDetailsBinding.tvRedLether.getText().toString();

        });
        activityScheduleMatchDetailsBinding.rlWhiteLether.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityScheduleMatchDetailsBinding.rlWhiteLether.setForeground(getResources().getDrawable(R.drawable.circle_red));
                activityScheduleMatchDetailsBinding.rlRedLeather.setForeground(null);
                activityScheduleMatchDetailsBinding.rlTennis.setForeground(null);
                activityScheduleMatchDetailsBinding.rlOther.setForeground(null);
            }

            activityScheduleMatchDetailsBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.purple_500));
            activityScheduleMatchDetailsBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));

            ballType = activityScheduleMatchDetailsBinding.tvWhiteLether.getText().toString();

        });
        activityScheduleMatchDetailsBinding.rlOther.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityScheduleMatchDetailsBinding.rlOther.setForeground(getResources().getDrawable(R.drawable.circle_red));
                activityScheduleMatchDetailsBinding.rlRedLeather.setForeground(null);
                activityScheduleMatchDetailsBinding.rlTennis.setForeground(null);
                activityScheduleMatchDetailsBinding.rlWhiteLether.setForeground(null);
            }

            activityScheduleMatchDetailsBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleMatchDetailsBinding.tvOthers.setTextColor(getResources().getColor(R.color.purple_500));

            ballType = activityScheduleMatchDetailsBinding.tvOthers.getText().toString();

        });


        activityScheduleMatchDetailsBinding.editTextNoOfOver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    int finalValue = Integer.parseInt(s.toString()) / 5;
                    activityScheduleMatchDetailsBinding.editTextOverPerBowler.setText(finalValue + "");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        activityScheduleMatchDetailsBinding.edittextDateTime.setOnClickListener(v -> {
            // Create a SimpleDateFormat for the desired date-time format
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", Locale.getDefault());

            // Get the current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Open DatePickerDialog for selecting a date
            DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, (datePicker, selectedYear, selectedMonth, selectedDay) -> {

                // Set the selected date in the calendar
                calendar.set(selectedYear, selectedMonth, selectedDay);

                // Now, open TimePickerDialog after selecting the date
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, (timePicker, selectedHour, selectedMinute) -> {
                    // Set the selected time in the calendar
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);

                    // Now that both date and time are selected, format them
                    String formattedDateTime = simpleDateFormat.format(calendar.getTime());
                    formattedDateTime = formattedDateTime.toUpperCase(Locale.getDefault());

                    // Display the formatted date and time in the EditText or TextView
                    activityScheduleMatchDetailsBinding.edittextDateTime.setText(formattedDateTime);

                }, hour, minute, false); // Use 24-hour format (false = 24-hour, true = 12-hour)

                // Show the TimePickerDialog
                timePickerDialog.show();

            }, year, month, day);

            // Set min and max dates for the DatePickerDialog
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);  // Disable past dates
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (90L * 24 * 60 * 60 * 1000));  // 90 days in future

            // Show the DatePickerDialog
            datePickerDialog.show();
        });


        activityScheduleMatchDetailsBinding.liSquadTeamOne.setOnClickListener(v -> {
            startActivity(new Intent(mContext, YourPlayerListActivity.class).putExtra("ID", String.valueOf(teamId1)));

        });

        activityScheduleMatchDetailsBinding.liSquadTeamTwo.setOnClickListener(v -> {
            startActivity(new Intent(mContext, YourPlayerListActivity.class).putExtra("ID", String.valueOf(teamId2)));
        });

        activityScheduleMatchDetailsBinding.rlProfileTeamOne.setOnClickListener(v -> {

        });

        activityScheduleMatchDetailsBinding.rlProfileTeamTwo.setOnClickListener(v -> {

        });


        /*
        * Validation for all mandatory data
        *
        * */
        activityScheduleMatchDetailsBinding.tvScheduleMatch.setOnClickListener(v -> {


//            if (activityScheduleMatchDetailsBinding.tvNoOfSquad.getText().toString().equalsIgnoreCase("(0)")) {
//                Toaster.customToast("Please add at least 11 squad in the " + teamName1);
//            } else if (activityScheduleMatchDetailsBinding.tvNoOfSquadTeamTwo.getText().toString().equalsIgnoreCase("(0)")) {
//                Toaster.customToast("Please add at least 11 squad in the " + teamName2);
//            }else
                if(matchType.isEmpty()){
                Toaster.customToast("Please Select Match Type !");
            }else if(activityScheduleMatchDetailsBinding.editTextNoOfOver.getText().toString().isEmpty()){
                Toaster.customToast("Please Add No Of Overs !");
            } else if(activityScheduleMatchDetailsBinding.editTextOverPerBowler.getText().toString().isEmpty()){
                Toaster.customToast("Please Add Over Per Bowler !");
            }else if(activityScheduleMatchDetailsBinding.edittextCity.getText().toString().isEmpty()){
                Toaster.customToast("Please Type City or Town");
            }else if(activityScheduleMatchDetailsBinding.editTextGround.getText().toString().isEmpty()){
                Toaster.customToast("Please Type Ground Name !");
            } else if(activityScheduleMatchDetailsBinding.edittextDateTime.getText().toString().isEmpty()){
                Toaster.customToast("Please Add Date and Time !");
            }else if(ballType.isEmpty()){
                Toaster.customToast("Please Select Ball Type !");
            }else if(pitch_type.isEmpty()){
                Toaster.customToast("Please Select Pitch Type !");
            }else {
              Toaster.customToast("All data submitted successfully!");
            }
        });

    }


    public void getTournamentDetails(String tournamentId) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getTournamentDetails(SessionManager.getToken(), tournamentId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        JSONObject finalData = jsonObject.getJSONObject("data");

                        Log.d("FinalResponse", finalData.toString());

//                        location = finalData.getString("location");
//                        activityTournamentDetailsBinding.tvtLocation.setText(location);
//
//                        JSONArray jsonArray = finalData.getJSONArray("teams");
//                        for(int i=0;i<jsonArray.length();i++){
//                            teamModelArrayList.add(new TeamModel(jsonArray.getJSONObject(i)));
//                        }
//
//                        if(teamModelArrayList.isEmpty()){
//                            activityTournamentDetailsBinding.tvTextSchedule.setText(mContext.getResources().getString(R.string.add_team));
//                        }else{
//                            activityTournamentDetailsBinding.tvTextSchedule.setText(mContext.getResources().getString(R.string.schedule_match));
//                        }
//
//
//                        yourTeamListAdapter = new YourTeamListAdapterHorizontal(mContext, teamModelArrayList, new YourTeamListAdapterHorizontal.itemClickListener() {
//
//                            @Override
//                            public void checkedItem(int pos, int teamId, String teamName, String logo) {
//                                position = pos;
//                                teamId = teamId;
//                                teamNameSelected = teamName;
//                                teamLogoSelected = logo;
//                            }
//
//                            @Override
//                            public void itemRemove(int id, int pos) {
//                                if (Global.isOnline(mContext)) {
//                                    removeTeamFromList(id,pos,tournamentId);
//                                } else {
//                                    Global.showDialog(mActivity);
//                                }
//                            }
//                        });
//
//
//                        activityTournamentDetailsBinding.rvTeamList.setAdapter(yourTeamListAdapter);

                        TournamentDetails tournamentDetails = new TournamentDetails(finalData);
                        //setData(tournamentDetails);

                        Log.d("Response", tournamentDetails.toString());


                    } catch (Exception e) {
                        Log.e("Error", "JSON parsing error: " + e.getMessage());
                    }
                } else {
                    Log.e("Error", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", "Failed to fetch data: " + t.getMessage());
                Global.hideLoder();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MainActivity.class));
        finish();

    }
}