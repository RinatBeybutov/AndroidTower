package com.example.movingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    private TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textInfo = findViewById(R.id.textInfo);
        Button buttonMenu = findViewById(R.id.buttonMenuOnHelp);
        String text = "Цель игры - перенести все диски с левого стрежня на любой другой \n" +
                "- за раз переносится только 1 диск;\n" +
                "- можно брать один из верхних кругов любой башни и переносить на любой из соседних стержней;\n" +
                "- элемент меньшего размера должен покрывать крупный.";
        textInfo.setTextSize(12f);
        textInfo.setText(text);

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });

    }
}