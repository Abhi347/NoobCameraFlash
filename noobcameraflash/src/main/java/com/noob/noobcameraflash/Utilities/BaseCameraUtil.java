package com.noob.noobcameraflash.Utilities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Abhishek on 08-12-2016.
 */

public abstract class BaseCameraUtil implements CameraFlashUtility {
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1337;
    private Activity mContext;
    private boolean isCameraPermissionGranted = false;

    //private boolean torchModeOn = false;
    private TorchMode mTorchMode = TorchMode.None;
    private TorchModeCallback mTorchModeCallback;

    //region Constructors
    public BaseCameraUtil(Activity context) {
        mContext = context;
    }
    //endregion

    //region Overrides
    @Override
    public boolean isFlashOn() {
        return mTorchMode == TorchMode.SwitchedOn;
    }

    @Override
    public void setTorchModeCallback(TorchModeCallback torchModeCallback) {
        mTorchModeCallback = torchModeCallback;
    }

    @Override
    public void takePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                String permissions[] = {Manifest.permission.CAMERA};

                getContext().requestPermissions(permissions, CAMERA_PERMISSION_REQUEST_CODE);
                return;
            }
        }
        setCameraPermissionGranted(true);
    }
    //endregion

    //protected methods
    protected void onCameraTorchModeChanged(TorchMode torchMode) {
        if (mTorchModeCallback != null) {
            mTorchModeCallback.onTorchModeChanged(torchMode);
        }
    }

    //endregion

    //region Accessors

    public Activity getContext() {
        return mContext;
    }

    public TorchMode getTorchMode() {
        return mTorchMode;
    }

    protected void setTorchMode(TorchMode torchMode) {
        mTorchMode = torchMode;
    }

    protected boolean isCameraPermissionGranted() {
        return isCameraPermissionGranted;
    }

    protected void setCameraPermissionGranted(boolean cameraPermissionGranted) {
        isCameraPermissionGranted = cameraPermissionGranted;
    }
    //endregion
}
