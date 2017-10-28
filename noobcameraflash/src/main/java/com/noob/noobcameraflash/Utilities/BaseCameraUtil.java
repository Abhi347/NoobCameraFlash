package com.noob.noobcameraflash.Utilities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Abhishek on 08-12-2016.
 */

public abstract class BaseCameraUtil implements CameraFlashUtility {
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1337;
    private Context mContext;

    //private boolean torchModeOn = false;
    private TorchMode mTorchMode = TorchMode.None;
    private TorchModeCallback mTorchModeCallback;

    //region Constructors
    BaseCameraUtil(Context context) {
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
    //endregion

    //protected methods
    protected void onCameraTorchModeChanged(TorchMode torchMode) {
        if (mTorchModeCallback != null) {
            mTorchModeCallback.onTorchModeChanged(torchMode);
        }
    }

    //endregion

    //region Accessors

    Context getContext() {
        return mContext;
    }

    public TorchMode getTorchMode() {
        return mTorchMode;
    }

    void setTorchMode(TorchMode torchMode) {
        mTorchMode = torchMode;
    }
    //endregion
}
