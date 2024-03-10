package com.project.LifeGame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.ws.rs.Produces;

@RestController
@RequestMapping("api/life")
@CrossOrigin(origins = "http://localhost:3000")
public class LifeGameController implements ErrorController {

    @PostMapping("/start")
    @Produces(MimeTypeUtils.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> startGame(@RequestBody String jsonObj) {
        int boardDim = 600;

        JFrame frame = new JFrame("Life");
        frame.setVisible(true);
        frame.setSize(boardDim, boardDim);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonObj);
            JSONArray startingCoordinates = (JSONArray) jsonObject.get("coordinates");
            LifeGame lifeGame = new LifeGame(boardDim, startingCoordinates);
            frame.add(lifeGame);
            frame.pack();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(jsonObj);
    }
}
