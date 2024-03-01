package com.cricoscore.view_model;

import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.ForgetPasswordBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.ForgetPasswordRepository;
import com.cricoscore.repository.LoginRepository;

public class ForgetPasswordViewModel extends ViewModel {
    private final ForgetPasswordRepository forgetPasswordRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();

    public ForgetPasswordViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        forgetPasswordRepository = new ForgetPasswordRepository();
    }

    public void getForgetPassword(String email){
        mProgressMutableData.postValue(View.VISIBLE);

        forgetPasswordRepository.getForgetPassword(new ForgetPasswordBody(email), new LoginRepository.ILoginResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(!status){
                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveUserId(authData.getUser_id());
                    SessionManager.saveEmail(authData.getEmail());
                    SessionManager.saveOtp(authData.getOtp());
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


    public LiveData<Integer> getForgetPasswordProgress() {
        return mProgressMutableData;
    }

    public LiveData<Boolean> getForgetPasswordResult() {
        return mSignUpResultMutableData;
    }
}
