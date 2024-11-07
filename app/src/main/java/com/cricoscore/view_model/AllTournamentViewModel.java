package com.cricoscore.view_model;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cricoscore.ApiResponse.ResponseStatus;
import com.cricoscore.ApiResponse.Tournament;
import com.cricoscore.ApiResponse.TournamentResponse;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AuthModel.AuthData;
import com.cricoscore.repository.TournamentListRepository;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import java.util.ArrayList;
import java.util.List;

public class AllTournamentViewModel extends ViewModel {

    private final TournamentListRepository tournamentListRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();;
    MutableLiveData<ArrayList<Tournament>> mTournamentListResultMutableData;
    MutableLiveData<Boolean> mSignUpResultMutableData = new MutableLiveData<>();

    public AllTournamentViewModel() {
        mProgressMutableData.postValue(View.GONE);
        mSignUpResultMutableData.postValue(false);
        mTournamentListResultMutableData = new MutableLiveData<>();
        tournamentListRepository = new TournamentListRepository();

    }

    public void getTournamentListResponse(String token) {
        mProgressMutableData.setValue(View.VISIBLE);

       tournamentListRepository.getAllTournamentResponse(token, new TournamentListRepository.IListOFTournamentResponse() {
           @Override
           public void onResponse(TournamentResponse tournamentLIstResponse, Boolean status) {
               mProgressMutableData.setValue(View.GONE);
               if(!status){
                  mTournamentListResultMutableData.postValue((ArrayList<Tournament>) tournamentLIstResponse.getData());
                   mSignUpResultMutableData.postValue(tournamentLIstResponse.isStatus());
               }else mSignUpResultMutableData.postValue(false);
           }

           @Override
           public void onFailure(Throwable t) {
               mProgressMutableData.postValue(View.GONE);
               Log.d("Login failure: ", t.getLocalizedMessage());
           }
       });

    }

    public LiveData<Integer> getTournamentListProgress() {
        return mProgressMutableData;
    }

    public LiveData<ArrayList<Tournament>> getTournamentListData() {
        return mTournamentListResultMutableData;
    }


}
