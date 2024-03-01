package com.cricoscore.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricoscore.R;
import com.cricoscore.databinding.FragmentCommentryBinding;
import com.cricoscore.databinding.FragmentTeamsFragmentsBinding;

public class TeamsFragments extends Fragment {


    FragmentTeamsFragmentsBinding fragmentTeamsFragmentsBinding;
    public TeamsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentTeamsFragmentsBinding = FragmentTeamsFragmentsBinding.inflate(inflater,container,false);
        return fragmentTeamsFragmentsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

       /* fragmentLiveBinding.rvScoreList.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentLiveBinding.rvScoreList.setHasFixedSize(true);

        fragmentLiveBinding.rvScoreList.setAdapter(new StartLiveScoringAdapter(getContext(), new StartLiveScoringAdapter.ItemClickListener() {
            @Override
            public void itemClick(int pos) {
                showBottomSheetResumeScoringDialog();
            }

            @Override
            public void itemView(int pos) {
                showBottomSheetDialog();
            }
        }));*/
    }
}