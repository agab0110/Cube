package com.videogame.gui;

import javax.swing.*;

import com.videogame.app.MongoClass;
import com.videogame.app.PlayerExcetion;

import java.awt.*;

public class MainFrame extends JFrame{
    private final MainFrame frame;

    private JPanel panel;
    private JLabel label;
    private ImageIcon image;
    private JTextField name;
    private JButton newGame;
    private JButton score;

    public MainFrame() {
        this.frame = this;

        this.setTitle("Videogame");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        image = new ImageIcon("C:\\Users\\39347\\Desktop\\java_programs\\Cube\\videogame\\cube.png");
        label = new JLabel();
        label.setIcon(image);
        label.setOpaque(true);
        name = new JTextField("Giocatore",40);
        newGame = new JButton("Nuova partita");
        score = new JButton("Punteggi");

        panel.setLayout(null);
        panel.setBackground(Color.black);
        label.setBounds(110, 160, 400,400);
        name.setBounds(95, 30, 400,40);
        score.setBounds(315, 110, 150,40);
        newGame.setBounds(125, 110, 150,40);


        this.buildFrame();

        MongoClass.connect();

    }
    private void buildFrame() {

        frame.add(panel);
        panel.add(label);
        panel.add(name);
        panel.add(newGame);
        panel.add(score);

        this.addAction();
    }

    private void addAction() {
        this.newGame.addActionListener(
            actionEvent -> {
                JOptionPane.showConfirmDialog(
                        this,
                        "Hai 10 secondi per fare il più alto punteggio possibile!",
                        "Regola",
                        JOptionPane.OK_OPTION
                );
                String name = this.name.getText();
                GameFrame game;
                try {
                    game = new GameFrame(name);
                    game.setVisible(true);
                } catch (PlayerExcetion e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Errore giocatore",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                this.dispose();
            }
        );
        this.score.addActionListener(
            actionEvent -> {
                this.panel.setVisible(false);
                this.remove(panel);
                ScorePanel scorePanel = new ScorePanel(frame, panel);
                this.add(scorePanel);
                scorePanel.setVisible(true);
            }
        );
    }
}
