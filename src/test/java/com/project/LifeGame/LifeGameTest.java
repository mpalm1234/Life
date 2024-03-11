package com.project.LifeGame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Graphics;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class LifeGameTest {

    private LifeGame lifeGame;
    private JSONArray startingCoordinates1, startingCoordinates2, startingCoordinates3;
    private int[][] gameBoard;

    final int BOARD_DIM = 600;
    final int DIM = 24;

    @Mock
    Graphics gMock;

    @BeforeEach
    void setUp() throws IOException, ParseException {

        // define working starting coordinate examples
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/test/resources/sample1.json"));
        startingCoordinates1 = (JSONArray) jsonObject.get("coordinates");
        jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/test/resources/sample2.json"));
        startingCoordinates2 = (JSONArray) jsonObject.get("coordinates");
        jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/test/resources/sample3.json"));
        startingCoordinates3 = (JSONArray) jsonObject.get("coordinates");

        // start game board with all zeros
        gameBoard = new int[DIM][DIM];
        for (int y = 0; y < DIM; y++) {
            for (int x = 0; x < DIM; x++) {
                gameBoard[0][0] = 0;
            }
        }
    }

    @Test
    public void populateStartingCells_populationNotDead() {
        gameBoard[5][5] = 1;
        gameBoard[6][6] = 1;
        gameBoard[7][6] = 1;
        gameBoard[5][7] = 1;
        gameBoard[6][7] = 1;

        lifeGame = new LifeGame(BOARD_DIM, startingCoordinates1);

        assertTrue(Arrays.deepEquals(gameBoard, lifeGame.getCells()));
        assertFalse(lifeGame.getPopulationDead());
    }

    @Test
    public void populateStartingCells_populationDead() {
        lifeGame = new LifeGame(BOARD_DIM, startingCoordinates2);

        assertTrue(Arrays.deepEquals(gameBoard, lifeGame.getCells()));
        assertTrue(lifeGame.getPopulationDead());
    }

    @Test
    public void getActiveNeighbors_multipleNeighbors() {
        int x = 5;
        int y = 7;

        assertEquals(2, lifeGame.getActiveNeighbors(x, y));
    }

    @Test
    public void getActiveNeighbors_zeroNeighbors() {
        int x = 1;
        int y = 1;

        assertEquals(0, lifeGame.getActiveNeighbors(x, y));
    }

    @Test
    public void drawCells_populationNotDead() {
        gameBoard[6][5] = 1;
        gameBoard[7][6] = 1;
        gameBoard[5][7] = 1;
        gameBoard[6][7] = 1;
        gameBoard[7][7] = 1;

        lifeGame = new LifeGame(BOARD_DIM, startingCoordinates1);
        lifeGame.drawCells(gMock);

        assertTrue(Arrays.deepEquals(gameBoard, lifeGame.getCells()));
        assertFalse(lifeGame.getPopulationDead());
    }

    @Test
    public void drawCells_successOneNeighbor() {
        lifeGame = new LifeGame(BOARD_DIM, startingCoordinates3);
        lifeGame.drawCells(gMock);

        assertTrue(Arrays.deepEquals(gameBoard, lifeGame.getCells()));
        assertTrue(lifeGame.getPopulationDead());
    }

}

