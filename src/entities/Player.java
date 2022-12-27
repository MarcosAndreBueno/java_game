package entities;

import main.Game;
import tiles.Collision;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndexI, aniSpeed = (int) (12 * Scale);
    private int playerWidth = (int) (13 * Scale), playerHeight = (int) (26 * Scale);

    private int playerAction = STANDING, playerDirection = DOWN;
    private float playerSpeed = 0.32f * Scale;

    private boolean upPressed, downPressed, leftPressed, rightPressed;

    //center camera on player
    public static float ScreenCenterX = (ScreenWidth/2) - (TileSize/2);
    public static float ScreenCenterY = (ScreenHeight/2) - (TileSize);
    private float playerCenterX = ScreenCenterX;
    private float playerCenterY = ScreenCenterY;

    public Game game;
    public int mapMaxWidth;
    public int mapMaxHeight;

    public Player(Game game) {
        super(200, 600);
        this.game = game;
        setHitbox(0,playerHeight,playerHeight/2,playerWidth);
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
        if (leftPressed && !rightPressed) {
            setPositionX(-playerSpeed);
            setDirection(LEFT);
            if (leftPressed && !rightPressed)
                if (Collision.checkPlayerCollision(this, LEFT))
                    resetPositionX(-playerSpeed);
        }
        if (rightPressed && !leftPressed) {
            setPositionX(playerSpeed);
            setDirection(RIGHT);
            if (rightPressed && !leftPressed)
                if (Collision.checkPlayerCollision(this, RIGHT))
                    resetPositionX(playerSpeed);
        }
        if (upPressed && !downPressed) {
            setPositionY(-playerSpeed);
            setDirection(UP);
            if (upPressed && !downPressed)
                if (Collision.checkPlayerCollision(this, UP))
                    resetPositionY(-playerSpeed);
        }
        if (downPressed && !upPressed) {
            setPositionY(playerSpeed);
            setDirection(DOWN);
            if (downPressed && !upPressed)
                if (Collision.checkPlayerCollision(this, DOWN))
                    resetPositionY(playerSpeed);
        }


        if (!upPressed && !downPressed && !leftPressed && !rightPressed)
            playerAction = STANDING;
        else {
            setAction(WALKING);

            mapMaxWidth = game.getMapManager().getMapMaxWidth();
            mapMaxHeight = game.getMapManager().getMapMaxHeight();

            //player movement if the screen reaches the edge of the map
            if (x + ScreenWidth > mapMaxWidth)      //right
                playerCenterX = x - (mapMaxWidth - ScreenWidth - ScreenCenterX);
            else if (x <= 0)                          //left
                playerCenterX = x + ScreenCenterX;
            if (y + ScreenHeight > mapMaxHeight)   //down
                playerCenterY = y - (mapMaxHeight - ScreenHeight - ScreenCenterY);
            else if (y <= 0)                          //up
                playerCenterY = y + ScreenCenterY;
        }
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

    private void setDirection(int direction) {
        playerDirection = direction;
    }

    private void setAction(int action) {
        playerAction = action;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
}