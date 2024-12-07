package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cricoscore.Adapter.SearchPlayerAdapter;
import com.cricoscore.Adapter.YourTeamListAdapter;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.ParamBody.AddPlayerBody;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityAddMatchBinding;
import com.cricoscore.databinding.ActivityAddPlayerBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.model.TeamModel;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlayer extends AppCompatActivity {

    ActivityAddPlayerBinding activityAddPlayerBinding;
    Context mContext;
    Activity mActivity;
    private ArrayList<DataModel> option_player_type = new ArrayList<>();
    private ArrayList<DataModel> option_player_role = new ArrayList<>();
    private ArrayList<DataModel> option_team_list = new ArrayList<>();
    private String filterType = "";
    public static Uri image_uri = null;
    String selectPlayerType = "";
    String selectPlayerRole = "";
    String selectTeam = "";
    int teamId=0;

    private SearchPlayerAdapter userAdapter;
    private List<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddPlayerBinding = ActivityAddPlayerBinding.inflate(getLayoutInflater());
        setContentView(activityAddPlayerBinding.getRoot());

        mActivity = this;
        mContext = this;

        ToolbarBinding toolbarBinding = activityAddPlayerBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.addPlayer));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        if (getIntent() != null) {
            teamId = getIntent().getIntExtra("ID",0);
            Toaster.customToast(teamId+"//");
        }


        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (image_uri != null) {
            activityAddPlayerBinding.profilePic.setImageURI(image_uri);
            //image_uri = null;
        }
    }

    private void initView() {

        // Sample user list
        userList = new ArrayList<>(Arrays.asList("alice", "bob", "charlie", "david", "pe", "kve", "hve", "tve"));


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new SearchPlayerAdapter(userList);
        recyclerView.setAdapter(userAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                boolean hasItems = userAdapter.filter(newText);
                recyclerView.setVisibility(hasItems ? View.VISIBLE : View.GONE);

                activityAddPlayerBinding.liMain.setVisibility(hasItems ? View.VISIBLE : View.GONE);

                userAdapter.filter(newText);
                return true;
            }
        });


        option_player_type.add(new DataModel("Bowler"));
        option_player_type.add(new DataModel("Batsman"));
        option_player_type.add(new DataModel("Wicketkeeper"));
        option_player_type.add(new DataModel("All Rounder"));
        activityAddPlayerBinding.dropPType.setOptionList(option_player_type);
        activityAddPlayerBinding.dropPType.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                selectPlayerType = name;
            }


            @Override
            public void onDismiss() {
            }
        });

        option_player_role.add(new DataModel("Captain"));
        option_player_role.add(new DataModel("Vice Captain"));
        option_player_role.add(new DataModel("Coach"));
        option_player_role.add(new DataModel("Player"));
        option_player_role.add(new DataModel("Commentator"));
        option_player_role.add(new DataModel("Manager"));
        option_player_role.add(new DataModel("Scorer"));
        activityAddPlayerBinding.dropPRole.setOptionList(option_player_role);
        activityAddPlayerBinding.dropPRole.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                selectPlayerRole = name;
            }


            @Override
            public void onDismiss() {
            }
        });


        option_team_list.add(new DataModel("Team A"));
        option_team_list.add(new DataModel("Team B"));
        option_team_list.add(new DataModel("Team C"));
        option_team_list.add(new DataModel("Team D"));
        option_team_list.add(new DataModel("Team E"));
        option_team_list.add(new DataModel("Team CF"));
        activityAddPlayerBinding.dropPAddToTeam.setOptionList(option_team_list);
        activityAddPlayerBinding.dropPAddToTeam.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                selectTeam = name;
            }


            @Override
            public void onDismiss() {
            }
        });


        activityAddPlayerBinding.editTextPName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddPlayerBinding.filledPlayerName.setErrorEnabled(false);
                activityAddPlayerBinding.filledPlayerName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddPlayerBinding.filledPlayerName.setErrorEnabled(false);
                    activityAddPlayerBinding.filledPlayerName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddPlayerBinding.filledPlayerName.setErrorEnabled(false);
                activityAddPlayerBinding.filledPlayerName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityAddPlayerBinding.editTextPName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddPlayerBinding.filledPlayerName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityAddPlayerBinding.editTextPName.getText().length() == 0) {
                    activityAddPlayerBinding.filledPlayerName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddPlayerBinding.filledPlayerName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddPlayerBinding.editTextPMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddPlayerBinding.filledPhoneNumber.setErrorEnabled(false);
                activityAddPlayerBinding.filledPhoneNumber.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddPlayerBinding.filledPhoneNumber.setErrorEnabled(false);
                    activityAddPlayerBinding.filledPhoneNumber.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddPlayerBinding.filledPhoneNumber.setErrorEnabled(false);
                activityAddPlayerBinding.filledPhoneNumber.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityAddPlayerBinding.editTextPMobile.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddPlayerBinding.filledPhoneNumber.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityAddPlayerBinding.editTextPMobile.getText().length() == 0) {
                    activityAddPlayerBinding.filledPhoneNumber.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddPlayerBinding.filledPhoneNumber.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });



        activityAddPlayerBinding.mbSubmit.setOnClickListener(v -> {

            if (activityAddPlayerBinding.editTextPName.getText().toString().isEmpty() ||
                    activityAddPlayerBinding.editTextPName.getText().toString().trim().length() < 3) {

                activityAddPlayerBinding.filledPlayerName.setErrorEnabled(true);
                activityAddPlayerBinding.filledPlayerName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddPlayerBinding.filledPlayerName.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddPlayerBinding.filledPlayerName.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddPlayerBinding.filledPlayerName.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddPlayerBinding.filledPlayerName.setError(getResources().getString(R.string.player_name));

            } else if (activityAddPlayerBinding.editTextPMobile.getText().toString().isEmpty()
                    || activityAddPlayerBinding.editTextPMobile.getText().toString().length() < 10
            ) {
                activityAddPlayerBinding.filledPhoneNumber.setErrorEnabled(true);
                activityAddPlayerBinding.filledPhoneNumber.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddPlayerBinding.filledPhoneNumber.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddPlayerBinding.filledPhoneNumber.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddPlayerBinding.filledPhoneNumber.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddPlayerBinding.filledPhoneNumber.setError(getResources().getString(R.string.phone_msg));

            }
//            else if (selectPlayerType.isEmpty()) {
//                Toaster.customToast("Please select player type!");
//            } else if (selectPlayerRole.isEmpty()) {
//                Toaster.customToast("Please select player role!");
//            } else if (selectTeam.isEmpty()) {
//                Toaster.customToast("Please select team!");
//            } else if (image_uri == null) {
//                Toaster.customToast("Please add profile image!");
//            }
            else {

                if (Global.isOnline(mContext)) {
                    getAddPlayer(activityAddPlayerBinding.editTextPName.getText().toString(),
                            activityAddPlayerBinding.editTextPMobile.getText().toString(),teamId,image_uri);
                } else {
                    Global.showDialog(mActivity);
                }

                //image_uri = null;
            }

        });

        activityAddPlayerBinding.middle.setOnClickListener(view -> {
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM", "AddPlayer"));
        });


    }


    public void getAddPlayer(String name, String phoneNumber,int teamId,Uri playerAvtar) {
        Global.showLoader(getSupportFragmentManager());

        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestPhone = RequestBody.create(MediaType.parse("text/plain"), phoneNumber);

        MultipartBody.Part bodyLogo=null;

        if (playerAvtar != null) {
            File file = new File(playerAvtar.getPath());
            String mimeType = getMimeType(file);
            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);

            // MultipartBody.Part is used to send also the actual file name
            bodyLogo = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

            // Proceed with the rest of your logic, e.g., making a network request with 'body'
        } else {
            // Handle the null case
            Log.e("FileUpload", "URI is null, cannot proceed with file upload.");
        }

        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.addPayer(SessionManager.getToken(), requestName,requestPhone,teamId,bodyLogo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.hideLoder();


                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        Toaster.customToast(jsonObject.getString("message"));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish(); // Optional: finish the current activity
                            }
                        }, 2000); // Delay for 20 seconds

                        image_uri=null;

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

    private String getMimeType(File file) {
        String mimeType = null;
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                mimeType = "image/jpeg";
                break;
            case "png":
                mimeType = "image/png";
                break;
            case "gif":
                mimeType = "image/gif";
                break;
            default:
                mimeType = "application/octet-stream"; // Fallback MIME type
                break;
        }
        return mimeType;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}