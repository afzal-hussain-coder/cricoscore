package com.cricoscore.view_model;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cricoscore.ApiResponse.AddTeamResponse;
import com.cricoscore.ApiResponse.ResponseStatus;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.repository.AddTeamRepo;
import com.cricoscore.repository.AddTournamentRepository;

public class AddTeamViewMode extends ViewModel {
    private AddTeamRepo addTeamRepo;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<ResponseStatus> mAddTournamentResultMutableData = new MutableLiveData<>();

    public  AddTeamViewMode(){
        mProgressMutableData.postValue(View.GONE);
        mAddTournamentResultMutableData.postValue(new ResponseStatus(false,""));
        addTeamRepo = new AddTeamRepo();
    }

    public void getAddTeamResponse(String token, String UserId, String city, String name, Uri teamLogo){
        mProgressMutableData.postValue(View.VISIBLE);

        addTeamRepo.getAddTeamResponse(token, UserId, city, name, teamLogo, new AddTeamRepo.IAddTeamResponse() {
            @Override
            public void onResponse(AddTeamResponse addTeamResponse, Boolean status) {
                mProgressMutableData.postValue(View.GONE);
                if(!status){
                    Toaster.customToast(addTeamResponse.getMessage());
                    mAddTournamentResultMutableData.postValue(new ResponseStatus(status,addTeamResponse.getMessage()));
                }else mAddTournamentResultMutableData.postValue(new ResponseStatus(status,addTeamResponse.getMessage()));
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
                mAddTournamentResultMutableData.postValue(new ResponseStatus(false,""));
                Log.d("Login failure: ", t.getLocalizedMessage());
            }
        });
    }

    public LiveData<Integer> getSubmitProfileProgress() {
        return mProgressMutableData;
    }

    public LiveData<ResponseStatus> getAddTournamentResult() {
        return mAddTournamentResultMutableData;
    }

}
