package com.example.movingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int COORDINATE_LEFT = 30;
    static final int COORDINATE_CENTRE = 460;
    static final int COORDINATE_RIGHT = 910;
    static final int DELTA_COORDINATES = COORDINATE_RIGHT - COORDINATE_CENTRE;

    private ImageView imageBlockOne;
    private ImageView imageBlockTwo;
    private ImageView imageBlockTree;
    private ImageView imageBlockZero;
    private ImageView imageBlockFour;
    private RelativeLayout layout;
    private boolean isBlockUp;

    private POSITION positionCursor = POSITION.LEFT;

    private TextView textTime;
    private TextView textSteps;

    private ArrayList<Block> listBlocks;

    private Block blockZero;

    // Time
    private long startTime = 0l;
    private Handler handler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    private int countSteps = 0;

    public static int countDisks = 3;
    public static boolean enableSteps = true;
    public static boolean enableChronometer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Buttons

        Button buttonUp = findViewById(R.id.buttonUp);
        Button buttonDown = findViewById(R.id.buttonDown);
        Button buttonLeft = findViewById(R.id.buttonLeft);
        Button buttonRight = findViewById(R.id.buttonRight);
        Button buttonMenu = findViewById(R.id.buttonMenuInGame);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Initialize Images

        imageBlockOne = findViewById(R.id.blockOne);
        imageBlockTwo = findViewById(R.id.blockTwo);
        imageBlockTree = findViewById(R.id.blockTree);
        imageBlockFour = findViewById(R.id.blockFour);

        imageBlockZero = findViewById(R.id.blockZero);

        listBlocks = new ArrayList<>();

        listBlocks.add(new Block(imageBlockOne));
        listBlocks.add(new Block(imageBlockTwo));
        listBlocks.add(new Block(imageBlockTree));
        listBlocks.add(new Block(imageBlockFour));

        blockZero = new Block(imageBlockZero);

        textTime = findViewById(R.id.textTime);
        textTime.setTextSize(20f);

        if (enableChronometer) {
            textTime.setVisibility(View.VISIBLE);

        } else {
            textTime.setVisibility(View.INVISIBLE);
        }

        startTimer();

        textSteps = findViewById(R.id.textSteps);
        textSteps.setTextSize(20f);

        if (enableSteps) {
            textSteps.setText("Steps: " + Integer.toString(countSteps));
        }
        else {
            textSteps.setVisibility(View.INVISIBLE);
        }

        //createStartPositionBoxes();

        //checkDisplay();

        // Set Listeners To Buttons

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingDownImage();
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingUpImage();
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingLeftImage();
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingRightImage();
            }
        });
    }

    private void createStartPositionBoxes() {

        blockZero.setPosition(POSITION.LEFT);
        blockZero.setLeftMargin(COORDINATE_LEFT);
        blockZero.setBottomMargin(10);

        int leftMarginTmp = 10;
        int bottomMarginTmp = 0;

        for(Block block : listBlocks)
        {
            block.setVisible(false);
        }

        for (int i = countDisks-1; i>=0; i--)
        {
            Block block = listBlocks.get(i);
            block.setVisible(true);
            block.setLeftMargin(leftMarginTmp);
            leftMarginTmp +=10;

            block.setBottomMargin(bottomMarginTmp + 10);
            bottomMarginTmp = bottomMarginTmp + 10 + block.getHeight();

        }

    }

    private void startTimer() {
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(updateTimerThread, 0);
    }

    private void stopTimer() {
        startTime = 0l;
        handler.removeCallbacks(updateTimerThread);
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            textTime.setText("Time: " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };

    private void checkDisplay() {
        // Узнаем размеры экрана из ресурсов
        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();

        // узнаем размеры экрана из класса Display
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);

        textTime.setText(
                "[Используя ресурсы] \n" +
                        "Ширина: " + displaymetrics.widthPixels + "\n" +
                        "Высота: " + displaymetrics.heightPixels + "\n"
                        + "\n" +
                        "[Используя Display] \n" +
                        "Ширина: " + metricsB.widthPixels + "\n" +
                        "Высота: " + metricsB.heightPixels + "\n"
        );
    }

    private void movingRightImage() {

        Block blockUpped = null;
        for (Block block : listBlocks) {
            if (block.isUp()) {
                blockUpped = block;
            }
        }

        if (positionCursor == POSITION.CENTRE) {
            if (blockUpped != null) {
                blockUpped.setLeftMargin(blockUpped.getLeftMargin() + DELTA_COORDINATES);
                blockUpped.setPosition(POSITION.RIGHT);
            }
            movingBlockZeroRight();
            positionCursor = POSITION.RIGHT;
            //textView.setText("3");
        }

        if (positionCursor == POSITION.LEFT) {
            if (blockUpped != null) {
                blockUpped.setLeftMargin(blockUpped.getLeftMargin() + DELTA_COORDINATES);
                blockUpped.setPosition(POSITION.CENTRE);
            }
            movingBlockZeroRight();
            positionCursor = POSITION.CENTRE;
            //textView.setText("2");
        }
    }

    private void movingLeftImage() {

        Block blockUpped = null;
        for (Block block : listBlocks) {
            if (block.isUp()) {
                blockUpped = block;
            }
        }

        if (positionCursor == POSITION.CENTRE) {
            if (blockUpped != null) {
                blockUpped.setLeftMargin(blockUpped.getLeftMargin() - DELTA_COORDINATES);
                blockUpped.setPosition(POSITION.LEFT);
            }
            positionCursor = POSITION.LEFT;
            movingBlockZeroLeft();

            //textView.setText("1");
        }

        if (positionCursor == POSITION.RIGHT) {
            if (isBlockUp) {
                blockUpped.setLeftMargin(blockUpped.getLeftMargin() - DELTA_COORDINATES);
                blockUpped.setPosition(POSITION.CENTRE);
            }
            positionCursor = POSITION.CENTRE;
            movingBlockZeroLeft();
            //textView.setText("2");
        }

    }

    private Block getHighestBox() {

        Block highestBlock = null;
        int hiestCoordinates = 0;
        for (Block block : listBlocks) {

            if (block.getPosition() != positionCursor || block.isUp()) {
                continue;
            }
            if (block.getBottomMargin() > hiestCoordinates) {
                highestBlock = block;
                hiestCoordinates = block.getBottomMargin();
            }
        }
        return highestBlock;
    }

    private void movingUpImage() {
        Block currentUpBlock = getHighestBox();

        if (!isBlockUp && currentUpBlock != null) {
            currentUpBlock.setBottomMargin(220);
            currentUpBlock.setUp(true);
            isBlockUp = true;
        }
    }

    private void movingDownImage() {

        Block blockUpped = null;
        for (Block block : listBlocks) {
            if (block.isUp()) {
                blockUpped = block;
            }
        }

        if (blockUpped == null) {
            return;
        }

        if (isBlockUp) {
            Block hiestBlock = getHighestBox();

            if (hiestBlock != null && blockUpped.getWidth() > hiestBlock.getWidth()) {
                return;
            }

            if (hiestBlock != null) {
                blockUpped.setBottomMargin(hiestBlock.getBottomMargin() + hiestBlock.getHeight() + 20);
            } else {
                blockUpped.setBottomMargin(20);
            }
            blockUpped.setUp(false);
            isBlockUp = false;
            countSteps++;
            textSteps.setText("Steps: " + Integer.toString(countSteps));
        }

        boolean isOver = true;
        Block tmpBlock = listBlocks.get(0);
        for (Block block : listBlocks) {
            if (block.getPosition() != tmpBlock.getPosition()) {
                isOver = false;
                break;
            }
            tmpBlock = block;
        }

        if (isOver) {
            stopTimer();
            //textView.setText("OVER");
        }
    }

    private void movingBlockZeroRight() {

        blockZero.setLeftMargin(blockZero.getLeftMargin() + DELTA_COORDINATES);
        switch (blockZero.getPosition()) {
            case LEFT:
                blockZero.setPosition(POSITION.CENTRE);
                break;
            case CENTRE:
                blockZero.setPosition(POSITION.RIGHT);
                break;
        }

    }

    private void movingBlockZeroLeft() {
        blockZero.setLeftMargin(blockZero.getLeftMargin() - DELTA_COORDINATES);
        switch (blockZero.getPosition()) {
            case RIGHT:
                blockZero.setPosition(POSITION.CENTRE);
                break;
            case CENTRE:
                blockZero.setPosition(POSITION.LEFT);
                break;
        }
    }


}