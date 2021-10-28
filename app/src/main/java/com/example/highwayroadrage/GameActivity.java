package com.example.highwayroadrage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private Player player;
    private ImageView wheel;

    private Button btnLeft;
    private Button btnRight;

    private ImageView imgBarrier;
    private ImageView imgHole;
    private ArrayList<ImageView> obstacles = new ArrayList<>();

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
            //moveObstacles();
            clockCounter++;
            //checkCollision();
            handler.postDelayed(this, DELAY);
            if (clockCounter % 2 == 0) {
                //generateObstacle();
            }
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
        player = new Player(playerRow);

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
            }
        });
    }

    /**
     * Change the rotation of the wheel image view
     * mentions the last call the user have done
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

/*
    private void generateObstacle() {
    }

    private void moveObstacles() {
    }

    private void checkCollision() {
        for (int i = 0; i<obstacles.size();i++){
            if(player.getCurrentPosition() == obstacles[4][i].getCurrentPosition()){

            }
        }

    }
*/

    @SuppressLint("WrongViewCast")
    private void findViews() {
        btnRight = findViewById(R.id.panel_BTN_right);
        btnLeft = findViewById(R.id.panel_BTN_left);
        wheel = findViewById(R.id.panel_IMG_wheel);

        imgBarrier = findViewById(R.id.panel_IMG_barrier);
        imgHole = findViewById(R.id.panel_IMG_hole);

        imgHeart1 = findViewById(R.id.panel_IMG_heart1);
        imgHeart2 = findViewById(R.id.panel_IMG_heart1);
        imgHeart3 = findViewById(R.id.panel_IMG_heart1);

        obstacles.add(imgBarrier);
        obstacles.add(imgHole);

        hearts.add(imgHeart1);
        hearts.add(imgHeart2);
        hearts.add(imgHeart3);

        playerRow.add(findViewById(R.id.panel_IMG_car0));
        playerRow.add(findViewById(R.id.panel_IMG_car1));
        playerRow.add(findViewById(R.id.panel_IMG_car2));

    }
}