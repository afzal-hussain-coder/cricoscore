package com.cricoscore.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cricoscore.Activity.DashboardLiveScoringActivity;
import com.cricoscore.Activity.ScheduleedMatchDetaislUpdateActivity;
import com.cricoscore.Activity.ScorecardActivity;
import com.cricoscore.Activity.ScoringDashBordActivity;
import com.cricoscore.Activity.StartInningActivity;
import com.cricoscore.Activity.YourMatchTeamListActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.Adapter.MatchAdapter;
import com.cricoscore.Adapter.ScheduleMatchAdapter;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.ScheduleMatchDetailsItem;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.utilities.Score;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchFragment extends Fragment {

    RecyclerView rv_home;
    CircleImageView iv_team_logo;
    public static Uri image_uri=null;
    ScheduleMatchAdapter scheduleMatchAdapter;
    int bowling_team_id = 0;
    int batting_team_id = 0;
    int inning_id = 0;
    int scheduleId = 0;
    int inning_no = 0;
    String is_match_status;
    String FROM = "";
    String radioSelectionText = "";

    public MatchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_home = view.findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_home.setHasFixedSize(true);

        if(Global.isOnline(getContext())){
            getScheduleMatchList();
        }else{
            Global.hideLoder();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(image_uri!=null){
            iv_team_logo.setImageURI(image_uri);
            image_uri = null;
        }
    }

    public List<Match> getMatchList(){
        List<Match> tList = new ArrayList<>();
        tList.add(new Match(Color.parseColor("#c8f5ae"),
                Color.parseColor("#c8f5ae"),"First-class Match","Inderjit Singh Bindra Stadium",
                "30-Mar-23 to 31-Sep-23"));
        tList.add(new Match(Color.parseColor("#d7e09b"),
                Color.parseColor("#d7e09b"),"One day Match","Dr. Y. S. Rajasekhara Reddy International Cricket Stadium",
                "01-Apr-23 to 22-july-23"));
        tList.add(new Match(Color.parseColor("#F1DB9C"),
                Color.parseColor("#F1DB9C"),"Twenty 20 (T20)","Rajiv Gandhi International Cricket Stadium",
                "9-Jan-23 to 31-Mar-23"));
        tList.add(new Match(Color.parseColor("#F4CEC8"),
                Color.parseColor("#F4CEC8"),"One Day Internationals","Vidarbha Cricket Association Stadium",
                "5-Feb-23 to 23-May-23"));
        tList.add(new Match(Color.parseColor("#E6C2EF"),
                Color.parseColor("#E6C2EF"),"Twenty 20 Internationals","Arun Jaitley Cricket Stadium","16-Nov-23 to 27-Dec-23"
        ));
        return tList;
    }
    public class Match{
        public int getBanner() {
            return banner;
        }

        public void setBanner(int banner) {
            this.banner = banner;
        }

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

        int banner;
        int logo;
        String name="";
        String address="";

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        String date="";


        public Match(int banner, int logo, String name, String address,String date) {
            this.banner = banner;
            this.logo = logo;
            this.name = name;
            this.address = address;
            this.date = date;
        }
    }


    public void getScheduleMatchList() {
        Global.showLoader(getParentFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getScheduleMatchList(SessionManager.getToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        JSONArray finalData = jsonObject.getJSONArray("data");

                        ArrayList<ScheduleMatchDetailsItem> list = new ArrayList<>();
                        for (int i = 0; i < finalData.length(); i++) {
                            list.add(new ScheduleMatchDetailsItem(finalData.getJSONObject(i)));
                        }

                        scheduleMatchAdapter = new ScheduleMatchAdapter(getActivity(), list, new ScheduleMatchAdapter.getImageCallListener() {
                            @Override
                            public void getDetails(int tournamentId, int id, String teamA, String teamB, ScheduleMatchDetailsItem jsonObject, int inningNo) {
                                //if(inningNo==2){

                                if (Global.isOnline(getContext())) {
                                    getTournamentDetails(tournamentId, id, teamA, teamB);
                                } else {
                                    Global.showDialog(getActivity());
                                }
                                //}else{
//                                    startActivity(new Intent(mContext,ScheduleedMatchDetaislUpdateActivity.class).putExtra("ID",id)
//                                            .putExtra("TeamA",teamA)
//                                            .putExtra("TeamB",teamB));
                                // }
                            }


                        });
                        rv_home.setAdapter(scheduleMatchAdapter);
                        Log.d("FinalResponse", finalData.length() + "");


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


    public void getTournamentDetails(int tournamentId, int id, String teamA, String teamB) {
        Global.showLoader(getParentFragmentManager());
        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getScheduleMatchDetails(SessionManager.getToken(), tournamentId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();

                if (response.isSuccessful() && response.body() != null) {
                    try {

                        // Convert ResponseBody to String
                        String responseBodyString = response.body().string();
                        Log.d("GetDetails", responseBodyString);
                        // Parse the string to a JSONObject
                        JSONObject jsonObject = new JSONObject(responseBodyString);

                        // Check if the status is true
                        boolean status = jsonObject.optBoolean("status", false);
                        String message = jsonObject.optString("message", "No message");

                        if (status) {
                            // Extract "data" object if needed
                            // Example: Get schedule_match_id from "data"
                            // Extract "data" object if needed
                            JSONObject dataObject = jsonObject.optJSONObject("data");
                            inning_no = dataObject.getInt("inning_no");
                            inning_id = dataObject.getInt("inning_id");
                            is_match_status = dataObject.getString("is_match_status");

                            //Toaster.customToast("inn"+inning_no+"/"+inning_id);

                            if (dataObject != null) {
                                if (is_match_status.equalsIgnoreCase("1")  && inning_id > 0) {
                                    showBottomSheetResumeScoringDialog();
                                } else if (inning_no == 2) {

                                    // Convert JSONObject to String
                                    String dataString = dataObject.toString();

                                    // Create an Intent to StartInningActivity
                                    Intent intent = new Intent(getContext(), StartInningActivity.class);

                                    // Pass the JSON data as a string extra
                                    intent.putExtra("dataObject", dataString);

                                    // Start the activity
                                    startActivity(intent);
                                } else {

                                    startActivity(new Intent(getContext(), ScheduleedMatchDetaislUpdateActivity.class).putExtra("ID", id)
                                            .putExtra("TeamA", teamA)
                                            .putExtra("TeamB", teamB));
                                }

                            } else {
                                Toaster.customToast("Toss result saved successfully!");
                                // startActivity(new Intent(mContext, StartInningActivity.class));
                            }

                        } else {
                            Toaster.customToast(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toaster.customToast("Failed to parse response!");
                    }
                } else {
                    Toaster.customToast("Failed to save toss result!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", "Failed to fetch data: " + t.getMessage());
                Global.hideLoder();
            }
        });
    }

    private void showBottomSheetResumeScoringDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_resume_scoring_area);

        RadioButton rb_resumeScoring = bottomSheetDialog.findViewById(R.id.rb_resumeScoring);
        RadioButton rb_startStreaming = bottomSheetDialog.findViewById(R.id.rb_startStreaming);
        RadioButton rbViewFullScorecard = bottomSheetDialog.findViewById(R.id.rbViewFullScorecard);


        rb_resumeScoring.setOnCheckedChangeListener((compoundButton, b) -> {
            rb_startStreaming.setChecked(false);
            rbViewFullScorecard.setChecked(false);
            radioSelectionText = rb_resumeScoring.getText().toString().trim();

        });
//        rb_startStreaming.setOnCheckedChangeListener((compoundButton, b) -> {
//            rbViewFullScorecard.setChecked(false);
//            rb_resumeScoring.setChecked(false);
//            radioSelectionText = rb_startStreaming.getText().toString().trim();
//        });
        rbViewFullScorecard.setOnCheckedChangeListener((compoundButton, b) -> {
            rb_startStreaming.setChecked(false);
            rb_resumeScoring.setChecked(false);
            radioSelectionText = rbViewFullScorecard.getText().toString().trim();
        });


        bottomSheetDialog.findViewById(R.id.mb_save).setOnClickListener(v -> {
            if (radioSelectionText.equalsIgnoreCase(getContext().getResources().getString(R.string.resume_scoring))) {

                startActivity(new Intent(getContext(), ScoringDashBordActivity.class).putExtra("inning_id", inning_id));
                requireActivity().finish();

            } else if(radioSelectionText.equalsIgnoreCase(getContext().getResources().getString(R.string.view_full_scorecard))) {
                  startActivity(new Intent(getContext(), ScorecardActivity.class).putExtra("FROM","SCORECARD"));
                 requireActivity().finish();
            }


            bottomSheetDialog.hide();
        });
        bottomSheetDialog.findViewById(R.id.mb_cancel).setOnClickListener(v -> {

            bottomSheetDialog.hide();
        });

        bottomSheetDialog.show();
    }


    public void showBottomSheetDialog(Uri image_uri) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.add_team_bottom_dialog);

        RelativeLayout rl_team_logo = bottomSheetDialog.findViewById(R.id.rl_team_logo);
        TextView tv_select_team_from_list = bottomSheetDialog.findViewById(R.id.tv_select_team_from_list);
        iv_team_logo = bottomSheetDialog.findViewById(R.id.iv_team_logo);
        TextInputEditText edit_text_teamName = bottomSheetDialog.findViewById(R.id.edit_text_teamName);
        TextInputEditText edit_text_city = bottomSheetDialog.findViewById(R.id.edit_text_city);
        MaterialButton mb_submit = bottomSheetDialog.findViewById(R.id.mb_submit);

        rl_team_logo.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getActivity(), CustomeCameraActivity.class)
                    .putExtra("FROM","MatchFragment"));
        });


        tv_select_team_from_list.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getActivity(), YourMatchTeamListActivity.class));
            bottomSheetDialog.dismiss();
        });

        mb_submit.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();

    }

}