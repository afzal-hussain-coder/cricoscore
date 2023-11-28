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
import com.cricoscore.ParamBody.LoginBody;
import com.cricoscore.ParamBody.LoginThroughPhoneNumberBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.LoginRepository;
import com.cricoscore.repository.SignUpRepository;

public class LoginViewModel extends ViewModel {
    private LoginRepository loginRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();
    SharedPreferences prefs;

    LoginViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        loginRepository = new LoginRepository();
        prefs = PreferenceManager.getDefaultSharedPreferences(CricoscopeApplication.getContext());
    }

    public void getLogin(String username,String password){
        mProgressMutableData.postValue(View.VISIBLE);

        loginRepository.getLogin(new LoginBody(username, password), new LoginRepository.ILoginResponse() {
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
                    SessionManager.saveOtp(prefs,authData.getOtp());

                    if(authData.getIs_mobile_verified().equalsIgnoreCase("1") ||
                    authData.getIs_email_verified().equalsIgnoreCase("1")){
                        SessionManager.save_check_login(prefs,true);
                    }else{
                        SessionManager.save_check_login(prefs,false);
                    }
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

    public void getLoginThroughOtp(String phoneNumber){
        mProgressMutableData.postValue(View.VISIBLE);

        loginRepository.getLoginThroughOtp(new LoginThroughPhoneNumberBody(phoneNumber), new LoginRepository.ILoginResponse() {
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
                    SessionManager.saveOtp(prefs,authData.getOtp());

                    if(authData.getIs_mobile_verified().equalsIgnoreCase("1") ||
                            authData.getIs_email_verified().equalsIgnoreCase("1")){
                        SessionManager.save_check_login(prefs,true);
                    }else{
                        SessionManager.save_check_login(prefs,false);
                    }
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

    public LiveData<Integer> getLoginProgress() {
        return mProgressMutableData;
    }

    public LiveData<Boolean> getLoginResult() {
        return mSignUpResultMutableData;
    }
}
