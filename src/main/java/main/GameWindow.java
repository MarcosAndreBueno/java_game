package main;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

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

        //block and reset inputs if window changes
        gameWindow.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowsFocusLost();
            }
        });
    }

    public static class ScreenSettings {
        public static final int BaseTileSize = 16;                              //16x16 pixels
        public static final float Scale = 2f;
        public static final int TileSize = (int) (BaseTileSize * Scale);        //32
        public static final int MaxScreenWidth = 32;
        public static final int MaxScreenHeight = 18;
        public static final int ScreenWidth = TileSize * MaxScreenWidth;        //1024
        public static final int ScreenHeight = TileSize * MaxScreenHeight;      //576
        public static float ScreenCenterX = (ScreenWidth / 2) - (TileSize / 2); //496
        public static float ScreenCenterY = (ScreenHeight / 2) - (TileSize);    //256
    }
}
