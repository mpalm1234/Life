package com.project.LifeGame;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import javax.swing.*;
import org.json.simple.JSONArray;

public class LifeGame extends JPanel implements ActionListener{
    private final int CELL_SIZE = 25;
    private final int BOARD_DIM;
    private final int DIM;
    private int[][] cells;

    LifeGame(int boardDim, JSONArray startingCoordinates) {
        BOARD_DIM = boardDim;
        DIM = BOARD_DIM/CELL_SIZE;
        setPreferredSize(new Dimension(BOARD_DIM, BOARD_DIM));
        setBackground(Color.white);

        // build initial active cells
        this.cells = new int [DIM][DIM];
        Iterator<String> iter = startingCoordinates.iterator();
        while (iter.hasNext()) {
            String coordinate = iter.next();
            List<String> nums = Arrays.asList(coordinate.split(","));
            cells[Integer.valueOf(nums.get(0))][Integer.valueOf(nums.get(1))] = 1;
        }

        // timer
        Timer gameLoop = new Timer(500, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw cells
        g.setColor(Color.green);
        int numNeighbors;
        int[][] newCells = new int[DIM][DIM];
        for (int y = 0; y < DIM; y++) {
            for (int x = 0; x < DIM; x++) {
                numNeighbors = getActiveNeighbors(x, y);
                if ((cells[x][y] == 1 && (numNeighbors == 2 || numNeighbors == 3))
                        || cells[x][y] == 0 && numNeighbors == 3) {
                    newCells[x][y] = 1;
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    newCells[x][y] = 0;
                }
            }
        }
        cells = newCells;

        // draw grid lines
        g.setColor(Color.black);
        for (int i = 0; i < DIM; i++) {
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, BOARD_DIM);
            g.drawLine(0, i*CELL_SIZE, BOARD_DIM, i*CELL_SIZE);
        }
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
