package com.example.power;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    private EditText heightEt, weightEt, weightDesiredEt;
    private Button btnSave, btnLoad;
    private SharedPreferences sharedPreferences;

    private static final String HEIGHT_KEY = "height";
    private static final String WEIGHT_KEY = "weight";
    private static final String WEIGHT_DESIRED_KEY = "weight_desired";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        heightEt = findViewById(R.id.height_et);
        weightEt = findViewById(R.id.weight_et);
        weightDesiredEt = findViewById(R.id.weight_desired_et);

        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        btnSave.setOnClickListener(v -> saveData());
        btnLoad.setOnClickListener(v -> loadData());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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