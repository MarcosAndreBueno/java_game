package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private BufferedImage img;
    private int aniTick, aniIndexI, aniSpeed = 30;

    private int playerAction = STANDING, playerDirection = DOWN;
    private float playerSpeed = 3.0f;

    private boolean upPressed, downPressed, leftPressed, rightPressed;

    public Player(float x, float y) {
        super(x, y);
        importImg();
        loadAnimations();
    }

    public void update() {
        updatePlayerInformations();
        updateAnimationTick();
    }

    public void render(Graphics2D g2) {
        g2.drawImage(animations[aniIndexI][playerDirection], (int) x, (int) y, 100, 200,null);
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
            if ((playerDirection == LEFT || playerDirection == RIGHT) && aniIndexI >= WALKING)
                aniIndexI = 0;
            else if (aniIndexI > WALKING)
                aniIndexI = 1;
        }
    }

    private void updatePlayerInformations() {
        if (leftPressed && !rightPressed) {
            setPositionX(-playerSpeed);
            setDirection(LEFT);
            setAction(WALKING);
        }
        if (rightPressed && !leftPressed) {
            setPositionX(playerSpeed);
            setDirection(RIGHT);
            setAction(WALKING);
        }
        if (upPressed && !downPressed) {
            setPositionY(-playerSpeed);
            setDirection(UP);
            setAction(WALKING);
        }
        if (downPressed && !upPressed) {
            setPositionY(playerSpeed);
            setDirection(DOWN);
            setAction(WALKING);
        }
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

    public void resetDirBooleans() {
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
    }

    private void setPositionX(float x) {
        this.x += x;
    }

    private void setPositionY(float y) {
        this.y += y;
    }

    private void setDirection(int direction) {
        playerDirection = direction;
    }

    private void setAction(int action) {
        playerAction = action;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
}