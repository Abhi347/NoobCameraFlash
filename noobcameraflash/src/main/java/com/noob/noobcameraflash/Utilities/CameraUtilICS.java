package com.noob.noobcameraflash.Utilities;

import android.hardware.Camera;

import java.util.List;

/**
 * Created by Abhishek on 23-11-2015.
 */
@SuppressWarnings("deprecation")
public class CameraUtilICS implements CameraFlashUtility {
    Camera mCamera;
    Camera.Parameters cameraParameters;
    private boolean torchModeOn = false;
    List<String> flashModes;

    private String getFlashMode() throws RuntimeException {
        if (mCamera == null)
            mCamera = Camera.open();
        cameraParameters = mCamera.getParameters();
        flashModes = cameraParameters.getSupportedFlashModes();
        return cameraParameters.getFlashMode();
    }

    @Override
    public boolean isFlashOn() {
        return torchModeOn;
    }

    @Override
    public void turnOnFlash() throws RuntimeException {
        String flashMode = getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(cameraParameters);
                torchModeOn = true;
            }
        }
    }

    @Override
    public void turnOffFlash() throws RuntimeException {
        String flashMode = getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // Turn off the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(cameraParameters);
            } else {
                //SetLog("FLASH_MODE_OFF not supported");
            }
        }
        torchModeOn = false;
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void refreshPermissions() {
        //
    }
}
