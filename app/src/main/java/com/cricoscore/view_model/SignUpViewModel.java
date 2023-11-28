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
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.SignUpRepository;

public class SignUpViewModel extends ViewModel {
    private SignUpRepository signUpRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();
    SharedPreferences prefs;


    public SignUpViewModel() {
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        signUpRepository = new SignUpRepository();
        prefs = PreferenceManager.getDefaultSharedPreferences(CricoscopeApplication.getContext());
    }

    public void signUp(String username, String email, String phoneNumber, String password) {
        mProgressMutableData.postValue(View.VISIBLE);


        signUpRepository.getSignUp(new SignUpBody(username, email, phoneNumber, password), new SignUpRepository.ISignUpResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);

                if (status == false) {

                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveUserId(prefs, authData.getUser_id());
                    SessionManager.saveOtp(prefs, authData.getOtp());
                    SessionManager.savePhone(prefs, authData.getPhone_number());
                    mSignUpResultMutableData.postValue(signUpResponse.getStatus());
                } else {
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

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public LiveData<Boolean> getSignUpResult() {
        return mSignUpResultMutableData;
    }

}
