package com.project.LifeGame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Graphics;
import java.io.FileReader;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LifeGameTest {

    private LifeGame lifeGame;

    private JSONArray startingCoordinates;

    @Mock
    Graphics gMock;

    @BeforeEach
    void setUp() {
        int boardDim = 600;

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/test/java/resources/sample1.json"));
            startingCoordinates = (JSONArray) jsonObject.get("coordinates");
            lifeGame = new LifeGame(boardDim, startingCoordinates);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void populateStartingCells_success() {
        // expected
        int[][] expected = new int[lifeGame.getDIM()][lifeGame.getDIM()];
        for (int y = 0; y < lifeGame.getDIM(); y++) {
            for (int x = 0; x < lifeGame.getDIM(); x++) {
                expected[0][0] = 0;
            }
        }
        expected[5][5] = 1;
        expected[6][6] = 1;
        expected[7][6] = 1;
        expected[5][7] = 1;
        expected[6][7] = 1;

        // actual
        lifeGame.populateStartingCells(startingCoordinates);

        // assertion
        assertTrue(Arrays.deepEquals(expected, lifeGame.getCells()));
    }

    @Test
    public void getActiveNeighbors_successForMultipleNeighbors() {
        int x = 5;
        int y = 7;

        assertEquals(2, lifeGame.getActiveNeighbors(x, y));
    }

    @Test
    public void getActiveNeighbors_successForZeroNeighbors() {
        int x = 1;
        int y = 1;

        assertEquals(0, lifeGame.getActiveNeighbors(x, y));
    }

    @Test
    public void drawCells_success() {
        // expected
        int[][] expected = new int[lifeGame.getDIM()][lifeGame.getDIM()];
        for (int y = 0; y < lifeGame.getDIM(); y++) {
            for (int x = 0; x < lifeGame.getDIM(); x++) {
                expected[0][0] = 0;
            }
        }
        expected[6][5] = 1;
        expected[7][6] = 1;
        expected[5][7] = 1;
        expected[6][7] = 1;
        expected[7][7] = 1;

        // actual
        lifeGame.drawCells(gMock);

        // assertion
        assertTrue(Arrays.deepEquals(expected, lifeGame.getCells()));
    }

}

