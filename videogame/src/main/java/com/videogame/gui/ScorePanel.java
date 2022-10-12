package com.videogame.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.videogame.app.MongoClass;

import java.awt.*;

public class ScorePanel extends JPanel{

    private MongoDatabase database;

    private JButton back;    
    private JTextField text;
    private JButton searchName;
    private JComboBox<String> pointsOperators;
    private JButton searchPoints;
    private JTextPane results;

    private String name;
    private int points;

    public ScorePanel(JFrame frame,  JPanel panel) {
        this.database = MongoClass.getDatabase();

        this.setLayout(null);
        this.setBackground(Color.black);
        this.back = new JButton("Indietro");
        this.text = new JTextField(20);
        this.searchName = new JButton("Cerca nome");
        String[] operators = {">","=","<"};
        this.pointsOperators = new JComboBox<>(operators);
        this.searchPoints = new JButton("Cerca punti");
        this.results = new JTextPane();

        this.addAction(frame, panel);

        this.buildPanel();
    }

    private void buildPanel() {
        this.add(back);
        back.setBounds(260,500,80,30);
        this.add(text);
        text.setBounds(30,10,250,30);
        this.add(searchName);
        searchName.setBounds(350,10,105,30);
        this.add(pointsOperators);
        pointsOperators.setBounds(300,10,40,30);
        this.add(searchPoints);
        searchPoints.setBounds(460,10,100,30);
        this.add(results);
        results.setBounds(100,75,400,400);
        this.searchAllScore();
    }

    private void addAction(JFrame frame,  JPanel panel) {
        this.back.addActionListener(
            actionEvent -> {
                this.setVisible(false);
                frame.remove(this);
                frame.add(panel);
                panel.setVisible(true);
            }
        );

        this.searchName.addActionListener(
            actionEvent -> {
                this.name = text.getText();
                this.searchName();
            }
        );
        this.searchPoints.addActionListener(
            actionEvent -> {
                this.points = Integer.parseInt(text.getText());
                this.searchPoints();
            }
        );
    }

    private void searchPoints() {
        MongoCollection<Document> playerCollection = database.getCollection("players");
        Bson filter;
        switch (pointsOperators.getItemAt(pointsOperators.getSelectedIndex())) {
            case ">":
                filter = Filters.gt("score", points);
                break;
            case "<":
                filter = Filters.lt("score", points);
                break;
            default:
                filter = Filters.eq("score", points);
                break;
        }
        StringBuilder resultString = new StringBuilder();
        for(Document document : playerCollection.find(filter)) {
            resultString.append(document.toString()).append("\n");
        }
        this.results.setText(resultString.toString());
    }

    private void searchName() {
        MongoCollection<Document> playerCollection = database.getCollection("players");
        Bson filter = Filters.eq("name", name);
        StringBuilder resultString = new StringBuilder();
        for(Document document : playerCollection.find(filter)) {
            resultString.append(document.toString()).append("\n");
        }
        this.results.setText(resultString.toString());
    }

    private void searchAllScore() {
        MongoCollection<Document> playerCollection = database.getCollection("players");
        StringBuilder resultString = new StringBuilder();
        for(Document document : playerCollection.find()) {
            resultString.append(document.toString()).append("\n");
        }
        this.results.setText(resultString.toString());
    }
}
