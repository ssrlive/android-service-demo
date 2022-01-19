package com.example.svctest;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestService3 extends IntentService {
    private final String TAG = "TestService3";

    public TestService3() {
        super("TestService3 workThreadName");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getExtras().getString("param");
        if (action.equals("s1")) {
            Log.i(TAG, "starting task1");
        } else if (action.equals("s2")) {
            Log.i(TAG, "starting task2");
        } else if (action.equals("s3")) {
            Log.i(TAG, "starting task3");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        Log.i(TAG, "setIntentRedelivery");
        super.setIntentRedelivery(enabled);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }
}
