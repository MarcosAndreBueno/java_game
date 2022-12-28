package entities;

import main.Game;
import tiles.Collision;

import static main.GameWindow.ScreenSettings.*;
import static tiles.CSVHandle.mapInfo;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;
import utilz.Constants.Entities;

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
        super(0, 600);
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
        if (!upPressed && !downPressed && !leftPressed && !rightPressed)
            playerAction = STANDING;
        else {
            if (leftPressed && !rightPressed) {
                setPositionX(-playerSpeed);
                setDirection(LEFT);
                checkCollisionLeft();
            }
            else if (rightPressed && !leftPressed) {
                setPositionX(playerSpeed);
                setDirection(RIGHT);
                checkCollisionRight();
            }
            if (upPressed && !downPressed) {
                setPositionY(-playerSpeed);
                setDirection(UP);
                checkCollisionUp();
            }
            else if (downPressed && !upPressed) {
                setPositionY(playerSpeed);
                setDirection(DOWN);
                checkCollisionDown();
            }

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

    private void checkCollisionLeft() {
        int direction = ((int) x + (int)ScreenCenterX + hitbox[LEFT]) / BaseTileSize;
        int cornerOne = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
        int cornerTwo = ((int) y + (int)ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
        if (Collision.isTileSolid(cornerOne, direction) ||
                Collision.isTileSolid(cornerTwo, direction))
            resetPositionX(-playerSpeed);
    }
    private void checkCollisionRight() {
        int direction = ((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;
        int cornerOne = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
        int cornerTwo = ((int) y + (int)ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
        if (Collision.isTileSolid(cornerOne, direction) ||
                Collision.isTileSolid(cornerTwo, direction))
            resetPositionX(playerSpeed);
    }
    private void checkCollisionUp() {
        int direction = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;
        int cornerOne = ((int) x + (int)ScreenCenterX + hitbox[LEFT]) / BaseTileSize;   //upleft
        int cornerTwo = ((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;  //upright
        if (Collision.isTileSolid(direction, cornerOne) ||
                Collision.isTileSolid(direction, cornerTwo))
            resetPositionY(-playerSpeed);
    }
    private void checkCollisionDown() {
        int direction = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;
        int cornerOne = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;  //downleft
        int cornerTwo = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize; //downright
        if (Collision.isTileSolid(direction, cornerOne) ||
                Collision.isTileSolid(direction, cornerTwo))
            resetPositionY(playerSpeed);
    }

    private void loadAnimations() {
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