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

import com.bumptech.glide.Glide;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityAddTournamentBinding;
import com.cricoscore.databinding.ActivityVisitProfileBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.cricoscore.view_model.LoginViewModel;
import com.cricoscore.view_model.SubmitUserProfileViewModel;

import java.util.Objects;

public class VisitProfileActivity extends AppCompatActivity {
    ActivityVisitProfileBinding activityVisitProfileBinding;
    Context mContext;
    Activity mActivity;
    public static Uri image_uri = null;
    SubmitUserProfileViewModel submitUserProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_profile);

        activityVisitProfileBinding = ActivityVisitProfileBinding.inflate(getLayoutInflater());
        setContentView(activityVisitProfileBinding.getRoot());

        mActivity = this;
        mContext = this;

        ToolbarBinding toolbarBinding = activityVisitProfileBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.edit_profile));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        /**
         * @SubmitProfile Result
         * @s --> Check result
         */
        submitUserProfileViewModel = new ViewModelProvider(this).get(SubmitUserProfileViewModel.class);
        submitUserProfileViewModel.getSubmitProfileResult().observe(this, responseStatus -> {
            if (responseStatus.isStatus()) {

                new Handler().postDelayed(() -> {
                    Toaster.customToast(responseStatus.getMessage());
                }, 100);

            }
        });
        submitUserProfileViewModel.getSubmitProfileProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });


        initView();
        setUserData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (image_uri != null) {
            activityVisitProfileBinding.profilePic.setImageURI(image_uri);
            //image_uri = null;
        }

    }

    private void initView() {
        activityVisitProfileBinding.middle.setOnClickListener(view -> {
            startActivity(new Intent(mContext, CustomeCameraActivity.class)
                    .putExtra("FROM", "VisitProfileActivity"));
        });


        activityVisitProfileBinding.editTextUsernameSignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityVisitProfileBinding.filledTextFieldUsernameSignup.setErrorEnabled(false);
                    activityVisitProfileBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityVisitProfileBinding.editTextUsernameSignup.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityVisitProfileBinding.editTextUsernameSignup.getText().length() == 0) {
                    activityVisitProfileBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityVisitProfileBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityVisitProfileBinding.editTextEmailSignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorEnabled(false);
                    activityVisitProfileBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityVisitProfileBinding.editTextEmailSignup.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityVisitProfileBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityVisitProfileBinding.editTextEmailSignup.getText().length() == 0) {
                    activityVisitProfileBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityVisitProfileBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityVisitProfileBinding.editTextPhoneSignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorEnabled(false);
                    activityVisitProfileBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityVisitProfileBinding.editTextPhoneSignup.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityVisitProfileBinding.editTextPhoneSignup.getText().length() == 0) {
                    activityVisitProfileBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityVisitProfileBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityVisitProfileBinding.editTextFirstname.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityVisitProfileBinding.filledTextFieldFirstname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityVisitProfileBinding.editTextFirstname.getText().length() == 0) {
                    activityVisitProfileBinding.filledTextFieldFirstname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityVisitProfileBinding.filledTextFieldFirstname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityVisitProfileBinding.editTextFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityVisitProfileBinding.filledTextFieldFirstname.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldFirstname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityVisitProfileBinding.filledTextFieldFirstname.setErrorEnabled(false);
                    activityVisitProfileBinding.filledTextFieldFirstname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityVisitProfileBinding.filledTextFieldFirstname.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldFirstname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });


        activityVisitProfileBinding.editTextLastname.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityVisitProfileBinding.filledTextFieldLastname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityVisitProfileBinding.editTextLastname.getText().length() == 0) {
                    activityVisitProfileBinding.filledTextFieldLastname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityVisitProfileBinding.filledTextFieldLastname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityVisitProfileBinding.editTextLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityVisitProfileBinding.filledTextFieldLastname.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldLastname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityVisitProfileBinding.filledTextFieldLastname.setErrorEnabled(false);
                    activityVisitProfileBinding.filledTextFieldLastname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityVisitProfileBinding.filledTextFieldLastname.setErrorEnabled(false);
                activityVisitProfileBinding.filledTextFieldLastname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityVisitProfileBinding.mbSubmit.setOnClickListener(v -> {

            if (Objects.requireNonNull(activityVisitProfileBinding.editTextUsernameSignup.getText()).toString().isEmpty()
                    || activityVisitProfileBinding.editTextUsernameSignup.getText().toString().length() < 3 ||
                    activityVisitProfileBinding.editTextUsernameSignup.getText().toString().length() > 21) {
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setErrorEnabled(true);
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldUsernameSignup.setError(getResources().getString(R.string.username_msg));
            } else if (Objects.requireNonNull(activityVisitProfileBinding.editTextFirstname.getText()).toString().isEmpty()
                    || activityVisitProfileBinding.editTextFirstname.getText().toString().length() < 2 ||
                    activityVisitProfileBinding.editTextFirstname.getText().toString().length() > 30) {
                activityVisitProfileBinding.filledTextFieldFirstname.setErrorEnabled(true);
                activityVisitProfileBinding.filledTextFieldFirstname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldFirstname.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldFirstname.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldFirstname.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldFirstname.setError(getResources().getString(R.string.firstname_msg));
            } else if (Objects.requireNonNull(activityVisitProfileBinding.editTextLastname.getText()).toString().isEmpty()
                    || activityVisitProfileBinding.editTextLastname.getText().toString().length() < 2 ||
                    activityVisitProfileBinding.editTextLastname.getText().toString().length() > 30) {
                activityVisitProfileBinding.filledTextFieldLastname.setErrorEnabled(true);
                activityVisitProfileBinding.filledTextFieldLastname.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldLastname.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldLastname.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldLastname.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldLastname.setError(getResources().getString(R.string.lastname_msg));
            } else if (Objects.requireNonNull(activityVisitProfileBinding.editTextEmailSignup.getText()).toString().isEmpty()) {
                activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorEnabled(true);
                activityVisitProfileBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldEmailSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldEmailSignup.setError("Email is required");
            } else if (!Global.isValidEmail(activityVisitProfileBinding.editTextEmailSignup.getText().toString())) {
                activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorEnabled(true);
                activityVisitProfileBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldEmailSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldEmailSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldEmailSignup.setError("Enter a valid email");
            } else if (Objects.requireNonNull(activityVisitProfileBinding.editTextPhoneSignup.getText()).toString().isEmpty()) {
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorEnabled(true);
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setError("Mobile number is required");
            } else if (!Global.isValidPhoneNumber(activityVisitProfileBinding.editTextPhoneSignup.getText().toString())) {
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorEnabled(true);
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityVisitProfileBinding.filledTextFieldPhoneSignup.setError("Enter 10-digit mobile number");
            }
//            else if (activityVisitProfileBinding.editTextPasswordSignup.getText().toString().isEmpty()) {
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setErrorEnabled(true);
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setError("Password is required");
//            } else if (!Global.isPasswordValidMethod(activityVisitProfileBinding.editTextPasswordSignup.getText().toString())) {
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setErrorEnabled(true);
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldPasswordSignup.setError("Please choose a stronger password. Try a mix of letters, numbers, and symbols.");
//            } else if (!Global.isMatchPassword(activityVisitProfileBinding.editTextPasswordSignup.getText().toString(), activityVisitProfileBinding.editTextConPasswordSignup.getText().toString())) {
//                activityVisitProfileBinding.filledTextFieldConPasswordSignup.setErrorEnabled(true);
//                activityVisitProfileBinding.filledTextFieldConPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldConPasswordSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldConPasswordSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldConPasswordSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
//                activityVisitProfileBinding.filledTextFieldConPasswordSignup.setError("Password doesn't match");
//            }
            else {
                /**
                 * SignUp Api Request
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Global.isOnline(mContext)) {
                        submitUserProfileViewModel.getSubmitUserProfile(image_uri, SessionManager.getToken(), activityVisitProfileBinding.editTextUsernameSignup.getText().toString(),
                                activityVisitProfileBinding.editTextFirstname.getText().toString(),
                                activityVisitProfileBinding.editTextLastname.getText().toString(),
                                activityVisitProfileBinding.editTextEmailSignup.getText().toString(),
                                activityVisitProfileBinding.editTextPhoneSignup.getText().toString(),
                                SessionManager.getUserId());
                    } else {
                        Global.showDialog(mActivity);
                    }
                }
            }


        });

    }

    // Set Data here
    private void setUserData() {
        activityVisitProfileBinding.editTextUsernameSignup.setText(SessionManager.getUserName());
        activityVisitProfileBinding.editTextFirstname.setText(SessionManager.getFirstName());
        activityVisitProfileBinding.editTextLastname.setText(SessionManager.getLastName());
        activityVisitProfileBinding.editTextEmailSignup.setText(SessionManager.getEmail());
        activityVisitProfileBinding.editTextPhoneSignup.setText(SessionManager.getPhone());
        // image_uri = Uri.parse(SessionManager.getUserAvtar());

        //Toaster.customToast(image_uri+"");

        Glide.with(mContext).load(SessionManager.getUserAvtar()).
                error(mContext.getResources().getDrawable(R.drawable.placeholder_user)).
                into(activityVisitProfileBinding.profilePic);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}