package com.noob.noobcameraflash.Utilities;

/**
 * Created by Abhishek on 23-11-2015.
 */
public interface CameraFlashUtility {
    boolean isFlashOn();
    void turnOnFlash();
    void turnOffFlash();
    void refreshPermissions();
    void setTorchModeCallback(TorchModeCallback torchModeCallback);
    void release();
}
