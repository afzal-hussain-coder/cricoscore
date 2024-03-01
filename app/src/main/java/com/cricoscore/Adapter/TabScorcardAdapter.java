package com.cricoscore.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cricoscore.Fragment.CommentaryFragment;
import com.cricoscore.Fragment.CurrentLiveFragment;
import com.cricoscore.Fragment.LiveFragment;
import com.cricoscore.Fragment.MatchFragment;
import com.cricoscore.Fragment.ScorecardFragment;
import com.cricoscore.Fragment.TeamsFragments;
import com.cricoscore.Fragment.TournamentFragment;

public class TabScorcardAdapter extends FragmentStateAdapter {


    public TabScorcardAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new CurrentLiveFragment();
                break;
            case 1:
                fragment = new ScorecardFragment();
                break;
            case 2:
                fragment = new CommentaryFragment();
                break;
            case 3:
                fragment = new TeamsFragments();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }

        return fragment;

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}