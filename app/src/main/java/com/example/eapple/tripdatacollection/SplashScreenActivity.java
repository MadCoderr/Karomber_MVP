package com.example.eapple.tripdatacollection;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import maes.tech.intentanim.CustomIntent;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Setting custom text Style
        //Typeface collegeSans = Typeface.createFromAsset(getAssets(), "fonts/college_sans.ttf");
        //logoText.setTypeface(collegeSans);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashScreenActivity.this, Main2Activity.class);
                startActivity(splashIntent);
                //overridePendingTransition(R.anim.fad_in, R.anim.fad_out);
                //CustomIntent.customType(SplashScreenActivity.this, "fadein-to-fadeout");
                finish();
            }
        }, SPLASH_TIME);
    }
}
