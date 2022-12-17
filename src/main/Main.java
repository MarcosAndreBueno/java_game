package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame gameWindow = new JFrame();
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setTitle("Music Game");

        GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);

        gameWindow.pack(); //apply screen options to window

        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
}
