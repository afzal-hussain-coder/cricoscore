package com.cricoscore.CustomeCamera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import com.cricoscore.Activity.AddMatchActivity;
import com.cricoscore.Activity.AddPlayer;
import com.cricoscore.Activity.AddTeamActivity;
import com.cricoscore.Activity.AddTournamentActivity;
import com.cricoscore.Activity.ScheduleCricketDetailsActivity;
import com.cricoscore.Activity.ScheduleMatchActivity;
import com.cricoscore.Activity.VisitProfileActivity;
import com.cricoscore.Fragment.MatchFragment;
import com.cricoscore.Fragment.TournamentFragment;
import com.cricoscore.R;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.databinding.ActivityCustomeCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class CustomeCameraActivity extends AppCompatActivity {


    ActivityCustomeCameraBinding activityCustomeCameraBinding;
    int cameraFacing = CameraSelector.LENS_FACING_BACK;
    Context mContext;
    Activity mActivity;

    private String FROM = "";

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
        if (result) {
            startCamera(cameraFacing);
        }
    });

    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), o -> {
        if (o == null) {
            Toaster.customToast("No image Selected");
        } else {
            CropImage.activity(o)
                    .start(CustomeCameraActivity.this);
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCustomeCameraBinding = ActivityCustomeCameraBinding.inflate(getLayoutInflater());
        setContentView(activityCustomeCameraBinding.getRoot());
        mActivity = this;
        mContext = this;

        if (ContextCompat.checkSelfPermission(CustomeCameraActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.CAMERA);
        } else {
            startCamera(cameraFacing);
        }

        FROM = getIntent().getStringExtra("FROM");

        activityCustomeCameraBinding.flipCamera.setOnClickListener(view -> {
            if (cameraFacing == CameraSelector.LENS_FACING_BACK) {
                cameraFacing = CameraSelector.LENS_FACING_FRONT;
                activityCustomeCameraBinding.toggleFlash.setVisibility(View.GONE);
            } else {
                activityCustomeCameraBinding.toggleFlash.setVisibility(View.VISIBLE);
                cameraFacing = CameraSelector.LENS_FACING_BACK;
            }
            startCamera(cameraFacing);
        });

        activityCustomeCameraBinding.ibClose.setOnClickListener(v -> finish());

        activityCustomeCameraBinding.gallery.setOnClickListener(v -> launcher.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
    }

    public void startCamera(int cameraFacing) {
        int aspectRatio = aspectRatio(activityCustomeCameraBinding.cameraPreview.getWidth(), activityCustomeCameraBinding.cameraPreview.getHeight());
        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) listenableFuture.get();

                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();

                ImageCapture imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(cameraFacing).build();

                cameraProvider.unbindAll();

                Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

                activityCustomeCameraBinding.capture.setOnClickListener(view -> {
                    if (ContextCompat.checkSelfPermission(CustomeCameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    takePicture(imageCapture);
                });

                activityCustomeCameraBinding.toggleFlash.setOnClickListener(view -> setFlashIcon(camera));

                preview.setSurfaceProvider(activityCustomeCameraBinding.cameraPreview.getSurfaceProvider());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    public void takePicture(ImageCapture imageCapture) {
        final File file = new File(getExternalFilesDir(null), System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        imageCapture.takePicture(outputFileOptions, Executors.newCachedThreadPool(), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {


                runOnUiThread(() -> CropImage.activity(outputFileResults.getSavedUri())
                        .start(CustomeCameraActivity.this)
                );
                startCamera(cameraFacing);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {

                runOnUiThread(() -> Toaster.customToast("Image not saved " + exception.getMessage()));
                startCamera(cameraFacing);
            }
        });
    }

    private void setFlashIcon(Camera camera) {
        if (camera.getCameraInfo().hasFlashUnit()) {
            if (camera.getCameraInfo().getTorchState().getValue() == 0) {
                camera.getCameraControl().enableTorch(true);
                activityCustomeCameraBinding.toggleFlash.setImageResource(R.drawable.baseline_flash_on_24);
            } else {
                camera.getCameraControl().enableTorch(false);
                activityCustomeCameraBinding.toggleFlash.setImageResource(R.drawable.baseline_flash_off_24);
            }
        } else {
            runOnUiThread(() -> Toaster.customToast("Flash is not available currently"));
        }
    }

    /*Aspect ratio of frame layout of camera */
    private int aspectRatio(int width, int height) {
        double previewRatio = (double) Math.max(width, height) / Math.min(width, height);
        if (Math.abs(previewRatio - 4.0 / 3.0) <= Math.abs(previewRatio - 16.0 / 9.0)) {
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }


    /*OnActivity result is getting the value of camera feed from device and camera*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri resultUri = result.getUri();

                switch (FROM) {
                    case "ScheduleMatchActivity":
                        ScheduleMatchActivity.image_uri = resultUri;
                        finish();
                        break;
                    case "ScheduleCricketDetailsActivity":
                        ScheduleCricketDetailsActivity.image_uri = resultUri;
                        finish();
                        break;
                    case "TournamentFragment":
                        TournamentFragment.image_uri = resultUri;
                        finish();
                        break;

                    case "MatchFragment":
                        MatchFragment.image_uri = resultUri;
                        finish();
                        break;
                    case "AddTournamentActivity":
                        AddTournamentActivity.image_uri = resultUri;
                        finish();
                        break;
                    case "AddMatchActivity":
                        AddMatchActivity.image_uri = resultUri;
                        finish();
                        break;

                    case "AddPlayer":
                        AddPlayer.image_uri = resultUri;
                        finish();
                        break;

                    case "AddTeamActivity":
                        AddTeamActivity.image_uri = resultUri;
                        finish();
                        break;
                    case "VisitProfileActivity":
                        VisitProfileActivity.image_uri = resultUri;
                        finish();
                        break;
                    default: {
                        finish();
                    }
                }

            }
        }
    }

}