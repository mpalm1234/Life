package org.example;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.Timer;

public class LifeGame extends JPanel implements ActionListener{
    private final int CELL_SIZE = 25;
    int boardDim;
    Set<Cell> cells = new HashSet<>();

    //game logic
    Timer gameLoop;

    LifeGame(int boardDim) {
        // draw board
        this.boardDim = boardDim;
        setPreferredSize(new Dimension(this.boardDim, this.boardDim));
        setBackground(Color.white);

        // draw cells
        // todo: use input from API for what the cells should be
        cells.add(new Cell(5, 5));
        cells.add(new Cell(6, 6));
        cells.add(new Cell(7, 6));
        cells.add(new Cell(5, 7));
        cells.add(new Cell(6, 7));

        // define timer
        gameLoop = new Timer(100, this);

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
        for (Cell cell : cells) {
            g.fillRect(cell.x * CELL_SIZE, cell.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // grid lines
        g.setColor(Color.black);
        for (int i = 0; i < boardDim/CELL_SIZE; i++) {
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, boardDim);
            g.drawLine(0, i*CELL_SIZE, boardDim, i*CELL_SIZE);
        }
    }

    public void repopulateCells() {
        for (Cell cell : cells) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repopulateCells();
        repaint();
    }
}
