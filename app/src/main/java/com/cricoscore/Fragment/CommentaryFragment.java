package com.cricoscore.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricoscore.Adapter.CommentaryAdapter;
import com.cricoscore.Adapter.StartLiveScoringAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.SelectStatusType;
import com.cricoscore.databinding.FragmentCommentryBinding;
import com.cricoscore.databinding.FragmentLiveBinding;

import java.util.ArrayList;

public class CommentaryFragment extends Fragment {


    FragmentCommentryBinding fragmentCommentryBinding;

    private ArrayList<DataModel> option_teamType_list = new ArrayList<>();
    private ArrayList<DataModel> option_commentaryType_list = new ArrayList<>();
    private String teamTypeSelection="";
    private String commentaryTypeSelection="";

    public CommentaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCommentryBinding = FragmentCommentryBinding.inflate(inflater,container,false);
        return fragmentCommentryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        option_teamType_list.add(new DataModel("Team A"));
        option_teamType_list.add(new DataModel("Team B"));
        fragmentCommentryBinding.dropTeamSelection.setOptionList(option_teamType_list);
        teamTypeSelection = option_teamType_list.get(0).getName();
        fragmentCommentryBinding.dropTeamSelection.setText(teamTypeSelection);
        fragmentCommentryBinding.dropTeamSelection.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    fragmentCommentryBinding.dropTeamSelection.setCompoundDrawableTintList(getContext().getResources().getColorStateList(R.color.black));
                }
            }


            @Override
            public void onDismiss() {
            }
        });

        option_commentaryType_list.add(new DataModel(getContext().getResources().getString(R.string.full_commentary)));
        option_commentaryType_list.add(new DataModel(getContext().getResources().getString(R.string.wickets)));
        option_commentaryType_list.add(new DataModel(getContext().getResources().getString(R.string.boundaries)));
        fragmentCommentryBinding.dropCommentaryType.setOptionList(option_commentaryType_list);
        commentaryTypeSelection = option_commentaryType_list.get(0).getName();
        fragmentCommentryBinding.dropCommentaryType.setText(commentaryTypeSelection);
        fragmentCommentryBinding.dropCommentaryType.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    fragmentCommentryBinding.dropCommentaryType.setCompoundDrawableTintList(getContext().getResources().getColorStateList(R.color.black));
                }
            }


            @Override
            public void onDismiss() {
            }
        });

        fragmentCommentryBinding.rcvCommentary.setHasFixedSize(true);
        fragmentCommentryBinding.rcvCommentary.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentCommentryBinding.rcvCommentary.setAdapter(new CommentaryAdapter(getContext()));

    }
}