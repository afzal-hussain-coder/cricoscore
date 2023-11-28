package com.cricoscore.repository;
import android.util.Log;

import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.SignUpBody;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {
    private static final String TAG = SignUpRepository.class.getSimpleName();

    private ApiRequest apiRequest;

    public SignUpRepository(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getSignUp(SignUpBody signUpBody,ISignUpResponse signUpResponse){


        Call<SignUpResponse> initiateSignUp = apiRequest.getSignUp(signUpBody);

        initiateSignUp.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.e(TAG,response.toString());

                if(response.isSuccessful()){
                    signUpResponse.onResponse(response.body(),false);
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        signUpResponse.onResponse(response.body(),true);
                        Toaster.customToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toaster.customToast(e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                signUpResponse.onFailure(t);
            }
        });


    }

    public interface ISignUpResponse{
        void onResponse(SignUpResponse signUpResponse,Boolean status);
        void onFailure(Throwable t);
    }
}
