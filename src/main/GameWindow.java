package main;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame gameWindow;

    public GameWindow(GamePanel gamePanel) {
        gameWindow = new JFrame();
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.add(gamePanel);
        gameWindow.setResizable(false);
        gameWindow.setTitle("Music Game");
        gameWindow.pack(); //apply screen options to window
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
}
