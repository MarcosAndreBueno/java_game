package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

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

    private float xPos = 100, yPos = 100;
    private float playerSpeed = 2;
    KeyboardInputs keyboardInputs;
    MouseInputs mouseInputs;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // optimize game paint

        //inputs
        keyboardInputs = new KeyboardInputs(this);
        mouseInputs = new MouseInputs(this);
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true); // starts with focus on window
    }

    public void updateGame() {
        if (keyboardInputs.upPressed)
            yPos -= playerSpeed;
        if (keyboardInputs.downPressed)
            yPos += playerSpeed;
        if (keyboardInputs.leftPressed)
            xPos -= playerSpeed;
        if (keyboardInputs.rightPressed)
            xPos += playerSpeed;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //prepare panel
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect((int)xPos,(int)yPos,tileSize,tileSize);
        g2.dispose(); //it saves some memory
    }
}
