package com.na.naview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.na.view.NaProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        progressbar.setMax(30.0f);
        progressbar.release();
//        progressbar.setProgress(mProgress);
        findViewById(R.id.btStart).setOnClickListener(this);
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

        }
    }
}

