package com.cricoscore.view_model;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.VerifyOtpBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.VerifyOtpRepository;

public class VerifyOtpModel extends ViewModel {
    private final VerifyOtpRepository verifyOtpRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();

    public VerifyOtpModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        verifyOtpRepository = new VerifyOtpRepository();
    }

    public void verifyOtp(int user_id, int otp) {
        mProgressMutableData.postValue(View.VISIBLE);


        verifyOtpRepository.getVerifyOtp(new VerifyOtpBody(user_id, otp), new VerifyOtpRepository.IVerifyOtpResponse() {
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
                    SessionManager.save_check_login(true);
                    mSignUpResultMutableData.postValue(signUpResponse.getStatus());
                }else mSignUpResultMutableData.postValue(false);
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

                if(!status){
                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveToken(authData.getToken());
                    SessionManager.saveUserId(authData.getUser_id());
                    mSignUpResultMutableData.postValue(signUpResponse.getStatus());
                }else mSignUpResultMutableData.postValue(false);
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
