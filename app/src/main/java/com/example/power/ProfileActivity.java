package com.example.power;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {
    private Button selectGenderButton;
    private Button selectLevelButton;
    private Button selectEatingButton;
    private static final String LEVEL_KEY = "level";
    private static final String GENDER_KEY = "gender";
    private static final String EATING_KEY = "eating";
    private EditText heightEt, weightEt, weightDesiredEt;
    private Button btnSave, btnLoad;
    private SharedPreferences sharedPreferences;

    private static final String HEIGHT_KEY = "height";
    private static final String WEIGHT_KEY = "weight";
    private static final String WEIGHT_DESIRED_KEY = "weight_desired";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        selectLevelButton = findViewById(R.id.button_level);
        selectGenderButton = findViewById(R.id.button_male_female);
        selectEatingButton = findViewById(R.id.button_eating);
        heightEt = findViewById(R.id.height_et);
        weightEt = findViewById(R.id.weight_et);
        weightDesiredEt = findViewById(R.id.weight_desired_et);

        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        btnSave.setOnClickListener(v -> saveData());
        btnLoad.setOnClickListener(v -> loadData());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        selectGenderButton.setOnClickListener(v -> showGenderDialog());
        selectEatingButton.setOnClickListener(v -> showEatingDialog());
        selectLevelButton.setOnClickListener(v -> showLevelDialog());
    }
    private void showLevelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите уровень")
                .setItems(new CharSequence[]{"Начинающий", "Продолжающий"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String level = (which == 0) ? "Начинаюший" : "Продолжающий";
                        saveLevelChoice(level);
                    }
                })
                .show();
    }
    private void showEatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите тип питаня")
                .setItems(new CharSequence[]{"Набор веса", "Сброс веса"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String eating = (which == 0) ? "Набор" : "Сброс";
                        saveEatingChoice(eating);
                    }
                })
                .show();
    }
    private void showGenderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите пол")
                .setItems(new CharSequence[]{"Мужской", "Женский"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String gender = (which == 0) ? "Мужской" : "Женский";
                        saveGenderChoice(gender);
                    }
                })
                .show();
    }
    private void saveGenderChoice(String gender) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GENDER_KEY, gender);
        editor.apply();
        // После сохранения пола, обновите текст на кнопке для отображения выбранного пола
        selectGenderButton.setText(gender);
        Toast.makeText(this, "Выбран пол: " + gender, Toast.LENGTH_SHORT).show();
    }
    private void saveLevelChoice(String level) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LEVEL_KEY, level);
        editor.apply();
        // После сохранения пола, обновите текст на кнопке для отображения выбранного пола
        selectLevelButton.setText(level);
        Toast.makeText(this, "Выбран уровень: " + level, Toast.LENGTH_SHORT).show();
    }
    private void saveEatingChoice(String eating) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EATING_KEY, eating);
        editor.apply();
        // После сохранения пола, обновите текст на кнопке для отображения выбранного пола
        selectEatingButton.setText(eating);
        Toast.makeText(this, "Выбран тип Питания: " + eating, Toast.LENGTH_SHORT).show();
    }
    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HEIGHT_KEY, heightEt.getText().toString());
        editor.putString(WEIGHT_KEY, weightEt.getText().toString());
        editor.putString(WEIGHT_DESIRED_KEY, weightDesiredEt.getText().toString());
        editor.apply();
        Toast.makeText(this, "Успешно!", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        String height = sharedPreferences.getString(HEIGHT_KEY, "");
        String weight = sharedPreferences.getString(WEIGHT_KEY, "");
        String weightDesired = sharedPreferences.getString(WEIGHT_DESIRED_KEY, "");

        heightEt.setText(height);
        weightEt.setText(weight);
        weightDesiredEt.setText(weightDesired);
        Toast.makeText(this, "Данные загружены", Toast.LENGTH_SHORT).show();
    }
}