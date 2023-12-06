package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.cricoscore.R;
import com.cricoscore.Utils.GenericKeyEvent;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityLoginBinding;
import com.cricoscore.view_model.ForgetPasswordViewModel;
import com.cricoscore.view_model.ResetPasswordViewModel;
import com.cricoscore.view_model.LoginThroughOtpViewModel;
import com.cricoscore.view_model.LoginViewModel;
import com.cricoscore.view_model.SignUpViewModel;
import com.cricoscore.view_model.VerifyOtpModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Animation anim_right;
    SignUpViewModel authViewModel;
    LoginViewModel loginViewModel;
    LoginThroughOtpViewModel loginThroughOtpViewModel;
    ResetPasswordViewModel resetPasswordViewModel;
    ForgetPasswordViewModel forgetPasswordViewModel;
    VerifyOtpModel verifyOtpModel;
    ActivityLoginBinding activityLoginBinding;
    Context mContext;
    Activity mActivity;

    boolean isVerifiedStatus = false;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        mContext = this;
        mActivity = this;

        anim_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right_layout);

        /**
         * @SignUp Result
         * @s --> Check result
         */
        authViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        authViewModel.getSignUpResult().observe(this, s -> {
            if (s) {

                char[] charArray = String.valueOf(SessionManager.getOtp()).toCharArray();

                activityLoginBinding.e1.setText(String.valueOf(charArray[0]));
                activityLoginBinding.e2.setText(String.valueOf(charArray[1]));
                activityLoginBinding.e3.setText(String.valueOf(charArray[2]));
                activityLoginBinding.e4.setText(String.valueOf(charArray[3]));

                activityLoginBinding.tvOtpMobileNumber.setText("XXXXXX"+SessionManager.getPhone().substring(6,10));

                activityLoginBinding.topLoginSignup.setVisibility(View.GONE);
                activityLoginBinding.top.setVisibility(View.GONE);
                activityLoginBinding.liSignUpLayout.setVisibility(View.GONE);
                activityLoginBinding.liOtpLatout.setVisibility(View.VISIBLE);
                activityLoginBinding.liOtpLatout.startAnimation(anim_right);
            }

        });
        authViewModel.getProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });

        /**
         * @Login Result
         * @s --> Check result
         */
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getLoginResult().observe(this, s -> {
            if (s) {

                if (SessionManager.getIsEmailVerified().equalsIgnoreCase("1")
                        || SessionManager.getIsPhoneVerified().equalsIgnoreCase("1")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                } else {
                    activityLoginBinding.tvOtpMobileNumber.setText(SessionManager.getPhone());


                    char[] charArray = String.valueOf(SessionManager.getOtp()).toCharArray();

                    activityLoginBinding.e1.setText(String.valueOf(charArray[0]));
                    activityLoginBinding.e2.setText(String.valueOf(charArray[1]));
                    activityLoginBinding.e3.setText(String.valueOf(charArray[2]));
                    activityLoginBinding.e4.setText(String.valueOf(charArray[3]));


                    activityLoginBinding.topLoginSignup.setVisibility(View.GONE);
                    activityLoginBinding.top.setVisibility(View.GONE);
                    activityLoginBinding.liSignInLayout.setVisibility(View.GONE);
                    activityLoginBinding.liOtpLatout.setVisibility(View.VISIBLE);
                    activityLoginBinding.liOtpLatout.startAnimation(anim_right);
                }
            }

        });
        loginViewModel.getLoginProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });

        /**
         * @Login through mobile number Result
         * @s --> Check result
         */
        loginThroughOtpViewModel = new ViewModelProvider(this).get(LoginThroughOtpViewModel.class);
        loginThroughOtpViewModel.getLoginThroughOtpResult().observe(this, aBoolean -> {
            if (aBoolean) {

                char[] charArray = String.valueOf(SessionManager.getOtp()).toCharArray();

                activityLoginBinding.e1.setText(String.valueOf(charArray[0]));
                activityLoginBinding.e2.setText(String.valueOf(charArray[1]));
                activityLoginBinding.e3.setText(String.valueOf(charArray[2]));
                activityLoginBinding.e4.setText(String.valueOf(charArray[3]));


                activityLoginBinding.liOtpLogin.setVisibility(View.GONE);
                activityLoginBinding.topLoginSignup.setVisibility(View.GONE);
                activityLoginBinding.top.setVisibility(View.GONE);
                activityLoginBinding.liOtpLatout.setVisibility(View.VISIBLE);
                activityLoginBinding.liOtpLatout.startAnimation(anim_right);
            }
        });

        loginThroughOtpViewModel.getLoginThroughOtpProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });


        /**
         * @verified Otp Result
         * aBoolean --> Check result
         */
        verifyOtpModel = new ViewModelProvider(this).get(VerifyOtpModel.class);
        verifyOtpModel.getVerifyOtpResult().observe(this, aBoolean -> {
            if (aBoolean) {

                if (isVerifiedStatus == true) {

                    activityLoginBinding.liResetPassword.startAnimation(anim_right);
                    activityLoginBinding.liResetPassword.setVisibility(View.VISIBLE);
                    activityLoginBinding.liOtpLatout.setVisibility(View.GONE);

                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }


            }
        });
        verifyOtpModel.getVerifyOTPProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });

        /**
         * Forget Password
         */

        forgetPasswordViewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        forgetPasswordViewModel.getForgetPasswordResult().observe(this, aBoolean -> {
            if (aBoolean) {
                isVerifiedStatus = true;
                char[] charArray = String.valueOf(SessionManager.getOtp()).toCharArray();

                activityLoginBinding.e1.setText(String.valueOf(charArray[0]));
                activityLoginBinding.e2.setText(String.valueOf(charArray[1]));
                activityLoginBinding.e3.setText(String.valueOf(charArray[2]));
                activityLoginBinding.e4.setText(String.valueOf(charArray[3]));


                activityLoginBinding.liForgotLayout.setVisibility(View.GONE);
                activityLoginBinding.topLoginSignup.setVisibility(View.GONE);
                activityLoginBinding.top.setVisibility(View.GONE);
                activityLoginBinding.liEditPhone.setVisibility(View.GONE);
                activityLoginBinding.tvPhoneMessage.setText(R.string.code_verification_msg_your_email);
                activityLoginBinding.liOtpLatout.setVisibility(View.VISIBLE);
                activityLoginBinding.liOtpLatout.startAnimation(anim_right);
            }
        });
        forgetPasswordViewModel.getForgetPasswordProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });


        /*
          Reset Password
         */

        resetPasswordViewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        resetPasswordViewModel.getResetPasswordResult().observe(this,responseStatus -> {
            if(responseStatus.isStatus()){

                new Handler().postDelayed(() -> {
                    Toaster.customToast(responseStatus.getMessage());
                    activityLoginBinding.liResetPassword.setVisibility(View.GONE);
                    activityLoginBinding.tvContinue.setText(getResources().getString(R.string.singn_in_to_continue));
                    activityLoginBinding.topLoginSignup.setVisibility(View.VISIBLE);
                    activityLoginBinding.top.setVisibility(View.VISIBLE);
                    activityLoginBinding.liSignInLayout.setVisibility(View.VISIBLE);
                    activityLoginBinding.liSignInLayout.startAnimation(anim_right);
                },100);

            }
        });

        resetPasswordViewModel.getResetPasswordProgress().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });


        // click of simple #Login In Text
        activityLoginBinding.txtLogin.setOnClickListener(v -> {
            activityLoginBinding.tvContinue.setText(getResources().getString(R.string.singn_in_to_continue));
            activityLoginBinding.liSignInLayout.startAnimation(anim_right);
            if (activityLoginBinding.liSignInLayout.getVisibility() == View.GONE) {
                activityLoginBinding.liSignInLayout.setVisibility(View.VISIBLE);
            }
            if (activityLoginBinding.liSignUpLayout.getVisibility() == View.VISIBLE) {
                activityLoginBinding.liSignUpLayout.setVisibility(View.GONE);
            }
            if (activityLoginBinding.viewSignup.getVisibility() == View.VISIBLE) {
                activityLoginBinding.viewSignup.setVisibility(View.GONE);
            }
            if (activityLoginBinding.viewLogin.getVisibility() == View.GONE) {
                activityLoginBinding.viewLogin.setVisibility(View.VISIBLE);
            }

            activityLoginBinding.txtLogin.setTextColor(getResources().getColor(R.color.purple_500));
            activityLoginBinding.txtSignup.setTextColor(getResources().getColor(R.color.dark_grey));
        });
        // click of simple #Sign Up Text
        activityLoginBinding.txtSignup.setOnClickListener(v -> {
            activityLoginBinding.tvContinue.setText(getResources().getString(R.string.singUp_continue));
            activityLoginBinding.liSignUpLayout.startAnimation(anim_right);
            activityLoginBinding.txtLogin.setTextColor(getResources().getColor(R.color.dark_grey));
            activityLoginBinding.txtSignup.setTextColor(getResources().getColor(R.color.purple_500));
            if (activityLoginBinding.liSignInLayout.getVisibility() == View.VISIBLE) {
                activityLoginBinding.liSignInLayout.setVisibility(View.GONE);
            }
            if (activityLoginBinding.liSignUpLayout.getVisibility() == View.GONE) {
                activityLoginBinding.liSignUpLayout.setVisibility(View.VISIBLE);
            }
            if (activityLoginBinding.viewLogin.getVisibility() == View.VISIBLE) {
                activityLoginBinding.viewLogin.setVisibility(View.GONE);
            }
            if (activityLoginBinding.viewSignup.getVisibility() == View.GONE) {
                activityLoginBinding.viewSignup.setVisibility(View.VISIBLE);
                activityLoginBinding.viewSignup.setBackgroundColor(getResources().getColor(R.color.purple_500));
            }
        });
        // click of simple #Sign Up account Text
        activityLoginBinding.txtSignupAccount.setOnClickListener(v -> {
            activityLoginBinding.tvContinue.setText(getResources().getString(R.string.singUp_continue));
            activityLoginBinding.liSignUpLayout.startAnimation(anim_right);
            activityLoginBinding.txtLogin.setTextColor(getResources().getColor(R.color.dark_grey));
            activityLoginBinding.txtSignup.setTextColor(getResources().getColor(R.color.purple_500));
            if (activityLoginBinding.liSignInLayout.getVisibility() == View.VISIBLE) {
                activityLoginBinding.liSignInLayout.setVisibility(View.GONE);
            }
            if (activityLoginBinding.liSignUpLayout.getVisibility() == View.GONE) {
                activityLoginBinding.liSignUpLayout.setVisibility(View.VISIBLE);
            }
            if (activityLoginBinding.viewLogin.getVisibility() == View.VISIBLE) {
                activityLoginBinding.viewLogin.setVisibility(View.GONE);
            }
            if (activityLoginBinding.viewSignup.getVisibility() == View.GONE) {
                activityLoginBinding.viewSignup.setVisibility(View.VISIBLE);
                activityLoginBinding.viewSignup.setBackgroundColor(getResources().getColor(R.color.purple_500));
            }
        });
        // click of simple #Log in account Text
        activityLoginBinding.txtLoginAccount.setOnClickListener(v -> {
            activityLoginBinding.tvContinue.setText(getResources().getString(R.string.singUp_continue));
            activityLoginBinding.liSignInLayout.startAnimation(anim_right);
            if (activityLoginBinding.liSignInLayout.getVisibility() == View.GONE) {
                activityLoginBinding.liSignInLayout.setVisibility(View.VISIBLE);
            }
            if (activityLoginBinding.liSignUpLayout.getVisibility() == View.VISIBLE) {
                activityLoginBinding.liSignUpLayout.setVisibility(View.GONE);
            }
            if (activityLoginBinding.viewSignup.getVisibility() == View.VISIBLE) {
                activityLoginBinding.viewSignup.setVisibility(View.GONE);
            }
            if (activityLoginBinding.viewLogin.getVisibility() == View.GONE) {
                activityLoginBinding.viewLogin.setVisibility(View.VISIBLE);
            }

            activityLoginBinding.txtLogin.setTextColor(getResources().getColor(R.color.purple_500));
            activityLoginBinding.txtSignup.setTextColor(getResources().getColor(R.color.dark_grey));
        });
        // click of simple #Forgot password Text
        activityLoginBinding.txtForgot.setOnClickListener(v -> {
            activityLoginBinding.liForgotLayout.setVisibility(View.VISIBLE);
            activityLoginBinding.liForgotLayout.startAnimation(anim_right);
            activityLoginBinding.liSignInLayout.setVisibility(View.GONE);
            activityLoginBinding.liSignUpLayout.setVisibility(View.GONE);
            activityLoginBinding.topLoginSignup.setVisibility(View.GONE);
            activityLoginBinding.tvContinue.setText(getResources().getString(R.string.recover_to_password));
        });
        // click of simple #back icon of #Forgot password layout
        activityLoginBinding.ivBack.setOnClickListener(v -> {
            activityLoginBinding.liSignInLayout.startAnimation(anim_right);
            activityLoginBinding.tvContinue.setText(getResources().getString(R.string.singn_in_to_continue));
            activityLoginBinding.liForgotLayout.setVisibility(View.GONE);
            activityLoginBinding.liSignInLayout.setVisibility(View.VISIBLE);
            activityLoginBinding.liSignUpLayout.setVisibility(View.GONE);
            activityLoginBinding.topLoginSignup.setVisibility(View.VISIBLE);
            activityLoginBinding.liForgotLayout.setVisibility(View.GONE);
        });

        activityLoginBinding.mbLoginThroughOtp.setOnClickListener(v -> {
            activityLoginBinding.txtForgot.setVisibility(View.GONE);
            activityLoginBinding.liDontAccount.setVisibility(View.GONE);
            activityLoginBinding.liOtpLogin.setVisibility(View.VISIBLE);
            activityLoginBinding.liOtpLogin.startAnimation(anim_right);
            activityLoginBinding.liManualLogin.setVisibility(View.GONE);
            activityLoginBinding.liSignUpLayout.setVisibility(View.GONE);
            activityLoginBinding.topLoginSignup.setVisibility(View.GONE);
            activityLoginBinding.tvContinue.setText(getResources().getString(R.string.login_through_otp_to_continue));
        });

        activityLoginBinding.ivBackOtp.setOnClickListener(v -> {
            activityLoginBinding.txtForgot.setVisibility(View.VISIBLE);
            activityLoginBinding.liDontAccount.setVisibility(View.VISIBLE);
            activityLoginBinding.liOtpLogin.setVisibility(View.GONE);
            activityLoginBinding.liManualLogin.startAnimation(anim_right);
            activityLoginBinding.liManualLogin.setVisibility(View.VISIBLE);
            activityLoginBinding.liSignUpLayout.setVisibility(View.GONE);
            activityLoginBinding.topLoginSignup.setVisibility(View.VISIBLE);
            activityLoginBinding.tvContinue.setText(getResources().getString(R.string.singn_in_to_continue));
        });

        activityLoginBinding.mbRequestOtp.setOnClickListener(v -> {
            activityLoginBinding.top.setVisibility(View.GONE);
            activityLoginBinding.liOtpLogin.setVisibility(View.GONE);
            activityLoginBinding.liOtpLatout.setVisibility(View.VISIBLE);
            activityLoginBinding.liOtpLatout.startAnimation(anim_right);

        });

        activityLoginBinding.rlEditOtpNumber.setOnClickListener(v -> {
            activityLoginBinding.liManualLogin.setVisibility(View.GONE);
            activityLoginBinding.liSignInLayout.setVisibility(View.VISIBLE);
            activityLoginBinding.top.setVisibility(View.VISIBLE);
            activityLoginBinding.liOtpLatout.setVisibility(View.GONE);
            activityLoginBinding.liOtpLogin.setVisibility(View.VISIBLE);
            activityLoginBinding.liOtpLogin.startAnimation(anim_right);
            activityLoginBinding.txtForgot.setVisibility(View.GONE);
            activityLoginBinding.liDontAccount.setVisibility(View.GONE);

            activityLoginBinding.ivBackOtp.setVisibility(View.GONE);
        });


        /**
         * Login view event
         */
        activityLoginBinding.filledTextFieldPassword.setTag("InVisible");
        activityLoginBinding.filledTextFieldPassword.setEndIconOnClickListener(v -> {
            if (activityLoginBinding.filledTextFieldPassword.getTag().equals("InVisible")) {
                activityLoginBinding.editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldPassword.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_black_24dp));
                activityLoginBinding.filledTextFieldPassword.setTag("Visible");
            } else {
                activityLoginBinding.editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldPassword.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_off_black_24dp));
                activityLoginBinding.filledTextFieldPassword.setTag("InVisible");
            }
        });

        activityLoginBinding.editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldUsername.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldUsername.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldUsername.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldUsername.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldUsername.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldUsername.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextUsername.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldUsername.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(activityLoginBinding.editTextUsername.getText()).length() == 0) {
                    activityLoginBinding.filledTextFieldUsername.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldUsername.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityLoginBinding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldPassword.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldPassword.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    activityLoginBinding.filledTextFieldPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldPassword.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextPassword.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextPassword.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                    activityLoginBinding.filledTextFieldPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                    activityLoginBinding.filledTextFieldPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityLoginBinding.mbLogin.setOnClickListener(v -> {

            if (Objects.requireNonNull(activityLoginBinding.editTextUsername.getText()).toString().isEmpty()
                    || activityLoginBinding.editTextUsername.getText().toString().length() < 3
                    || activityLoginBinding.editTextUsername.getText().toString().length() > 21) {
                activityLoginBinding.filledTextFieldUsername.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldUsername.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldUsername.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldUsername.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldUsername.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldUsername.setError(getResources().getString(R.string.username_msg));
            } else if (activityLoginBinding.editTextPassword.getText().toString().isEmpty()) {
                activityLoginBinding.filledTextFieldPassword.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPassword.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPassword.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPassword.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPassword.setError("Password is required");
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Global.isOnline(mContext)) {
                        loginViewModel.getLogin(activityLoginBinding.editTextUsername.getText().toString(),
                                activityLoginBinding.editTextPassword.getText().toString());
                    } else {
                        Global.showDialog(mActivity);
                    }
                }
            }


        });
        //End Login view event


        /**
         * SignUp view event
         */

        activityLoginBinding.filledTextFieldPasswordSignup.setTag("InVisible");
        activityLoginBinding.filledTextFieldPasswordSignup.setEndIconOnClickListener(v -> {
            if (activityLoginBinding.filledTextFieldPasswordSignup.getTag().equals("InVisible")) {
                activityLoginBinding.editTextPasswordSignup.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldPasswordSignup.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_black_24dp));
                activityLoginBinding.filledTextFieldPasswordSignup.setTag("Visible");
            } else {
                activityLoginBinding.editTextPasswordSignup.setTransformationMethod(PasswordTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldPasswordSignup.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_off_black_24dp));
                activityLoginBinding.filledTextFieldPasswordSignup.setTag("InVisible");
            }
        });

        activityLoginBinding.filledTextFieldConPasswordSignup.setTag("InVisible");
        activityLoginBinding.filledTextFieldConPasswordSignup.setEndIconOnClickListener(v -> {
            if (activityLoginBinding.filledTextFieldConPasswordSignup.getTag().equals("InVisible")) {
                activityLoginBinding.editTextConPasswordSignup.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldConPasswordSignup.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_black_24dp));
                activityLoginBinding.filledTextFieldConPasswordSignup.setTag("Visible");
            } else {
                activityLoginBinding.editTextConPasswordSignup.setTransformationMethod(PasswordTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldConPasswordSignup.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_off_black_24dp));
                activityLoginBinding.filledTextFieldConPasswordSignup.setTag("InVisible");
            }
        });

        activityLoginBinding.editTextUsernameSignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldUsernameSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldUsernameSignup.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldUsernameSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextUsernameSignup.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextUsernameSignup.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityLoginBinding.editTextEmailSignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldEmailSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldEmailSignup.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldEmailSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextEmailSignup.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextEmailSignup.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityLoginBinding.editTextPhoneSignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldPhoneSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldPhoneSignup.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldPhoneSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextPhoneSignup.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextPhoneSignup.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });


        activityLoginBinding.editTextPasswordSignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldPasswordSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldPasswordSignup.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    activityLoginBinding.filledTextFieldPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldPasswordSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextPasswordSignup.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextPasswordSignup.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                    activityLoginBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                    activityLoginBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityLoginBinding.editTextConPasswordSignup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldConPasswordSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldConPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldConPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldConPasswordSignup.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldConPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    activityLoginBinding.filledTextFieldConPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldConPasswordSignup.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldConPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldConPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextConPasswordSignup.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldConPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldConPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                activityLoginBinding.filledTextFieldConPasswordSignup.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                activityLoginBinding.filledTextFieldConPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            }
        });

        activityLoginBinding.mbSignUp.setOnClickListener(v -> {

            if (Objects.requireNonNull(activityLoginBinding.editTextUsernameSignup.getText()).toString().isEmpty()
                    || activityLoginBinding.editTextUsernameSignup.getText().toString().length() < 3 ||
                    activityLoginBinding.editTextUsernameSignup.getText().toString().length()>21) {
                activityLoginBinding.filledTextFieldUsernameSignup.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldUsernameSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldUsernameSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldUsernameSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldUsernameSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldUsernameSignup.setError(getResources().getString(R.string.username_msg));
            } else if (Objects.requireNonNull(activityLoginBinding.editTextEmailSignup.getText()).toString().isEmpty()) {
                activityLoginBinding.filledTextFieldEmailSignup.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldEmailSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldEmailSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldEmailSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldEmailSignup.setError("Email is required");
            } else if (!Global.isValidEmail(activityLoginBinding.editTextEmailSignup.getText().toString())) {
                activityLoginBinding.filledTextFieldEmailSignup.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldEmailSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldEmailSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldEmailSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldEmailSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldEmailSignup.setError("Enter a valid email");
            } else if (Objects.requireNonNull(activityLoginBinding.editTextPhoneSignup.getText()).toString().isEmpty()) {
                activityLoginBinding.filledTextFieldPhoneSignup.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPhoneSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPhoneSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPhoneSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPhoneSignup.setError("Mobile number is required");
            } else if (!Global.isValidPhoneNumber(activityLoginBinding.editTextPhoneSignup.getText().toString())) {
                activityLoginBinding.filledTextFieldPhoneSignup.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldPhoneSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPhoneSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPhoneSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPhoneSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPhoneSignup.setError("Enter 10-digit mobile number");
            } else if (activityLoginBinding.editTextPasswordSignup.getText().toString().isEmpty()) {
                activityLoginBinding.filledTextFieldPasswordSignup.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPasswordSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPasswordSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPasswordSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPasswordSignup.setError("Password is required");
            } else if (!Global.isPasswordValidMethod(activityLoginBinding.editTextPasswordSignup.getText().toString())) {
                activityLoginBinding.filledTextFieldPasswordSignup.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPasswordSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPasswordSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPasswordSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldPasswordSignup.setError("Please choose a stronger password. Try a mix of letters, numbers, and symbols.");
            } else if (!Global.isMatchPassword(activityLoginBinding.editTextPasswordSignup.getText().toString(), activityLoginBinding.editTextConPasswordSignup.getText().toString())) {
                activityLoginBinding.filledTextFieldConPasswordSignup.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldConPasswordSignup.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldConPasswordSignup.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldConPasswordSignup.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldConPasswordSignup.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldConPasswordSignup.setError("Password doesn't match");
            } else {
                /**
                 * SignUp Api Request
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Global.isOnline(mContext)) {
                        authViewModel.signUp(activityLoginBinding.editTextUsernameSignup.getText().toString(),
                                activityLoginBinding.editTextEmailSignup.getText().toString(),
                                activityLoginBinding.editTextPhoneSignup.getText().toString(),
                                activityLoginBinding.editTextPasswordSignup.getText().toString());
                    } else {
                        Global.showDialog(mActivity);
                    }
                }
            }


        });

        /**
         * End of SignUp view Event
         */

        /**
         * Forgot password event
         */
        activityLoginBinding.editTextForgotPasswordEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextForgotPasswordEmail.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextForgotPasswordEmail.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });
        activityLoginBinding.mbRecover.setOnClickListener(v -> {
            if (activityLoginBinding.editTextForgotPasswordEmail.getText().toString().isEmpty()) {
                activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setError("Email is required");
            } else if (!Global.isValidEmail(activityLoginBinding.editTextForgotPasswordEmail.getText().toString())) {
                activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setError("Enter a valid email");
            } else {
                if (Global.isOnline(mContext)) {
                    forgetPasswordViewModel.getForgetPassword(activityLoginBinding.editTextForgotPasswordEmail.getText().toString());
                } else {
                    Global.showDialog(mActivity);
                }

            }
        });
        // End Forgot password event

        /**
         * LOGIN THROUGH OTP event
         */
        activityLoginBinding.editTextMobileThroughOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldThroughOtp.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldThroughOtp.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldThroughOtp.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldThroughOtp.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldThroughOtp.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldThroughOtp.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextMobileThroughOtp.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldThroughOtp.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextMobileThroughOtp.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldThroughOtp.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldThroughOtp.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });
        activityLoginBinding.mbRequestOtp.setOnClickListener(v -> {
            if (Objects.requireNonNull(activityLoginBinding.editTextMobileThroughOtp.getText()).toString().isEmpty()) {
                activityLoginBinding.filledTextFieldThroughOtp.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldThroughOtp.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldThroughOtp.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldThroughOtp.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldThroughOtp.setError("Mobile number is required");
            } else if (!Global.isValidPhoneNumber(activityLoginBinding.editTextMobileThroughOtp.getText().toString())) {
                activityLoginBinding.filledTextFieldThroughOtp.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldThroughOtp.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldThroughOtp.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldThroughOtp.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldThroughOtp.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldThroughOtp.setError("Enter a 10-digit mobile number");
            } else {

                if (Global.isOnline(mContext)) {
                    loginThroughOtpViewModel.getLoginThroughOtp(activityLoginBinding.editTextMobileThroughOtp.getText().toString());
                } else {
                    Global.showDialog(mActivity);
                }

            }
        });

        // End OTP event

        /**
         * Forget password event
         */
        activityLoginBinding.editTextForgotPasswordEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextForgotPasswordEmail.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextForgotPasswordEmail.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });
        activityLoginBinding.mbRecover.setOnClickListener(v -> {
            if (activityLoginBinding.editTextForgotPasswordEmail.getText().toString().isEmpty()) {
                activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setError("Email is required");
            } else if (!Global.isValidEmail(activityLoginBinding.editTextForgotPasswordEmail.getText().toString())) {
                activityLoginBinding.filledTextFieldForgotEmail.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldForgotEmail.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldForgotEmail.setError("Enter valid email");
            } else {
                if (Global.isOnline(mContext)) {
                    forgetPasswordViewModel.getForgetPassword(activityLoginBinding.editTextForgotPasswordEmail.getText().toString());
                } else {
                    Global.showDialog(mActivity);
                }

            }
        });

        /**
         * submit otp
         */
        activityLoginBinding.e1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activityLoginBinding.e1.getText().toString().length() == 1)     //size as per your requirement
                {
                    activityLoginBinding.e2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        activityLoginBinding.e2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activityLoginBinding.e2.getText().toString().length() == 1)     //size as per your requirement
                {
                    activityLoginBinding.e3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        activityLoginBinding.e3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activityLoginBinding.e3.getText().toString().length() == 1)     //size as per your requirement
                {
                    activityLoginBinding.e4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        activityLoginBinding.e4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activityLoginBinding.e4.getText().toString().length() == 1)     //size as per your requirement
                {
                    //e2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        activityLoginBinding.e2.setOnKeyListener(new GenericKeyEvent(activityLoginBinding.e2, activityLoginBinding.e1));
        activityLoginBinding.e3.setOnKeyListener(new GenericKeyEvent(activityLoginBinding.e3, activityLoginBinding.e2));
        activityLoginBinding.e4.setOnKeyListener(new GenericKeyEvent(activityLoginBinding.e4, activityLoginBinding.e3));


        findViewById(R.id.mb_otp_submit).setOnClickListener(v -> {
            if (activityLoginBinding.e1.getText().toString().isEmpty() && activityLoginBinding.e2.getText().toString().isEmpty()
                    && activityLoginBinding.e3.getText().toString().isEmpty() && activityLoginBinding.e4.getText().toString().isEmpty()) {
                Toaster.customToast("OTP Required");
            } else {

                if (isVerifiedStatus == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Global.isOnline(mContext)) {
                            verifyOtpModel.emailVerifyOtp(SessionManager.getUserId(), SessionManager.getOtp());
                        } else {
                            Global.showDialog(mActivity);
                        }
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Global.isOnline(mContext)) {
                            verifyOtpModel.verifyOtp(SessionManager.getUserId(), SessionManager.getOtp());
                        } else {
                            Global.showDialog(mActivity);
                        }
                    }
                }


            }

        });
        //end submit otp


        // Reset Password Event

        activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setTag("InVisible");
        activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconOnClickListener(v -> {
            if (activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.getTag().equals("InVisible")) {
                activityLoginBinding.editTextCurrentPasswordResetPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_black_24dp));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setTag("Visible");
            } else {
                activityLoginBinding.editTextCurrentPasswordResetPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_off_black_24dp));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setTag("InVisible");
            }
        });

        activityLoginBinding.filledTextFieldConPasswordResetPassword.setTag("InVisible");
        activityLoginBinding.filledTextFieldConPasswordResetPassword.setEndIconOnClickListener(v -> {
            if (activityLoginBinding.filledTextFieldConPasswordResetPassword.getTag().equals("InVisible")) {
                activityLoginBinding.editTextConPasswordResetPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_black_24dp));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setTag("Visible");
            } else {
                activityLoginBinding.editTextConPasswordResetPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setEndIconDrawable(getResources().getDrawable(R.drawable.visibility_off_black_24dp));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setTag("InVisible");
            }
        });

        activityLoginBinding.editTextCurrentPasswordResetPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextCurrentPasswordResetPassword.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (activityLoginBinding.editTextCurrentPasswordResetPassword.getText().length() == 0) {
                    activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                    activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                    activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        activityLoginBinding.editTextConPasswordResetPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    activityLoginBinding.filledTextFieldConPasswordResetPassword.setErrorEnabled(false);
                    activityLoginBinding.filledTextFieldConPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    activityLoginBinding.filledTextFieldConPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setErrorEnabled(false);
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        activityLoginBinding.editTextConPasswordResetPassword.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            }
        });


        activityLoginBinding.mbResetPassword.setOnClickListener(view -> {

            if (Objects.requireNonNull(activityLoginBinding.editTextCurrentPasswordResetPassword.getText()).toString().isEmpty()) {
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setError("Current password is required");
            } else if (!Global.isPasswordValidMethod(activityLoginBinding.editTextCurrentPasswordResetPassword.getText().toString())) {
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldCurrentPasswordResetPassword.setError("Please choose a stronger password. Try a mix of letters, numbers, and symbols.");
            } else if (!Global.isMatchPassword(activityLoginBinding.editTextCurrentPasswordResetPassword.getText().toString(), Objects.requireNonNull(activityLoginBinding.editTextConPasswordResetPassword.getText()).toString())) {
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setErrorEnabled(true);
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                activityLoginBinding.filledTextFieldConPasswordResetPassword.setError("Password doesn't match");
            } else {

                if (Global.isOnline(mContext)) {
                    resetPasswordViewModel.getResetPassword(SessionManager.getUserId(),
                            activityLoginBinding.editTextCurrentPasswordResetPassword.getText().toString(),
                            activityLoginBinding.editTextConPasswordResetPassword.getText().toString());
                } else {
                    Global.showDialog(mActivity);
                }

            }
        });


    }
}