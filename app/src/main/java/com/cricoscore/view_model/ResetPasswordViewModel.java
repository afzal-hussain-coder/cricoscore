package com.cricoscore.view_model;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cricoscore.ApiResponse.ResponseStatus;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.ResetPasswordBody;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.repository.ResetPasswordRepository;

public class ResetPasswordViewModel extends ViewModel {
    private final ResetPasswordRepository forgetPasswordRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<ResponseStatus> mSignUpResultMutableData = new MutableLiveData<>();

    public ResetPasswordViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(new ResponseStatus(false,""));
        forgetPasswordRepository = new ResetPasswordRepository();
    }

    public void getResetPassword(int userid,String password,String confirmPssword){
        mProgressMutableData.postValue(View.VISIBLE);

        forgetPasswordRepository.getResetPassword(new ResetPasswordBody(userid,password,confirmPssword), new ResetPasswordRepository.IResetPasswordResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(!status){
                    Toaster.customToast(signUpResponse.getMessage());
                    mSignUpResultMutableData.postValue(new ResponseStatus(true,signUpResponse.getMessage()));
                }else mSignUpResultMutableData.postValue(new ResponseStatus(false,signUpResponse.getMessage()));
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
                mSignUpResultMutableData.postValue(new ResponseStatus(false,""));
                Log.d("Login failure: ", t.getLocalizedMessage());
            }
        });

    }

    public LiveData<Integer> getResetPasswordProgress() {
        return mProgressMutableData;
    }

    public LiveData<ResponseStatus> getResetPasswordResult() {
        return mSignUpResultMutableData;
    }
}
