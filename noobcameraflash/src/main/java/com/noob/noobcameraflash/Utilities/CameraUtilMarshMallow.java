package com.noob.noobcameraflash.Utilities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by Abhishek on 28-11-2015.
 */
@SuppressWarnings("ConstantConditions")
@TargetApi(Build.VERSION_CODES.M)
public class CameraUtilMarshMallow extends BaseCameraUtil {
    private CameraManager mCameraManager;
    private CameraManager.TorchCallback mTorchCallback;

    public CameraUtilMarshMallow(Activity context) {
        super(context);
        try {
            openCamera(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera(Activity context) throws CameraAccessException {
        checkCameraPermission(context);
        if (isCameraPermissionGranted()) {
            if (mCameraManager == null)
                mCameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
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
        CameraCharacteristics cameraCharacteristics = getCameraManager().getCameraCharacteristics("0");
        return cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
    }

    public void checkCameraPermission(Activity context) throws CameraAccessException {
        boolean flashAvailable = isFlashAvailable();
        if (flashAvailable) {
            takePermissions();
        }
    }


    @Override
    public void turnOnFlash() {
        try {
            String[] cameraIds = getCameraManager().getCameraIdList();
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = getCameraManager().getCameraCharacteristics(id);
                if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    getCameraManager().setTorchMode(id, true);
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
            String[] cameraIds = getCameraManager().getCameraIdList();
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = getCameraManager().getCameraCharacteristics(id);
                if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    getCameraManager().setTorchMode(id, false);
                    setTorchMode(TorchMode.SwitchedOff);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void release() {
        if (mCameraManager != null) {
            mCameraManager.unregisterTorchCallback(mTorchCallback);
            mCameraManager = null;
        }
    }

    //region Accessors

    public CameraManager getCameraManager() {
        if (mCameraManager == null) {
            try {
                openCamera(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mCameraManager;
    }
    //endregion
}
