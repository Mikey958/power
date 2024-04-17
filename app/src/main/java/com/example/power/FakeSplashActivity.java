package com.example.power;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class FakeSplashActivity extends AppCompatActivity {

    TextView loadtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_splash);
        loadtext = findViewById(R.id.textview);
        loadtext.setText(getResources().getString(R.string.logo_text));

        TextPaint paint = loadtext.getPaint();
        float height = paint.measureText(getResources().getString(R.string.logo_text));

        Shader textShader = new LinearGradient(0,0,loadtext.getTextSize(), height ,
                new int[]{
                        Color.parseColor("#000000"),
                        Color.parseColor("#8AC1D9"),
                        Color.parseColor("#8AC1D9"),
                        Color.parseColor("#8AC1D9"),
            },null, Shader.TileMode.CLAMP);
        loadtext.getPaint().setShader(textShader);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FakeSplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}