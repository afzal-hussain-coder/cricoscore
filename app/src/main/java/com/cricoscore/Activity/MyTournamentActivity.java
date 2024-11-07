package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cricoscore.Adapter.MyTournamentAdapter;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityMyTournamentBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTournamentActivity extends AppCompatActivity {

    ActivityMyTournamentBinding activityMyTournamentBinding;
    Context mContext;
    Activity mActivity;
    public static Uri image_uri = null;
    TextInputEditText edit_text_teamName;
    TextInputEditText edit_text_city;
    CircleImageView iv_team_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyTournamentBinding = ActivityMyTournamentBinding.inflate(getLayoutInflater());
        setContentView(activityMyTournamentBinding.getRoot());

        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityMyTournamentBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.my_tournament));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        activityMyTournamentBinding.rvmyTournament.setHasFixedSize(true);
        activityMyTournamentBinding.rvmyTournament.setLayoutManager(new LinearLayoutManager(mContext));

        activityMyTournamentBinding.rvmyTournament.setAdapter(new MyTournamentAdapter(mContext, () -> {
            showBottomSheetDialog();
        }));


    }

    public void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.add_team_bottom_dialog);

        RelativeLayout rl_team_logo = bottomSheetDialog.findViewById(R.id.rl_team_logo);
        TextView tv_select_team_from_list = bottomSheetDialog.findViewById(R.id.tv_select_team_from_list);
        iv_team_logo = bottomSheetDialog.findViewById(R.id.iv_team_logo);
        edit_text_teamName = bottomSheetDialog.findViewById(R.id.edit_text_teamName);
        TextInputLayout filledTeamName = bottomSheetDialog.findViewById(R.id.filledTeamName);
        TextInputLayout filledCity = bottomSheetDialog.findViewById(R.id.filledCity);
        edit_text_city = bottomSheetDialog.findViewById(R.id.edit_text_city);
        MaterialButton mb_submit = bottomSheetDialog.findViewById(R.id.mb_submit);

        rl_team_logo.setOnClickListener(view -> {
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM", "TournamentFragment"));
        });

        tv_select_team_from_list.setOnClickListener(v -> {
            startActivity(new Intent(mContext, YourTeamListActivity.class));
            bottomSheetDialog.dismiss();
        });


        edit_text_teamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filledTeamName.setErrorEnabled(false);
                filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    filledTeamName.setErrorEnabled(false);
                    filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                filledTeamName.setErrorEnabled(false);
                filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        edit_text_teamName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (edit_text_teamName.getText().length() == 0) {
                    filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });


        edit_text_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filledCity.setErrorEnabled(false);
                filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    filledCity.setErrorEnabled(false);
                    filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                filledCity.setErrorEnabled(false);
                filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        edit_text_city.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (edit_text_city.getText().length() == 0) {
                    filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });


        mb_submit.setOnClickListener(view -> {

            if (edit_text_teamName.getText().toString().isEmpty() || edit_text_teamName.getText().toString().length() < 3
                    || edit_text_teamName.getText().toString().length() > 21) {
                filledTeamName.setErrorEnabled(true);
                filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledTeamName.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledTeamName.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledTeamName.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledTeamName.setError(getResources().getString(R.string.team_name));
            } else if (edit_text_city.getText().toString().isEmpty() || edit_text_city.getText().toString().length() > 3) {
                filledCity.setErrorEnabled(true);
                filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledCity.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledCity.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledCity.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledCity.setError(getResources().getString(R.string.city_name));
            } else {
                bottomSheetDialog.dismiss();
            }


        });

        bottomSheetDialog.show();

    }
}