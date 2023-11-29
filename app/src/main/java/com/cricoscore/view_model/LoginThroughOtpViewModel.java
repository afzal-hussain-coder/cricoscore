package com.cricoscore.view_model;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.LoginThroughPhoneNumberBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.LoginRepository;

public class LoginThroughOtpViewModel extends ViewModel {
    private final LoginRepository loginRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();

    LoginThroughOtpViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        loginRepository = new LoginRepository();
    }

    public void getLoginThroughOtp(String phoneNumber){
        mProgressMutableData.postValue(View.VISIBLE);

        loginRepository.getLoginThroughOtp(new LoginThroughPhoneNumberBody(phoneNumber), new LoginRepository.ILoginResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(!status){
                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveUserId(authData.getUser_id());
                    SessionManager.saveOtp(authData.getOtp());
                    SessionManager.savePhone(authData.getPhone_number());

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

    public LiveData<Integer> getLoginThroughOtpProgress() {
        return mProgressMutableData;
    }

    public LiveData<Boolean> getLoginThroughOtpResult() {
        return mSignUpResultMutableData;
    }
}
