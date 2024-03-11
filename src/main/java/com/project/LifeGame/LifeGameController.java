package com.project.LifeGame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

@RestController
@RequestMapping("api/life")
@CrossOrigin(origins = "http://localhost:3000")
public class LifeGameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LifeGameController.class);
    private final int BOARD_DIM = 600;

    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody JSONObject jsonObj) {
        JFrame frame = new JFrame("Life");
        frame.setVisible(true);
        frame.setSize(BOARD_DIM, BOARD_DIM);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(jsonObj));
            JSONArray startingCoordinates = (JSONArray) jsonObject.get("coordinates");
            if (startingCoordinates == null) {
                throw new Exception("coordinates not defined");
            }

            LifeGame lifeGame = new LifeGame(BOARD_DIM, startingCoordinates);
            frame.add(lifeGame);
            frame.pack();
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(jsonObj);
    }
}
