package com.example.highwayroadrage;

import android.widget.ImageView;

public class Obstacle {
    private int[][] currentPosition;
    private ImageView obstacleImg;

    public Obstacle(int[][] currentPosition, ImageView obstacleImg) {
        this.currentPosition = currentPosition;
        this.obstacleImg = obstacleImg;
    }

    /**
     *
     * @param mode - can be VISIBLE / INVISIBLE.
     */
    private void switchVisible(int mode){
        this.obstacleImg.setVisibility(mode);
    }

    public int[][] getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int[][] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ImageView getObstacleImg() {
        return obstacleImg;
    }

    public void setObstacleImg(ImageView obstacleImg) {
        this.obstacleImg = obstacleImg;
    }
}
