package entities;

import main.Game;
import main.GameWindow;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndexI, aniSpeed = 30;
    private int playerWidth = 30, playerHeight = 60;

    private int playerAction = STANDING, playerDirection = DOWN;
    private float playerSpeed = 0.8f;

    private boolean upPressed, downPressed, leftPressed, rightPressed;

    //center camera on player
    public static float ScreenCenterX = (ScreenWidth/2) - (TileSize/2);
    public static float ScreenCenterY = (ScreenHeight/2) - (TileSize);
    private float playerCenterX = ScreenCenterX;
    private float playerCenterY = ScreenCenterY;

    public Game game;
    public int levelMaxWidth;
    public int levelMaxHeight;

    public Player(Game game) {
        super(200, 200);
        this.game = game;
        loadAnimations();
    }

    public void update() {
        updatePlayerInformations();
        updateAnimationTick();
    }

    public void render(Graphics2D g2) {
        g2.drawImage(animations[aniIndexI][playerDirection], (int)playerCenterX, (int)playerCenterY, playerWidth, playerHeight,null);
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
        boolean moving = false;
        if (leftPressed && !rightPressed) {
            setPositionX(-playerSpeed);
            setDirection(LEFT);
            setAction(WALKING);
            moving = true;
        }
        if (rightPressed && !leftPressed) {
            setPositionX(playerSpeed);
            setDirection(RIGHT);
            setAction(WALKING);
            moving = true;
        }
        if (upPressed && !downPressed) {
            setPositionY(-playerSpeed);
            setDirection(UP);
            setAction(WALKING);
            moving = true;
        }
        if (downPressed && !upPressed) {
            setPositionY(playerSpeed);
            setDirection(DOWN);
            setAction(WALKING);
            moving = true;
        }
        if (!moving)
            playerAction = STANDING;

        this.levelMaxWidth = game.getLevelManager().getLevelMaxWidth();
        this.levelMaxHeight = game.getLevelManager().getLevelMaxHeight();

        //player movement if the screen reaches the edge of the level
        if (x + ScreenWidth > levelMaxWidth) {      //right
            playerCenterX = x - (levelMaxWidth - ScreenWidth - ScreenCenterX);
        }
        else if (x <= 0)                            //left
            playerCenterX = x + ScreenCenterX;
        if (y + ScreenHeight > levelMaxHeight) {    //down
            playerCenterY = y - (levelMaxHeight - ScreenHeight - ScreenCenterY);
        }
        else if (y <= 0)                            //up
            playerCenterY = y + ScreenCenterY;
    }

    private void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(LoadSaveImage.PLAYER_ATLAS);
        animations = new BufferedImage[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                animations[i][j] = img.getSubimage(j*16,i*32,16,32);
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

    public float getPositionX() {
        return x;
    }

    public float getPositionY() {
        return y;
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