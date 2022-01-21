package com.example.svctest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class RemoteService extends Service {
    private static final String TAG = "RemoteService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        // the service will be killed without the following call.
        ChangeToForegroundService.toForeground(this, MainActivity.class,
                R.mipmap.ic_launcher, "channelIdRemote", 2, "Add numbers", "Wait to calc...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return new IRemoteService.Stub() {
            @Override
            public int add(int value1, int value2) throws RemoteException {
                Log.i(TAG, String.format("RemoteService.add(%d, %d)", value1, value2));
                return value1 + value2;
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
