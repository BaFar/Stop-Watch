package com.example.dell.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView stopWatchTv;
    private Button startBtn,stopBtn,pauseBtn;
    private SimpleDateFormat dateFormat;
    private Handler watchHandler;

    private long initialValue=0;
    private boolean status=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateFormat = new SimpleDateFormat("mm:ss:S");
        stopWatchTv = (TextView) findViewById(R.id.stopWatchTime);
        startBtn = (Button) findViewById(R.id.startBtn);
        pauseBtn = (Button) findViewById(R.id.pauseBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);

        watchHandler= new Handler();
    }

    public void stopStopWatch(View view) {
        status=false;
        initialValue=0;
        stopWatchTv.setText("00:00:00");
        pauseBtn.setText("Pause");
        startBtn.setEnabled(true);
    }

    public void pauseStopWatch(View view) {
        if (status){

            status=false;
            pauseBtn.setText("Resume");
        }
        else if (!status && initialValue!=0){
            status=true;
            pauseBtn.setText("Pause");
            startStopWatch(startBtn);
        }

    }

    public void startStopWatch(View view) {
        startBtn.setEnabled(false);

        status=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(status){
                    try {
                        Thread.sleep(10);
                        initialValue=initialValue+10;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    watchHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String time=dateFormat.format(new Date(initialValue));
                            stopWatchTv.setText(time);
                        }
                    });
                }
            }
        }).start();
    }
}



