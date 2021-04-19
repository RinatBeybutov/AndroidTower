    package com.example.movingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class second_main extends AppCompatActivity {

    public static boolean enableSteps = true;
    public static boolean enableChronometer = true;
    private ImageView boxL5;
    private ImageView boxL4;
    private ImageView boxL3;
    private ImageView boxL2;
    private ImageView boxL1;

    private ImageView boxC5;
    private ImageView boxC4;
    private ImageView boxC3;
    private ImageView boxC2;
    private ImageView boxC1;

    private ImageView boxR5;
    private ImageView boxR4;
    private ImageView boxR3;
    private ImageView boxR2;
    private ImageView boxR1;

    private ImageView boxZero;

    Block blockZero;

    Block blockL4;
    Block blockL5;
    Block blockL3;
    Block blockL2;
    Block blockL1;

    Block blockC4;
    Block blockC5;
    Block blockC3;
    Block blockC2;
    Block blockC1;

    Block blockR4;
    Block blockR5;
    Block blockR3;
    Block blockR2;
    Block blockR1;

    ArrayList<Block> listBlocksL = new ArrayList<>();
    ArrayList<Block> listBlocksC = new ArrayList<>();
    ArrayList<Block> listBlocksR = new ArrayList<>();

    private RelativeLayout layoutLeft;
    private RelativeLayout layoutCenter;
    private RelativeLayout layoutRight;
    private RelativeLayout upLayout;

    private POSITION CURRENT_POSITION = POSITION.LEFT;
    private boolean isUp = false;

    //private TextView textPosition;
    private TextView textTime;
    private TextView textSteps;

    public static int countDisks = 3;

    // Time
    private long startTime = 0l;
    private Handler handler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    private int countSteps = 0;

    // Coordinate blockZero
    private int leftCoordinate = 0;
    private int centerCoordinate = 0;
    private int rightCoordinate = 0;

    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button buttonU = findViewById(R.id.buttonU);
        Button buttonD = findViewById(R.id.buttonD);
        Button buttonL = findViewById(R.id.buttonL);
        Button buttonR = findViewById(R.id.buttonR);
        Button buttonMenu = findViewById(R.id.buttonMenu);

        boxL5 = findViewById(R.id.boxL5);
        boxC5 = findViewById(R.id.boxC5);
        boxR5 = findViewById(R.id.boxR5);

        boxL4 = findViewById(R.id.boxL4);
        boxC4 = findViewById(R.id.boxC4);
        boxR4 = findViewById(R.id.boxR4);

        boxL3 = findViewById(R.id.boxL3);
        boxC3 = findViewById(R.id.boxC3);
        boxR3 = findViewById(R.id.boxR3);

        boxL2 = findViewById(R.id.boxL2);
        boxC2 = findViewById(R.id.boxC2);
        boxR2 = findViewById(R.id.boxR2);

        boxL1 = findViewById(R.id.boxL1);
        boxC1 = findViewById(R.id.boxC1);
        boxR1 = findViewById(R.id.boxR1);

        boxZero = findViewById(R.id.blockZero);

        layoutLeft = findViewById(R.id.layoutLeftTower);
        layoutCenter = findViewById(R.id.layoutCenterTower);
        layoutRight = findViewById(R.id.layoutRightTower);
        upLayout = findViewById(R.id.upLayout);

        //textPosition = findViewById(R.id.textView);
        //textPosition.setText("1");
        //textPosition.setTextSize(20f);

        textTime = findViewById(R.id.textTime);
        textTime.setTextSize(20f);

        textSteps = findViewById(R.id.textStep);
        textSteps.setText("Steps: " + Integer.toString(countSteps));
        textSteps.setTextSize(20f);

        if (!enableChronometer) {
            textTime.setVisibility(View.INVISIBLE);
        }

        if (!enableSteps) {
            textSteps.setVisibility(View.INVISIBLE);
        }

        initializationBlocks();

        startTimer();

        startPosition();

        buttonU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingUp();
            }
        });

        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingRight();
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingDown();
            }
        });

        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingLeft();
            }
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Menu.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
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

    private void startPosition() {
        for (int i = 0; i < countDisks; i++) {
            listBlocksL.get(i).setVisible(true);
        }
        blockZero.setLeftMargin(100);
    }

    private void initializationBlocks() {

        blockZero = new Block(boxZero);
        blockZero.setVisible(false);

        blockL4 = new Block(boxL4);
        blockL5 = new Block(boxL5);
        blockL3 = new Block(boxL3);
        blockL2 = new Block(boxL2);
        blockL1 = new Block(boxL1);

        listBlocksL.add(blockL5);
        listBlocksL.add(blockL4);
        listBlocksL.add(blockL3);
        listBlocksL.add(blockL2);
        listBlocksL.add(blockL1);

        blockC4 = new Block(boxC4);
        blockC5 = new Block(boxC5);
        blockC3 = new Block(boxC3);
        blockC2 = new Block(boxC2);
        blockC1 = new Block(boxC1);

        listBlocksC.add(blockC4);
        listBlocksC.add(blockC5);
        listBlocksC.add(blockC3);
        listBlocksC.add(blockC2);
        listBlocksC.add(blockC1);

        blockR4 = new Block(boxR4);
        blockR5 = new Block(boxR5);
        blockR3 = new Block(boxR3);
        blockR2 = new Block(boxR2);
        blockR1 = new Block(boxR1);

        listBlocksR.add(blockR4);
        listBlocksR.add(blockR5);
        listBlocksR.add(blockR3);
        listBlocksR.add(blockR2);
        listBlocksR.add(blockR1);

        for (Block block : listBlocksC) {
            block.setVisible(false);
        }

        for (Block block : listBlocksR) {
            block.setVisible(false);
        }

        for (Block block : listBlocksL) {
            block.setVisible(false);
        }

    }

    private void movingUp() {

        if (leftCoordinate == 0) {
            int width = upLayout.getWidth();
            leftCoordinate = width / 6 - boxZero.getWidth() / 2;
            centerCoordinate = width / 6 + width / 3 - boxZero.getWidth() / 2;
            rightCoordinate = width - width / 6 - boxZero.getWidth() / 2;
            blockZero.setVisible(true);
            blockZero.setLeftMargin(leftCoordinate);
        }

        isUp = true;
        Block currentUpBlock = getHighestBlock();
        if (currentUpBlock != null) {
            currentUpBlock.setUp(true);
            currentUpBlock.setBottomMargin(layoutLeft.getHeight() - 10 - currentUpBlock.getHeight());
        }
    }

    private void movingDown() {

        ArrayList<Block> currentListBlocks = null;

        switch (CURRENT_POSITION) {
            case LEFT:
                currentListBlocks = listBlocksL;
                break;
            case CENTRE:
                currentListBlocks = listBlocksC;
                break;
            case RIGHT:
                currentListBlocks = listBlocksR;
                break;
        }

        Block blockUpped = null;
        for (Block block : currentListBlocks) {
            if (block.isUp()) {
                blockUpped = block;
            }
        }

        if (blockUpped == null) {
            return;
        }

        if (isUp) {
            Block hiestBlock = getHighestBlock();

            if (hiestBlock != null && blockUpped.getWidth() > hiestBlock.getWidth()) {
                return;
            }

            if (hiestBlock != null) {
                blockUpped.setBottomMargin(hiestBlock.getBottomMargin() + hiestBlock.getHeight() + 10);
            } else {
                blockUpped.setBottomMargin(20);
            }
            blockUpped.setUp(false);
            isUp = false;
            countSteps++;
            textSteps.setText("Steps: " + Integer.toString(countSteps));
        }

        checkEndGame();

    }

    private void checkEndGame() {

        boolean isOver = false;

        if (CURRENT_POSITION == POSITION.CENTRE) {

            int countVisible = 0;
            for (Block block : listBlocksC) {
                if (block.isVisible()) {
                    countVisible++;
                }
            }

            if (countVisible == countDisks) {
                isOver = true;
            }
        }

        if (CURRENT_POSITION == POSITION.RIGHT) {
            int countVisible = 0;
            for (Block block : listBlocksR) {
                if (block.isVisible()) {
                    countVisible++;
                }
            }

            if (countVisible == countDisks) {
                isOver = true;
            }
        }

        if (isOver) {
            stopTimer();
            Intent intent = new Intent(getApplicationContext(), EndGame.class);

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            String time = mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds);
            System.out.println(time + "||" + countSteps);
            intent.putExtra("time", time);
            intent.putExtra("steps", countSteps);

            startActivity(intent);
        }
    }

    private void movingLeft() {

        if (CURRENT_POSITION == POSITION.RIGHT) {

            if (isUp) {
                int width = 0;
                for (Block block : listBlocksR) {
                    if (block.isUp()) {
                        block.setVisible(false);
                        block.setUp(false);
                        width = block.getWidth();
                    }
                }

                for (Block block : listBlocksC) {
                    if (block.getWidth() == width) {
                        block.setVisible(true);
                        block.setBottomMargin(layoutCenter.getHeight() - 10 - block.getHeight());
                        block.setUp(true);
                    }
                }
            }
            CURRENT_POSITION = POSITION.CENTRE;
            blockZero.setLeftMargin(centerCoordinate);
            return;
        }

        if (CURRENT_POSITION == POSITION.CENTRE) {
            if (isUp) {
                int width = 0;
                for (Block block : listBlocksC) {
                    if (block.isUp()) {
                        block.setVisible(false);
                        block.setUp(false);
                        width = block.getWidth();
                    }
                }

                for (Block block : listBlocksL) {
                    if (block.getWidth() == width) {
                        block.setVisible(true);
                        block.setBottomMargin(layoutCenter.getHeight() - 10 - block.getHeight());
                        block.setUp(true);
                    }
                }
            }
            CURRENT_POSITION = POSITION.LEFT;
            blockZero.setLeftMargin(leftCoordinate);
        }
    }

    private void movingRight() {

        if (CURRENT_POSITION == POSITION.LEFT) {

            if (isUp) {
                int width = 0;
                for (Block block : listBlocksL) {
                    if (block.isUp()) {
                        block.setVisible(false);
                        block.setUp(false);
                        width = block.getWidth();
                    }
                }

                for (Block block : listBlocksC) {
                    if (block.getWidth() == width) {
                        block.setVisible(true);
                        block.setBottomMargin(layoutCenter.getHeight() - 10 - block.getHeight());
                        block.setUp(true);
                    }
                }
            }
            CURRENT_POSITION = POSITION.CENTRE;
            blockZero.setLeftMargin(centerCoordinate);
            return;
        }

        if (CURRENT_POSITION == POSITION.CENTRE) {

            if (isUp) {
                int width = 0;
                for (Block block : listBlocksC) {
                    if (block.isUp()) {
                        block.setVisible(false);
                        block.setUp(false);
                        width = block.getWidth();
                    }
                }

                for (Block block : listBlocksR) {
                    if (block.getWidth() == width) {
                        block.setVisible(true);
                        block.setBottomMargin(layoutCenter.getHeight() - 10 - block.getHeight());
                        block.setUp(true);
                    }
                }
            }
            CURRENT_POSITION = POSITION.RIGHT;
            blockZero.setLeftMargin(rightCoordinate);
        }

    }

    private Block getHighestBlock() {
        Block highestBlock = null;
        int highestCoordinates = 0;
        int i = 0;

        ArrayList<Block> currentListBlocks = null;

        switch (CURRENT_POSITION) {
            case LEFT:
                currentListBlocks = listBlocksL;
                break;
            case CENTRE:
                currentListBlocks = listBlocksC;
                break;
            case RIGHT:
                currentListBlocks = listBlocksR;
                break;
        }

        for (Block block : currentListBlocks) {
            if (!block.isVisible() || block.isUp()) {
                continue;
            }
            if (block.getBottomMargin() > highestCoordinates) {
                highestBlock = block;
                highestCoordinates = block.getBottomMargin();
            }

        }
        return highestBlock;
    }
}