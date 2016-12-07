package com.noob.noobcameraflash.managers;

import android.app.Activity;
import android.os.Build;

import com.noob.lumberjack.LogLevel;
import com.noob.lumberjack.LumberJack;
import com.noob.noobcameraflash.Utilities.CameraFlashUtility;
import com.noob.noobcameraflash.Utilities.CameraUtilICS;
import com.noob.noobcameraflash.Utilities.CameraUtilLollipop;
import com.noob.noobcameraflash.Utilities.CameraUtilMarshMallow;

/**
 * Created by abhi on 23/10/16.
 */

public class NoobCameraManager {
    private CameraFlashUtility mCameraUtil;
    //region singleton
    private static NoobCameraManager mInstance;

    public static NoobCameraManager getInstance() {
        if (mInstance == null) {
            mInstance = new NoobCameraManager();
        }
        return mInstance;
    }

    private NoobCameraManager() {
    }
    //endregion singleton

    public void init(Activity activity, LogLevel logLevel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraUtil = new CameraUtilMarshMallow(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCameraUtil = new CameraUtilLollipop(activity);
        } else {
            mCameraUtil = new CameraUtilICS(activity);
        }
        setLogLevel(logLevel);
    }

    public void init(Activity activity) {
        init(activity, LogLevel.None);
    }

    public void takePermissions() {
        mCameraUtil.takePermissions();
    }

    public boolean isFlashOn() {
        return mCameraUtil.isFlashOn();
    }

    public void turnOnFlash() {
        mCameraUtil.turnOnFlash();
    }

    public void turnOffFlash() {
        mCameraUtil.turnOffFlash();
    }

    public void toggleFlash() {
        if (isFlashOn()) {
            turnOffFlash();
        } else {
            turnOnFlash();
        }
    }

    public void setLogLevel(LogLevel logLevel) {
        LumberJack.setLogLevel(logLevel);
    }

    //May or may not release all resources
    public void release() {
        if (mCameraUtil != null)
            mCameraUtil.release();
    }
}
