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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Abhishek on 28-11-2015.
 */
@SuppressWarnings("ConstantConditions")
@TargetApi(Build.VERSION_CODES.M)
public class CameraUtilMarshMallow extends BaseCameraUtil {
    private CameraManager mCameraManager;
    CameraManager.TorchCallback mTorchCallback;


    public CameraUtilMarshMallow(Activity context) {
        super(context);
        try {
            mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            checkCameraPermission(context);
            openCamera(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera(Activity context) throws CameraAccessException {
        checkCameraPermission(context);
        if (isCameraPermissionGranted()) {
            mTorchCallback = new CameraManager.TorchCallback() {
                @Override
                public void onTorchModeUnavailable(@NonNull String cameraId) {
                    super.onTorchModeUnavailable(cameraId);
                    onCameraTorchModeChanged(TorchMode.Unavailable);
                }

                @Override
                public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
                    super.onTorchModeChanged(cameraId, enabled);
                    if (enabled)
                        setTorchMode(TorchMode.SwitchedOn);
                    else
                        setTorchMode(TorchMode.SwitchedOff);
                }
            };
            mCameraManager.registerTorchCallback(mTorchCallback, null);
        }
    }

    private boolean isFlashAvailable() throws CameraAccessException {
        CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics("0");
        return cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
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
            setCameraPermissionGranted(true);
        }
    }


    @Override
    public void turnOnFlash() {
        try {
            String[] cameraIds = mCameraManager.getCameraIdList();
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);
                if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    mCameraManager.setTorchMode(id, true);
                    setTorchMode(TorchMode.SwitchedOn);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnOffFlash() {
        try {
            String[] cameraIds = mCameraManager.getCameraIdList();
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);
                if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    mCameraManager.setTorchMode(id, false);
                    setTorchMode(TorchMode.SwitchedOff);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void release() {
        if(mCameraManager!=null){
            mCameraManager.unregisterTorchCallback(mTorchCallback);
            mCameraManager = null;
        }
    }
}
