package com.cricoscore.repository;

import android.net.Uri;
import android.util.Log;

import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ParamBody.ForgetPasswordBody;
import com.cricoscore.ParamBody.SubmitUserProfileBody;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitUserProfileRepository {

    private static final String TAG = SubmitUserProfileRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public SubmitUserProfileRepository(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getSubmitUserProfile(String token, String username,
                                     String firstName,
                                     String lastName,
                                     String phone,
                                     String email,
                                     int userId, Uri imagePath,
                                     ISubmitUserProfileResponse iSubmitUserProfileResponse){
        // Create the image part
        String newExtension="";
        if(imagePath!= null){
            String extension = imagePath.toString().substring(imagePath.toString().lastIndexOf("."));
            newExtension = extension.replace(".","");
        }

        File file=null;
        if(imagePath!=null){
            file = new File(imagePath.getPath());
            //Toaster.customToast(imagePath.getPath());
        }


        if(imagePath == null){

            RequestBody userNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), username);
            RequestBody firstNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), firstName);
            RequestBody lastNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), lastName);
            RequestBody phoneNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), phone);
            RequestBody emailNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody userIdRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));

            Call<SignUpResponse> initiateLogin = apiRequest.getUserSubmitProfileWithoutImage(token,
                    userNameRequestBody,
                    firstNameRequestBody,
                    lastNameRequestBody,
                    phoneNameRequestBody,
                    emailNameRequestBody,
                    userIdRequestBody);
            initiateLogin.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    Log.e(TAG,response+"");

                    if(response.isSuccessful()){
                        iSubmitUserProfileResponse.onResponse(response.body(),false);
                    }else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.e(TAG,jObjError.toString());
                            iSubmitUserProfileResponse.onResponse(response.body(),true);
                            Toaster.customToast(jObjError.getString("message"));
                        } catch (Exception e) {
                            Toaster.customToast(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    iSubmitUserProfileResponse.onFailure(t);
                }
            });
        }else{
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/"+newExtension), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("avatar", file.getName(), imageRequestBody);

            RequestBody userNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), username);
            RequestBody firstNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), firstName);
            RequestBody lastNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), lastName);
            RequestBody phoneNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), phone);
            RequestBody emailNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody userIdRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));

            Call<SignUpResponse> initiateLogin = apiRequest.getUserSubmitProfile(token,
                    userNameRequestBody,
                    firstNameRequestBody,
                    lastNameRequestBody,
                    phoneNameRequestBody,
                    emailNameRequestBody,
                    userIdRequestBody,
                    imagePart);
            initiateLogin.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    Log.e(TAG,response+"");

                    if(response.isSuccessful()){
                        iSubmitUserProfileResponse.onResponse(response.body(),false);
                    }else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.e(TAG,jObjError.toString());
                            iSubmitUserProfileResponse.onResponse(response.body(),true);
                            Toaster.customToast(jObjError.getString("message"));
                        } catch (Exception e) {
                            Toaster.customToast(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    iSubmitUserProfileResponse.onFailure(t);
                }
            });
        }




    }

    public interface ISubmitUserProfileResponse{
        void onResponse(SignUpResponse signUpResponse, Boolean status);
        void onFailure(Throwable t);
    }
}
