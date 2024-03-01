package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;

import com.cricoscore.Adapter.ShortAreaSubCategoryAdapter;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityWagonWheelBinding;
import com.cricoscore.databinding.ToolbarBinding;

import java.util.Objects;


public class WagonWheelActivity extends AppCompatActivity {
    ActivityWagonWheelBinding activityWagonWheelBinding;
    Context mContext;
    Activity mActivity;
    private String selectedSegment = "";
    private String selectedShortArea = "";
    boolean checkBoxStatus;
    boolean checkBoxDitBallStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWagonWheelBinding = ActivityWagonWheelBinding.inflate(getLayoutInflater());
        setContentView(activityWagonWheelBinding.getRoot());
        mContext = this;
        mActivity = this;

        checkBoxStatus = SessionManager.getWW1sBoolean();
        checkBoxDitBallStatus = SessionManager.getWWDotBoolean();

        ToolbarBinding toolbarBinding = activityWagonWheelBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(mContext.getResources().getString(R.string.wogen_wheel));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        activityWagonWheelBinding.wagonWheel.setOnSegmentClickListener(segmentText -> {
            activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);


            switch (segmentText) {
                case "Deep\n Mid Wicket":
                    activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

                    activityWagonWheelBinding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setHasFixedSize(true);
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setAdapter(new ShortAreaSubCategoryAdapter(mContext, Global.getMidWicketSubShortArea(mContext), value -> {
                        selectedShortArea = value;
                        activityWagonWheelBinding.rlButtonGroup.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);
                    }));
                    break;
                case "Long On":
                    activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

                    activityWagonWheelBinding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setHasFixedSize(true);
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setAdapter(new ShortAreaSubCategoryAdapter(mContext, Global.getLongOnSubShortArea(mContext), value -> {
                        selectedShortArea = value;
                        activityWagonWheelBinding.rlButtonGroup.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);
                    }));

                    break;
                case "Long Off":
                    activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

                    activityWagonWheelBinding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setHasFixedSize(true);
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setAdapter(new ShortAreaSubCategoryAdapter(mContext, Global.getLongOffSubShortArea(mContext), value -> {
                        selectedShortArea = value;
                        activityWagonWheelBinding.rlButtonGroup.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);
                    }));

                    break;
                case "Deep Cover":
                    activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

                    activityWagonWheelBinding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setHasFixedSize(true);
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setAdapter(new ShortAreaSubCategoryAdapter(mContext, Global.getDeepCoverSubShortArea(mContext), value -> {
                        selectedShortArea = value;
                        activityWagonWheelBinding.rlButtonGroup.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);
                    }));

                    break;
                case "Deep Point":

                    activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

                    activityWagonWheelBinding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setHasFixedSize(true);
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setAdapter(new ShortAreaSubCategoryAdapter(mContext, Global.getDeepPointSubShortArea(mContext), value -> {
                        selectedShortArea = value;
                        activityWagonWheelBinding.rlButtonGroup.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);
                    }));

                    break;
                case "Third Man":
                    activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

                    activityWagonWheelBinding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setHasFixedSize(true);
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setAdapter(new ShortAreaSubCategoryAdapter(mContext, Global.getThirdManSubShortArea(mContext), value -> {
                        selectedShortArea = value;
                        activityWagonWheelBinding.rlButtonGroup.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);
                    }));

                    break;
                case "Deep\n Fine Leg":
                    activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

                    activityWagonWheelBinding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setHasFixedSize(true);
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setAdapter(new ShortAreaSubCategoryAdapter(mContext, Global.getDeepFineLegSubShortArea(mContext), value -> {
                        selectedShortArea = value;
                        activityWagonWheelBinding.rlButtonGroup.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);
                    }));

                    break;
                case "Deep\n Square Leg":
                    activityWagonWheelBinding.liShortAreaDetails.setVisibility(View.VISIBLE);

                    activityWagonWheelBinding.rcvSubCategoryShortArea.setLayoutManager(new GridLayoutManager(mContext, 3));
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setHasFixedSize(true);
                    activityWagonWheelBinding.rcvSubCategoryShortArea.setAdapter(new ShortAreaSubCategoryAdapter(mContext, Global.getDeepSquareLegSubShortArea(mContext), value -> {
                        selectedShortArea = value;
                        activityWagonWheelBinding.rlButtonGroup.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> activityWagonWheelBinding.scrollView.fullScroll(View.FOCUS_DOWN), 600);
                    }));

                    break;
            }

            if (segmentText.contains("\n")) {
                segmentText = segmentText.replace("\n", "");
            }
            selectedSegment = segmentText;
            activityWagonWheelBinding.tvShortArea.setText(segmentText);

        });

        activityWagonWheelBinding.tvNonOfAbove.setOnClickListener(view -> {
            activityWagonWheelBinding.rlButtonGroup.setVisibility(View.GONE);
            Toaster.customToast(selectedSegment);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        });
        activityWagonWheelBinding.mbSave.setOnClickListener(view -> {
                    Toaster.customToast(selectedSegment + "  " + selectedShortArea);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);

                });
        activityWagonWheelBinding.mbCancel.setOnClickListener(view -> {

        });

        activityWagonWheelBinding.cbDontShowWWForDotsBall.setChecked(checkBoxDitBallStatus);
        activityWagonWheelBinding.cbDontShowWWForDotsBall.setOnCheckedChangeListener((compoundButton, b) -> {

            if(b==true){
                SessionManager.saveWWDotBoolean(b);
            }else{
                SessionManager.saveWWDotBoolean(false);
            }


        });

        activityWagonWheelBinding.cbDontShowWWFor1s.setChecked(checkBoxStatus);
        activityWagonWheelBinding.cbDontShowWWFor1s.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b==true){
                SessionManager.saveWW1sBoolean(b);
            }else{
                SessionManager.saveWW1sBoolean(false);
            }

        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}