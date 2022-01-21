package com.example.svctest;

import static android.widget.Toast.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

        final Intent intent = new Intent(getApplicationContext(), TestService1.class);

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

        runIntentService();

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new OnClickListener() {
            TextView result = findViewById(R.id.txtAddResult);
            EditText value1 = findViewById(R.id.edtValue1);
            EditText value2 = findViewById(R.id.edtValue2);
            @Override
            public void onClick(View v) {
                int res = -1;
                try {
                    int v1 = Integer.parseInt(value1.getText().toString());
                    int v2 = Integer.parseInt(value2.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result.setText(Integer.toString(res));
            }
        });
    }

    private Timer timer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    void runIntentService() {
        String[] params = { "s1", "s2", "s3", };
        for(String val:params) {
            _runIntentService(val);
        }
    }

    void _runIntentService(String param) {
        Intent intent = new Intent(getApplicationContext(), TestService3.class);
        Bundle bundle = new Bundle();
        bundle.putString("param", param);
        intent.putExtras(bundle);
        startService(intent);
    }
}