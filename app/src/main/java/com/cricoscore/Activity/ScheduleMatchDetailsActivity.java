package com.cricoscore.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.cricoscore.ParamBody.CreateScheduleBody;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityScheduleMatchBinding;
import com.cricoscore.databinding.ActivityScheduleMatchDetailsBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.PlayerModel;
import com.cricoscore.model.ScheduleMatchDetailsItem;
import com.cricoscore.model.TeamModel;
import com.cricoscore.model.TournamentModel.TournamentDetails;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    int teamId1,teamId2;
    String teamOnePlayerId="";
    String teamTwoPlayerId ="";
    ArrayList<Integer> receivedListOne = new ArrayList<>();
    ArrayList<Integer> receivedListTwo = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private ActivityResultLauncher<Intent> teamActivityLauncher;

    ScheduleMatchDetailsItem scheduleMatchDetailsItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityScheduleMatchDetailsBinding = ActivityScheduleMatchDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityScheduleMatchDetailsBinding.getRoot());
        mContext = this;
        mActivity = this;

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        ToolbarBinding toolbarBinding = activityScheduleMatchDetailsBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.schedule_match));
        toolbarBinding.toolbar.setNavigationOnClickListener(v ->
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
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
        teamId1 = intent.getIntExtra("teamId1", -1);  // Default value -1 if not found


        String teamLogo1 = intent.getStringExtra("teamLogo1");
        Glide.with(mContext).load(Global.BASE_URL + "/" + teamLogo1).into(activityScheduleMatchDetailsBinding.imgFirstTeamLogo);

        String teamName2 = intent.getStringExtra("teamName2");
        activityScheduleMatchDetailsBinding.tvTNameTeamTwo.setText(teamName2);
        teamId2 = intent.getIntExtra("teamId2", -1);
        //Toaster.customToast(teamId2+"");// Default value -1 if not found
        String teamLogo2 = intent.getStringExtra("teamLogo2");
        Glide.with(mContext).load(Global.BASE_URL + "/" + teamLogo2).into(activityScheduleMatchDetailsBinding.imgTeamTwoLogo);

        //activityScheduleMatchDetailsBinding.tvNoOfSquad1.setText(String.valueOf(teamAList.size())); // For Team A
       // activityScheduleMatchDetailsBinding.tvNoOfSquadTeamTwo.setText(String.valueOf(teamBList.size())); // For Team B

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


        // Initialize the launcher
        teamActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Refresh the data from SharedPreferences
                        updateTeamData();
                    }
                }
        );

        // Launch TeamPlayerlistActivity for Team A
        activityScheduleMatchDetailsBinding.liSquadTeamOne.setOnClickListener(v -> {
            Intent intent1 = new Intent(mContext, TeamPlayerlistActivity.class);
            intent1.putExtra("From", "A");
            intent1.putExtra("ID", teamId1);
            teamActivityLauncher.launch(intent1);
        });

        // Launch TeamPlayerlistActivity for Team B
        activityScheduleMatchDetailsBinding.liSquadTeamTwo.setOnClickListener(v -> {
            Intent intent2 = new Intent(mContext, TeamPlayerlistActivity.class);
            intent2.putExtra("From", "B");
            intent2.putExtra("ID", teamId2);
            teamActivityLauncher.launch(intent2);
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


            if (activityScheduleMatchDetailsBinding.tvNoOfSquad1.getText().toString().equalsIgnoreCase("(0)")) {
                Toaster.customToast("Please add at least 2 squad in the " + teamName1);
            } else if (activityScheduleMatchDetailsBinding.tvNoOfSquadTeamTwo.getText().toString().equalsIgnoreCase("(0)")) {
                Toaster.customToast("Please add at least 2 squad in the " + teamName2);
            }else if(matchType.isEmpty()){
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

                    /*Create Schedule Api Calling...*/

                    if (Global.isOnline(mContext)) {
                        createSchedule(Integer.parseInt(tournamentId),activityScheduleMatchDetailsBinding.editTextGround.getText().toString(),
                                activityScheduleMatchDetailsBinding.editTextGround.getText().toString(),
                                ballType,pitch_type,
                                activityScheduleMatchDetailsBinding.editTextNoOfOver.getText().toString(),
                                activityScheduleMatchDetailsBinding.editTextOverPerBowler.getText().toString(),
                                teamId1,
                                teamId2,
                                activityScheduleMatchDetailsBinding.edittextDateTime.getText().toString(),
                                teamOnePlayerId,
                                teamTwoPlayerId,matchType);
                    } else {
                        Global.showDialog(mActivity);
                    }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();



        //activityScheduleMatchDetailsBinding.tvNoOfSquadTeamTwo.setText(TeamPlayerlistActivity.retrieveListB(sharedPreferences).size()+"");
        //activityScheduleMatchDetailsBinding.tvNoOfSquad1.setText(retrieveList(sharedPreferences).size()+"");

        if (Global.isOnline(mContext)) {
            getTournamentDetails(tournamentId);
        } else {
            Global.showDialog(mActivity);
        }

        updateTeamData();
    }

    private void updateTeamData() {
        //SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Update TextViews with data from SharedPreferences
        ArrayList<PlayerModel> teamAList = retrieveList("TeamA_List");
        ArrayList<PlayerModel> teamBList  = retrieveList("TeamB_List");

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < teamAList.size(); j++) {
            sb.append(teamAList.get(j).getPlayer_id());
            if (j < teamAList.size() - 1) {
                sb.append(",");
            }
        }

        teamOnePlayerId = sb.toString();

        StringBuilder sb2 = new StringBuilder();

        for (int j = 0; j < teamBList.size(); j++) {
            sb2.append(teamBList.get(j).getPlayer_id());
            if (j < teamBList.size() - 1) {
                sb2.append(",");
            }
        }

        teamTwoPlayerId = sb2.toString();

        activityScheduleMatchDetailsBinding.tvNoOfSquad1.setText(String.valueOf(teamAList.size())); // For Team A
        activityScheduleMatchDetailsBinding.tvNoOfSquadTeamTwo.setText(String.valueOf(teamBList.size())); // For Team B
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

                        scheduleMatchDetailsItem = new ScheduleMatchDetailsItem(finalData);

                       // Toaster.customToast(scheduleMatchDetailsItem.getBallType());

//                        activityScheduleMatchDetailsBinding.tvTeamName.setText(scheduleMatchDetailsItem.getTeamAInfo().getName());
//
//                        Glide.with(mContext).load(Global.BASE_URL + "/" + scheduleMatchDetailsItem.getTeamAInfo().getTeamLogo()).into(activityScheduleMatchDetailsBinding.imgFirstTeamLogo);
//
//                        activityScheduleMatchDetailsBinding.tvTNameTeamTwo.setText(scheduleMatchDetailsItem.getTeamBInfo().getName());
//
//                        Glide.with(mContext).load(Global.BASE_URL + "/" + scheduleMatchDetailsItem.getTeamBInfo().getTeamLogo()).into(activityScheduleMatchDetailsBinding.imgTeamTwoLogo);

                        activityScheduleMatchDetailsBinding.editTextNoOfOver.setText(scheduleMatchDetailsItem.getNoOfOver()+"");
                        activityScheduleMatchDetailsBinding.editTextOverPerBowler.setText(scheduleMatchDetailsItem.getNoOfOverBowler()+"");
                        activityScheduleMatchDetailsBinding.edittextCity.setText(scheduleMatchDetailsItem.getLocation());
                        activityScheduleMatchDetailsBinding.editTextGround.setText(scheduleMatchDetailsItem.getGround());
                        activityScheduleMatchDetailsBinding.edittextDateTime.setText(scheduleMatchDetailsItem.getStartDateTime());


                        JSONArray jsonArray = finalData.getJSONArray("teams");
                        for(int i=0;i<jsonArray.length();i++){

                            if(jsonArray.getJSONObject(i).getInt("team_id")==teamId1){
                                JSONArray jsonArrayPlayer = jsonArray.getJSONObject(i).getJSONArray("players");

                                for(int j = 0;j<jsonArrayPlayer.length();j++){
                                    receivedListOne.add(jsonArrayPlayer.getJSONObject(j).getInt("player_id"));
                                }

                                Log.d("PalyerSize",receivedListOne.size()+"");
                               // activityScheduleMatchDetailsBinding.tvNoOfSquad1.setText(jsonArrayPlayer.length()+"");

                                StringBuilder sb = new StringBuilder();

                                for (int j = 0; j < jsonArrayPlayer.length(); j++) {
                                    sb.append(jsonArrayPlayer.getJSONObject(j).getString("player_id"));
                                    if (j < jsonArrayPlayer.length() - 1) {
                                        sb.append(",");
                                    }
                                }

                               ///teamOnePlayerId = sb.toString();

                                Log.d("PlayerOne",jsonArrayPlayer.length()+"/"+teamOnePlayerId);

                            }else if(jsonArray.getJSONObject(i).getInt("team_id")==teamId2){
                                JSONArray jsonArrayPlayer2 = jsonArray.getJSONObject(i).getJSONArray("players");

                                for(int j = 0;j<jsonArrayPlayer2.length();j++){
                                    receivedListTwo.add(jsonArrayPlayer2.getJSONObject(j).getInt("player_id"));
                                }

                                Log.d("PalyerSize",receivedListTwo.size()+"");

                                //activityScheduleMatchDetailsBinding.tvNoOfSquadTeamTwo.setText(jsonArrayPlayer2.length()+"");

                                StringBuilder sb = new StringBuilder();

                                for (int j = 0; j < jsonArrayPlayer2.length(); j++) {

                                    Log.d("Id",jsonArrayPlayer2.getJSONObject(j).getString("player_id"));

                                    sb.append(jsonArrayPlayer2.getJSONObject(j).getString("player_id"));
                                    if (j < jsonArrayPlayer2.length() - 1) {
                                        sb.append(",");
                                    }
                                }

                               // teamTwoPlayerId = sb.toString();

                                Log.d("PlayerTwo",jsonArrayPlayer2.length()+"/"+teamTwoPlayerId);
                            }


                        }


                        ballType = scheduleMatchDetailsItem.getBallType();


                        if(ballType.equalsIgnoreCase(activityScheduleMatchDetailsBinding.tvTennis.getText().toString())){
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
                        }else if(ballType.equalsIgnoreCase(activityScheduleMatchDetailsBinding.tvWhiteLether.getText().toString())){
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
                        }else if(ballType.equalsIgnoreCase(activityScheduleMatchDetailsBinding.tvRedLether.getText().toString())){
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
                        }else if(ballType.equalsIgnoreCase(activityScheduleMatchDetailsBinding.tvOthers.getText().toString())){
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
                        }


                        pitch_type = scheduleMatchDetailsItem.getPitchType();


                        activityScheduleMatchDetailsBinding.chipSpecializationGroupListPitchType.removeAllViews();
                        ArrayList<String> pitchType = new ArrayList<>(Arrays.asList(pitchTypeArray));

                        // Get the selected pitch type from your data model
                        String selectedPitchType = scheduleMatchDetailsItem.getPitchType();

                        for (String chipText : pitchType) {
                            Chip chip = new Chip(mContext);
                            chip.setText(chipText);
                            chip.setChipBackgroundColorResource(R.color.platinum_gray);
                            chip.setChipStrokeWidth(2.0f);
                            chip.setTextAppearance(R.style.MyChipTextAppearanceList);

                            // Check if this chip matches the selected pitch type
                            if (chipText.equals(selectedPitchType)) {
                                // This chip should be selected initially
                                chip.setChipBackgroundColorResource(R.color.purple_500);
                                chip.setTextColor(getResources().getColor(R.color.white));
                                selectedChipPitchType = chip; // Update the reference to the selected chip
                                pitch_type = chipText; // Set the selected pitch type
                            } else {
                                // Set default unselected chip colors
                                chip.setChipBackgroundColorResource(R.color.platinum_gray);
                                chip.setTextColor(getResources().getColor(R.color.dim_grey));
                            }

                            // Set a click listener for each chip
                            chip.setOnClickListener(v -> {
                                // Deselect the previously selected chip
                                if (selectedChipPitchType != null) {
                                    selectedChipPitchType.setChipBackgroundColorResource(R.color.platinum_gray);
                                    selectedChipPitchType.setTextColor(getResources().getColor(R.color.dim_grey));
                                }

                                // Select the new chip
                                selectedChipPitchType = chip;
                                chip.setChipBackgroundColorResource(R.color.purple_500);
                                chip.setTextColor(getResources().getColor(R.color.white));

                                pitch_type = chipText;
                            });

                            // Add the chip to the ChipGroup
                            activityScheduleMatchDetailsBinding.chipSpecializationGroupListPitchType.addView(chip);
                        }

                        activityScheduleMatchDetailsBinding.chipSpecializationGroupList.removeAllViews();
                        ArrayList<String> categoriesList = new ArrayList<>(Arrays.asList(categoriesArray));

                        // Get the selected ball type from your data model
                        String selectedBallType = scheduleMatchDetailsItem.getMatch_type();

                        for (String chipText : categoriesList) {
                            Chip chip = new Chip(mContext);
                            chip.setText(chipText);
                            chip.setChipBackgroundColorResource(R.color.platinum_gray);
                            chip.setChipStrokeWidth(2.0f);
                            chip.setTextAppearance(R.style.MyChipTextAppearanceList);

                            // Check if this chip matches the selected ball type
                            if (chipText.equals(selectedBallType)) {
                                // This chip should be selected initially
                                chip.setChipBackgroundColorResource(R.color.purple_500);
                                chip.setTextColor(getResources().getColor(R.color.white));
                                selectedChip = chip; // Update the reference to the selected chip
                                matchType = chipText; // Set the selected match type
                            } else {
                                // Set default unselected chip colors
                                chip.setChipBackgroundColorResource(R.color.platinum_gray);
                                chip.setTextColor(getResources().getColor(R.color.dim_grey));
                            }

                            // Set a click listener for each chip
                            chip.setOnClickListener(v -> {
                                // Deselect the previously selected chip
                                if (selectedChip != null) {
                                    selectedChip.setChipBackgroundColorResource(R.color.platinum_gray);
                                    selectedChip.setTextColor(getResources().getColor(R.color.dim_grey));
                                }

                                // Select the new chip
                                selectedChip = chip;
                                chip.setChipBackgroundColorResource(R.color.purple_500);
                                chip.setTextColor(getResources().getColor(R.color.white));

                                matchType = chipText;
                            });

                            // Add the chip to the ChipGroup
                            activityScheduleMatchDetailsBinding.chipSpecializationGroupList.addView(chip);
                        }




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

    public void createSchedule(Integer tournament_id,
                               String location,
                               String ground,
                               String ball_type,
                               String pitch_type,
                               String no_of_over,
                               String no_of_over_bowler,
                               Integer team_a,
                               Integer team_b,
                               String start_date_time,
                               String team_a_player_ids,
                               String team_b_player_ids,
                               String matchType) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.addSchedule(SessionManager.getToken(), new CreateScheduleBody(tournament_id,
                location,
                ground,
                ball_type,
                pitch_type,
                no_of_over,
                no_of_over_bowler,
                team_a,
                team_b,
                start_date_time,
                team_a_player_ids,
                team_b_player_ids,
                matchType,
                0
                ));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        boolean status = jsonObject.optBoolean("status", true);
                        if (status) {
                            // If status is true, proceed to the next activity
                            startActivity(new Intent(mContext, ScheduleMatchActivity.class));
                        } else {
                            // If status is false, get and display the error message
                            String message = jsonObject.optString("message", "Unknown error occurred");
                            Toaster.customToast(message);
                            Log.e("Error", "Server Response: " + message);
                        }

                    } catch (Exception e) {
                        Log.e("Error", "JSON parsing error: " + e.getMessage());
                    }
                } else {
                    try {
                        // Extract the error body for non-successful responses
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error occurred";
                        JSONObject errorObject = new JSONObject(errorBody); // Parse error body as JSON
                        String message = errorObject.optString("message", "Unknown error occurred");
                        Toaster.customToast(message);
                        Log.e("Error", "Response error: " + response.code() + ", Message: " + message);
                    } catch (Exception e) {
                        Log.e("Error", "Error handling response: " + e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", "Failed to fetch data: " + t.getMessage());
                Global.hideLoder();
            }
        });
    }

    // Retrieve List for Team A or Team B
    public ArrayList<PlayerModel> retrieveList(String key) {
        //SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        ArrayList<PlayerModel> list = new ArrayList<>();
        String jsonString = sharedPreferences.getString(key, null);

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(new PlayerModel(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MainActivity.class));
        finish();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply(); // or editor.commit();

    }
}