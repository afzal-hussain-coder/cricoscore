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
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cricoscore.Adapter.YourTeamListAdapterHorizontal;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityTournamentDetailsBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.TeamModel;
import com.cricoscore.model.TournamentModel.TournamentDetails;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    String teamName = "";
    String tournamentId = "";
    String tournamentName = "";
    String formatedStartDate = "";
    String formatedEndDate = "";
    String location = "";
    int teamId;
    ArrayList<TeamModel>teamModelArrayList = new ArrayList<>();
    // Variables to store selected team details
    private int team1Id = -1;
    private String teamNameSelected1 = "";
    private String teamLogoSelected1 = "";

    private int team2Id = -1;
    private String teamNameSelected2 = "";
    private String teamLogoSelected2 = "";

    private int posClick = 0;
    List<TeamModel> teamIdListt = new ArrayList<>();
    ImageView img_add;

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
        img_add = toolbarBinding.imgAdd;


        /*get tournament id from another activity through intent*/
        if (getIntent() != null) {
            tournamentId = getIntent().getStringExtra("id");
        }

        if (Global.isOnline(mContext)) {
            getTournamentDetails(tournamentId);
        } else {
            Global.showDialog(mActivity);
        }


        toolbarBinding.toolbartext.setText(tournamentName);
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        img_add.setVisibility(View.VISIBLE);

        activityTournamentDetailsBinding.tvInfo.startAnimation((Animation) AnimationUtils.loadAnimation(mContext, R.anim.translate));

        activityTournamentDetailsBinding.rvTeamList.setLayoutManager(new LinearLayoutManager(mContext));
        activityTournamentDetailsBinding.rvTeamList.setHasFixedSize(true);

        activityTournamentDetailsBinding.mbScheduleMatch.setOnClickListener(v -> {
            showBottomSheetDialog();
        });

        img_add.setOnClickListener(v -> {
            startActivity(new Intent(mContext,YourTeamListActivity.class).putExtra("Id",tournamentId));
        });

        activityTournamentDetailsBinding.liSchedule.setOnClickListener(v -> {

           // if(teamModelArrayList.isEmpty()){
             //   startActivity(new Intent(mContext,YourTeamListActivity.class).putExtra("Id",tournamentId));
          // }else{
                if (team1Id != -1 && team2Id != -1 && teamIdListt.size()==2) {
                    // If both teams are selected, proceed to the next activity
                    Intent intent = new Intent(mContext, ScheduleMatchDetailsActivity.class);
                    intent.putExtra("teamName1", teamNameSelected1);
                    intent.putExtra("teamId1", team1Id);
                    intent.putExtra("teamLogo1", teamLogoSelected1);
                    intent.putExtra("teamName2", teamNameSelected2);
                    intent.putExtra("teamId2", team2Id);
                    intent.putExtra("teamLogo2", teamLogoSelected2);
                    intent.putExtra("id",tournamentId);
                    //intent.putExtra("TeamA",);
                    //intent.putExtra("TeamB",);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Please select two teams before submitting", Toast.LENGTH_SHORT).show();
//                    if(activityTournamentDetailsBinding.tvTextSchedule.getText().toString().equalsIgnoreCase("Add Team")){
//                        startActivity(new Intent(mContext,YourTeamListActivity.class).putExtra("Id",tournamentId)
//                        );
//                    }else{
//                        Toast.makeText(mContext, "Please select two teams before submitting", Toast.LENGTH_SHORT).show();
//                    }

                    // Notify the user to select two teams before proceeding

                }
           // }




//            if(position == 2){
//                startActivity(new Intent(mContext, ScheduleMatchDetailsActivity.class).putExtra("id",tournamentId));
//                //finish();
//                //showBottomSheetDialog();
//            }else{
//                Toaster.customToast("Select two team to create a schedule");
//            }

        });
    }

    /*show dialog to select schedule date and time to create schedule*/

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
                tvSdate.setText(Global.convertUTCDateToMM(date));
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
                tvSdate.setText(Global.convertUTCDateToMM(date));
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
                updateTime(hour, min, tv_start_time);

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


                updateTime(hour, min, tv_end_time);

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

            startActivity(new Intent(mContext, ScheduleMatchActivity.class));
            finish();
            bottomSheetDialog.hide();
        });


        bottomSheetDialog.show();
    }

    /*Update Time dialog to set time from device*/

    private void updateTime(int hours, int mins, TextView tv_time) {
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

    /*Here we call get tournament details Api*/

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
                        location = finalData.getString("location");
                        activityTournamentDetailsBinding.tvtLocation.setText(location);

                        JSONArray jsonArray = finalData.getJSONArray("teams");
                        for(int i=0;i<jsonArray.length();i++){
                            teamModelArrayList.add(new TeamModel(jsonArray.getJSONObject(i)));
                        }

                        if(teamModelArrayList.isEmpty() || teamModelArrayList.size()<2){
                            activityTournamentDetailsBinding.liSchedule.setVisibility(View.GONE);
                        }else{
                            activityTournamentDetailsBinding.liSchedule.setVisibility(View.VISIBLE);
                            activityTournamentDetailsBinding.tvTextSchedule.setText(mContext.getResources().getString(R.string.submit));
                        }


                        yourTeamListAdapter = new YourTeamListAdapterHorizontal(mContext, teamModelArrayList, new YourTeamListAdapterHorizontal.itemClickListener() {

                            @Override
                            public void checkedItem(int pos,  List<TeamModel> teamIdList) {
                                position = pos;
                                teamIdListt = teamIdList;
                                //Toaster.customToast(teamIdList.size()+"/");
                                if (pos == 1) {
                                    team1Id = teamIdList.get(0).getTeam_id();
                                    teamNameSelected1 = teamIdList.get(0).getName();
                                    teamLogoSelected1 = teamIdList.get(0).getTeam_logo();

                                    Log.d("TeamSelection", "Team 1 selected: " + team1Id);
                                } else if (pos == 2) {
                                    team2Id = teamIdList.get(1).getTeam_id();
                                    teamNameSelected2 =  teamIdList.get(1).getName();
                                    teamLogoSelected2 = teamIdList.get(1).getTeam_logo();
                                    Log.d("TeamSelection", "Team 2 selected: " + team2Id);
                                }


                            }

                            @Override
                            public void itemRemove(int id, int pos) {
                                if (Global.isOnline(mContext)) {
                                    removeTeamFromList(id,pos,tournamentId);
                                } else {
                                    Global.showDialog(mActivity);
                                }
                            }
                        });




                        activityTournamentDetailsBinding.rvTeamList.setAdapter(yourTeamListAdapter);

                        TournamentDetails tournamentDetails = new TournamentDetails(finalData);
                        setData(tournamentDetails);

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


    /* This method is called to onBackPressed for back to the previous activity*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    /* this method is used to set data to the activity views*/
    public void setData(TournamentDetails tournamentDetails) {

        activityTournamentDetailsBinding.tvTName.setText(tournamentDetails.getName());
        Glide.with(mContext).load(Global.BASE_URL + "/" + tournamentDetails.getTournament_logo()).into(activityTournamentDetailsBinding.image);
        Glide.with(mContext).load(Global.BASE_URL + "/" + tournamentDetails.getTournament_banner()).into(activityTournamentDetailsBinding.imgBanner);

        ZonedDateTime zonedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            zonedDateTime = ZonedDateTime.parse(tournamentDetails.getStart_date());
            // Define the output format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Format the date to the desired output
            formatedStartDate = zonedDateTime.format(formatter);

        }
        ZonedDateTime zonedDateTimee = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            zonedDateTimee = ZonedDateTime.parse(tournamentDetails.getEnd_date());
            // Define the output format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Format the date to the desired output
            formatedEndDate = zonedDateTimee.format(formatter);

        }

        activityTournamentDetailsBinding.tvdate.setText(formatedStartDate + " " +
                "to " + formatedEndDate);


        activityTournamentDetailsBinding.tvTournamentType.setText(tournamentDetails.getType());
        activityTournamentDetailsBinding.tvBallType.setText(tournamentDetails.getBall_type());
        activityTournamentDetailsBinding.tvFees.setText(tournamentDetails.getFees() + "");
        activityTournamentDetailsBinding.tvPrize.setText(tournamentDetails.getPrize());
        activityTournamentDetailsBinding.tvDiscount.setText(tournamentDetails.getDiscount() + "");
        activityTournamentDetailsBinding.tvNoOfTeam.setText(tournamentDetails.getNo_of_team());
        activityTournamentDetailsBinding.tvSponser.setText(tournamentDetails.getSponser());



//        yourTeamListAdapter = new YourTeamListAdapterHorizontal(mContext, new ArrayList<>(), (pos, string) -> {
//            position = pos;
//            teamName = string;
//            if (position == 2) {
//                activityTournamentDetailsBinding.mbScheduleMatch.setVisibility(View.VISIBLE);
//            } else {
//                activityTournamentDetailsBinding.mbScheduleMatch.setVisibility(View.GONE);
//            }
//        });
//        activityTournamentDetailsBinding.rvTeamList.setAdapter(yourTeamListAdapter);


    }

    public String convertDateFormat(String date) {
        // Input date string
        String inputDate = "2024-01-31T18:30:00.000Z";
        String formattedDate = "";

        // Parse the input date
        ZonedDateTime zonedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            zonedDateTime = ZonedDateTime.parse(date);
            // Define the output format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Format the date to the desired output
            formattedDate = zonedDateTime.format(formatter);
        }

        Log.d("formateddate", formattedDate);
        return formattedDate;

    }

    /*This Method is used to remove item from list using Api*/

    public void removeTeamFromList(int teamId,int pos,String tournamentId) {

        Global.showLoader(getSupportFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.removeTeamFromTournament(SessionManager.getToken(), teamId,Integer.parseInt(tournamentId));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject
                        JSONObject removedJsonObjectData = jsonObject.getJSONObject("data");

                        if(removedJsonObjectData.length()<2){
                            activityTournamentDetailsBinding.liSchedule.setVisibility(View.GONE);
                        }else{
                            activityTournamentDetailsBinding.liSchedule.setVisibility(View.VISIBLE);
                            activityTournamentDetailsBinding.tvTextSchedule.setText(mContext.getResources().getString(R.string.submit));
                        }

                        yourTeamListAdapter.deleteItem(pos);

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
}