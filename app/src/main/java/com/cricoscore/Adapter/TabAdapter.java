package com.cricoscore.Adapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.cricoscore.Fragment.LiveFragment;
import com.cricoscore.Fragment.MatchFragment;
import com.cricoscore.Fragment.TournamentFragment;

public class TabAdapter extends FragmentStateAdapter {


    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new TournamentFragment();
                break;
            case 1:
                fragment = new MatchFragment();
                break;
//            case 2:
//                fragment = new LiveFragment();
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }

        return fragment;

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}