package com.cricoscore.repository;

import android.util.Log;

import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.LoginThroughPhoneNumberBody;
import com.cricoscore.ParamBody.ResetPasswordBody;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordRepository {
    private static final String TAG =ResetPasswordRepository.class.getSimpleName();

    private ApiRequest apiRequest;

    public ResetPasswordRepository(){
        apiRequest  = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

    }

    public void getResetPassword(ResetPasswordBody resetPasswordBody,IResetPasswordResponse iResetPasswordResponse){
        Call<SignUpResponse> initiliaze = apiRequest.getResetPassword(resetPasswordBody);
        initiliaze.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.e(TAG,response+"");

                if(response.isSuccessful()){
                    iResetPasswordResponse.onResponse(response.body(),false);
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.e(TAG,jObjError.toString());
                        iResetPasswordResponse.onResponse(response.body(),true);
                        Toaster.customToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toaster.customToast(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                iResetPasswordResponse.onFailure(t);
            }
        });

    }


    public interface IResetPasswordResponse{
        void onResponse(SignUpResponse signUpResponse, Boolean status);
        void onFailure(Throwable t);
    }

}
