package entities;

import game_states.Playing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.NpcAndEnemiesCsv.*;
import static utilz.Constants.PlayerConstants.*;

public abstract class EnemyEntity extends Entity implements GameEntity {

    protected int aniTick, aniIndexI;

    protected float oldX = playing.getPlayer().getPositionX();
    protected float oldY = playing.getPlayer().getPositionY();
    protected float npcCenterX, npcCenterY;
    protected float px, py;

    protected long previousTime = playing.getGame().getGameTime();
    protected int pressedButton = -1;

    protected String[][] npcInfo;
    protected final int npcID;
    protected boolean playerHit;

    public EnemyEntity(int npcID, String[][]npcInfo, Playing playing) {
        super(Float.parseFloat(npcInfo[npcID][POSITION_X]),
                Float.parseFloat(npcInfo[npcID][POSITION_Y]),
                npcInfo[npcID][SPRITE_ATLAS],
                playing);

        this.npcInfo = npcInfo;
        this.npcID = npcID;
    }

    public void setEntityInitialCenter() {
        if (playing.getPlayer().getPositionX() >= 0)
            npcCenterX = x + ScreenCenterX - playing.getPlayer().getPositionX();
        else
            npcCenterX = x + ScreenCenterX;
        if (playing.getPlayer().getPositionY() >= 0)
            npcCenterY = y + ScreenCenterY - playing.getPlayer().getPositionY();
        else
            npcCenterY = y + ScreenCenterY;
    }
    protected void checkCollision() {
        if (pressedButton > -1)
            switch (pressedButton) {
                case UP -> { y += -entitySpeed; npcCenterY += -entitySpeed; checkCollisionUp(entitySpeed); }
                case LEFT -> { x += -entitySpeed; npcCenterX += -entitySpeed; checkCollisionLeft(entitySpeed); }
                case DOWN -> { y += entitySpeed; npcCenterY += entitySpeed; checkCollisionDown(entitySpeed); }
                case RIGHT -> { x += entitySpeed; npcCenterX += entitySpeed; checkCollisionRight(entitySpeed); }
            }
    }

    public boolean checkIfOutOfMap(int direction, float x, float y) {
        try {
            return playing.getMapManager().getCollision().isTileSolid(x,y,hitbox,direction);
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }

    public void checkCollisionAttack() {
        float[] objectHitbox = new float[4];

        switch (direction) {
            case UP -> {
                objectHitbox = new float[]{-20,0,0,28};
                playing.getMapManager().getCollision().isEntityHere(this, x, y, objectHitbox, direction);
            }
            case LEFT -> {
                objectHitbox = new float[]{32,-50,64,1};
                playing.getMapManager().getCollision().isEntityHere(this, x, y, objectHitbox, direction);
            }
            case DOWN -> {
                objectHitbox = new float[]{20,0,82,28};
                playing.getMapManager().getCollision().isEntityHere(this, x, y, objectHitbox, direction);
            }
            case RIGHT -> {
                objectHitbox = new float[]{16,0,64,70};
                playing.getMapManager().getCollision().isEntityHere(this, x, y, objectHitbox, direction);
            }
        }
        if (playerHit) {
            performAttack();
        }
    }

    private void performAttack() {
        int hitDamage = 10;

        playing.getPlayer().setHp(-hitDamage);
        playing.getPlayer().setEntityStatus(direction);

        playerHit = false;
    }

    protected void updatePosition() {
        //if the screen is moving with player
        if (px > 0 && px + ScreenWidth < playing.getMapManager().getMapMaxWidth())
            npcCenterX = npcCenterX - (px - oldX);
        oldX = px;
        //if the screen is moving with player
        if (py > 0 && py + ScreenHeight < playing.getMapManager().getMapMaxHeight())
            npcCenterY = npcCenterY - (py - oldY);
        oldY = py;
    }

    protected void randomMovement() {
        Random random = new Random();
        int number = random.nextInt(5);
        long currentTime = playing.getGame().getGameTime();

        if (currentTime - previousTime >= 1) {
            switch (number) {
                case UP -> { setPressedButton(UP); setDirection(UP); setAction(WALKING); }
                case LEFT -> { setPressedButton(LEFT); setDirection(LEFT); setAction(WALKING); }
                case DOWN -> { setPressedButton(DOWN); setDirection(DOWN); setAction(WALKING); }
                case RIGHT -> { setPressedButton(RIGHT); setDirection(RIGHT); setAction(WALKING); }
                case 4 -> { resetPressedButtons(); setAction(STANDING); }
            }
            previousTime = currentTime;
        }
    }
    
    protected void setAction(int action) {
        this.aniAction = action;
    }
    
    @Override
    public void resetPositionX(float x) {
        this.x += x * -1;
        this.npcCenterX += x * -1;
    }

    @Override
    public void resetPositionY(float y) {
        this.y += y * -1;
        this.npcCenterY += y * -1;
    }

    public String getName() {
        return npcInfo[npcID][NAME];
    }

    public void setPlayerHit(boolean playerHit) {
        this.playerHit = playerHit;
    }

    protected void setPressedButton(int pressedButton) {
        this.pressedButton = pressedButton;
    }

    protected void resetPressedButtons() {
        this.pressedButton = -1;
    }
    
}
