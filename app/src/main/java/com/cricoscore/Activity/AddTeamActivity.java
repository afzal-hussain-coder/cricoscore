package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityAddTeamBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.view_model.AddTeamViewMode;
import com.cricoscore.view_model.AddTournamentViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class AddTeamActivity extends AppCompatActivity {

    ActivityAddTeamBinding activityAddTeamBinding;
    Context mContext;
    Activity mActivity;
    SelectTournamentType drop_tournamentName, drop_matchName;
    String tournamentType = "";
    private ArrayList<DataModel> option_tournament_list = new ArrayList<>();
    private ArrayList<DataModel> option_match_list = new ArrayList<>();
    String matchType = "";
    public static Uri image_uri = null;

    AddTeamViewMode addTeamViewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAddTeamBinding = ActivityAddTeamBinding.inflate(getLayoutInflater());
        setContentView(activityAddTeamBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityAddTeamBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.add_team));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        /**
         * @AddTeam Result
         * @s --> Check result
         */

        addTeamViewMode = new ViewModelProvider(this).get(AddTeamViewMode.class);

        addTeamViewMode.getAddTournamentResult().observe(this, responseStatus -> {
            if (responseStatus.isStatus()) {
                new Handler().postDelayed(() -> {
                    Toaster.customToast(responseStatus.getMessage());
                }, 100);

            }
        });

        addTeamViewMode.getSubmitProfileProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });


        drop_matchName = activityAddTeamBinding.dropMatchName;
        option_match_list.add(new DataModel("Match A"));
        option_match_list.add(new DataModel("Match B"));
        option_match_list.add(new DataModel("Match C"));
        option_match_list.add(new DataModel("Match D"));
        drop_matchName.setOptionList(option_match_list);
        drop_matchName.setClickListener(new SelectTournamentType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                matchType = name;
            }


            @Override
            public void onDismiss() {
            }
        });

        drop_tournamentName = activityAddTeamBinding.dropTournamentName;
        option_tournament_list.add(new DataModel("Tournament A"));
        option_tournament_list.add(new DataModel("Tournament B"));
        option_tournament_list.add(new DataModel("Tournament C"));
        option_tournament_list.add(new DataModel("Tournament D"));
        drop_tournamentName.setOptionList(option_tournament_list);
        drop_tournamentName.setClickListener(new SelectTournamentType.onClickInterface() {
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


        activityAddTeamBinding.editTextTeamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTeamBinding.filledTeamName.setErrorEnabled(false);
                activityAddTeamBinding.filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTeamBinding.filledTeamName.setErrorEnabled(false);
                    activityAddTeamBinding.filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTeamBinding.filledTeamName.setErrorEnabled(false);
                activityAddTeamBinding.filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityAddTeamBinding.editTextTeamName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTeamBinding.filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityAddTeamBinding.editTextTeamName.getText().length() == 0) {
                    activityAddTeamBinding.filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTeamBinding.filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityAddTeamBinding.editTextCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityAddTeamBinding.filledTextFieldCity.setErrorEnabled(false);
                activityAddTeamBinding.filledTextFieldCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityAddTeamBinding.filledTextFieldCity.setErrorEnabled(false);
                    activityAddTeamBinding.filledTextFieldCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityAddTeamBinding.filledTextFieldCity.setErrorEnabled(false);
                activityAddTeamBinding.filledTextFieldCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityAddTeamBinding.editTextCity.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityAddTeamBinding.filledTextFieldCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityAddTeamBinding.editTextCity.getText().length() == 0) {
                    activityAddTeamBinding.filledTextFieldCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityAddTeamBinding.filledTextFieldCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });


        activityAddTeamBinding.mbSubmit.setOnClickListener(v -> {

            if (activityAddTeamBinding.editTextTeamName.getText().toString().isEmpty() || activityAddTeamBinding.editTextTeamName.getText().toString().length() < 3
                    || activityAddTeamBinding.editTextTeamName.getText().toString().length() > 21) {
                activityAddTeamBinding.filledTeamName.setErrorEnabled(true);
                activityAddTeamBinding.filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTeamBinding.filledTeamName.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTeamBinding.filledTeamName.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTeamBinding.filledTeamName.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTeamBinding.filledTeamName.setError(getResources().getString(R.string.team_name));
            } else if (activityAddTeamBinding.editTextCity.getText().toString().isEmpty() || activityAddTeamBinding.editTextCity.getText().toString().length() < 3
                    || activityAddTeamBinding.editTextCity.getText().toString().length() > 21) {
                activityAddTeamBinding.filledTextFieldCity.setErrorEnabled(true);
                activityAddTeamBinding.filledTextFieldCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTeamBinding.filledTextFieldCity.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTeamBinding.filledTextFieldCity.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTeamBinding.filledTextFieldCity.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityAddTeamBinding.filledTextFieldCity.setError(getResources().getString(R.string.city_namee));
            }
//            else if (tournamentType.isEmpty()) {
//                Toaster.customToast("Select Tournament !");
//            } else if (matchType.isEmpty()) {
//                Toaster.customToast("Select match!");
//            } else if (image_uri == null) {
//                Toaster.customToast("Please add team logo !");
//            }
            else {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Global.isOnline(mContext)) {
                        addTeamViewMode.getAddTeamResponse(SessionManager.getToken(),SessionManager.getUserId().toString(),
                                activityAddTeamBinding.editTextCity.getText().toString().trim(),
                                activityAddTeamBinding.editTextTeamName.getText().toString(),
                                image_uri);
                    } else {
                        Global.showDialog(mActivity);
                    }
                }

                image_uri = null;
                drop_tournamentName.setText(tournamentType);
                drop_matchName.setText(matchType);
                activityAddTeamBinding.editTextTeamName.setText("");
                activityAddTeamBinding.editTextCity.setText("");


//                startActivity(new Intent(mContext, YourTeamListActivity.class));
//                finish();
            }


        });

        activityAddTeamBinding.middle.setOnClickListener(view -> {
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM", "AddTeamActivity"));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (image_uri != null) {
            activityAddTeamBinding.profilePic.setImageURI(image_uri);
            //image_uri = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    /*
    add team response

    {
    "status": true,
    "message": "Add Information",
    "data": {
        "team_id": 5,
        "name": "Gurgram Team A Update",
        "team_logo": "",
        "city": "gurgaon",
        "created_by": 1,
        "created_at": "2024-03-05T05:37:32.000Z",
        "update_at": "2024-03-05T05:37:32.000Z"
    }
}
     */

}