package com.videogame.gui;

import javax.swing.*;


import com.videogame.app.Player;
import com.videogame.app.PlayerExcetion;

import java.awt.*;
import java.util.Random;

public class GameFrame extends JFrame {

    public GameFrame(String name) throws PlayerExcetion {

        this.setTitle("Videogame");
        this.setSize(600, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        VideoGamePanel panel = new VideoGamePanel(this);
        this.add(panel);

        if(name.isEmpty()) {
            throw new PlayerExcetion("Nome non presente");
        }
        panel.addPlayer(new Player(name, Color.white));

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Player player = new Player(
                    "Pareschi " + (i + 1),
                    new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)),
                    random.nextInt(this.getWidth() - 20),
                    random.nextInt(this.getHeight() - 20)
            );
            panel.addPlayer(player);

            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }

                    if (random.nextBoolean())
                        player.moveRight();

                    if (random.nextBoolean())
                        player.moveLeft();

                    if (random.nextBoolean())
                        player.moveUp();

                    if (random.nextBoolean())
                        player.moveDown();
                }
            });
            thread.start();
        }
    }
}
