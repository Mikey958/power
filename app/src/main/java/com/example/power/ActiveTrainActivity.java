package com.example.power;
import static com.example.power.AchievmentActivity.PREFS_NAME;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class ActiveTrainActivity extends AppCompatActivity {


    // Переменная для хранения оставшегося времени таймера
    private long remainingTime;
    // Определение переменных
    private ImageView gifOneImage;
    private TextView nameTrain;
    private ProgressBar circularProgressBar;
    private TextView timerTextView;

    private int currentExerciseIndex = 0;

    private int totalExerciseTime;
    private View pauseButton;
    private View lastTrainBtn;
    private View skipTrainBtn;
    private AlertDialog dialog;
    private List<Integer> currentExercise = new ArrayList<>();

    // Массивы ресурсов
    private int[] gifResources; // Инициализация в onCreate
    private int[] exerciseNames; // Инициализация в onCreate
    private int[] exerciseDurations = { 30, 30, 25, 25, 25, 30, 30, 25, 25, 25}; // Инициализация в onCreate
    // Переменная для хранения начального времени таймера для упражнений
    private int initialTime;
    private int timeRemaining;

    // Таймер
    CountDownTimer timer;

    // Время подготовки к упражнению в секундах
    private final int prepTime = 10;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_train);
        //Навигационное меню
        findViewById(R.id.achivmentIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActiveTrainActivity.this,AchievmentActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.homeIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActiveTrainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.profileIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActiveTrainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.eatingIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActiveTrainActivity.this,EatingActivity.class);
                startActivity(intent);
            }
        });

        initialTime = exerciseDurations[currentExerciseIndex] * 1000;
        // Инициализация UI компонентов
        initializeUI();

        // Загрузка ресурсов
        loadResources();

        // Инициализация массива упражнений
        initializeExercises();

        // Запуск подготовки к первому упражнению
        prepareForExercise(currentExerciseIndex);
    }

    // Инициализация UI компонентов
    private void initializeUI() {
        gifOneImage = findViewById(R.id.gifOneImage);
        nameTrain = findViewById(R.id.nameTrain);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        timerTextView = findViewById(R.id.timerTextView);
        pauseButton = findViewById(R.id.pauseButton);
        lastTrainBtn = findViewById(R.id.lastTrainBtn);
        skipTrainBtn = findViewById(R.id.skipTrainBtn);

        // Обработчики кликов
        pauseButton.setOnClickListener(view -> pauseTraining());
        lastTrainBtn.setOnClickListener(view -> previousExercise());
        skipTrainBtn.setOnClickListener(view -> skipExercise());
    }

    // Загрузка ресурсов
    private void loadResources() {
        // Загрузка GIF ресурсов и названий упражнений
        gifResources = new int[]{R.drawable.gif_1, R.drawable.gif_2,   R.drawable.gif_3,
                R.drawable.gif_4,  R.drawable.gif_5, R.drawable.gif_1,  R.drawable.gif_2,
                R.drawable.gif_3, R.drawable.gif_4, R.drawable.gif_5};

        exerciseNames = new int[]{ R.string.elem_train_3, R.string.elem_train_4,  R.string.elem_train_5,
                 R.string.elem_train_1, R.string.elem_train_2,   R.string.elem_train_3,
                 R.string.elem_train_4, R.string.elem_train_5,   R.string.elem_train_1,
                 R.string.elem_train_2};
        exerciseDurations = new int[]{ 30, 30, 25, 25, 25, 30, 30, 25, 25, 25};
    }

    // Инициализация массива упражнений
    private void initializeExercises() {
        for (int i = 0; i < gifResources.length; i++) {
            currentExercise.add(i);
        }
    }

    // Подготовка к упражнению
    private void prepareForExercise(int exerciseIndex) {
        // Показываем гифку подготовки и устанавливаем таймер подготовки
        Glide.with(this).load(R.drawable.graybackgroundfortrain).into(gifOneImage);
        nameTrain.setText(getString(R.string.elem_train_6));
        // Проверка на корректность индекса
        if (exerciseIndex >= 0 && exerciseIndex < exerciseDurations.length) {
            // Установка начального времени таймера для текущего упражнения
            initialTime = exerciseDurations[exerciseIndex];
            timeRemaining = initialTime;

            // Запуск таймера с подготовительным временем и началом упражнения
            startTimer(prepTime, () -> startExercise(exerciseIndex));
        } else {
            // Обработка ошибки некорректного индекса
            handleInvalidExerciseIndex();
        }
    }

    // Запуск таймера с callback по завершению
    private void startTimer(int time, Runnable onFinish) {

        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("Timer", "Time remaining: " + millisUntilFinished);
                // Обновление UI каждую секунду
                updateTimerUI((int) millisUntilFinished);
                timeRemaining = (int) millisUntilFinished / 1000;
            }

            public void onFinish() {
                // Вызов callback
                onFinish.run();
            }
        }.start();
    }

    // Обновление UI таймера
    private void updateTimerUI(int time) {

        int minutes = time / 60000;
        int seconds = (time % 60000) / 1000;
        String timeLeftText;

        if (minutes < 10) timeLeftText = "0" + minutes;
        else timeLeftText = "" + minutes;

        timeLeftText += ":";

        if (seconds < 10) timeLeftText += "0" + seconds;
        else timeLeftText += seconds;

        timerTextView.setText(timeLeftText);
    }

    // Запуск упражнения
    private void startExercise(int exerciseIndex) {
        // Загрузка гифки и имени упражнения
        Glide.with(this).load(gifResources[exerciseIndex]).into(gifOneImage);
        nameTrain.setText(getString(exerciseNames[exerciseIndex]));
        circularProgressBar.setProgress(0);
        startTimer(exerciseDurations[exerciseIndex], this::nextExercise);
    }

    // Переход к следующему упражнению
    private void nextExercise() {
        if (currentExerciseIndex < currentExercise.size() - 1) {
            currentExerciseIndex++;
            prepareForExercise(currentExerciseIndex);
        } else {
            // Тренировка завершена
            timerTextView.setText(getString(R.string.training_complete));
            showCompletionDialog();
        }
    }

    private void showCompletionDialog() {
        // Получаем SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstFinishTrainDialogShown = settings.getBoolean("firstFinishTrainDialogShown", false);

        if (!isFirstFinishTrainDialogShown) {
            // Пользователь впервые видит finish_train_dialog_layout.xml, сохраняем это состояние
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstFinishTrainDialogShown", true);
            editor.apply();
            // Создаем диалоговое окно с пользовательским макетом
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Загружаем пользовательский макет
            View customLayout = getLayoutInflater().inflate(R.layout.finish_train_dialog_layout, null);
            builder.setView(customLayout);

            // Находим элементы в пользовательском макете
            TextView title = customLayout.findViewById(R.id.customDialogTitle);
            TextView message = customLayout.findViewById(R.id.customDialogMessage);
            Button closeButton = customLayout.findViewById(R.id.customDialogCloseButton);

            // Устанавливаем текст для элементов
            title.setText("Поздравляем!");
            message.setText("Тренировка завершена");

            // Добавляем обработчик нажатия для кнопки закрыть
            closeButton.setOnClickListener(v -> {
                // Закрыть диалоговое окно
                dialog.dismiss();
                // Действия после закрытия диалога, например, возврат на главный экран
                finish();
            });

            // Создаем и показываем диалоговое окно
            AlertDialog dialog = builder.create();
            dialog.show();

            // Меняем изображение на достижение в AchievmentActivity
            SharedPreferences.Editor achievmentEditor = settings.edit();
            achievmentEditor.putBoolean("firstTrainingAchievement", true);
            achievmentEditor.apply();
        }
    }

    // Переопределение метода onPause
    @Override
    protected void onPause() {
        super.onPause();
        saveTimerState(); // Сохраняем состояние таймера
    }

    // Переопределение метода onResume
    @Override
    protected void onResume() {
        super.onResume();
        restoreTimerState(); // Восстанавливаем состояние таймера
    }

    // Метод для сохранения состояния таймера
    private void saveTimerState() {
        if (timer != null) {
            timer.cancel(); // Останавливаем таймер
            remainingTime = timeRemaining * 1000; // Сохраняем оставшееся время в миллисекундах
        }
    }

    // Метод для восстановления состояния таймера
    private void restoreTimerState() {
        if (remainingTime > 0) {
            startTimer((int) (remainingTime / 1000), this::nextExercise); // Восстанавливаем таймер
        }
    }

    // Пауза тренировки
    private void pauseTraining() {
        saveTimerState(); // Сохраняем состояние таймера
        showPauseDialog(); // Показываем диалог паузы
    }

    // Показать диалоговое окно паузы
    private void showPauseDialog() {
        // Создаем новый диалог
        final Dialog pauseDialog = new Dialog(this);
        // Устанавливаем для диалога макет из XML
        pauseDialog.setContentView(R.layout.pause_dialog);
        // Находим кнопки в макете
        Button continueButton = pauseDialog.findViewById(R.id.continueButton);
        Button restartButton = pauseDialog.findViewById(R.id.restartButton);
        Button exitButton = pauseDialog.findViewById(R.id.exitButton);

        // Устанавливаем обработчики нажатий для кнопок
        continueButton.setOnClickListener(v -> {
            resumeTraining();
            pauseDialog.dismiss();
        });
        restartButton.setOnClickListener(v -> {
            resetTraining();
            pauseDialog.dismiss();
        });
        exitButton.setOnClickListener(v -> {
            // Создаем намерение для перехода обратно в TrainingActivity
            Intent intent = new Intent(ActiveTrainActivity.this, TrainingActivity.class);
            // Запускаем активность
            startActivity(intent);
            // Закрываем текущую активность, чтобы пользователь не мог вернуться к ней, нажав кнопку "Назад"
            finish();
        });

        // Показываем диалогт
        pauseDialog.show();
    }

    // Возобновление тренировки
    private void resumeTraining() {
        restoreTimerState(); // Восстанавливаем состояние таймера
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    // Сброс тренировки
    private void resetTraining() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        currentExerciseIndex = 0;
        prepareForExercise(currentExerciseIndex);
    }

    // Переход к предыдущему упражнению
    private void previousExercise() {
        if (currentExerciseIndex > 0) {
            stopAndResetTimer(); // Остановить и сбросить таймер
            currentExerciseIndex--;
            prepareForExercise(currentExerciseIndex);
        }
    }

    // Пропуск текущего упражнения
    private void skipExercise() {
        if (currentExerciseIndex < currentExercise.size() - 1) {
            stopAndResetTimer(); // Остановить и сбросить таймер
            currentExerciseIndex++;
            prepareForExercise(currentExerciseIndex);
        }
    }
    // Метод для остановки и сброса таймера
    private void stopAndResetTimer() {
        if (timer != null) {
            timer.cancel(); // Останавливаем таймер
            timer = null; // Обнуляем ссылку на таймер
        }
        // Сбросить переменные, связанные с таймером
        timeRemaining = initialTime;

        // Обновить UI
        updateTimerUI(initialTime);
    }
    private void handleInvalidExerciseIndex() {
        // Выводим сообщение об ошибке
        Toast.makeText(this, "Выбрано некорректное упражнение", Toast.LENGTH_SHORT).show();
        // Можно также добавить логику для возврата пользователя к выбору упражнения
    }

}


