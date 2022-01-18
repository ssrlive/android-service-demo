package com.example.svctest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        bindSvc = findViewById(R.id.btnBindSvc);
        bindSvc.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        unbindSvc = findViewById(R.id.btnUnbindSvc);
        unbindSvc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        TextView textView = findViewById(R.id.textView);
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        textView.setText("count = " + count);
                    }
                });
                ++count;
            }
        };
        timer.schedule(timerTask, 1000L, 1000L);
    }

    private int count = 0;
    private Timer timer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}