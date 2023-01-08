package entities;

import game_states.Playing;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;

import main.GameWindow;
import utilz.Constants.Entities;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity implements GameEntity{

    //player movement
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    //center camera on player
    private float playerCenterX;
    private float playerCenterY;

    public Player(float x, float y, String sprite, Playing playing) {
        super(x, y, sprite, playing);
        initialize();
    }

    public void initialize() {
        loadAnimations();
        setHitbox(0, aniHeight, aniHeight / 2, aniWidth);
        setEntityInitialCenter();
    }

    //when the player gets into a new map
    public void setEntityInitialCenter(){
        playerCenterX = ScreenCenterX;
        playerCenterY = ScreenCenterY;
        setPlayerCenter();
    }

    public void update() {
        updatePlayerInformations();
        updateAnimationTick();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(animations[aniIndexI][aniDirection], (int)playerCenterX, (int)playerCenterY,
                aniWidth, aniHeight,null);
    }

    //change image after few frames
    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            if (aniAction == STANDING)
                aniIndexI = 0;
            else
                aniIndexI++;
            if ((aniDirection == LEFT || aniDirection == RIGHT) && aniIndexI >= WALKING)
                aniIndexI = 0;
            else if (aniIndexI > WALKING)
                aniIndexI = 1;
        }
    }

    private void updatePlayerInformations() {
        if (!upPressed && !downPressed && !leftPressed && !rightPressed)
            aniAction = STANDING;
        else {
            if (leftPressed && !rightPressed) {
                setPositionX(-entitySpeed);
                setDirection(LEFT);
                checkCollisionLeft();
            }
            else if (rightPressed && !leftPressed) {
                setPositionX(entitySpeed);
                setDirection(RIGHT);
                checkCollisionRight();
            }
            if (upPressed && !downPressed) {
                setPositionY(-entitySpeed);
                setDirection(UP);
                checkCollisionUp();
            }
            else if (downPressed && !upPressed) {
                setPositionY(entitySpeed);
                setDirection(DOWN);
                checkCollisionDown();
            }

            setAction(WALKING);
            setPlayerCenter();
        }
    }

    //player movement if the screen reaches the edge of the map
    public void setPlayerCenter() {
        //left or maps smaller than the screen width
        if (x <= 0 || playing.getMapManager().getMapMaxWidth() < ScreenWidth)
            playerCenterX = x + ScreenCenterX;
        //right
        else if (x + ScreenWidth > playing.getMapManager().getMapMaxWidth())
            playerCenterX = x - (playing.getMapManager().getMapMaxWidth() - ScreenWidth - ScreenCenterX);
        //up or maps smaller than the screen height
        if (y <= 0 || playing.getMapManager().getMapMaxHeight() < ScreenHeight)
            playerCenterY = y + ScreenCenterY;
        //down
        else if (y + ScreenHeight > playing.getMapManager().getMapMaxHeight())
            playerCenterY = y - (playing.getMapManager().getMapMaxHeight() - ScreenHeight - ScreenCenterY);
    }

    public void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(Entities.PLAYER_ATLAS);
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
        aniDirection = direction;
    }

    private void setAction(int action) {
        aniAction = action;
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

    public float getPlayerCenterX() {
        return playerCenterX;
    }

    public float getPlayerCenterY() {
        return playerCenterY;
    }
}