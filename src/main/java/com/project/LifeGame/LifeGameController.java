package com.project.LifeGame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

@RestController
@RequestMapping("api/v1/life")
@CrossOrigin(origins = "http://localhost:3000")
public class LifeGameController implements ErrorController {

    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody JSONObject obj) {

        return ResponseEntity.ok(obj);
    }
}
