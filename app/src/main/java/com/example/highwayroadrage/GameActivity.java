package com.example.highwayroadrage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Player player;
    private ImageView wheel;

    private Button btnLeft;
    private Button btnRight;

    ArrayList<ArrayList<ImageView>> obstaclesList = new ArrayList<>();
    private ArrayList<ImageView> leftColumnObstacles = new ArrayList<>();
    private ArrayList<ImageView> middleColumnObstacles = new ArrayList<>();
    private ArrayList<ImageView> rightColumnObstacles = new ArrayList<>();

    private ImageView imgHeart1;
    private ImageView imgHeart2;
    private ImageView imgHeart3;
    private ArrayList<ImageView> hearts = new ArrayList<>();

    private ArrayList<ImageView> playerRow = new ArrayList<>();

    private TextView lblScore;
    private int clockCounter = 0;

    final int DELAY = 1000; // 1000 milliseconds == 1 second
    final Handler handler = new Handler();

    /**
     * the clock of the game , every 1 second we move obstacles and checks
     * whether the player got hit or not by the obstacles
     */
    Runnable r = new Runnable() {
        public void run() {
            handler.postDelayed(this, DELAY);
            moveObstacles();
            if (clockCounter % 2 == 0) {
                generateObstacle();
            }
            clockCounter++;
            checkCollision();
        }
    };

    /**
     * Hide the status and navigation bar at the top of the screen!
     */
    public void hideStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    );
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStatusBar();

        findViews();
        findObstaclesView();
        player = new Player(playerRow);
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateWheel(45);
                moveRight();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateWheel(-45);
                moveLeft();
                // Vibrate for 400 milliseconds
                v.vibrate(400);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();
    }

    private void startTicker() {
        handler.postDelayed(r, DELAY);
    }

    private void stopTicker() {
        handler.removeCallbacks(r);
    }

    /**
     * Set every obstacle visibility to false - invisible
     */
    private void setObstaclesInvisible() {
        for (int i = 0; i < rightColumnObstacles.size(); i++) {
            rightColumnObstacles.get(i).setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < middleColumnObstacles.size(); i++) {
            middleColumnObstacles.get(i).setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < leftColumnObstacles.size(); i++) {
            leftColumnObstacles.get(i).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Change the rotation of the wheel image view
     * mentions the last call the user have done
     *
     * @param degree
     */
    private void rotateWheel(int degree) {
        wheel.setPivotX(wheel.getWidth() / 2);
        wheel.setPivotY(wheel.getHeight() / 2);
        wheel.setRotation(0);
        wheel.setPivotX(wheel.getWidth() / 2);
        wheel.setPivotY(wheel.getHeight() / 2);
        wheel.setRotation(degree);
    }

    private void moveLeft() {
        if (player.getCurrentPosition() - 1 >= 0) {

            int lastPosition = player.getCurrentPosition();
            player.setCurrentPosition(player.getCurrentPosition() - 1);
            int newPosition = player.getCurrentPosition();

            switchPlayerPosition(lastPosition, newPosition);
        }
    }

    private void moveRight() {
        if (player.getCurrentPosition() + 1 < 3) {
            int lastPosition = player.getCurrentPosition();
            player.setCurrentPosition(player.getCurrentPosition() + 1);
            int newPosition = player.getCurrentPosition();

            switchPlayerPosition(lastPosition, newPosition);
        }
    }

    /**
     * @param lastPosition
     * @param newPosition  moves the player from the last position to the new one.
     */
    private void switchPlayerPosition(int lastPosition, int newPosition) {
        playerRow.get(lastPosition).setVisibility(View.INVISIBLE);
        playerRow.get(newPosition).setVisibility(View.VISIBLE);
    }

    private void generateObstacle() {
        Random rand = new Random();
        int columnNumber = rand.nextInt(3);
        obstaclesList.get(columnNumber).get(0).setVisibility(View.VISIBLE);
    }

    //TODO work it out , solve the bug
    private void moveObstacles() {
        int lastPosition;
        for (ArrayList<ImageView> list : obstaclesList) {
            for (ImageView v : list) {
                if (v.getVisibility() == View.VISIBLE) {
                    lastPosition = list.indexOf(v);
                    switchObstaclePosition(lastPosition, obstaclesList.indexOf(list));
                }
            }
        }
    }

    //TODO work it out , solve the bug
    private void switchObstaclePosition(int lastPosition, int columnNumber) {
        if (lastPosition+1 < obstaclesList.get(0).size() ){
            obstaclesList.get(columnNumber).get(lastPosition).setVisibility(View.INVISIBLE);
            obstaclesList.get(columnNumber).get(lastPosition+1).setVisibility(View.VISIBLE);
        }
    }

    //TODO make it shorter and add a toast and update heart function
    private void checkCollision() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (leftColumnObstacles.get(leftColumnObstacles.size() - 1).getVisibility() == View.VISIBLE &&
                playerRow.get(0).getVisibility() == View.VISIBLE) {
            player.setLives(player.getLives() - 1);
            // Vibrate for 400 milliseconds
            // Get instance of Vibrator from current Context
            v.vibrate(400);
        }
        if (middleColumnObstacles.get(middleColumnObstacles.size() - 1).getVisibility() == View.VISIBLE &&
                playerRow.get(1).getVisibility() == View.VISIBLE) {
            player.setLives(player.getLives() - 1);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
        }
        if (rightColumnObstacles.get(rightColumnObstacles.size() - 1).getVisibility() == View.VISIBLE &&
                playerRow.get(2).getVisibility() == View.VISIBLE) {
            player.setLives(player.getLives() - 1);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
        }
    }

    /**
     * the first digit of the panel image represent the row
     * the second digit of the panel image represent the index inside the row
     */
    private void findObstaclesView() {
        leftColumnObstacles.add(0, findViewById(R.id.panel_IMG_hole11));
        leftColumnObstacles.add(1, findViewById(R.id.panel_IMG_hole21));
        leftColumnObstacles.add(2, findViewById(R.id.panel_IMG_hole31));
        leftColumnObstacles.add(3, findViewById(R.id.panel_IMG_hole41));
        leftColumnObstacles.add(4, findViewById(R.id.panel_IMG_hole51));

        middleColumnObstacles.add(0, findViewById(R.id.panel_IMG_barrier12));
        middleColumnObstacles.add(1, findViewById(R.id.panel_IMG_barrier22));
        middleColumnObstacles.add(2, findViewById(R.id.panel_IMG_barrier32));
        middleColumnObstacles.add(3, findViewById(R.id.panel_IMG_barrier42));
        middleColumnObstacles.add(4, findViewById(R.id.panel_IMG_barrier52));

        rightColumnObstacles.add(0, findViewById(R.id.panel_IMG_hole13));
        rightColumnObstacles.add(1, findViewById(R.id.panel_IMG_hole23));
        rightColumnObstacles.add(2, findViewById(R.id.panel_IMG_hole33));
        rightColumnObstacles.add(3, findViewById(R.id.panel_IMG_hole43));
        rightColumnObstacles.add(4, findViewById(R.id.panel_IMG_hole53));

        obstaclesList.add(0, leftColumnObstacles);
        obstaclesList.add(1, middleColumnObstacles);
        obstaclesList.add(2, rightColumnObstacles);

        setObstaclesInvisible();
    }

    @SuppressLint("WrongViewCast")
    private void findViews() {
        btnRight = findViewById(R.id.panel_BTN_right);
        btnLeft = findViewById(R.id.panel_BTN_left);
        wheel = findViewById(R.id.panel_IMG_wheel);

        imgHeart1 = findViewById(R.id.panel_IMG_heart1);
        imgHeart2 = findViewById(R.id.panel_IMG_heart1);
        imgHeart3 = findViewById(R.id.panel_IMG_heart1);

        hearts.add(imgHeart1);
        hearts.add(imgHeart2);
        hearts.add(imgHeart3);

        playerRow.add(findViewById(R.id.panel_IMG_car0));
        playerRow.add(findViewById(R.id.panel_IMG_car1));
        playerRow.add(findViewById(R.id.panel_IMG_car2));

    }
}