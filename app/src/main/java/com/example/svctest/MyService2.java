package com.example.svctest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService2 extends Service {
    private final String TAG = "MyService2";
    private int count;
    private boolean quit;

    private MyBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        public int getCount() {
            return count;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind called");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate called");
        new Thread() {
            @Override
            public void run() {
                // super.run();
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++count;
                }
            }
        }.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind called");
        return true; // super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy called");
        this.quit = true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind called");
    }
}
