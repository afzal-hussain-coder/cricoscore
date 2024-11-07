package com.cricoscore.view_model;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cricoscore.ApiResponse.AddTournamentResponse;
import com.cricoscore.ApiResponse.ResponseStatus;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.repository.AddTournamentRepository;

import java.net.URI;

public class AddTournamentViewModel extends ViewModel {
    private final AddTournamentRepository addTournamentRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<ResponseStatus> mAddTournamentResultMutableData = new MutableLiveData<>();

    public AddTournamentViewModel(){
        mProgressMutableData.postValue(View.GONE);
        mAddTournamentResultMutableData.postValue(new ResponseStatus(false,""));
        addTournamentRepository = new AddTournamentRepository();
    }

    public void getAddTournamentResponse(String token, String tournamentType, String prize, String ballType, String tName, String startDate, String endDate,
                                         String state, String city, String location, String groundName, float fees, int discount, int numOfTeam,
                                         String sponsoreName, int userId, int tournamentId,Uri uri,Uri uriBanner) {

        mProgressMutableData.postValue(View.VISIBLE);
        addTournamentRepository.getAddTournamentResponse(token, tournamentType, prize, ballType,
                tName, startDate, endDate, state, city, location, groundName, fees, discount, numOfTeam, sponsoreName, userId,
                tournamentId,uri,uriBanner, new AddTournamentRepository.IAddTournamentResponse() {
                    @Override
                    public void onResponse(AddTournamentResponse addTournamentResponse, Boolean status) {
                        mProgressMutableData.postValue(View.GONE);
                        if(!status){
                            Toaster.customToast(addTournamentResponse.getMessage());
                            mAddTournamentResultMutableData.postValue(new ResponseStatus(true,addTournamentResponse.getMessage()));
                        }else mAddTournamentResultMutableData.postValue(new ResponseStatus(false,addTournamentResponse.getMessage()));
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
