package com.example.power;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AchievmentActivity2 extends AppCompatActivity {
    // Имя файла настроек
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievment2);

        // Получаем SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Получаем статусы достижений
        boolean isFirstEatingActivityAchievement = settings.getBoolean("firstEatingActivity", false);
        boolean isFirstMainActivityAchievement = settings.getBoolean("firstMainActivityVisit", false);
        boolean isFirstTrainingAchievement = settings.getBoolean("firstTrainingAchievement", false);
        boolean isLongAbsenceAchievement = settings.getBoolean("longAbsenceAchievement", false);
        boolean isThirdAchievementUnlocked = settings.getBoolean("achievementUnlocked", false);

        // Управление отображением элементов для достижений
        manageAchievementView(isFirstEatingActivityAchievement, 2);
        manageAchievementView(isFirstMainActivityAchievement, 1);
        manageAchievementView(isFirstTrainingAchievement, 4);
        manageAchievementView(isLongAbsenceAchievement, 5);
        manageAchievementView(isThirdAchievementUnlocked, 3);

        // Обработчик нажатия для кнопки возвращения на главный экран
        findViewById(R.id.homeIconButton).setOnClickListener(v -> {
            Intent intent = new Intent(AchievmentActivity2.this, MainActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.eatingIconButton).setOnClickListener(v -> {
            Intent intent = new Intent(AchievmentActivity2.this, EatingActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.trainingIconButton).setOnClickListener(v -> {
            Intent intent = new Intent(AchievmentActivity2.this, TrainingActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.profileIconButton).setOnClickListener(v -> {
            Intent intent = new Intent(AchievmentActivity2.this, ProfileActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.button_all).setOnClickListener(v -> {
            Intent intent = new Intent(AchievmentActivity2.this, AchievmentActivity.class);
            startActivity(intent);
        });

        // Добавьте обработчики для других кнопок, если необходимо
    }

    private void manageAchievementView(boolean isAchievementUnlocked, int achievementNumber) {
        int imageViewId = getResources().getIdentifier("mem_" + achievementNumber, "id", getPackageName());
        ImageView imageView = findViewById(imageViewId);
        if (isAchievementUnlocked) {
            String imageName = "mem_" + achievementNumber + "_color";
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            imageView.setImageResource(imageResId);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        // Убедитесь, что остальные идентификаторы используются правильно и соответствуют типам представлений в вашем layout
    }

}
