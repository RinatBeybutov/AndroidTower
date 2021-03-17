package com.example.movingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    private TextView textInfo;

    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences = getSharedPreferences("mySettings", Context.MODE_PRIVATE);

        Button buttonRestart = findViewById(R.id.buttonRestart);
        Button buttonMenu = findViewById(R.id.buttonMenuInRefresh);
        Button buttonSaveRecord = findViewById(R.id.buttonSaveRecrord);

        textInfo = findViewById(R.id.textRecordInEdngame);

        final int steps = getIntent().getExtras().getInt("steps");
        final String time = getIntent().getExtras().getString("time");

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

        buttonSaveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String record = "Steps: " + Integer.toString(steps) + " Time: " + time;

                if (newHighscore(steps, time)) {
                    SharedPreferences.Editor ed = mPreferences.edit();
                    switch (MainActivity.countDisks) {
                        case 3:
                            ed.putString("record3", record);
                            break;
                        case 4:
                            ed.putString("record4", record);
                            break;
                        case 5:
                            ed.putString("record5", record);
                            break;
                    }

                    ed.commit();
                }
            }
        });

    }

    private boolean newHighscore(int steps, String time) {

        String oldRecord = "";

        switch (MainActivity.countDisks) {
            case 3:
                if (!mPreferences.contains("record3")) {
                    return true;
                } else {
                    oldRecord = mPreferences.getString("record3", "");
                }
                break;
            case 4:
                if (!mPreferences.contains("record4")) {
                    return true;
                } else {
                    oldRecord = mPreferences.getString("record4", "");
                }
                break;
            case 5:
                if (!mPreferences.contains("record5")) {
                    return true;
                } else {
                    oldRecord = mPreferences.getString("record5", "");
                }
                break;
        }

        String[] array = oldRecord.split(" ");

        int oldSteps = Integer.parseInt(array[1]);

        String oldTime = array[3];

        //System.out.println(oldTime + " || " + time);
        //System.out.println(steps + " || " + oldSteps);
        //System.out.println("Between steps " + (oldSteps >= steps));
        //System.out.println("Between time " + oldTime.compareTo(time));

        if (oldSteps >= steps && oldTime.compareTo(time) > 0) {
            return true;
        }

        return false;
    }
}