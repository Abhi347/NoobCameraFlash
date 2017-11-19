package com.noob.noobcameraflash.Utilities;

import android.content.Context;
import android.hardware.Camera;

import com.noob.lumberjack.LumberJack;

import java.util.List;

/**
 * Created by Abhishek on 23-11-2015.
 */
@SuppressWarnings("deprecation")
public class CameraUtilICS extends BaseCameraUtil {
    private Camera mCamera;
    private Camera.Parameters cameraParameters;
    private List<String> flashModes;

    public CameraUtilICS(Context context) {
        super(context);
    }

    private String getFlashMode() throws RuntimeException {
        if (mCamera == null)
            mCamera = Camera.open();
        cameraParameters = mCamera.getParameters();
        flashModes = cameraParameters.getSupportedFlashModes();
        return cameraParameters.getFlashMode();
    }


    @Override
    public void turnOnFlash() throws RuntimeException {
        String flashMode = getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(cameraParameters);
                setTorchMode(TorchMode.SwitchedOn);
                mCamera.startPreview(); // 23.02.2017 Roberto
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
                setTorchMode(TorchMode.SwitchedOff);
                mCamera.stopPreview(); // 23.02.2017 Roberto
            } else {
                LumberJack.e("FLASH_MODE_OFF not supported");
                setTorchMode(TorchMode.Unavailable);
            }
        }
        release();
    }

    @Override
    public void release() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
