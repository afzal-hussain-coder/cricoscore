package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.cricoscore.Adapter.BatsmanScorecardAdapter;
import com.cricoscore.Adapter.BowlerScorecardAdapter;
import com.cricoscore.Adapter.TabAdapter;
import com.cricoscore.Adapter.TabScorcardAdapter;
import com.cricoscore.R;
import com.cricoscore.databinding.ActivityScorecardBinding;
import com.cricoscore.databinding.ActivityStartLiveScoringBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class ScorecardActivity extends AppCompatActivity {

    ActivityScorecardBinding activityScorecardBinding;
    Context mContext;
    Activity mActivity;


    TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabScorcardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityScorecardBinding = ActivityScorecardBinding.inflate(getLayoutInflater());
        setContentView(activityScorecardBinding.getRoot());
        mContext = this;
        mActivity = this;

        ToolbarBinding toolbarBinding = activityScorecardBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(getResources().getString(R.string.scorecard));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());

        activityScorecardBinding.tabLayout.addTab(activityScorecardBinding.tabLayout.newTab().setText(mContext.getResources().getString(R.string.live)));
        activityScorecardBinding.tabLayout.addTab(activityScorecardBinding.tabLayout.newTab().setText(mContext.getResources().getString(R.string.scorecard)));
        activityScorecardBinding.tabLayout.addTab(activityScorecardBinding.tabLayout.newTab().setText(mContext.getResources().getString(R.string.commentary)));
        activityScorecardBinding.tabLayout.addTab(activityScorecardBinding.tabLayout.newTab().setText(mContext.getResources().getString(R.string.info)));
        activityScorecardBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new TabScorcardAdapter(fragmentManager , getLifecycle());
        activityScorecardBinding.viewPager.setAdapter(adapter);

        activityScorecardBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                activityScorecardBinding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        activityScorecardBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                activityScorecardBinding.tabLayout.selectTab(activityScorecardBinding.tabLayout.getTabAt(position));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}