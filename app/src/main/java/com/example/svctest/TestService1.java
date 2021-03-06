package com.example.svctest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService1 extends Service {
    private final String TAG = "TestService1";

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind called");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate called");
        ChangeToForegroundService.toForeground(this, MainActivity.class,
                R.mipmap.ic_launcher, "channelId", 1, "Socket server", "Running...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int res = super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "onStartCommand called");
        return res;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestory called");
    }
}
