package com.cricoscore.view_model;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cricoscore.ApiResponse.ResponseStatus;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.ForgetPasswordBody;
import com.cricoscore.ParamBody.SubmitUserProfileBody;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.ForgetPasswordRepository;
import com.cricoscore.repository.LoginRepository;
import com.cricoscore.repository.SubmitUserProfileRepository;

public class SubmitUserProfileViewModel extends ViewModel {

    private final SubmitUserProfileRepository submitUserProfileRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<ResponseStatus> mSubmitProfileResultMutableData = new MutableLiveData<>();

    public SubmitUserProfileViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSubmitProfileResultMutableData.postValue(new ResponseStatus(false,""));
        submitUserProfileRepository = new SubmitUserProfileRepository();
    }

    public void getSubmitUserProfile(Uri imagePath, String token, String username, String firstName, String lastName, String email, String phoneNumber, int userId){
        mProgressMutableData.postValue(View.VISIBLE);

        submitUserProfileRepository.getSubmitUserProfile(token,username,firstName,lastName,phoneNumber,email,userId,imagePath, new SubmitUserProfileRepository.ISubmitUserProfileResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(!status){
                    Toaster.customToast(signUpResponse.getMessage());
                    mSubmitProfileResultMutableData.postValue(new ResponseStatus(true,signUpResponse.getMessage()));
                }else mSubmitProfileResultMutableData.postValue(new ResponseStatus(false,signUpResponse.getMessage()));
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
                mSubmitProfileResultMutableData.postValue(new ResponseStatus(false,""));
                Log.d("Login failure: ", t.getLocalizedMessage());
            }
        });

    }


    public LiveData<Integer> getSubmitProfileProgress() {
        return mProgressMutableData;
    }

    public LiveData<ResponseStatus> getSubmitProfileResult() {
        return mSubmitProfileResultMutableData;
    }
}
