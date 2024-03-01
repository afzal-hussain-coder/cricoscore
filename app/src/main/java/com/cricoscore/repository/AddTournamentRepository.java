package com.cricoscore.repository;

import android.util.Log;

import com.cricoscore.ApiResponse.AddTournamentResponse;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import okhttp3.MediaType;
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
                                         String sponsoreName, String organizerName, String organizerPhone, String organizerEmail, int userId, int tournamentId
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
        RequestBody requestBodyorganizerName = RequestBody.create(MediaType.parse("text/plain"), organizerName);
        RequestBody requestBodyorganizerPhone = RequestBody.create(MediaType.parse("text/plain"), organizerPhone);
        RequestBody requestBodyorganizerEmail = RequestBody.create(MediaType.parse("text/plain"), organizerEmail);
        RequestBody requestBodyuserId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody requestBodytournamentId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tournamentId));

        Call<AddTournamentResponse> initiateAddTournament = apiRequest.getAddTournamentResponse(token, requestBodytournamentType, requestBodyprize,
                requestBodyballType, requestBodytName, requestBodystartDate, requestBodyendDate, requestBodystate, requestBodycity,
                requestBodylocation, requestBodygroundName, requestBodyfees, requestBodydiscount, requestBodynumOfTeam, requestBodysponsoreName,
                requestBodygroundName, requestBodyorganizerPhone, requestBodyorganizerEmail, requestBodytournamentId, requestBodyuserId);

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


}
