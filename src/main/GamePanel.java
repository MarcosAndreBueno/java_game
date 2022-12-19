package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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

    private BufferedImage img;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //preferred size does not include borders if inside gamePanel
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // optimize game paint

        importImg();

        //inputs
        keyboardInputs = new KeyboardInputs(this);
        mouseInputs = new MouseInputs(this);
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true); // starts with focus on window
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/res/sample_character_02.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        g2.drawImage(img.getSubimage(0,0,16,32), (int) xPos, (int) yPos,64,128,null);
        g2.dispose(); //it saves some memory
    }
}
