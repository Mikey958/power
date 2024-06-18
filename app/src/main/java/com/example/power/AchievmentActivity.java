package com.example.power;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class AchievmentActivity extends AppCompatActivity {
    // Имя файла настроек
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievment);

        // Получаем SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long lastVisitTime = settings.getLong("lastVisitTime", System.currentTimeMillis());

        findViewById(R.id.button_received).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем SharedPreferences
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                // Получаем статусы достижений
                boolean isFirstEatingActivityAchievement = settings.getBoolean("firstEatingActivity", false);
                boolean isFirstMainActivityAchievement = settings.getBoolean("firstMainActivityVisit", false);
                boolean isFirstTrainingAchievement = settings.getBoolean("firstTrainingAchievement", false);
                boolean isAchievementUnlocked = settings.getBoolean("achievementUnlocked", false);

                // Создаем Intent и добавляем статусы достижений
                Intent intent = new Intent(AchievmentActivity.this, AchievmentActivity2.class);
                intent.putExtra("firstEatingActivity", isFirstEatingActivityAchievement);
                intent.putExtra("firstMainActivityVisit", isFirstMainActivityAchievement);
                intent.putExtra("firstTrainingAchievement", isFirstTrainingAchievement);
                intent.putExtra("longAbsenceAchievement", settings.getBoolean("longAbsenceAchievement", false));
                intent.putExtra("achievementUnlocked", isAchievementUnlocked);
                startActivity(intent);
            }
        });
        findViewById(R.id.profileIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AchievmentActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.homeIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AchievmentActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.eatingIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AchievmentActivity.this,EatingActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.trainingIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AchievmentActivity.this,TrainingActivity.class);
                startActivity(intent);
            }
        });


        // Проверяем, прошло ли более 7 дней с последнего визита
        if (System.currentTimeMillis() - lastVisitTime > 7 * 24 * 60 * 60 * 1000) {
            // Пользователь не заходил более 7 дней, выдаем достижение
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("longAbsenceAchievement", true);
            editor.apply();

            // Обновляем ImageView в главном потоке UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Получаем ссылку на ImageView для достижения
                    ImageView imageViewAchievement = findViewById(R.id.mem_5);
                    imageViewAchievement.setImageResource(R.drawable.mem_5_color);
                    imageViewAchievement.invalidate(); // Принудительно перерисовываем ImageView
                }
            });
        }
        // Обновляем время последнего визита
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("lastVisitTime", System.currentTimeMillis());
        editor.apply();

        // Проверяем, было ли достижение уже получено для EatingActivity
        boolean isFirstEatingActivityAchievement = settings.getBoolean("firstEatingActivity", false);
        // Проверяем, было ли достижение уже получено для MainActivity
        boolean isFirstMainActivityAchievement = settings.getBoolean("firstMainActivityVisit", false);
        // Проверяем, было ли новое достижение уже получено
        boolean isFirstTrainingAchievement = settings.getBoolean("firstTrainingAchievement", false);
        // Проверяем, было ли достижение уже получено
        boolean isAchievementUnlocked = settings.getBoolean("achievementUnlocked", false);

        // Получаем ссылку на ImageView
        ImageView imageViewEating = findViewById(R.id.mem_2);
        ImageView imageViewMain = findViewById(R.id.mem_1);
        ImageView imageViewTraining = findViewById(R.id.mem_4);
        // Обновляем ImageView в главном потоке UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Получаем ссылку на ImageView для достижения
                ImageView imageViewAchievement = findViewById(R.id.mem_3);

                // Если достижение уже было получено, меняем изображение
                if (isAchievementUnlocked) {
                    imageViewAchievement.setImageResource(R.drawable.mem_3_color);
                    imageViewAchievement.invalidate(); // Принудительно перерисовываем ImageView
                }
            }
        });

        // Если достижение для EatingActivity уже было получено, меняем изображение
        if (isFirstEatingActivityAchievement) {
            imageViewEating.setImageResource(R.drawable.mem_2_color);
        }

        // Если достижение для MainActivity уже было получено, меняем изображение
        if (isFirstMainActivityAchievement) {
            imageViewMain.setImageResource(R.drawable.mem_1_color);
        }

        // Если новое достижение уже было получено, меняем изображение
        if (isFirstTrainingAchievement) {
            imageViewTraining.setImageResource(R.drawable.mem_4_color);
        }

    }
}