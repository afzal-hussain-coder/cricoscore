package com.cricoscore.repository;

import android.net.Uri;
import android.util.Log;

import com.cricoscore.ApiResponse.AddTeamResponse;
import com.cricoscore.ApiResponse.AddTournamentResponse;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeamRepo {
    private static final String TAG = AddTeamRepo.class.getSimpleName();
    private ApiRequest apiRequest;


    public AddTeamRepo() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getAddTeamResponse(String token, String userId, String city, String name, Uri teamLogo , IAddTeamResponse iAddTeamResponse) {

        RequestBody requestUserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody requestCity = RequestBody.create(MediaType.parse("text/plain"), city);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        //RequestBody requestTeamLogo= RequestBody.create(MediaType.parse("text/plain"), teamLogo);

        MultipartBody.Part bodyLogo=null;

        if (teamLogo != null) {
            File file = new File(teamLogo.getPath());
            String mimeType = getMimeType(file);
            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);

            // MultipartBody.Part is used to send also the actual file name
            bodyLogo = MultipartBody.Part.createFormData("team_logo", file.getName(), requestFile);

            // Proceed with the rest of your logic, e.g., making a network request with 'body'
        } else {
            // Handle the null case
            Log.e("FileUpload", "URI is null, cannot proceed with file upload.");
        }

//        Call<AddTeamResponse> addTeamResponseCall = apiRequest.getAddTeamResponse(token, requestUserId, requestCity,
//                requestName, bodyLogo);
//
//        addTeamResponseCall.enqueue(new Callback<AddTeamResponse>() {
//            @Override
//            public void onResponse(Call<AddTeamResponse> call, Response<AddTeamResponse> response) {
//                Log.e(TAG, response + "");
//                if(response.isSuccessful()){
//                    iAddTeamResponse.onResponse(response.body(),false);
//                }else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Log.e(TAG,jObjError.toString());
//                        iAddTeamResponse.onResponse(response.body(),true);
//                        Toaster.customToast(jObjError.getString("message"));
//                    } catch (Exception e) {
//                        Toaster.customToast(e.getMessage());
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<AddTeamResponse> call, Throwable t) {
//                iAddTeamResponse.onFailure(t);
//
//            }
//        });


    }

    public interface IAddTeamResponse {
        void onResponse(AddTeamResponse addTeamResponse, Boolean status);

        void onFailure(Throwable t);
    }

    private String getMimeType(File file) {
        String mimeType = null;
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                mimeType = "image/jpeg";
                break;
            case "png":
                mimeType = "image/png";
                break;
            case "gif":
                mimeType = "image/gif";
                break;
            default:
                mimeType = "application/octet-stream"; // Fallback MIME type
                break;
        }
        return mimeType;
    }
}
