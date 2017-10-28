package com.noob.noobcameraflash.managers;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.support.annotation.NonNull;

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

    public void init(@NonNull Context context, @NonNull LogLevel logLevel) throws CameraAccessException, SecurityException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraUtil = new CameraUtilMarshMallow(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCameraUtil = new CameraUtilLollipop(context);
        } else {
            mCameraUtil = new CameraUtilICS(context);
        }
        setLogLevel(logLevel);
    }

    public void init(Context context) throws CameraAccessException, SecurityException {
        init(context, LogLevel.None);
    }

    public void setCameraUtil(CameraFlashUtility cameraUtil) {
        mCameraUtil = cameraUtil;
    }

    public boolean isFlashOn() {
        return mCameraUtil.isFlashOn();
    }

    public void turnOnFlash() throws CameraAccessException {
        mCameraUtil.turnOnFlash();
    }

    public void turnOffFlash() throws CameraAccessException {
        mCameraUtil.turnOffFlash();
    }

    public void toggleFlash() throws CameraAccessException {
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
