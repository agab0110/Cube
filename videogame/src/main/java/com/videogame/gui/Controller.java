package com.videogame.gui;

import javax.swing.*;

import com.videogame.app.Player;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
    private final Player player;
    private final VideoGamePanel panel;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public Controller(Player player, VideoGamePanel panel) {
        this.panel = panel;
        this.player = player;
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
                if (up)
                    player.moveUp();

                if (down)
                    player.moveDown();

                if (left)
                    player.moveLeft();

                if (right)
                    player.moveRight();

                if (up || right || left || down)
                    panel.checkIntersections();
            }
        });
        thread.start();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                this.up = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                this.down = true;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                this.left = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                this.right = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                this.up = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                this.down = false;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                this.left = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                this.right = false;
                break;
        }
    }
}
