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
    protected boolean upPressed, downPressed, leftPressed, rightPressed;

    //center camera on player
    private float playerCenterX;
    private float playerCenterY;

    public Player(Playing playing) {
        super(45, 560, playing);
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
    private void setPlayerCenter() {
        if (x + ScreenWidth > playing.getMapManager().getMapMaxWidth())      //right
            playerCenterX = x - (playing.getMapManager().getMapMaxWidth() - ScreenWidth - ScreenCenterX);
        else if (x <= 0)                        //left
            playerCenterX = x + ScreenCenterX;
        if (y + ScreenHeight > playing.getMapManager().getMapMaxHeight())    //down
            playerCenterY = y - (playing.getMapManager().getMapMaxHeight() - ScreenHeight - ScreenCenterY);
        else if (y <= 0)                        //up
            playerCenterY = y + ScreenCenterY;
    }

    private void checkCollisionLeft() {
        int direction = ((int) x + (int)ScreenCenterX + hitbox[LEFT]) / BaseTileSize;
        int cornerOne = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
        int cornerTwo = ((int) y + (int)ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
        if (playing.getMapManager().getCollision().isTileSolid(cornerOne, direction) ||
                playing.getMapManager().getCollision().isTileSolid(cornerTwo, direction))
            resetPositionX(-entitySpeed);
        if (playing.getMapManager().getCollision().isEntityHereX(x,y, hitbox, LEFT))
            resetPositionX(-entitySpeed);
    }
    private void checkCollisionRight() {
        int direction = ((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;
        int cornerOne = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
        int cornerTwo = ((int) y + (int)ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
        if (playing.getMapManager().getCollision().isTileSolid(cornerOne, direction) ||
                playing.getMapManager().getCollision().isTileSolid(cornerTwo, direction))
            resetPositionX(entitySpeed);
        if (playing.getMapManager().getCollision().isEntityHereX(x,y, hitbox, RIGHT))
            resetPositionX(entitySpeed);
    }
    private void checkCollisionUp() {
        int direction = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;
        int cornerOne = ((int) x + (int)ScreenCenterX + hitbox[LEFT]) / BaseTileSize;   //upleft
        int cornerTwo = ((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;  //upright
        if (playing.getMapManager().getCollision().isTileSolid(direction, cornerOne) ||
                playing.getMapManager().getCollision().isTileSolid(direction, cornerTwo))
            resetPositionY(-entitySpeed);
        if (playing.getMapManager().getCollision().isEntityHereY(x,y, hitbox, UP))
            resetPositionY(-entitySpeed);
    }
    private void checkCollisionDown() {
        int direction = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;
        int cornerOne = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;  //downleft
        int cornerTwo = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize; //downright
        if (playing.getMapManager().getCollision().isTileSolid(direction, cornerOne) ||
                playing.getMapManager().getCollision().isTileSolid(direction, cornerTwo))
            resetPositionY(entitySpeed);
        if (playing.getMapManager().getCollision().isEntityHereY(x,y,hitbox, DOWN))
            resetPositionY(entitySpeed);
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