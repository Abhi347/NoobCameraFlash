package com.example.noobcameraflash;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.noob.lumberjack.LogLevel;
import com.noob.noobcameraflash.Utilities.BaseCameraUtil;
import com.noob.noobcameraflash.managers.NoobCameraManager;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    TextView mStatusTextView;
    private int CAMERA_PERMISSION_REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusTextView = findViewById(R.id.status_text);
        try {
            NoobCameraManager.getInstance().init(this, LogLevel.Verbose);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPermissionsClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            String[] permissions = {Manifest.permission.CAMERA};
            requestPermissions(permissions, CAMERA_PERMISSION_REQUEST_CODE);
        }
        updateStatus();
    }

    public void onFlashOnClick(View view) {
        try {
            NoobCameraManager.getInstance().turnOnFlash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateStatus();
    }

    public void onFlashOffClick(View view) {
        try {
            NoobCameraManager.getInstance().turnOffFlash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateStatus();
    }

    public void onFlashToggleClick(View view) {
        try {
            NoobCameraManager.getInstance().toggleFlash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateStatus();
    }

    public void onReleaseClick(View view) {
        NoobCameraManager.getInstance().release();
        updateStatus();
    }

    private void updateStatus() {
        if (NoobCameraManager.getInstance().isFlashOn()) {
            mStatusTextView.setText("Current Flash Status: On");
        } else {
            mStatusTextView.setText("Current Flash Status: Off");
        }
    }
}
