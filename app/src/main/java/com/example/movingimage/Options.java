package com.example.movingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;




public class Options extends AppCompatActivity {

    CheckBox checkSteps;
    CheckBox checkChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button buttonMenu = findViewById(R.id.buttonMenuInOptions);

        checkSteps = findViewById(R.id.checkBoxSteps);
        checkChronometer = findViewById(R.id.checkBoxChonometr);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        RadioButton button3 = findViewById(R.id.buttonCount3);
        RadioButton button4 = findViewById(R.id.buttonCount4);
        RadioButton button5 = findViewById(R.id.buttonCount5);

        checkChronometer.setChecked(MainActivity.enableChronometer);
        checkSteps.setChecked(MainActivity.enableSteps);

        switch (MainActivity.countDisks){
            case 3:
                button3.setChecked(true);
                break;
            case 4:
                button4.setChecked(true);
                break;
            case 5:
                button5.setChecked(true);
                break;
        }


        button3.setOnClickListener(buttonClickListener);
        button4.setOnClickListener(buttonClickListener);
        button5.setOnClickListener(buttonClickListener);

        checkSteps.setOnClickListener(checkBoxListener);
        checkChronometer.setOnClickListener(checkBoxListener);

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Options.this, Menu.class);
                startActivity(intent);
            }
        });

    }

    View.OnClickListener checkBoxListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox checkBoxTmp = (CheckBox) v;
            switch (checkBoxTmp.getId()){
                case R.id.checkBoxSteps:
                    MainActivity.enableSteps = checkSteps.isChecked();
                    break;
                case R.id.checkBoxChonometr:
                    MainActivity.enableChronometer = checkChronometer.isChecked();
                    break;
            }
        }
    };


        View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton buttonTmp = (RadioButton) v;
            switch (buttonTmp.getId()){
                case R.id.buttonCount3:
                    MainActivity.countDisks = 3;
                    Log.e("OptionsClick", "3");
                    break;
                case R.id.buttonCount4:
                    MainActivity.countDisks = 4;
                    break;
                case R.id.buttonCount5:
                    MainActivity.countDisks = 5;
                    break;
            }
        }
    };

}