package com.cricoscore.view_model;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cricoscore.ApiResponse.UserProfileResponse;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.UserProfileRepository;

public class UserProfileViewModel extends ViewModel {
    private final UserProfileRepository userProfileRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();

    UserProfileViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        userProfileRepository = new UserProfileRepository();
    }

    public void getUserProfile(String token,Integer userId){
        mProgressMutableData.postValue(View.VISIBLE);

        userProfileRepository.getUserProfile(token,userId,userId, new UserProfileRepository.IUserProfileResponse() {
            @Override
            public void onResponse(UserProfileResponse userProfileResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(!status){
                    AuthData authData = userProfileResponse.getData();
                    //SessionManager.saveToken(authData.getToken());
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
                    mSignUpResultMutableData.postValue(userProfileResponse.getStatus());
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

    public LiveData<Integer> getUserProfileLoader() {
        return mProgressMutableData;
    }

    public LiveData<Boolean> getUserProfileResult() {
        return mSignUpResultMutableData;
    }
}
