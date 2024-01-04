package com.cricoscore.view_model;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.SignUpBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.SignUpRepository;

public class SignUpViewModel extends ViewModel {
    private final SignUpRepository signUpRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();


    public SignUpViewModel() {
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        signUpRepository = new SignUpRepository();
    }

    public void signUp(String username, String email, String phoneNumber, String password) {
        mProgressMutableData.postValue(View.VISIBLE);


        signUpRepository.getSignUp(new SignUpBody(username, email, phoneNumber, password), new SignUpRepository.ISignUpResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);

                if (!status) {

                    AuthData authData = signUpResponse.getData();
                    SessionManager.saveUserId(authData.getUser_id());
                    SessionManager.saveOtp(authData.getEmail_code());
                    SessionManager.savePhone(authData.getPhone_number());
                    mSignUpResultMutableData.postValue(signUpResponse.getStatus());
                } else mSignUpResultMutableData.postValue(false);

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
