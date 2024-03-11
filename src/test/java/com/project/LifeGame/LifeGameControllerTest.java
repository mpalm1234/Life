package com.project.LifeGame;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LifeGameControllerTest {

    @Mock
    LifeGame lifeGame;
    @InjectMocks
    private LifeGameController lifeGameController;
    private JSONObject startingCoordinates1, startingCoordinates4, startingCoordinates3;

    @BeforeEach
    void setUp() throws IOException, ParseException {
        // define working starting coordinate examples
        JSONParser jsonParser = new JSONParser();
        startingCoordinates1 = (JSONObject) jsonParser.parse(new FileReader("src/test/resources/sample1.json"));
        startingCoordinates4 = (JSONObject) jsonParser.parse(new FileReader("src/test/resources/sample4.json"));
    }

    @Test
    public void startGame_success() {
        ResponseEntity<?> actualResponse = lifeGameController.startGame(startingCoordinates1);
        assertEquals(HttpStatusCode.valueOf(200), actualResponse.getStatusCode());
    }

    @Test
    public void startGame_emptyRequestBody() {
        ResponseEntity<?> actualResponse = lifeGameController.startGame(null);
        assertEquals(HttpStatusCode.valueOf(400), actualResponse.getStatusCode());
    }

    @Test
    public void startGame_coordinatesNotDefined() {
        ResponseEntity<?> actualResponse = lifeGameController.startGame(startingCoordinates4);
        assertEquals(HttpStatusCode.valueOf(400), actualResponse.getStatusCode());
    }
}

