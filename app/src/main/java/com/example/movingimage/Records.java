package com.example.movingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Records extends AppCompatActivity {

    private TextView textTitle;
    private TextView textTitle3;
    private TextView textTitle4;
    private TextView textTitle5;

    private TextView textRecords3;
    private TextView textRecords4;
    private TextView textRecords5;

    SharedPreferences mPreferences;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        textTitle = findViewById(R.id.textTitle);
        textTitle3 = findViewById(R.id.textTitle3);
        textTitle4 = findViewById(R.id.textTitle4);
        textTitle5 = findViewById(R.id.textTitle5);

        textRecords3 = findViewById(R.id.textRecordInRecords3);
        textRecords4 = findViewById(R.id.textRecordInRecords4);
        textRecords5 = findViewById(R.id.textRecordInRecords5);

        textTitle.setText("Records");
        textRecords3.setTextSize(10f);
        textRecords4.setTextSize(10f);
        textRecords5.setTextSize(10f);

        textTitle3.setText("3 Диска");
        textTitle4.setText("4 Диска");
        textTitle5.setText("5 Диска");

        textTitle.setTextSize(30f);
        textTitle3.setTextSize(15f);
        textTitle4.setTextSize(15f);
        textTitle5.setTextSize(15f);


        mPreferences = getSharedPreferences("mySettings", Context.MODE_PRIVATE);

        /*mPreferences.edit().remove("record3");
        mPreferences.edit().remove("record4");
        mPreferences.edit().remove("record5");
        mPreferences.edit().clear().apply();*/


        if(mPreferences.contains("record3"))
        {
            textRecords3.setText(mPreferences.getString("record3",""));
        } else {
            textRecords3.setText("---");
        }

        if(mPreferences.contains("record4"))
        {
            textRecords4.setText(mPreferences.getString("record4",""));
        } else {
            textRecords4.setText("---");
        }

        if(mPreferences.contains("record5"))
        {
            textRecords5.setText(mPreferences.getString("record5",""));
        } else {
            textRecords5.setText("---");
        }

    }
}