package com.project.LifeGame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardDim = 600;

        JFrame frame = new JFrame("Life");
        frame.setVisible(true);
        frame.setSize(boardDim, boardDim);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LifeGame lifeGame = new LifeGame(boardDim);
        frame.add(lifeGame);
        frame.pack();
    }
}