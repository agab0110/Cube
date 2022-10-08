package com.videogame.app;

import java.awt.*;

public class Player {
    private static final int MOVE_SPEED = 5;
    private String name;
    private Color color;
    private int x;
    private int y;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.x = 0;
        this.y = 0;
    }

    public Player(String name, Color color, int x, int y){
        this.name = name;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void moveUp() {
        this.y -= MOVE_SPEED;
    }

    public void moveDown() {
        this.y += MOVE_SPEED;
    }

    public void moveLeft() {
        this.x -= MOVE_SPEED;
    }

    public void moveRight() {
        this.x += MOVE_SPEED;
    }

    public boolean intersects(Player player2) {
        if (player2.x >= this.x && player2.x <= (this.x + 20)) {
            return player2.y >= this.y && player2.y <= (this.y + 20);
        }

        return false;
    }
}

