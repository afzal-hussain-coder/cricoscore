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
import com.cricoscore.ParamBody.ForgetPasswordBody;
import com.cricoscore.ParamBody.LoginBody;
import com.cricoscore.ParamBody.LoginThroughPhoneNumberBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.ForgetPasswordRepository;
import com.cricoscore.repository.LoginRepository;

public class ForgetPasswordViewModel extends ViewModel {
    private ForgetPasswordRepository forgetPasswordRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();
    SharedPreferences prefs;

    ForgetPasswordViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        forgetPasswordRepository = new ForgetPasswordRepository();
        prefs = PreferenceManager.getDefaultSharedPreferences(CricoscopeApplication.getContext());
    }

    public void getForgetPassword(String email){
        mProgressMutableData.postValue(View.VISIBLE);

        forgetPasswordRepository.getForgetPassword(new ForgetPasswordBody(email), new LoginRepository.ILoginResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(status==false){
                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveUserId(prefs, authData.getUser_id());
                    SessionManager.saveEmail(prefs, authData.getEmail());
                    SessionManager.saveOtp(prefs,authData.getOtp());
                    mSignUpResultMutableData.postValue(signUpResponse.getStatus());
                }else{
                    mSignUpResultMutableData.postValue(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
                mSignUpResultMutableData.postValue(false);
                Log.d("Login failure: ", t.getLocalizedMessage());
            }
        });

    }


    public LiveData<Integer> getForgetPasswordProgress() {
        return mProgressMutableData;
    }

    public LiveData<Boolean> getForgetPasswordResult() {
        return mSignUpResultMutableData;
    }
}
