package com.example.power;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity2 extends AppCompatActivity {
    private EditText massFatEt, massMuscleEt;
    private TextView fatPercentTv, imtTv;
    private ImageView backgroundBlue, backgroundGreen, backgroundOrange, backgroundPink, backgroundBlueIMT, backgroundGreenIMT, backgroundOrangeIMT, backgroundPinkIMT, backgroundRedIMT;
    private SharedPreferences sharedPreferences;
    private static final String MASS_FAT_KEY = "mass_fat";
    private static final String MASS_MUSCLE_KEY = "mass_muscle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        massFatEt = findViewById(R.id.massFatEt);
        massMuscleEt = findViewById(R.id.massMuscleEt);
        fatPercentTv = findViewById(R.id.fatPercent);
        imtTv = findViewById(R.id.textViewIMT);
        backgroundBlue = findViewById(R.id.background_blue);
        backgroundGreen = findViewById(R.id.background_green);
        backgroundOrange = findViewById(R.id.background_orange);
        backgroundPink = findViewById(R.id.background_pink);
        backgroundBlueIMT = findViewById(R.id.background_blue_imt);
        backgroundGreenIMT = findViewById(R.id.background_green_imt);
        backgroundOrangeIMT = findViewById(R.id.background_orange_imt);
        backgroundPinkIMT = findViewById(R.id.background_pink_imt);
        backgroundRedIMT = findViewById(R.id.background_red_imt);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        loadValues();

        massFatEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Здесь ничего не нужно делать
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Здесь ничего не нужно делать
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveValues();
                calculateFatPercentage();
                calculateIMT();
            }
        });

        massMuscleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Здесь ничего не нужно делать
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Здесь ничего не нужно делать
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveValues();
                calculateFatPercentage();
                calculateIMT();
            }
        });

        findViewById(R.id.button_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity2.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.achivmentIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity2.this,AchievmentActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.homeIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.eatingIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity2.this,EatingActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.trainingIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity2.this,TrainingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveValues() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MASS_FAT_KEY, massFatEt.getText().toString());
        editor.putString(MASS_MUSCLE_KEY, massMuscleEt.getText().toString());
        editor.apply();
    }

    private void loadValues() {
        String massFat = sharedPreferences.getString(MASS_FAT_KEY, "");
        String massMuscle = sharedPreferences.getString(MASS_MUSCLE_KEY, "");
        massFatEt.setText(massFat);
        massMuscleEt.setText(massMuscle);
        calculateFatPercentage();
        calculateIMT();
    }

    private void calculateFatPercentage() {
        String massFatString = massFatEt.getText().toString();
        if (!massFatString.isEmpty()) {
            float weight = Float.parseFloat(sharedPreferences.getString(ProfileActivity.WEIGHT_KEY, "0"));
            float massFat = Float.parseFloat(massFatString);
            float fatPercent = (massFat / weight) * 100;
            fatPercentTv.setText(String.format("%.1f", fatPercent) + "%");
            // Обновление фона в зависимости от процента жира
            updateBackground(fatPercent, new ImageView[]{backgroundBlue, backgroundGreen, backgroundOrange, backgroundPink}, R.drawable.background_for_color_cube);
        }
    }

    private void calculateIMT() {
        String weightString = sharedPreferences.getString(ProfileActivity.WEIGHT_KEY, "0");
        String heightString = sharedPreferences.getString(ProfileActivity.HEIGHT_KEY, "0");
        if (!weightString.isEmpty() && !heightString.isEmpty()) {
            float weight = Float.parseFloat(weightString);
            float height = Float.parseFloat(heightString) / 100;
            if (height != 0) { // Дополнительная проверка, чтобы избежать деления на ноль
                float imt = weight / (height * height);
                imt = Math.round(imt * 10) / 10.0f; // Округление до одного знака после запятой
                imtTv.setText(String.format("%.1f", imt));
                // Обновление фона в зависимости от ИМТ
                updateBackgroundIMT(imt, new ImageView[]{backgroundBlueIMT, backgroundGreenIMT, backgroundOrangeIMT, backgroundPinkIMT, backgroundRedIMT}, R.drawable.background_for_color_cube);
            }
        }
    }

    private void updateBackground(float value, ImageView[] backgrounds, int drawableId) {
        for (ImageView background : backgrounds) {
            background.setImageResource(0); // Сброс фона
        }

        if (value < 18.7) {
            backgrounds[0].setImageResource(drawableId);
        } else if (value < 30.1) {
            backgrounds[1].setImageResource(drawableId);
        } else if (value < 37.2) {
            backgrounds[2].setImageResource(drawableId);
        } else {
            backgrounds[3].setImageResource(drawableId);
        }
    }
    private void updateBackgroundIMT(float value, ImageView[] backgrounds, int drawableId) {
        for (ImageView background : backgrounds) {
            background.setImageResource(0); // Сброс фона
        }

        if (value < 17.8) {
            backgrounds[0].setImageResource(drawableId);
        } else if (value < 26) {
            backgrounds[1].setImageResource(drawableId);
        } else if (value < 31) {
            backgrounds[2].setImageResource(drawableId);
        } else if (value < 40) {
            backgrounds[3].setImageResource(drawableId);
        } else {
            backgrounds[4].setImageResource(drawableId);
        }
    }
}
