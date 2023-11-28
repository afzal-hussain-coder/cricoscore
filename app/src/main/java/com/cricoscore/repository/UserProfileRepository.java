package com.cricoscore.repository;

import android.util.Log;

import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ApiResponse.UserProfileResponse;
import com.cricoscore.ParamBody.LoginBody;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileRepository {
    private static final String TAG = UserProfileRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public UserProfileRepository(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getUserProfile(String token,Integer userId,Integer userid, IUserProfileResponse userProfileResponse){
        Call<UserProfileResponse> initiateLogin = apiRequest.getUserProfile(token,userId,userid);
        initiateLogin.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                Log.e(TAG,response+"");

                if(response.isSuccessful()){
                    userProfileResponse.onResponse(response.body(),false);
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.e(TAG,jObjError.toString());
                        userProfileResponse.onResponse(response.body(),true);
                        Toaster.customToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toaster.customToast(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                userProfileResponse.onFailure(t);
            }
        });
    }

    public interface IUserProfileResponse{
        void onResponse(UserProfileResponse userProfileResponse, Boolean status);
        void onFailure(Throwable t);
    }
}
