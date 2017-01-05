package com.na.naview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.na.view.NaProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private NaProgressBar progressbar;
//    private float mProgress = 0.0f;
//    private Handler mHander = new Handler(){
//        @Override
//        public void dispatchMessage(Message msg) {
//            super.dispatchMessage(msg);
//            mProgress+=0.1;
//            progressbar.setProgress(mProgress);
//            if (mProgress < 30.0f){
//                mHander.sendEmptyMessageDelayed(100, 50);
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressbar = (NaProgressBar) findViewById(R.id.progressbar);
        progressbar.setOnClickListener(this);
        progressbar.setMax(30);
        progressbar.release();
//        progressbar.setProgress(mProgress);
        findViewById(R.id.btStart).setOnClickListener(this);
        findViewById(R.id.btRev).setOnClickListener(this);
        progressbar.setOnProgressBarListener(new NaProgressBar.onProgressBarListener() {
            @Override
            public void onFinish() {
                Log.e(TAG, "onFinish");
            }

            @Override
            public void onStop(int progress) {
                Log.e(TAG, "onStop progress=" + progress);
            }

            @Override
            public void onProgress(float progress) {
                Log.e(TAG, "onProgress progress=" + progress);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.progressbar: {
//                mProgress = 0.0f;
//                mHander.removeCallbacksAndMessages(100);
//                mHander.sendEmptyMessageDelayed(100, 50);
                if (progressbar.isRunning()) {
                    progressbar.stop();
                } else {
                    progressbar.start();
                }
                break;
            }
            case R.id.btStart: {
                progressbar.release();
                break;
            }
            case R.id.btRev:{
                progressbar.cancelBack();
                break;
            }

        }
    }
}

