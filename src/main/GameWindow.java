package main;

import javax.swing.JFrame;
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
        private static final int baseTileSize = 16; //16x16 pixels
        private static final float scale = 2.5f;
        private static final int tileSize = (int) (baseTileSize * scale);
        private static final int maxScreenWidth = 32;
        private static final int maxScreenHeight = 18;
        public static final int ScreenWidth = tileSize * maxScreenWidth;
        public static final int ScreenHeight = tileSize * maxScreenHeight;
    }
}
