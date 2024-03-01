package com.cricoscore.view_model;

import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.LoginBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private final LoginRepository loginRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();

    public LoginViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        loginRepository = new LoginRepository();
    }

    public void getLogin(String username,String password){
        mProgressMutableData.postValue(View.VISIBLE);

        loginRepository.getLogin(new LoginBody(username, password), new LoginRepository.ILoginResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(!status){

                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveToken(authData.getToken());
                    SessionManager.saveUserId(authData.getUser_id());
                    SessionManager.saveUserName(authData.getUsername());
                    SessionManager.saveEmail(authData.getEmail());
                    SessionManager.saveUserStatus(authData.getStatus());
                    SessionManager.savePhone(authData.getPhone_number());
                    SessionManager.saveFirstName(authData.getFirst_name());
                    SessionManager.saveLastName(authData.getLast_name());
                    SessionManager.saveIsEmailVerified(authData.getIs_email_verified());
                    SessionManager.saveIsPhoneVerified(authData.getIs_mobile_verified());
                    SessionManager.saveOtp(authData.getOtp());

                    SessionManager.save_check_login(authData.getIs_mobile_verified().equalsIgnoreCase("1") ||
                            authData.getIs_email_verified().equalsIgnoreCase("1"));
                    mSignUpResultMutableData.postValue(signUpResponse.getStatus());
                }else mSignUpResultMutableData.postValue(false);
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
