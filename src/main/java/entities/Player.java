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
        setHitbox(aniHeight/2f, aniWidth/2f, aniHeight, aniWidth/0.65f);
        aniWidth = (int) (32*Scale);
        aniHeight= (int) (32*Scale);
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
        if (aniDirection >= ATTACKING_01)
            g2.drawImage(animations[aniDirection][aniFrame], (int) playerCenterX-64, (int) playerCenterY-64,
                    aniWidth*3, aniHeight*3, null);
        else
            g2.drawImage(animations[aniDirection][aniFrame], (int) playerCenterX, (int) playerCenterY,
                    aniWidth, aniHeight, null);
    }

    //change image after few frames
    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            switch (aniAction) {
                case STANDING:
                    aniFrame = 0;
                    aniDirection = direction;
                    break;
                case WALKING:
                    aniFrame++;
                    aniDirection = direction;
                    if (aniFrame > WALKING_FRAMES)
                        aniFrame = 1;
                    break;
                case ATTACKING_01:
                    aniFrame++;
                    aniDirection = direction + ATTACKING_01;
                    if (aniFrame >= ATTACKING_01_FRAMES) {
                        aniFrame = 1;
                        aniAction = STANDING;
                    }
                    break;
                case ATTACKING_02:
                    aniFrame++;
                    aniDirection = direction + ATTACKING_02;
                    if (aniFrame >= ATTACKING_02_FRAMES) {
                        aniFrame = 1;
                        aniAction = STANDING;
                    }
                    break;
            }
        }
    }

    private void updatePlayerInformations() {
        if (!leftPressed && !rightPressed && !upPressed && !downPressed &&
                aniAction != ATTACKING_01 && aniAction != ATTACKING_02)
            setAction(STANDING);
        if (leftPressed && !rightPressed) {
            setPositionX(-entitySpeed);
            setDirection(LEFT);
            checkCollisionLeft();
        } else if (rightPressed && !leftPressed) {
            setPositionX(entitySpeed);
            setDirection(RIGHT);
            checkCollisionRight();
        }
        if (upPressed && !downPressed) {
            setPositionY(-entitySpeed);
            setDirection(UP);
            checkCollisionUp();
        } else if (downPressed && !upPressed) {
            setPositionY(entitySpeed);
            setDirection(DOWN);
            checkCollisionDown();
        }
        setPlayerCenter();
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
        animations = new BufferedImage[12][9];
        int tempI = 8;
        //walk movement
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                animations[i][j] = img.getSubimage(j * 64, tempI * 64, 64, 64);
            }
            tempI += 1;
        }
        //attack animations
        tempI = 21;
        int tempJ = 0;
        for (int i = 4; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                animations[i][j] = img.getSubimage(tempJ * 64, tempI * 64,64*3, 64*3);
                tempJ += 3;
            }
            tempI += 3;
            tempJ = 0;
        }
    }

    public void resetDirBooleans() {
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
    }

    private void setDirection(int direction) {
        this.direction = direction;
    }

    public void setAction(int action) {
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