package com.example.power;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EatingActivityLunch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eating_dinner);

        findViewById(R.id.achivmentIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivityLunch.this,AchievmentActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.homeIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivityLunch.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.profileIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivityLunch.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.trainingIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatingActivityLunch.this,TrainingActivity.class);
                startActivity(intent);
            }
        });

        //Иницилизация кнопок для рецептов
        ImageButton inf_btn_1 = findViewById(R.id.inf_btn_1);
        ImageButton inf_btn_2 = findViewById(R.id.inf_btn_2);
        ImageButton inf_btn_3 = findViewById(R.id.inf_btn_3);
        ImageButton inf_btn_4 = findViewById(R.id.inf_btn_4);
        ImageButton inf_btn_5 = findViewById(R.id.inf_btn_5);
        ImageButton inf_btn_6 = findViewById(R.id.inf_btn_6);

        inf_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNone();
            }
        });
        inf_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNone();
            }
        });
        inf_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNone();
            }
        });
        inf_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNone();
            }
        });
        inf_btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNone();
            }
        });
        inf_btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNone();
            }
        });
    }

    private void showDialogNone() {
        final Dialog dialog = new Dialog(EatingActivityLunch.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.recept_none);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f); // Установите уровень размытия
        dialog.show();

        ImageButton imageButton2 = dialog.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}