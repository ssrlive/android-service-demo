package com.example.svctest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.btnStart);
        stop = findViewById(R.id.btnStop);

        start.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("TAG", "start.setOnClickListener called");
            }
        });

        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "stop.setOnClickListener called");
            }
        });
    }
}