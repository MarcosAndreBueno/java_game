package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int baseTileSize = 16; //16x16 pixels
    final int scale = 3;

    final int tileSize = baseTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // optimize game paint
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // go to run method
    }
    @Override
    public void run() {
        while (gameThread != null) {
            System.out.println("The game loop is running");

            // update game information
            update();
            // draw the update information
            repaint(); //go to paintComponent method
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //prepare panel
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(100,100,tileSize,tileSize);
        g2.dispose(); //it saves some memory
    }
}
