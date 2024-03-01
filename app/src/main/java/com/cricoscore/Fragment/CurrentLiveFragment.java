package com.cricoscore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricoscore.R;
import com.cricoscore.databinding.FragmentCommentryBinding;
import com.cricoscore.databinding.FragmentCurrentLiveBinding;


public class CurrentLiveFragment extends Fragment {


    FragmentCurrentLiveBinding fragmentCurrentLiveBinding;
    public CurrentLiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCurrentLiveBinding = FragmentCurrentLiveBinding.inflate(inflater,container,false);
        return fragmentCurrentLiveBinding.getRoot();
    }
}