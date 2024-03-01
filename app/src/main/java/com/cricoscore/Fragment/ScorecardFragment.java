package com.cricoscore.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStateManagerControl;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricoscore.Adapter.BatsmanScorecardAdapter;
import com.cricoscore.Adapter.BowlerScorecardAdapter;
import com.cricoscore.R;
import com.cricoscore.databinding.FragmentScorecardBinding;
import com.cricoscore.databinding.FragmentTeamsFragmentsBinding;

public class ScorecardFragment extends Fragment {

    FragmentScorecardBinding fragmentScorecardBinding;

    public ScorecardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentScorecardBinding = FragmentScorecardBinding.inflate(inflater,container,false);
        return fragmentScorecardBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Team Scorecard Details
        // BatsmanScore
        fragmentScorecardBinding.rvBatsmanTeam1.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentScorecardBinding.rvBatsmanTeam1.setHasFixedSize(true);
        fragmentScorecardBinding.rvBatsmanTeam1.setAdapter(new BatsmanScorecardAdapter(getContext()));

        fragmentScorecardBinding.rlTeam1Details.setOnClickListener(v -> {
            if(fragmentScorecardBinding.liTeam1Details.getVisibility()==View.GONE){
                fragmentScorecardBinding.liTeam1Details.setVisibility(View.VISIBLE);
                fragmentScorecardBinding.imgDownArrow.setVisibility(View.GONE);
                fragmentScorecardBinding.imgUpArrow.setVisibility(View.VISIBLE);
            }else{
                fragmentScorecardBinding.liTeam1Details.setVisibility(View.GONE);
                fragmentScorecardBinding.imgDownArrow.setVisibility(View.VISIBLE);
                fragmentScorecardBinding.imgUpArrow.setVisibility(View.GONE);
            }

        });
        //BowlerScore
        fragmentScorecardBinding.rvBowlerTeam1.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentScorecardBinding.rvBowlerTeam1.setHasFixedSize(true);
        fragmentScorecardBinding.rvBowlerTeam1.setAdapter(new BowlerScorecardAdapter(getContext()));

        //Team2 Scorecard Details
        /// BatsmanScore
        fragmentScorecardBinding.rvBatsmanTeam2.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentScorecardBinding.rvBatsmanTeam2.setHasFixedSize(true);
        fragmentScorecardBinding.rvBatsmanTeam2.setAdapter(new BatsmanScorecardAdapter(getContext()));

        fragmentScorecardBinding.rlTeam2Details.setOnClickListener(v -> {
            if(fragmentScorecardBinding.liTeam2Details.getVisibility()== View.GONE){
                fragmentScorecardBinding.liTeam2Details.setVisibility(View.VISIBLE);
                fragmentScorecardBinding.imgDownArrow2.setVisibility(View.GONE);
                fragmentScorecardBinding.imgUpArrow2.setVisibility(View.VISIBLE);
            }else{
                fragmentScorecardBinding.liTeam2Details.setVisibility(View.GONE);
                fragmentScorecardBinding.imgDownArrow2.setVisibility(View.VISIBLE);
                fragmentScorecardBinding.imgUpArrow2.setVisibility(View.GONE);
            }

        });

        //BowlerScore
        fragmentScorecardBinding.rvBowlerTeam2.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentScorecardBinding.rvBowlerTeam2.setHasFixedSize(true);
        fragmentScorecardBinding.rvBowlerTeam2.setAdapter(new BowlerScorecardAdapter(getContext()));
    }
}