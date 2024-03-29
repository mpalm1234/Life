package com.project.LifeGame;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import org.json.simple.JSONArray;

public class LifeGame extends JPanel implements ActionListener{
    private final int CELL_SIZE = 25;
    private final int BOARD_DIM;
    private final int DIM;
    private int[][] cells;
    public int[][] getCells() {
        return this.cells;
    }
    private boolean populationDead; // purpose is to skip logic if there aren't any active cells left
    public boolean getPopulationDead() {
        return this.populationDead;
    }

    LifeGame(int boardDim, JSONArray startingCoordinates) {
        // create board
        BOARD_DIM = boardDim;
        DIM = BOARD_DIM/CELL_SIZE;
        setPreferredSize(new Dimension(BOARD_DIM, BOARD_DIM));
        setBackground(Color.white);

        // build initial active cells
        populateStartingCells(startingCoordinates);

        // timer
        Timer gameLoop = new Timer(500, this);
        gameLoop.start();
    }

    @SuppressWarnings("unchecked")
    public void populateStartingCells(JSONArray startingCoordinates) {
        populationDead = true;
        cells = new int [DIM][DIM];
        for (String coordinate : (Iterable<String>) startingCoordinates) {
            populationDead = false;
            List<String> nums = Arrays.asList(coordinate.split(","));
            cells[Integer.parseInt(nums.get(0))][Integer.parseInt(nums.get(1))] = 1;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (populationDead) {
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("Population died", BOARD_DIM/3, BOARD_DIM/2);
        } else {
            drawCells(g);
        }

        g.setColor(Color.black);
        for (int i = 0; i < DIM; i++) {
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, BOARD_DIM);
            g.drawLine(0, i*CELL_SIZE, BOARD_DIM, i*CELL_SIZE);
        }
    }

    public void drawCells(Graphics g) {
        int[][] newCells = new int[DIM][DIM];
        populationDead = true;

        g.setColor(Color.green);
        for (int y = 0; y < DIM; y++) {
            for (int x = 0; x < DIM; x++) {
                int numNeighbors = getActiveNeighbors(x, y);
                if ((cells[x][y] == 1 && (numNeighbors == 2 || numNeighbors == 3))
                        || cells[x][y] == 0 && numNeighbors == 3) {
                    populationDead = false;
                    newCells[x][y] = 1;
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    newCells[x][y] = 0;
                }
            }
        }

        cells = newCells;
    }

    public int getActiveNeighbors(int x, int y) {
        int n = 0;

        if (x > 0 && cells[x-1][y] == 1) n++;                  // left
        if (x < 23 && cells[x+1][y] == 1) n++;                 // right
        if (y > 0 && cells[x][y-1] == 1) n++;                  // up
        if (y < 23 && cells[x][y+1] == 1) n++;                 // down

        if (x > 0 && y > 0 && cells[x-1][y-1] == 1) n++;      // top left
        if (x > 0 && y < 23 && cells[x-1][y+1] == 1) n++;     // bottom left
        if (x < 23 && y > 0 && cells[x+1][y-1] == 1) n++;     // top right
        if (x < 23 && y < 23 && cells[x+1][y+1] == 1) n++;    // bottom right

        return n;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
