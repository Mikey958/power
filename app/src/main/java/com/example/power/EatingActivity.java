package com.example.power;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EatingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eating);

        findViewById(R.id.achivmentIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivity.this,AchievmentActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.homeIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.profileIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.trainingIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivity.this,TrainingActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.breakfast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivity.this,EatingActivityBreakfast.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.lunch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivity.this,EatingActivityLunch.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.dinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivity.this,EatingActivityDinner.class);
                startActivity(intent);
            }
        });

    }
}