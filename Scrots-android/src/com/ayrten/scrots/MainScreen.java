/**
 * Created by Andy Yeung & Tony Doan
 */
package com.ayrten.scrots;

import com.ayrten.scrots.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/*
    Splash Screen
 */
public class MainScreen extends Activity {
    private static final int SPLASH_TIME = 3000; // 3 seconds for splash screen
    private static final int splashView = R.layout.activity_main_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(splashView);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // Start menu screen
                startActivity(new Intent(MainScreen.this, MenuScreen.class));
                finish();
            }
        }, SPLASH_TIME);
    }
}
