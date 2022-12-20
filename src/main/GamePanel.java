package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {
    // Screen settings
    final int baseTileSize = 16; //16x16 pixels
    final int scale = 3;

    final int tileSize = baseTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    KeyboardInputs keyboardInputs;
    MouseInputs mouseInputs;

    private BufferedImage img;
    private BufferedImage[][] animations;

    private float xPos = 100, yPos = 100;
    private float playerSpeed = 3;

    private int aniTick, aniIndexI, playerAction, aniSpeed = 30;
    private int playerDirection = DOWN;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //preferred size does not include borders if inside gamePanel
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // optimize game paint

        importImg();
        loadAnimations();

        //inputs
        keyboardInputs = new KeyboardInputs(this);
        mouseInputs = new MouseInputs(this);
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true); // starts with focus on window
    }

    private void loadAnimations() {
        animations = new BufferedImage[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                animations[i][j] = img.getSubimage(j*16,i*32,16,32);
            }
        }
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
    }

    //change image after few frames
    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            if (playerAction == STANDING)
                aniIndexI = 0;
            else
                aniIndexI++;
                if (aniIndexI > WALKING)
                    aniIndexI = 1;
        }
    }

    private void setAnimations() {
        if (keyboardInputs.upPressed || keyboardInputs.rightPressed || keyboardInputs.leftPressed || keyboardInputs.downPressed)
            playerAction = WALKING;
        else
            playerAction = STANDING;
    }

    private void updatePosition() {
        if (playerAction == WALKING) {
            if (keyboardInputs.leftPressed) {
                xPos -= playerSpeed;
                playerDirection = LEFT;
            } if (keyboardInputs.rightPressed) {
                xPos += playerSpeed;
                playerDirection = RIGHT;
            } if (keyboardInputs.upPressed) {
                yPos -= playerSpeed;
                playerDirection = UP;
            } if (keyboardInputs.downPressed) {
                yPos += playerSpeed;
                playerDirection = DOWN;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //prepare panel
        Graphics2D g2 = (Graphics2D) g;

        updateAnimationTick();
        setAnimations();
        updatePosition();

        g2.drawImage(animations[aniIndexI][playerDirection], (int) xPos, (int) yPos, 100, 200,null);
        g2.dispose(); //it saves some memory
    }
}
