package com.noob.noobcameraflash.Utilities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

/**
 * Created by Abhishek on 28-11-2015.
 */
@TargetApi(Build.VERSION_CODES.M)
public class CameraUtilMarshMallow implements CameraFlashUtility {

    public enum TorchMode {
        UNAVAILABLE,
        SWITCHED_ON,
        SWITCHED_OFF
    }

    public interface TorchModeCallback {
        void onTorchModeChanged(TorchMode status);
    }

    private CameraManager mCameraManager;
    private Activity mContext;
    private boolean isCameraPermissionGranted = false;

    private boolean torchModeOn = false;
    private TorchModeCallback mTorchModeCallback;

    public CameraUtilMarshMallow(Activity context) {
        mContext = context;
        try {
            mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            checkCameraPermission(context);
            openCamera(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera(Activity context) throws CameraAccessException {
        checkCameraPermission(context);
        if (isCameraPermissionGranted) {
            mCameraManager.registerTorchCallback(new CameraManager.TorchCallback() {
                @Override
                public void onTorchModeUnavailable(String cameraId) {
                    super.onTorchModeUnavailable(cameraId);
                    mTorchModeCallback.onTorchModeChanged(TorchMode.UNAVAILABLE);
                }

                @Override
                public void onTorchModeChanged(String cameraId, boolean enabled) {
                    super.onTorchModeChanged(cameraId, enabled);
                    torchModeOn = enabled;
                    if (mTorchModeCallback != null) {
                        if (torchModeOn)
                            mTorchModeCallback.onTorchModeChanged(TorchMode.SWITCHED_ON);
                        else
                            mTorchModeCallback.onTorchModeChanged(TorchMode.SWITCHED_OFF);
                    }
                }
            }, null);
        }
    }

    private boolean isFlashAvailable() throws CameraAccessException {
        CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics("0");
        boolean flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        return flashAvailable;
    }

    public void checkCameraPermission(Activity context) throws CameraAccessException {
        boolean flashAvailable = isFlashAvailable();
        if (flashAvailable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    String permissions[] = {Manifest.permission.CAMERA};

                    context.requestPermissions(permissions, 100);
                    return;
                }
            }
            isCameraPermissionGranted = true;
        }
    }

    @Override
    public boolean isFlashOn() {
        return false;
    }

    @Override
    public void turnOnFlash() {
        try {
            String[] cameraIds = mCameraManager.getCameraIdList();
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);
                if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {

                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnOffFlash() {

    }

    @Override
    public void refreshPermissions() {
        try {
            ArrayList<String> mCameraIds = new ArrayList<>();
            String[] cameraIds = mCameraManager.getCameraIdList();
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);
                if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTorchModeCallback(TorchModeCallback torchModeCallback) {
        mTorchModeCallback = torchModeCallback;
    }
}
