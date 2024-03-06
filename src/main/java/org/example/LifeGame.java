package org.example;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class LifeGame extends JPanel implements ActionListener{
    private final int CELL_SIZE = 25;
    private final int BOARD_DIM;
    private final int DIM;
    int[][] cells;

    //game logic
    Timer gameLoop;

    LifeGame(int boardDim) {
        BOARD_DIM = boardDim;
        DIM = BOARD_DIM/CELL_SIZE;
        this.cells = new int [DIM][DIM];
        setPreferredSize(new Dimension(BOARD_DIM, BOARD_DIM));
        setBackground(Color.white);

        // draw cells
        // todo: use input from API for what the cells should be
        cells[5][5] = 1;
        cells[6][6] = 1;
        cells[7][6] = 1;
        cells[5][7] = 1;
        cells[6][7] = 1;

        // define timer
        gameLoop = new Timer(500, this);

        // begin timer
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // cells
        g.setColor(Color.green);
        for (int y = 0; y < DIM; y++) {
            for (int x = 0; x < DIM; x++) {
                if (cells[x][y] == 1) {
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        // grid lines
        g.setColor(Color.black);
        for (int i = 0; i < DIM; i++) {
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, BOARD_DIM);
            g.drawLine(0, i*CELL_SIZE, BOARD_DIM, i*CELL_SIZE);
        }
    }

    public void repopulateCells() {
        int numNeighbors;
        int[][] newCells = new int[DIM][DIM];
        for (int y = 0; y < DIM; y++) {
            for (int x = 0; x < DIM; x++) {
                numNeighbors = getActiveNeighbors(x, y);
                if ((cells[x][y] == 1 && (numNeighbors == 2 || numNeighbors == 3))
                    || cells[x][y] == 0 && numNeighbors == 3) {
                    newCells[x][y] = 1;
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
        repopulateCells();
        repaint();
    }
}
