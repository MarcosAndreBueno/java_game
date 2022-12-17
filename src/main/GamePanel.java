package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    // Screen settings
    final int baseTileSize = 16; //16x16 pixels
    final int scale = 3;

    final int tileSize = baseTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // optimize game paint
    }
}
