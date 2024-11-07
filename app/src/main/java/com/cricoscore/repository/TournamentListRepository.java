package com.cricoscore.repository;

import android.util.Log;

import com.cricoscore.ApiResponse.TournamentResponse;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentListRepository {
    private static final String TAG = TournamentListRepository.class.getSimpleName();
    ApiRequest apiRequest;

    public TournamentListRepository(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

  public void getAllTournamentResponse(String token,IListOFTournamentResponse iListOFTournamentResponse){

        Call<TournamentResponse> init = apiRequest.getAllTournament(token);


        init.enqueue(new Callback<TournamentResponse>() {
            @Override
            public void onResponse(Call<TournamentResponse> call, Response<TournamentResponse> response) {
                if (response.isSuccessful()) {
                    iListOFTournamentResponse.onResponse(response.body(), false);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.e(TAG, jObjError.toString());
                        iListOFTournamentResponse.onResponse(response.body(), true);
                        Toaster.customToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toaster.customToast(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TournamentResponse> call, Throwable t) {
                iListOFTournamentResponse.onFailure(t);
            }
        });


  }

    public interface IListOFTournamentResponse{
        void onResponse(TournamentResponse tournamentLIstResponse,Boolean status);
        void onFailure(Throwable t);
    }

}
