package com.example.power;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ActiveTrainActivity extends AppCompatActivity {

    private ImageView gifOneImage;
    private TextView nameTrain;
    private ProgressBar circularProgressBar;
    private TextView timerTextView;

    // Массив ресурсов GIF-анимаций для упражнений
    private int[] gifResources = {R.drawable.gif_1, R.drawable.gif_2, R.drawable.gif_3,R.drawable.gif_4,R.drawable.gif_5, R.drawable.gif_1, R.drawable.gif_2, R.drawable.gif_3,R.drawable.gif_4,R.drawable.gif_5};
    // Массив ресурсов названий упражнений из strings
    private int[] exerciseNames = {R.string.elem_train_3, R.string.elem_train_4, R.string.elem_train_5, R.string.elem_train_1, R.string.elem_train_2, R.string.elem_train_3, R.string.elem_train_4, R.string.elem_train_5, R.string.elem_train_1, R.string.elem_train_2};
    // Время выполнения каждого упражнения в секундах
    private int[] exerciseDurations = {30, 30, 25, 25, 25, 30, 30, 25, 25, 25};
    // Время подготовки к упражнению в секундах
    private int prepTime = 5;

    private List<Integer> currentExercise = new ArrayList<>();

    private CountDownTimer timer;
    private int currentExerciseIndex = 0;
    private int timeRemaining;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_train);

        gifOneImage = findViewById(R.id.gifOneImage);
        nameTrain = findViewById(R.id.nameTrain);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        timerTextView = findViewById(R.id.timerTextView);

        // Загрузка первой гифки и имени упражнения
        Glide.with(this)
                .load(gifResources[currentExerciseIndex])
                .into(gifOneImage);
        nameTrain.setText(getString(exerciseNames[currentExerciseIndex]));

        // Инициализация массива упражнений для циклического выполнения
        for (int i = 0; i < gifResources.length; i++) {
            currentExercise.add(i);
        }

        // Запуск таймера
        startTimer(prepTime);
    }

    private void startTimer(int time) {
        timeRemaining = time;

        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = (int) (millisUntilFinished / 1000);
                timerTextView.setText(String.format("%02d:%02d", timeRemaining / 60, timeRemaining % 60));
                circularProgressBar.setProgress(timeRemaining);
            }

            @Override
            public void onFinish() {
                if (currentExerciseIndex == currentExercise.size()) {
                    // Тренировка завершена
                    timerTextView.setText("Тренировка завершена!");
                    return;
                }

                // Переход к следующему упражнению
                nextExercise();
            }
        }.start();
    }

    private void nextExercise() {
        // Проверка на завершение цикла упражнений
        if (currentExerciseIndex < currentExercise.size()) {
            int currentExerciseId = currentExercise.get(currentExerciseIndex);
            // Загрузка гифки и имени следующего упражнения
            Glide.with(this)
                    .load(gifResources[currentExerciseId])
                    .into(gifOneImage);
            nameTrain.setText(getString(exerciseNames[currentExerciseId]));

            // Установка времени для следующего упражнения
            startTimer(exerciseDurations[currentExerciseId]);

            currentExerciseIndex++;
        } else {
            // Тренировка завершена
            timerTextView.setText("Тренировка завершена!");
        }
    }
}

