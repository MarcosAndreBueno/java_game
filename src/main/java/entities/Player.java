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
    protected int maxHP, hp;
    public int attack;

    public Player(float x, float y, String sprite, Playing playing) {
        super(x, y, sprite, playing);
        initialize();
    }

    public void initialize() {
        loadAnimations();
        setHitbox(aniHeight/2f, aniWidth/2.5f, aniHeight/1.3f, aniWidth/1.3f);
        setEntityInitialCenter();
        maxHP = 50;
        hp = maxHP;
        attack = 10;
        entityName = "Player";
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
        //animation
        if (aniDirection >= ATTACKING_01)
            g2.drawImage(animations[aniDirection][aniFrame],
                    (int) (playerCenterX-8*Scale*4), (int) (playerCenterY-8*Scale*4),
                    aniWidth*3, aniHeight*3, null);
        else
            g2.drawImage(animations[aniDirection][aniFrame], (int) playerCenterX, (int) playerCenterY,
                    aniWidth, aniHeight, null);

        //HP
        int w1 = (int) (100 * Scale);
        g2.setColor(Color.BLACK);
        g2.fillRect(30,20, w1, (int) (4 * Scale));
        g2.setColor(Color.RED);
        int w2 = hp * 100 / maxHP;
        g2.fillRect(30,20,w1 * w2 / 100,(int) (4 * Scale));
    }

    //update image after few frames
    private void updateAnimationTick() {
        switch (aniAction) {
            case STANDING:
                aniFrame = 0;
                aniDirection = direction;
                break;
            case WALKING:
                if (checkAniTickFrame(aniMoveSpeed)) {
                    aniFrame++;
                    aniDirection = direction;
                    if (aniFrame > WALKING_FRAMES)
                        aniFrame = 1;
                }
                break;
            case ATTACKING_01:
                if (checkAniTickFrame(attackSpeed)) {
                    aniFrame++;
                    aniDirection = direction + ATTACKING_01;
                    if (aniFrame >= ATTACKING_01_FRAMES) {
                        aniFrame = 1;
                        aniAction = STANDING;
                    }
                }
                break;
            case ATTACKING_02:
                if (checkAniTickFrame(attackSpeed)) {
                    aniFrame++;
                    aniDirection = direction + ATTACKING_02;
                    if (aniFrame >= ATTACKING_02_FRAMES) {
                        aniFrame = 1;
                        aniAction = STANDING;
                    }
                }
                break;
        }
    }

    //checks the need to change the frame
    private boolean checkAniTickFrame(int aniSpeed) {
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            return true;
        }
        else
            aniTick++;
        return false;
    }

    private void updatePlayerInformations() {
        if (!leftPressed && !rightPressed && !upPressed && !downPressed &&
                aniAction != ATTACKING_01 && aniAction != ATTACKING_02)
            setAction(STANDING);
        else if (aniAction == WALKING){
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
        } else
            checkCollisionAttack();
    }

    public void checkCollisionAttack() {
        float[] objectHitbox = new float[4];

        switch (direction) {
            case UP -> {
                if (aniAction == ATTACKING_01)
                    objectHitbox = new float[]{aniHeight/9f, aniWidth/2f, aniHeight/3.2f, aniWidth/1.5f};
                else if (aniAction == ATTACKING_02)
                    objectHitbox = new float[]{aniHeight/-8f, aniWidth/-10f, aniHeight/3.2f, aniWidth/0.7f};
                if (playing.getMapManager().getCollision().isEntityHere(this, x, y, objectHitbox, direction))
                    System.out.println("attack up");
            }
            case LEFT -> {
                if (aniAction == ATTACKING_01)
                    objectHitbox = new float[]{aniHeight/2f, aniWidth/-5f, aniHeight/2f, 0};
                else if (aniAction == ATTACKING_02)
                    objectHitbox = new float[]{aniHeight/4f, aniWidth/-2.5f, aniHeight/1.2f, 0};
                if (playing.getMapManager().getCollision().isEntityHere(this, x, y, objectHitbox, direction))
                    System.out.println("attack left");
            }
            case DOWN -> {
                if (aniAction == ATTACKING_01)
                    objectHitbox = new float[]{0, aniWidth/2.9f, aniHeight/1.1f, aniWidth/2f};
                else if (aniAction == ATTACKING_02)
                    objectHitbox = new float[]{0, aniWidth/-9f, aniHeight/0.9f, aniWidth/0.85f};
                if (playing.getMapManager().getCollision().isEntityHere(this, x, y, objectHitbox, direction))
                    System.out.println("attack down");
            }
            case RIGHT -> {
                if (aniAction == ATTACKING_01)
                    objectHitbox = new float[]{aniHeight/2f, 0, aniHeight/2f, aniWidth/0.9f};
                else if (aniAction == ATTACKING_02)
                    objectHitbox = new float[]{aniHeight/4f, 0, aniHeight/1.2f, aniWidth/0.8f};
                if (playing.getMapManager().getCollision().isEntityHere(this, x, y, objectHitbox, direction))
                    System.out.println("attack right");
            }
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

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp += hp;
        if (this.hp > maxHP)
            this.hp = maxHP;
        else if (this.hp < 0)
            this.hp = 0;
    }

    public float getPlayerCenterX() {
        return playerCenterX;
    }

    public float getPlayerCenterY() {
        return playerCenterY;
    }
}