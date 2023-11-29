package com.cricoscore.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.cricoscore.Adapter.MenuAdapter;
import com.cricoscore.Adapter.TabAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.DataModel;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SelectStatusType;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.SharedPreferencesManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.Drawer;
import com.cricoscore.view_model.SignUpViewModel;
import com.cricoscore.view_model.UserProfileViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView list_nav;
    private TextView txt_nav_name;
    private ProgressDialog progress;
    private MenuAdapter menuadapter;
    private DrawerLayout drawer;
    private ArrayList<Drawer> drawerList;
    Activity mActivity;
    Context mContext;
    TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabAdapter adapter;
    SelectStatusType drop_pStatus;
    private ArrayList<DataModel> option_status_list = new ArrayList<>();
    String filterTypeStatus="";

    UserProfileViewModel userProfileViewModel;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        mContext = this;

        userProfileViewModel =  new ViewModelProvider(this).get(UserProfileViewModel.class);

        userProfileViewModel.getUserProfileResult().observe(this,aBoolean -> {
         if(aBoolean){
             txt_nav_name.setText(SessionManager.getUserName());
         }
        });
        userProfileViewModel.getUserProfileLoader().observe(this, integer -> {
            if (integer == 0) {
                Global.showLoader(getSupportFragmentManager());
            } else {
                Global.hideLoder();
            }
        });

        if(Global.isOnline(mContext)){
            userProfileViewModel.getUserProfile(SessionManager.getToken(),SessionManager.getUserId());
        }else{
            Global.showDialog(mActivity);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        TextView mTitle = toolbar.findViewById(R.id.toolbartext);
        mTitle.setText(getResources().getString(R.string.home));
        mTitle.setVisibility(View.GONE);


        drawer = findViewById(R.id.drawer_layout);
        list_nav = findViewById(R.id.list_nav);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we don't want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
/*
                if (SessionManager.get_image(prefs) != null && !SessionManager.get_image(prefs).isEmpty())
                    Glide.with(mActivity).load(SessionManager.get_image(prefs)).dontAnimate().into(profile_pic);

                if (SessionManager.get_verified(prefs).equalsIgnoreCase("1")) {
                    iv_verified.setVisibility(View.VISIBLE);
                    if(SessionManager.get_verified(prefs).equalsIgnoreCase("1") &&
                            SessionManager.get_criconet_verified(prefs).equalsIgnoreCase("1")){
                        iv_verified.setColorFilter(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                    }else{
                        iv_verified.setColorFilter(ContextCompat.getColor(mActivity, R.color.verified_user_color));
                    }
                } else {
                    iv_verified.setVisibility(View.GONE);
                    iv_verified.setImageTintList(ContextCompat.getColorStateList(mActivity, R.color.bckground_light));
                }


                Glide.with(mActivity).load(SessionManager.get_cover(prefs)).dontAnimate().into(cover);
                //Toaster.customToast(SessionManager.get_user_name(prefs)+"/"+SessionManager.get_fname(prefs)+"/"+SessionManager.get_name(prefs));
                if (SessionManager.get_name(prefs).equalsIgnoreCase("")) {
                    txt_nav_name.setText(Global.capitalizeFirstLatterOfString(SessionManager.get_fname(prefs)));

                } else {
                    txt_nav_name.setText(Global.capitalizeFirstLatterOfString(SessionManager.get_name(prefs)));
                }*/

                //txt_nav_name.setText(SessionManager.getUserName(prefs));

            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setItemTextImageOnNavigationDrawer();

        initView();
    }

    private void initView() {
        txt_nav_name = findViewById(R.id.txt_nav_name);
        drop_pStatus = findViewById(R.id.drop_pStatus);
        option_status_list.add(new DataModel("All"));
        option_status_list.add(new DataModel("Ongoing"));
        option_status_list.add(new DataModel("Upcoming"));
        drop_pStatus.setOptionList(option_status_list);
//        filterTypeStatus = option_status_list.get(0).getName();
//        drop_pStatus.setText(filterTypeStatus);
        drop_pStatus.setClickListener(new SelectStatusType.onClickInterface() {
            @Override
            public void onClickAction() {
            }

            @Override
            public void onClickDone(String name) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drop_pStatus.setCompoundDrawableTintList(mContext.getResources().getColorStateList(R.color.black));
                }
            }


            @Override
            public void onDismiss() {
            }
        });
        tabLayout=findViewById(R.id.tabLayout);
        viewPager2=findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Tournament"));
        tabLayout.addTab(tabLayout.newTab().setText("Match"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new TabAdapter(fragmentManager , getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                drop_pStatus.setText(filterTypeStatus);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drop_pStatus.setCompoundDrawableTintList(mContext.getResources().getColorStateList(R.color.dark_grey));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void setItemTextImageOnNavigationDrawer() {
        drawerList = new ArrayList<>();
        drawerList.add(new Drawer(getString(R.string.home), false, R.drawable.home_black_24dp));
        drawerList.add(new Drawer(getString(R.string.live_scoring), false, R.drawable.sports_cricket_black_24dp));
        drawerList.add(new Drawer(getString(R.string.scorecard), false, R.drawable.sports_cricket_black_24dp));
        drawerList.add(new Drawer(getString(R.string.addTournament), false, R.drawable.sports_cricket_black_24dp));
        //drawerList.add(new Drawer(getString(R.string.ScheduleTournament), false, R.drawable.sports_cricket_black_24dp));
        drawerList.add(new Drawer(getString(R.string.addMatch), false, R.drawable.sports_cricket_black_24dp));
        drawerList.add(new Drawer(getString(R.string.schedule_match), false, R.drawable.sports_cricket_black_24dp));
        drawerList.add(new Drawer(getString(R.string.add_team), false, R.drawable.team));
        drawerList.add(new Drawer(getString(R.string.yourteamlist), false, R.drawable.team));
        drawerList.add(new Drawer(getString(R.string.addPlayer), false, R.drawable.player_icon));
        drawerList.add(new Drawer(getString(R.string.yourPlayerlist), false, R.drawable.player_icon));
        drawerList.add(new Drawer(getString(R.string.logout), false, R.drawable.logout_black_24dp));

        menuadapter = new MenuAdapter(mActivity, drawerList);
        list_nav.setAdapter(menuadapter);
        list_nav.addFooterView(new View(mActivity), null, true);

        progress = new ProgressDialog(mActivity);
        progress.setMessage(getString(R.string.loading));
        progress.setCancelable(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        list_nav.setOnItemClickListener((parent, view, position, id) -> navigationItemClick(position));


    }

    public void navigationItemClick(int position) {
        drawer = findViewById(R.id.drawer_layout);
        drawer.cancelPendingInputEvents();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.live_scoring))) {
            startActivity(new Intent(mActivity,StartLiveScoringActivity.class));
        }else  if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.scorecard))) {
            startActivity(new Intent(mActivity,ScorecardActivity.class));
        }
        else if (drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.addTournament))) {
            startActivity(new Intent(mActivity, AddTournamentActivity.class));
        }
//       else if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.schedule_tournament))) {
//           startActivity(new Intent(mActivity, ScheduleTournamentActivity.class));
//       }
        else if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.addMatch))) {
            startActivity(new Intent(mActivity, AddMatchActivity.class));
        }
        else if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.schedule_match))) {
            startActivity(new Intent(mActivity, ScheduleMatchActivity.class));
        }
        else if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.addPlayer))) {
            startActivity(new Intent(mActivity, AddPlayer.class));
        }else if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.yourteamlist))) {
            startActivity(new Intent(mActivity, YourTeamListActivity.class));
        }else if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.add_team))) {
            startActivity(new Intent(mActivity, AddTeamActivity.class));
        }else if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.yourPlayerlist))) {
            startActivity(new Intent(mActivity, YourPlayerListActivity.class));
        }else if(drawerList.get(position).getTitle().equalsIgnoreCase(getResources().getString(R.string.logout))) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("");
            alertDialog.setMessage(getResources().getString(R.string.Do_you_really_want_to_logout));
            alertDialog.setPositiveButton(getResources().getString(R.string.Yes),
                    (dialog, which) -> {
                        SharedPreferencesManager.clearPreferences();
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
            alertDialog.setNegativeButton(getResources().getString(R.string.No),
                    (dialog, which) -> {
                    });
            alertDialog.show();


        }

    }
}