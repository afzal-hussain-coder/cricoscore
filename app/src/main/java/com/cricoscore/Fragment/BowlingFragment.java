package com.cricoscore.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Adapter.BowlingLeaderboardAdapter;
import com.cricoscore.ApiResponse.StateModel;
import com.cricoscore.R;
import com.cricoscore.Utils.CustomLoaderView;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.BattingLeaderboatrdModel;
import com.cricoscore.model.BowlingLeaderboatrdModel;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BowlingFragment extends Fragment {
    BowlingLeaderboardAdapter bowlingLeaderboardAdapter;
    RecyclerView recyclerView;
    CustomLoaderView customLoaderView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_batting, container, false);

        customLoaderView = CustomLoaderView.initialize(getActivity());
        if (Global.isOnline(getContext())) {
            getFetchData();
        } else {
            Global.showDialog(getActivity());
        }

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    public void getFetchData() {
        //Global.showLoader(getActivity().getSupportFragmentManager());
        customLoaderView.showLoader();

        ApiRequest apiService = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        Call<ResponseBody> call = apiService.getLeaderBoard(SessionManager.getToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               // Global.hideLoder();
                customLoaderView.hideLoader();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string(); // Get the JSON string
                        JSONObject jsonObject = new JSONObject(jsonString); // Convert to JSONObject

                        List<BowlingLeaderboatrdModel> battingList = new ArrayList<>();

                        if (jsonObject.has("data")) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArrayBatting = jsonObject1.getJSONArray("bowling_leader_board");
                            for (int i = 0; i < jsonArrayBatting.length(); i++) {
                                battingList.add(new BowlingLeaderboatrdModel(jsonArrayBatting.getJSONObject(i)));
                            }
                        }

                        bowlingLeaderboardAdapter = new BowlingLeaderboardAdapter(getActivity(),battingList);
                        recyclerView.setAdapter(bowlingLeaderboardAdapter);

                       Log.d("BattingResponse",jsonObject.toString());


                    } catch (Exception e) {
                        Log.e("Error", "JSON parsing error: " + e.getMessage());
                    }
                } else {
                    Log.e("Error", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", "Failed to fetch data: " + t.getMessage());
                //Global.hideLoder();
                customLoaderView.hideLoader();
            }
        });
    }
}