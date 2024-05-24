package com.example.power;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class FullBodyTrainingStartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_full_body_training_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Иницилизация гиф файлов */
        ImageView Gif1 = findViewById(R.id.gifOneImage);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView Gif3 = findViewById(R.id.gifThreeImage);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView Gif4 = findViewById(R.id.gifFourImage);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView Gif5 = findViewById(R.id.gifFiveImage);

        /* Запуск анимации гиф файлов */
        Glide.with(this)
                .load(R.drawable.gif_1)
                .into(Gif1);
        Glide.with(this)
                .load(R.drawable.gif_3)
                .into(Gif3);
        Glide.with(this)
                .load(R.drawable.gif_4)
                .into(Gif4);
        Glide.with(this)
                .load(R.drawable.gif_5)
                .into(Gif5);

        /* Навигационное меню */
        findViewById(R.id.achivmentIconButton).setOnClickListener(v -> {
            Intent intent=new Intent(FullBodyTrainingStartActivity.this,AchievmentActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.homeIconButton).setOnClickListener(v -> {
            Intent intent=new Intent(FullBodyTrainingStartActivity.this,MainActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.profileIconButton).setOnClickListener(v -> {
            Intent intent=new Intent(FullBodyTrainingStartActivity.this,ProfileActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.eatingIconButton).setOnClickListener(v -> {
            Intent intent = new Intent(FullBodyTrainingStartActivity.this, EatingActivity.class);
            startActivity(intent);
        });

        /* Кнопка Начало */
        findViewById(R.id.start).setOnClickListener(v -> {
            Intent intent = new Intent(FullBodyTrainingStartActivity.this, ActiveTrainActivity.class);
            startActivity(intent);
        });

    }
}