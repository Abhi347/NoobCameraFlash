package com.example.noobcameraflash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.noob.lumberjack.LogLevel;
import com.noob.noobcameraflash.managers.NoobCameraManager;

public class MainActivity extends AppCompatActivity {

    TextView mStatusTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusTextView = (TextView) findViewById(R.id.status_text);
        NoobCameraManager.getInstance().init(this, LogLevel.Verbose);
    }

    public void onPermissionsClick(View view) {
        NoobCameraManager.getInstance().takePermissions();
        updateStatus();
    }

    public void onFlashOnClick(View view) {
        NoobCameraManager.getInstance().turnOnFlash();
        updateStatus();
    }

    public void onFlashOffClick(View view) {
        NoobCameraManager.getInstance().turnOffFlash();
        updateStatus();
    }

    public void onFlashToggleClick(View view) {
        if(NoobCameraManager.getInstance().isFlashOn()){
            NoobCameraManager.getInstance().turnOffFlash();
        }else{
            NoobCameraManager.getInstance().turnOnFlash();
        }
        updateStatus();
    }

    private void updateStatus(){
        if(NoobCameraManager.getInstance().isFlashOn()){
            mStatusTextView.setText("Current Flash Status: On");
        }else{
            mStatusTextView.setText("Current Flash Status: Off");
        }
    }
}
