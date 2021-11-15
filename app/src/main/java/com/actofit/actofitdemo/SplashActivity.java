package com.actofit.actofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    private static final int TIME_OUT = 1600;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferencesHelper.initDefault(getApplicationContext());

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(TIME_OUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (!TextUtils.isEmpty(SharedPreferencesHelper.getInstance().getName())) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, RegUserActivity.class));
                        finish();
                    }

                }
            }
        };
        timer.start();

    }
}