package com.example.movingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    private TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button buttonRestart = findViewById(R.id.buttonRestart);
        Button buttonMenu = findViewById(R.id.buttonMenuInRefresh);
        Button buttonSaveRecord = findViewById(R.id.buttonSaveRecrord);

        textInfo = findViewById(R.id.textRecord);

        int steps = getIntent().getExtras().getInt("steps");
        String time = getIntent().getExtras().getString("time");

        String text = "Steps: " + Integer.toString(steps) + "\n" +
                 "Time: " + time;

        textInfo.setTextSize(30f);
        textInfo.setText(text);

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}