package com.cricoscore.view_model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ApiResponse.UserProfileResponse;
import com.cricoscore.CricoscopeApplication;
import com.cricoscore.ParamBody.LoginBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.LoginRepository;
import com.cricoscore.repository.UserProfileRepository;

public class UserProfileViewModel extends ViewModel {
    private UserProfileRepository userProfileRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();
    SharedPreferences prefs;

    UserProfileViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        userProfileRepository = new UserProfileRepository();
        prefs = PreferenceManager.getDefaultSharedPreferences(CricoscopeApplication.getContext());
    }

    public void getUserProfile(String token,Integer userId){
        mProgressMutableData.postValue(View.VISIBLE);

        userProfileRepository.getUserProfile(token,userId,userId, new UserProfileRepository.IUserProfileResponse() {
            @Override
            public void onResponse(UserProfileResponse userProfileResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(status==false){
                    AuthData authData = userProfileResponse.getData();
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
                    SessionManager.saveOtp(prefs,authData.getOtp());
                    mSignUpResultMutableData.postValue(userProfileResponse.getStatus());
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

    public LiveData<Integer> getUserProfileLoader() {
        return mProgressMutableData;
    }

    public LiveData<Boolean> getUserProfileResult() {
        return mSignUpResultMutableData;
    }
}
