package com.cricoscore.view_model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.CricoscopeApplication;
import com.cricoscore.ParamBody.SignUpBody;
import com.cricoscore.ParamBody.VerifyOtpBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.SignUpRepository;
import com.cricoscore.repository.VerifyOtpRepository;

public class VerifyOtpModel extends ViewModel {
    private VerifyOtpRepository verifyOtpRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();
    SharedPreferences prefs;

    public VerifyOtpModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        verifyOtpRepository = new VerifyOtpRepository();
        prefs = PreferenceManager.getDefaultSharedPreferences(CricoscopeApplication.getContext());
    }
    public void verifyOtp(int user_id, int otp) {
        mProgressMutableData.postValue(View.VISIBLE);


        verifyOtpRepository.getVerifyOtp(new VerifyOtpBody(user_id, otp), new VerifyOtpRepository.IVerifyOtpResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);

                if(status==false){
                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveToken(prefs, authData.getToken());
                    SessionManager.saveUserId(prefs, authData.getUser_id());
                    SessionManager.saveUserName(prefs, authData.getUsername());
                    SessionManager.saveEmail(prefs, authData.getEmail());
                    SessionManager.saveUserStatus(prefs, authData.getStatus());
                    SessionManager.savePhone(prefs, authData.getPhone_number());
                    SessionManager.saveFirstName(prefs, authData.getFirst_name());
                    SessionManager.saveLastName(prefs, authData.getLast_name());
                    SessionManager.saveIsEmailVerified(prefs, authData.getIs_email_verified());
                    SessionManager.saveIsPhoneVerified(prefs, authData.getIs_mobile_verified());
                    SessionManager.save_check_login(prefs,true);
                    mSignUpResultMutableData.postValue(signUpResponse.getStatus());
                }else{
                    mSignUpResultMutableData.postValue(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
                mSignUpResultMutableData.postValue(false);
                Log.d("SignUp failure: ", t.getLocalizedMessage());
            }
        });


    }

    public void emailVerifyOtp(int user_id, int otp) {
        mProgressMutableData.postValue(View.VISIBLE);


        verifyOtpRepository.getEmailVerifyOtp(new VerifyOtpBody(user_id, otp), new VerifyOtpRepository.IVerifyOtpResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);

                if(status==false){
                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveToken(prefs, authData.getToken());
                    SessionManager.saveUserId(prefs, authData.getUser_id());
                    SessionManager.saveUserName(prefs, authData.getUsername());
                    SessionManager.saveEmail(prefs, authData.getEmail());
                    SessionManager.saveUserStatus(prefs, authData.getStatus());
                    SessionManager.savePhone(prefs, authData.getPhone_number());
                    SessionManager.saveFirstName(prefs, authData.getFirst_name());
                    SessionManager.saveLastName(prefs, authData.getLast_name());
                    SessionManager.saveIsEmailVerified(prefs, authData.getIs_email_verified());
                    SessionManager.saveIsPhoneVerified(prefs, authData.getIs_mobile_verified());
                    SessionManager.save_check_login(prefs,true);
                    mSignUpResultMutableData.postValue(signUpResponse.getStatus());
                }else{
                    mSignUpResultMutableData.postValue(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
                mSignUpResultMutableData.postValue(false);
                Log.d("SignUp failure: ", t.getLocalizedMessage());
            }
        });


    }

    public LiveData<Integer> getVerifyOTPProgress() {
        return mProgressMutableData;
    }

    public LiveData<Boolean> getVerifyOtpResult() {
        return mSignUpResultMutableData;
    }
}
