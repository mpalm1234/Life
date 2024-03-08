package com.project.LifeGame;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import javax.swing.*;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        int boardDim = 600;

        JFrame frame = new JFrame("Life");
        frame.setVisible(true);
        frame.setSize(boardDim, boardDim);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/sample.json"));
            JSONArray startingCoordinates = (JSONArray) jsonObject.get("coordinates");
            LifeGame lifeGame = new LifeGame(boardDim, startingCoordinates);
            frame.add(lifeGame);
            frame.pack();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpringApplication.run(Main.class, args);
    }
}