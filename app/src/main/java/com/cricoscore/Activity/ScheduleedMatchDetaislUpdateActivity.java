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
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.cricoscore.ParamBody.CreateScheduleBody;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityScheduleMatchBinding;
import com.cricoscore.databinding.ActivityScheduleedMatchDetaislUpdateBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.PlayerModel;
import com.cricoscore.model.ScheduleMatchDetailsItem;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleedMatchDetaislUpdateActivity extends AppCompatActivity {

    ActivityScheduleedMatchDetaislUpdateBinding activityScheduleedMatchDetaislUpdateBinding;
    Context mContext;
    Activity mActivity;

    int scheduleId;

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
    List<ScheduleMatchDetailsItem.PlayerModel> receivedListOne = new ArrayList<>();
    List<ScheduleMatchDetailsItem.PlayerModel> receivedListTwo = new ArrayList<>();
    ArrayList<Integer> teamASelectedPlayerIdList = new ArrayList<>();
    ArrayList<Integer> teamBSelectedPlayerIdList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private ActivityResultLauncher<Intent> teamActivityLauncher;
    int tournamentId;
    String teamName1="",teamName2="";
    String teamNameAFrom ="";
    String teamNameBFrom ="";

    ArrayList<Integer> receivedPlayerList = new ArrayList<>();
    ArrayList<Integer> receivedPlayerListA = new ArrayList<>();
    ArrayList<Integer> receivedPlayerListB = new ArrayList<>();
    ArrayList<PlayerModel> teamAList ;
    ArrayList<PlayerModel> teamBList  ;
    ArrayList<PlayerModel>addDtaList = new ArrayList<>();

    ScheduleMatchDetailsItem scheduleMatchDetailsItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleed_match_detaisl_update);

        activityScheduleedMatchDetaislUpdateBinding = ActivityScheduleedMatchDetaislUpdateBinding.inflate(getLayoutInflater());
        setContentView(activityScheduleedMatchDetaislUpdateBinding.getRoot());

        mContext = this;
        mActivity = this;

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        ToolbarBinding toolbarBinding = activityScheduleedMatchDetaislUpdateBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.schedule_match_details));
        toolbarBinding.toolbar.setNavigationOnClickListener(v ->
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    finish();
                }
        );





        activityScheduleedMatchDetaislUpdateBinding.chipSpecializationGroupList.removeAllViews();

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


            activityScheduleedMatchDetaislUpdateBinding.chipSpecializationGroupList.addView(chip);
        }


        activityScheduleedMatchDetaislUpdateBinding.chipSpecializationGroupListPitchType.removeAllViews();

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


            activityScheduleedMatchDetaislUpdateBinding.chipSpecializationGroupListPitchType.addView(chip);
        }

        activityScheduleedMatchDetaislUpdateBinding.rlTennis.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityScheduleedMatchDetaislUpdateBinding.rlTennis.setForeground(getResources().getDrawable(R.drawable.circle_red));
                activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setForeground(null);
                activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setForeground(null);
                activityScheduleedMatchDetaislUpdateBinding.rlOther.setForeground(null);
            }

            activityScheduleedMatchDetaislUpdateBinding.tvTennis.setTextColor(getResources().getColor(R.color.purple_500));
            activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));

            ballType = activityScheduleedMatchDetaislUpdateBinding.tvTennis.getText().toString();

        });
        activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setForeground(getResources().getDrawable(R.drawable.circle_red));

                activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setForeground(null);
                activityScheduleedMatchDetaislUpdateBinding.rlTennis.setForeground(null);
                activityScheduleedMatchDetaislUpdateBinding.rlOther.setForeground(null);
            }

            activityScheduleedMatchDetaislUpdateBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvRedLether.setTextColor(getResources().getColor(R.color.purple_500));
            activityScheduleedMatchDetaislUpdateBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));

            ballType = activityScheduleedMatchDetaislUpdateBinding.tvRedLether.getText().toString();

        });
        activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setForeground(getResources().getDrawable(R.drawable.circle_red));
                activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setForeground(null);
                activityScheduleedMatchDetaislUpdateBinding.rlTennis.setForeground(null);
                activityScheduleedMatchDetaislUpdateBinding.rlOther.setForeground(null);
            }

            activityScheduleedMatchDetaislUpdateBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.purple_500));
            activityScheduleedMatchDetaislUpdateBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));

            ballType = activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.getText().toString();

        });
        activityScheduleedMatchDetaislUpdateBinding.rlOther.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityScheduleedMatchDetaislUpdateBinding.rlOther.setForeground(getResources().getDrawable(R.drawable.circle_red));
                activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setForeground(null);
                activityScheduleedMatchDetaislUpdateBinding.rlTennis.setForeground(null);
                activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setForeground(null);
            }

            activityScheduleedMatchDetaislUpdateBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
            activityScheduleedMatchDetaislUpdateBinding.tvOthers.setTextColor(getResources().getColor(R.color.purple_500));

            ballType = activityScheduleedMatchDetaislUpdateBinding.tvOthers.getText().toString();

        });

        activityScheduleedMatchDetaislUpdateBinding.editTextNoOfOver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    int finalValue = Integer.parseInt(s.toString()) / 5;
                    activityScheduleedMatchDetaislUpdateBinding.editTextOverPerBowler.setText(finalValue + "");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityScheduleedMatchDetaislUpdateBinding.edittextDateTime.setOnClickListener(v -> {
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
                    activityScheduleedMatchDetaislUpdateBinding.edittextDateTime.setText(formattedDateTime);

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



        // Launch TeamPlayerlistActivity for Team A
        activityScheduleedMatchDetaislUpdateBinding.liSquadTeamOne.setOnClickListener(v -> {
            //receivedPlayerList = receivedPlayerListA;

            Intent intent1 = new Intent(mContext, FinalScheduledTeamPlayerListActivity.class);
            intent1.putExtra("From", teamName1);
            intent1.putExtra("ID", teamId1);
            intent1.putExtra("PlayerList", scheduleId); // Sending list for Team A
            intent1.putExtra("List",receivedPlayerListA);// Sending list for Team B
            startActivity(intent1);
        });

// Launch TeamPlayerlistActivity for Team B
        activityScheduleedMatchDetaislUpdateBinding.liSquadTeamTwo.setOnClickListener(v -> {
            //receivedPlayerList = receivedPlayerListB;

            Intent intent2 = new Intent(mContext, FinalScheduledTeamPlayerListActivity.class);
            intent2.putExtra("Fromm", teamName2);
            intent2.putExtra("ID", teamId2);
            intent2.putExtra("PlayerList", scheduleId);
            intent2.putExtra("List",receivedPlayerListB);// Sending list for Team B
            startActivity(intent2);
        });
        activityScheduleedMatchDetaislUpdateBinding.rlProfileTeamOne.setOnClickListener(v -> {

        });

        activityScheduleedMatchDetaislUpdateBinding.rlProfileTeamTwo.setOnClickListener(v -> {

        });

        activityScheduleedMatchDetaislUpdateBinding.tvUpdateMatch.setOnClickListener(v -> {


            if (activityScheduleedMatchDetaislUpdateBinding.tvNoOfSquad1.getText().toString().equalsIgnoreCase("(0)")) {
                Toaster.customToast("Please add at least 2 squad in the " + teamName1);
            } else if (activityScheduleedMatchDetaislUpdateBinding.tvNoOfSquadTeamTwo.getText().toString().equalsIgnoreCase("(0)")) {
                Toaster.customToast("Please add at least 2 squad in the " + teamName2);
            }else if(matchType.isEmpty()){
                Toaster.customToast("Please Select Match Type !");
            }else if(activityScheduleedMatchDetaislUpdateBinding.editTextNoOfOver.getText().toString().isEmpty()){
                Toaster.customToast("Please Add No Of Overs !");
            } else if(activityScheduleedMatchDetaislUpdateBinding.editTextOverPerBowler.getText().toString().isEmpty()){
                Toaster.customToast("Please Add Over Per Bowler !");
            }else if(activityScheduleedMatchDetaislUpdateBinding.edittextCity.getText().toString().isEmpty()){
                Toaster.customToast("Please Type City or Town");
            }else if(activityScheduleedMatchDetaislUpdateBinding.editTextGround.getText().toString().isEmpty()){
                Toaster.customToast("Please Type Ground Name !");
            } else if(activityScheduleedMatchDetaislUpdateBinding.edittextDateTime.getText().toString().isEmpty()){
                Toaster.customToast("Please Add Date and Time !");
            }else if(ballType.isEmpty()){
                Toaster.customToast("Please Select Ball Type !");
            }else if(pitch_type.isEmpty()){
                Toaster.customToast("Please Select Pitch Type !");
            }else {

                /*Create Schedule Api Calling...*/

                if (Global.isOnline(mContext)) {
                    createSchedule(tournamentId,activityScheduleedMatchDetaislUpdateBinding.edittextCity.getText().toString(),
                            activityScheduleedMatchDetaislUpdateBinding.editTextGround.getText().toString(),
                            ballType,pitch_type,
                            activityScheduleedMatchDetaislUpdateBinding.editTextNoOfOver.getText().toString(),
                            activityScheduleedMatchDetaislUpdateBinding.editTextOverPerBowler.getText().toString(),
                            teamId1,
                            teamId2,
                            activityScheduleedMatchDetaislUpdateBinding.edittextDateTime.getText().toString(),
                            teamOnePlayerId,
                            teamTwoPlayerId,
                            matchType,
                            scheduleId);
                } else {
                    Global.showDialog(mActivity);
                }
            }
        });

        activityScheduleedMatchDetaislUpdateBinding.tvNextToss.setOnClickListener(v -> {

            if(receivedPlayerListA.size()<2){
                Toaster.customToast(teamName1+" has less than 5 member in the playing squad.");
            }else if( receivedPlayerListB.size()<2){
                Toaster.customToast(teamName2+" has less than 5 member in the playing squad.");
            }
            else{
                startActivity(new Intent(mContext,TossActivity.class).putExtra("TeamAName",teamName1)
                        .putExtra("TeamBName",teamName2)
                        .putExtra("TeamAId",teamId1)
                        .putExtra("TeamBId",teamId2)
                        .putExtra("ScheduleId",scheduleId));
            }


        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null) {
            scheduleId = getIntent().getIntExtra("ID",0);
            teamNameAFrom = getIntent().getStringExtra("TeamA");
            teamNameBFrom = getIntent().getStringExtra("TeamB");

          //  Toaster.customToast(teamNameAFrom+"/"+teamNameBFrom);
        }

        if (Global.isOnline(mContext)) {
            getScheduleMatchDetails(scheduleId);
        } else {
            Global.showDialog(mActivity);
        }

    }

    public void getScheduleMatchDetails(int scheduledId) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getScheduleMatchDetails(SessionManager.getToken(), scheduledId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                        //Log.d("Response",jsonObject.toString());

                        scheduleMatchDetailsItem = new ScheduleMatchDetailsItem(jsonObject1);

                        tournamentId = scheduleMatchDetailsItem.getTournamentId();
                        teamName1 = scheduleMatchDetailsItem.getTeamAInfo().getName();
                        teamName2 = scheduleMatchDetailsItem.getTeamBInfo().getName();
                        teamId1 = scheduleMatchDetailsItem.getTeamAInfo().getTeamId();
                        teamId2 = scheduleMatchDetailsItem.getTeamBInfo().getTeamId();

                        receivedListOne = scheduleMatchDetailsItem.getTeamAInfo().getPlayers();
                        receivedListTwo = scheduleMatchDetailsItem.getTeamBInfo().getPlayers();


                        receivedPlayerListA.clear();
                        receivedPlayerListB.clear();

                        for(int i = 0;i<scheduleMatchDetailsItem.getSelectedTeamAPlayer().size();i++){
                            receivedPlayerListA.add(scheduleMatchDetailsItem.getSelectedTeamAPlayer().get(i).getPlayerId());
                        }

                        for(int i = 0;i<scheduleMatchDetailsItem.getSelectedTeamBPlayer().size();i++){
                            receivedPlayerListB.add(scheduleMatchDetailsItem.getSelectedTeamBPlayer().get(i).getPlayerId());
                        }



                        activityScheduleedMatchDetaislUpdateBinding.tvTeamName.setText(scheduleMatchDetailsItem.getTeamAInfo().getName());

                        Glide.with(mContext).load(Global.BASE_URL + "/" + scheduleMatchDetailsItem.getTeamAInfo().getTeamLogo()).into(activityScheduleedMatchDetaislUpdateBinding.imgFirstTeamLogo);

                        activityScheduleedMatchDetaislUpdateBinding.tvTNameTeamTwo.setText(scheduleMatchDetailsItem.getTeamBInfo().getName());

                        Glide.with(mContext).load(Global.BASE_URL + "/" + scheduleMatchDetailsItem.getTeamBInfo().getTeamLogo()).into(activityScheduleedMatchDetaislUpdateBinding.imgTeamTwoLogo);

                        activityScheduleedMatchDetaislUpdateBinding.editTextNoOfOver.setText(scheduleMatchDetailsItem.getNoOfOver()+"");
                        activityScheduleedMatchDetaislUpdateBinding.editTextOverPerBowler.setText(scheduleMatchDetailsItem.getNoOfOverBowler()+"");
                        activityScheduleedMatchDetaislUpdateBinding.edittextCity.setText(scheduleMatchDetailsItem.getLocation());
                        activityScheduleedMatchDetaislUpdateBinding.editTextGround.setText(scheduleMatchDetailsItem.getGround());
                        activityScheduleedMatchDetaislUpdateBinding.edittextDateTime.setText(scheduleMatchDetailsItem.getStartDateTime());

                        String valuesA = scheduleMatchDetailsItem.getTeamAPlayerIds();
                        teamOnePlayerId = scheduleMatchDetailsItem.getTeamAPlayerIds();
                        teamASelectedPlayerIdList.clear();
                        for (String value : valuesA.split(",")) {
                            teamASelectedPlayerIdList.add(Integer.parseInt(value.trim()));
                        }

                        String valuesB = scheduleMatchDetailsItem.getTeamBPlayerIds();
                        teamTwoPlayerId = scheduleMatchDetailsItem.getTeamBPlayerIds();
                        teamBSelectedPlayerIdList.clear();
                        for (String value : valuesB.split(",")) {
                            teamBSelectedPlayerIdList.add(Integer.parseInt(value.trim()));
                        }

                        activityScheduleedMatchDetaislUpdateBinding.tvNoOfSquad1.setText(String.valueOf(teamASelectedPlayerIdList.size())); // For Team A
                        activityScheduleedMatchDetaislUpdateBinding.tvNoOfSquadTeamTwo.setText(String.valueOf(teamBSelectedPlayerIdList.size())); // For Team B

                        ballType = scheduleMatchDetailsItem.getBallType();

                        if(ballType.equalsIgnoreCase(activityScheduleedMatchDetaislUpdateBinding.tvTennis.getText().toString())){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                activityScheduleedMatchDetaislUpdateBinding.rlTennis.setForeground(getResources().getDrawable(R.drawable.circle_red));
                                activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setForeground(null);
                                activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setForeground(null);
                                activityScheduleedMatchDetaislUpdateBinding.rlOther.setForeground(null);
                            }

                            activityScheduleedMatchDetaislUpdateBinding.tvTennis.setTextColor(getResources().getColor(R.color.purple_500));
                            activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));
                        }else if(ballType.equalsIgnoreCase(activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.getText().toString())){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setForeground(getResources().getDrawable(R.drawable.circle_red));
                                activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setForeground(null);
                                activityScheduleedMatchDetaislUpdateBinding.rlTennis.setForeground(null);
                                activityScheduleedMatchDetaislUpdateBinding.rlOther.setForeground(null);
                            }

                            activityScheduleedMatchDetaislUpdateBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.purple_500));
                            activityScheduleedMatchDetaislUpdateBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));
                        }else if(ballType.equalsIgnoreCase(activityScheduleedMatchDetaislUpdateBinding.tvRedLether.getText().toString())){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setForeground(getResources().getDrawable(R.drawable.circle_red));

                                activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setForeground(null);
                                activityScheduleedMatchDetaislUpdateBinding.rlTennis.setForeground(null);
                                activityScheduleedMatchDetaislUpdateBinding.rlOther.setForeground(null);
                            }

                            activityScheduleedMatchDetaislUpdateBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvRedLether.setTextColor(getResources().getColor(R.color.purple_500));
                            activityScheduleedMatchDetaislUpdateBinding.tvOthers.setTextColor(getResources().getColor(R.color.black));
                        }else if(ballType.equalsIgnoreCase(activityScheduleedMatchDetaislUpdateBinding.tvOthers.getText().toString())){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                activityScheduleedMatchDetaislUpdateBinding.rlOther.setForeground(getResources().getDrawable(R.drawable.circle_red));
                                activityScheduleedMatchDetaislUpdateBinding.rlRedLeather.setForeground(null);
                                activityScheduleedMatchDetaislUpdateBinding.rlTennis.setForeground(null);
                                activityScheduleedMatchDetaislUpdateBinding.rlWhiteLether.setForeground(null);
                            }

                            activityScheduleedMatchDetaislUpdateBinding.tvTennis.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvWhiteLether.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvRedLether.setTextColor(getResources().getColor(R.color.black));
                            activityScheduleedMatchDetaislUpdateBinding.tvOthers.setTextColor(getResources().getColor(R.color.purple_500));
                        }


                        pitch_type = scheduleMatchDetailsItem.getPitchType();


                        activityScheduleedMatchDetaislUpdateBinding.chipSpecializationGroupListPitchType.removeAllViews();
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
                            activityScheduleedMatchDetaislUpdateBinding.chipSpecializationGroupListPitchType.addView(chip);
                        }

                        activityScheduleedMatchDetaislUpdateBinding.chipSpecializationGroupList.removeAllViews();
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
                            activityScheduleedMatchDetaislUpdateBinding.chipSpecializationGroupList.addView(chip);
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
                               String matchType,
                               int scheduleId) {
        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.updateSchedule(SessionManager.getToken(), new CreateScheduleBody(tournament_id,
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
                scheduleId
        ));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(mContext,ScheduleMatchActivity.class));


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