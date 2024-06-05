package com.example.power;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
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
    private int prepTime = 10;

    private List<Integer> currentExercise = new ArrayList<>();

    private CountDownTimer timer;
    private int currentExerciseIndex = 0;
    private int timeRemaining;

    private int totalExerciseTime;
    private View pauseButton;
    private View lastTrainBtn;
    private View skipTrainBtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_train);

        gifOneImage = findViewById(R.id.gifOneImage);
        nameTrain = findViewById(R.id.nameTrain);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        timerTextView = findViewById(R.id.timerTextView);
        pauseButton = findViewById(R.id.pauseButton);

        lastTrainBtn = findViewById(R.id.lastTrainBtn);
        skipTrainBtn = findViewById(R.id.skipTrainBtn);

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

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTraining();
            }
        });

        lastTrainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousExercise();
            }
        });

        skipTrainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipExercise();
            }
        });

        // Запускаем тренировку
        startExercise(currentExerciseIndex);
    }
    // Глобальная переменная для диалога
    private AlertDialog dialog;




    private void startTimer(int time) {
        timeRemaining = time;
        totalExerciseTime = time; // Сохраним общее время упражнения
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = (int) (millisUntilFinished / 1000);
                timerTextView.setText(String.format("%02d:%02d", timeRemaining / 60, timeRemaining % 60));

                // Вычисляем процент оставшегося времени
                float progress = (float) timeRemaining / totalExerciseTime;
                // Устанавливаем прогресс бар в соответствии с процентом

                circularProgressBar.setProgress((int) (progress * 100));
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

    private void startExercise(int exerciseIndex) {
        // Загрузка гифки и имени упражнения
        Glide.with(this)
                .load(gifResources[exerciseIndex])
                .into(gifOneImage);
        nameTrain.setText(getString(exerciseNames[exerciseIndex]));

        // Обновляем progressBar
        circularProgressBar.setProgress(0); // Сбрасываем прогресс

        // Запуск таймера
        startTimer(exerciseDurations[exerciseIndex]);
    }

    private void previousExercise() {
        // Проверяем, есть ли предыдущее упражнение
        if (currentExerciseIndex > 0) {
            currentExerciseIndex--;
            startExercise(currentExerciseIndex);

            // Если таймер работает, сбрасываем его
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            // Запускаем таймер для текущего упражнения
            startTimer(exerciseDurations[currentExerciseIndex]);
        }
    }

    private void skipExercise() {
        // Проверяем, есть ли следующее упражнение
        if (currentExerciseIndex < gifResources.length - 1) {
            currentExerciseIndex++;
            startExercise(currentExerciseIndex);

            // Если таймер работает, сбрасываем его
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            // Запускаем таймер для текущего упражнения
            startTimer(exerciseDurations[currentExerciseIndex]);
        }
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
            timerTextView.setText("Молодец!");
        }
    }
    private void pauseTraining() {
        // Останавливаем таймер
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        // Покажем окно с кнопками
        showPauseDialog();
    }
    private void showPauseDialog() {
        // Создаем AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        // Создаем список кнопок
        View dialogView = LayoutInflater.from(this).inflate(R.layout.pause_dialog, null); // Ваш layout
        Button continueButton = dialogView.findViewById(R.id.continueButton);
        Button restartButton = dialogView.findViewById(R.id.restartButton);
        Button exitButton = dialogView.findViewById(R.id.exitButton);

        // Устанавливаем обработчики клика для кнопок
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Продолжаем тренировку
                resumeTraining();
                dialog.dismiss();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Начинаем тренировку заново
                resetTraining();
                dialog.dismiss();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Выходим в меню (закройте эту активность)
                finish();
            }
        });
        // Добавляем стиль с закругленными краями

        builder.setView(dialogView);

        // Создаем AlertDialog и показываем
        dialog = builder.create();
        dialog.show();

        // Размываем задний фон
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.dimAmount = 0.7f; // Уровень размытия
        dialog.getWindow().setAttributes(lp);
    }
    private void resumeTraining() {
        // Возобновляем тренировку
        // Если время не закончилось
        if (timeRemaining > 0 && timer == null) {
            // Запускаем таймер с оставшимся временем
            startTimer(timeRemaining);
        }
    }

    private void resetTraining() {
        // Сбрасываем тренировку
        // Запускаем тренировку заново
        currentExerciseIndex = 0; // Возвращаем индекс к начальному упражнению
        startExercise(currentExerciseIndex);
    }
}

