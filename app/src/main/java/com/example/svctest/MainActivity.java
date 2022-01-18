package com.example.svctest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button stop;

    private Button bindSvc;
    private Button unbindSvc;

    MyService2.MyBinder myBinder;
    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("------Service Connected-------");
            myBinder = (MyService2.MyBinder) service;
            _isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("------Service DisConnected-------");
            myBinder = null;
            _isConnected = false;
        }

        public boolean isConnected() {
            return _isConnected;
        }

        private boolean _isConnected;
    };
    private MyServiceConnection conn = new MyServiceConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.btnStart);
        stop = findViewById(R.id.btnStop);

        final Intent intent = new Intent();
        intent.setPackage(this.getPackageName());
        intent.setAction("com.example.svctest.TEST_SERVICE1_ACTION");

        start.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("TAG", "start.setOnClickListener called");
                startService(intent);
            }
        });

        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "stop.setOnClickListener called");
                stopService(intent);
            }
        });

        final Intent intent2 = new Intent();
        intent2.setPackage(this.getPackageName());
        intent2.setAction("com.example.svctest.MY_SERVICE2_ACTION");

        bindSvc = findViewById(R.id.btnBindSvc);
        bindSvc.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!conn.isConnected()) {
                    bindService(intent2, conn, Service.BIND_AUTO_CREATE);
                }
            }
        });

        unbindSvc = findViewById(R.id.btnUnbindSvc);
        unbindSvc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conn.isConnected()) {
                    unbindService(conn);
                    conn.onServiceDisconnected(null);
                }
            }
        });

        TextView textView = findViewById(R.id.textView);
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (myBinder != null) {
                            textView.setText("Background service count = " + myBinder.getCount());
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000L, 1000L);
    }

    private Timer timer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}