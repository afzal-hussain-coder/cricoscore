package com.cricoscore.repository;

import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

public class MyTournamentRepo {
    private static final String TAG = MyTournamentRepo.class.getSimpleName();
    ApiRequest apiRequest;

    public MyTournamentRepo(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }


    public void getMyTournamentResponse(String token,String userId){

    }

    public interface IInterfaceOfMyTournament{
        void onResponse();
    }
}
