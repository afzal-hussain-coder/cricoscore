package com.cricoscore.repository;

import android.util.Log;

import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.VerifyOtpBody;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpRepository {
    private static final String TAG = VerifyOtpRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public VerifyOtpRepository(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getVerifyOtp(VerifyOtpBody verifyOtpBody,IVerifyOtpResponse iVerifyOtpResponse){
        Call<SignUpResponse> initiateVerifyOtp = apiRequest.getVerifyOtp(verifyOtpBody);
        initiateVerifyOtp.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.e(TAG,response.toString());

                if(response.isSuccessful()){
                    iVerifyOtpResponse.onResponse(response.body(),false);
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        iVerifyOtpResponse.onResponse(response.body(),true);
                        Toaster.customToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toaster.customToast(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                iVerifyOtpResponse.onFailure(t);
            }
        });

    }

    public void getEmailVerifyOtp(VerifyOtpBody verifyOtpBody,IVerifyOtpResponse iVerifyOtpResponse){
        Call<SignUpResponse> initiateVerifyOtp = apiRequest.getEmailVerifyOtp(verifyOtpBody);
        initiateVerifyOtp.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.e(TAG,response.toString());

                if(response.isSuccessful()){
                    iVerifyOtpResponse.onResponse(response.body(),false);
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        iVerifyOtpResponse.onResponse(response.body(),true);
                        Toaster.customToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toaster.customToast(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                iVerifyOtpResponse.onFailure(t);
            }
        });

    }

    public interface IVerifyOtpResponse{
        void onResponse(SignUpResponse signUpResponse, Boolean status);
        void onFailure(Throwable t);
    }
}
