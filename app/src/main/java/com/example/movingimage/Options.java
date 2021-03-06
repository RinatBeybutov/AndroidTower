package com.example.movingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Options extends AppCompatActivity {

    private CheckBox checkSteps;
    private CheckBox checkChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button buttonMenu = findViewById(R.id.buttonMenuInOptions);

        checkSteps = findViewById(R.id.checkBoxSteps);
        checkChronometer = findViewById(R.id.checkBoxChonometr);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        RadioButton button3 = findViewById(R.id.buttonCount3);
        RadioButton button4 = findViewById(R.id.buttonCount4);
        RadioButton button5 = findViewById(R.id.buttonCount5);

        checkChronometer.setChecked(Main_Activity.enableChronometer);
        checkSteps.setChecked(Main_Activity.enableSteps);

        switch (Main_Activity.countDisks){
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
                Intent intent = new Intent(getApplicationContext(), Menu.class);
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
                    Main_Activity.enableSteps = checkSteps.isChecked();
                    break;
                case R.id.checkBoxChonometr:
                    Main_Activity.enableChronometer = checkChronometer.isChecked();
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
                    Main_Activity.countDisks = 3;
                    break;
                case R.id.buttonCount4:
                    Main_Activity.countDisks = 4;
                    break;
                case R.id.buttonCount5:
                    Main_Activity.countDisks = 5;
                    break;
            }
        }
    };

}