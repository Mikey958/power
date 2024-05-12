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

public class TrainingActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_training);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.full_body_train_start).setOnClickListener(v -> {
            Intent intent=new Intent(TrainingActivity.this,FullBodyTrainingStartActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.achivmentIconButton).setOnClickListener(v -> {
            Intent intent=new Intent(TrainingActivity.this,AchievmentActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.homeIconButton).setOnClickListener(v -> {
            Intent intent=new Intent(TrainingActivity.this,MainActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.profileIconButton).setOnClickListener(v -> {
            Intent intent=new Intent(TrainingActivity.this,ProfileActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.eatingIconButton).setOnClickListener(v -> {
            Intent intent=new Intent(TrainingActivity.this,EatingActivity.class);
            startActivity(intent);
        });
    }
}