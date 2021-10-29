package com.example.highwayroadrage;

import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Player {
    private int lives;
    private int currentPosition;
    private ImageView playerImg;
    private ArrayList<ImageView> carRow;

    public Player(ArrayList<ImageView> playerRow) {
        this.lives = 2;
        this.currentPosition = 1;
        carRow = playerRow;
    }

    public ArrayList<ImageView> getCarRow() {
        return carRow;
    }

    public void setCarRow(ArrayList<ImageView> carRow) {
        this.carRow = carRow;
    }

    public ImageView getPlayerImg() {
        return playerImg;
    }

    public void setPlayerImg(ImageView playerImg) {
        this.playerImg = playerImg;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
