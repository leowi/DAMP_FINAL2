package com.example.damp_final2.sound_detector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.damp_final2.BroadcastReceiver;

public class DetectSoundService extends Service implements DetectSoundListener {
    private DetectorThread detectorThread;
    private RecorderThread recorderThread;
    private static final int NOTIFICATION_Id = 001;
    public static final int DETECT_NONE = 0;
    public static final int DETECT_WHISTLE = 1;
    public static int selectedDetection = DETECT_NONE;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        //initNotification();
        startDetection();
        return START_STICKY;
    }
    public void startDetection(){
        selectedDetection = DETECT_WHISTLE;
        recorderThread = new RecorderThread();
        recorderThread.start();
        detectorThread = new DetectorThread(recorderThread);
        detectorThread.setDetectarSonidoListener(this);
        detectorThread.start();
        Toast.makeText(this, "Servicio iniciado", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (recorderThread != null) {
            recorderThread.stopRecording();
            recorderThread = null;
        }
        if (detectorThread != null) {
            detectorThread.stopDetection();
            detectorThread = null;
        }
        selectedDetection = DETECT_NONE;
        Toast.makeText(this, "Servicio detenido", Toast.LENGTH_SHORT).show();
        //stopNotification();
    }
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onWhistleDetected() {
        if (recorderThread != null) {
            recorderThread.stopRecording();
            recorderThread = null;
        }
        if (detectorThread != null) {
            detectorThread.stopDetection();
            detectorThread = null;
        }
        selectedDetection = DETECT_NONE;

        display("take");


        //Toast.makeText(this, "Silbido detectado", Toast.LENGTH_SHORT).show();
        //this.stopSelf();
    }
    private void display(String result) {
        Intent intent = new Intent(BroadcastReceiver.ACTION_MESSAGE);
        intent.putExtra(BroadcastReceiver.DISPLAY_RESULT, result);
        sendBroadcast(intent);
    }
}