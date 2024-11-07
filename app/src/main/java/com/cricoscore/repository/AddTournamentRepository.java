package com.cricoscore.repository;

import android.net.Uri;
import android.util.Log;

import com.cricoscore.ApiResponse.AddTournamentResponse;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import java.io.File;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTournamentRepository {

    private static final String TAG = AddTournamentRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public AddTournamentRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getAddTournamentResponse(String token, String tournamentType, String prize, String ballType, String tName, String startDate, String endDate,
                                         String state, String city, String location, String groundName, float fees, int discount, int numOfTeam,
                                         String sponsoreName, int userId, int tournamentId,Uri uri,Uri uriBanner
            , IAddTournamentResponse iAddTournamentResponse) {

        RequestBody requestBodytournamentType = RequestBody.create(MediaType.parse("text/plain"), tournamentType);
        RequestBody requestBodyprize = RequestBody.create(MediaType.parse("text/plain"), prize);
        RequestBody requestBodyballType = RequestBody.create(MediaType.parse("text/plain"), ballType);
        RequestBody requestBodytName = RequestBody.create(MediaType.parse("text/plain"), tName);
        RequestBody requestBodystartDate = RequestBody.create(MediaType.parse("text/plain"), startDate);
        RequestBody requestBodyendDate = RequestBody.create(MediaType.parse("text/plain"), endDate);
        RequestBody requestBodystate = RequestBody.create(MediaType.parse("text/plain"), state);
        RequestBody requestBodycity = RequestBody.create(MediaType.parse("text/plain"), city);
        RequestBody requestBodylocation = RequestBody.create(MediaType.parse("text/plain"), location);
        RequestBody requestBodygroundName = RequestBody.create(MediaType.parse("text/plain"), groundName);
        RequestBody requestBodyfees = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(fees));
        RequestBody requestBodydiscount = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(discount));
        RequestBody requestBodynumOfTeam = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(numOfTeam));
        RequestBody requestBodysponsoreName = RequestBody.create(MediaType.parse("text/plain"), sponsoreName);
        RequestBody requestBodyuserId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody requestBodytournamentId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tournamentId));

        MultipartBody.Part body=null;
        MultipartBody.Part bodyBanner=null;

        if (uri != null) {
            File file = new File(uri.getPath());
            String mimeType = getMimeType(file);
            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);

            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("tournament_logo", file.getName(), requestFile);

            // Proceed with the rest of your logic, e.g., making a network request with 'body'
        } else {
            // Handle the null case
            Log.e("FileUpload", "URI is null, cannot proceed with file upload.");
        }

        if (uriBanner != null) {
            File file = new File(uriBanner.getPath());
            String mimeType = getMimeType(file);
            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);

            // MultipartBody.Part is used to send also the actual file name
            bodyBanner = MultipartBody.Part.createFormData("tournament_banner", file.getName(), requestFile);

            // Proceed with the rest of your logic, e.g., making a network request with 'body'
        } else {
            // Handle the null case
            Log.e("FileUpload", "URI is null, cannot proceed with file upload.");
        }



        Call<AddTournamentResponse> initiateAddTournament = apiRequest.getAddTournamentResponse(token, requestBodytournamentType, requestBodyprize,
                requestBodyballType, requestBodytName, requestBodystartDate, requestBodyendDate, requestBodystate, requestBodycity,
                requestBodylocation, requestBodygroundName, requestBodyfees, requestBodydiscount, requestBodynumOfTeam, requestBodysponsoreName,
                requestBodyuserId,body,bodyBanner);

        initiateAddTournament.enqueue(new Callback<AddTournamentResponse>() {
            @Override
            public void onResponse(Call<AddTournamentResponse> call, Response<AddTournamentResponse> response) {
                Log.e(TAG, response + "");

                if (response.isSuccessful()) {
                    iAddTournamentResponse.onResponse(response.body(), false);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.e(TAG, jObjError.toString());
                        iAddTournamentResponse.onResponse(response.body(), true);
                        Toaster.customToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toaster.customToast(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<AddTournamentResponse> call, Throwable t) {
                iAddTournamentResponse.onFailure(t);
            }
        });

    }

    public interface IAddTournamentResponse {
        void onResponse(AddTournamentResponse addTournamentResponse, Boolean status);

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
