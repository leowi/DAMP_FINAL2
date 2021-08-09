package com.example.damp_final2;

import android.content.Context;
import android.content.Intent;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    public static final String ACTION_MESSAGE = "camera.message";
    public static final String DISPLAY_RESULT = "camera.result";
    private CameraListener cameraListener;

    public BroadcastReceiver() {

    }
    public BroadcastReceiver(CameraListener cameraListener) {
        this.cameraListener = cameraListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        final String result = intent.getStringExtra(DISPLAY_RESULT);
        if (action.equals(ACTION_MESSAGE)) {
            cameraListener.take(result);
        }

    }
}
