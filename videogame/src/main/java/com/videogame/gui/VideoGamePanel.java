package com.videogame.gui;

import javax.swing.*;
import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.videogame.app.MongoClass;
import com.videogame.app.Player;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class VideoGamePanel extends JPanel {
    private int point = 0;
    private int time = 10;
    private Player mainPlayer;
    private List<Player> players;
    private Controller controller;
    private MongoDatabase database;
    private JFrame frame;

    public VideoGamePanel(JFrame frame) {
        this.frame = frame;

        this.database = MongoClass.getDatabase();
        this.players = new ArrayList<>();
        new Timer(10, e -> {
            revalidate();
            repaint();
        }).start();

        new Timer (1000, e -> {
            time--;
            if(time == 0) {
                checkWin();
            }
        }
        ).start();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        if (this.players.size() == 1) {
            this.mainPlayer = player;
            this.controller = new Controller(this.mainPlayer, this);
            this.addKeyListener(this.controller);
            this.setFocusable(true);
            this.requestFocus();
        }
    }

    public void checkIntersections() {
        List<Player> toRemove = new ArrayList<>();
        for (Player player : this.players) {
            if (player == this.mainPlayer)
                continue;

            if (mainPlayer.intersects(player)) {
                toRemove.add(player);
            }
        }

        for (Player player : toRemove) {
            this.players.remove(player);
            point++;
        }

        if (toRemove.size() > 0)
            checkWin();
    }

    private void checkWin() {
        if (this.players.size() == 1 || this.time == 0) {
            MongoCollection<Document> playerCollection = database.getCollection("players");
            Document document = new Document();
            document.put("name", mainPlayer.getName());
            document.put("score", point);
            playerCollection.insertOne(document);
            
            MongoClass.disconnect();            

            JOptionPane.showMessageDialog(this,
                    "Punteggio: " + this.point,
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);

            frame.dispose();

            MainFrame videogame = new MainFrame();
            videogame.setVisible(true);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Player player : this.players) {
            g.setColor(player.getColor());
            g.fillRect(player.getX(), player.getY(), 20, 20);
            //g.drawString(player.getName(), player.getX(), player.getY() + 30);
            if(player == this.mainPlayer){
                g.drawString(player.getName(), player.getX(), player.getY() + 30);
            }
        }
    }
}
