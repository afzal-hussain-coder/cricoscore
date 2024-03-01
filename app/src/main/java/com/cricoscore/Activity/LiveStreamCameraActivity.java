package com.cricoscore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cricoscore.R;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityLiveStreamCameraBinding;
import com.cricoscore.databinding.BottomDialogCreateStreamkeyBinding;
import com.cricoscore.databinding.ToolbarBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.permissionx.guolindev.PermissionX;

import java.util.Objects;

import cn.nodemedia.NodePublisher;

 //activityLiveStreamCameraBinding.startBtn.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(
//                                        getResources(), R.color.purple_700, null)));

public class LiveStreamCameraActivity extends AppCompatActivity {
    ActivityLiveStreamCameraBinding activityLiveStreamCameraBinding;
    BottomDialogCreateStreamkeyBinding bottomDialogCreateStreamkeyBinding;
    Context mContext;
    Activity mActivity;
    ToolbarBinding toolbarBinding;

    NodePublisher nodePublisher;
    boolean isCameraSwitch = false;
    boolean isAudioEnable = true;
    boolean isStartStreaming = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLiveStreamCameraBinding = ActivityLiveStreamCameraBinding.inflate(getLayoutInflater());
        setContentView(activityLiveStreamCameraBinding.getRoot());
        mContext = this;
        mActivity = this;

        toolbarBinding = activityLiveStreamCameraBinding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbarBinding.toolbartext.setText(mContext.getResources().getString(R.string.liveStreamYourLocalMatch));
        toolbarBinding.toolbar.setNavigationOnClickListener(v -> finish());


        nodePublisher = new NodePublisher(this, "");


        activityLiveStreamCameraBinding.startBtn.setOnClickListener(view -> {
            PermissionX.init(this).permissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {

                            isStartStreaming = !isStartStreaming;

                            if(isStartStreaming==true){
                                toolbarBinding.toolbar.setVisibility(View.GONE);
                                activityLiveStreamCameraBinding.startBtn.setText(mContext.getResources().getString(R.string.stop_streaming));
                                startPublishing(isCameraSwitch,isAudioEnable);

                            }else{
                                activityLiveStreamCameraBinding.startBtn.setText(getResources().getString(R.string.start_streaming));
                                nodePublisher.closeCamera();
                                nodePublisher.stop();
                                toolbarBinding.toolbar.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Toaster.customToast("permissions are required");
                        }
                    });

        });

        activityLiveStreamCameraBinding.startSwitch.setOnClickListener(view -> {
            isCameraSwitch = !isCameraSwitch;
            nodePublisher.closeCamera();
            nodePublisher.openCamera(isCameraSwitch);
        });

        activityLiveStreamCameraBinding.btAudio.setOnClickListener(view -> {
            isAudioEnable = !isAudioEnable;
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);

            if(isAudioEnable == false){
                audioManager.setMicrophoneMute(false);
                activityLiveStreamCameraBinding.btAudio.setBackground(getDrawable(R.drawable.mic_off));
            }else{
                audioManager.setMicrophoneMute(true);
                activityLiveStreamCameraBinding.btAudio.setBackground(getDrawable(R.drawable.mic_on));
            }

            startPublishing(isCameraSwitch,isAudioEnable);

        });


        showBottomStopMatchSheetDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startPublishing(boolean value, boolean isAudioEnabled) {

        nodePublisher.closeCamera();
        if(isAudioEnabled==true){
            nodePublisher.setAudioCodecParam(NodePublisher.NMC_CODEC_ID_AAC, NodePublisher.NMC_PROFILE_AUTO, 48000, 1, 64_000);
        }else{
            nodePublisher.setAudioCodecParam(NodePublisher.NMC_CODEC_ID_H265, NodePublisher.NMC_PROFILE_AUTO, 0, 0, 0);
        }
        //nodePublisher.setVideoOrientation(NodePublisher.VIDEO_ORIENTATION_LANDSCAPE_LEFT);
        nodePublisher.setVideoCodecParam(NodePublisher.NMC_CODEC_ID_H264,
                NodePublisher.NMC_PROFILE_AUTO, 1280, 720, 20, 25_500_00);
        nodePublisher.openCamera(value);
        nodePublisher.attachView(activityLiveStreamCameraBinding.publishingLayout);
        nodePublisher.start("rtmp://180.179.210.3:1949/show/live");

    }


    private void showBottomStopMatchSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomDialogCreateStreamkeyBinding = BottomDialogCreateStreamkeyBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomDialogCreateStreamkeyBinding.getRoot());
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        

        bottomDialogCreateStreamkeyBinding.editTextStreamKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setErrorEnabled(false);
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setErrorEnabled(false);
                    bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setErrorEnabled(false);
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        bottomDialogCreateStreamkeyBinding.editTextStreamKey.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (Objects.requireNonNull(bottomDialogCreateStreamkeyBinding.editTextStreamKey.getText()).length() == 0) {
                    bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });

        bottomDialogCreateStreamkeyBinding.mbSave.setOnClickListener(view -> {

            if (Objects.requireNonNull(bottomDialogCreateStreamkeyBinding.editTextStreamKey.getText()).toString().isEmpty()
                    || bottomDialogCreateStreamkeyBinding.editTextStreamKey.getText().toString().length() < 3
                    || bottomDialogCreateStreamkeyBinding.editTextStreamKey.getText().toString().length() > 21) {
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setErrorEnabled(true);
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                bottomDialogCreateStreamkeyBinding.filledTextFieldStreamKey.setError(getResources().getString(R.string.streamKey_msg));
            }else{
                toolbarBinding.toolbar.setVisibility(View.GONE);
                bottomSheetDialog.dismiss();
            }


        });
        bottomDialogCreateStreamkeyBinding.mbCancel.setOnClickListener(view -> {
         finish();
        });

        bottomSheetDialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nodePublisher.detachView();
        nodePublisher.closeCamera();
        nodePublisher.stop();
    }
}