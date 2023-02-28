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

    protected long previousTime = playing.getGame().getGameTime();
    protected long testTime = playing.getGame().getGameTime();
    protected int pressedButton = -1;

    protected String[][] npcInfo;
    protected final int npcID;

    protected int maxHP, hp;
    protected int sightRange, attackRange;
    protected int pushBack, hitDamage;
    protected long cooldownCounter = System.currentTimeMillis();
    protected int attackCooldown, attack1Damage;
    protected float attack1PushBack;
    protected boolean following, playerHit, canPerformAttack;

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
            entityCenterX = x + ScreenCenterX - playing.getPlayer().getPositionX();
        else
            entityCenterX = x + ScreenCenterX;
        if (playing.getPlayer().getPositionY() >= 0)
            entityCenterY = y + ScreenCenterY - playing.getPlayer().getPositionY();
        else
            entityCenterY = y + ScreenCenterY;
    }
    protected void checkCollision() {
        if (pressedButton > -1)
            switch (pressedButton) {
                case UP -> { y += -entitySpeed; entityCenterY += -entitySpeed; checkCollisionUp(entitySpeed); }
                case LEFT -> { x += -entitySpeed; entityCenterX += -entitySpeed; checkCollisionLeft(entitySpeed); }
                case DOWN -> { y += entitySpeed; entityCenterY += entitySpeed; checkCollisionDown(entitySpeed); }
                case RIGHT -> { x += entitySpeed; entityCenterX += entitySpeed; checkCollisionRight(entitySpeed); }
            }
    }

    public void updateEnemyCenter() {
        float screenDifferenceX = Math.abs(this.x - playing.getPlayer().getPositionX());
        float screenDifferenceY = Math.abs(this.y - playing.getPlayer().getPositionY());

        if (playing.getPlayer().getCenterX() > entityCenterX)
            entityCenterX = playing.getPlayer().getCenterX() - screenDifferenceX;
        else
            entityCenterX = playing.getPlayer().getCenterX() + screenDifferenceX;
        if (playing.getPlayer().getCenterY() > entityCenterY)
            entityCenterY = playing.getPlayer().getCenterY() - screenDifferenceY;
        else
            entityCenterY = playing.getPlayer().getCenterY() + screenDifferenceY;
    }

    protected void randomMovement() {
        Random random = new Random();
        int number = random.nextInt(5);
        long currentTime = playing.getGame().getGameTime();

        if (currentTime - previousTime >= 3000) {
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

    protected boolean canAttack() {
        if (System.currentTimeMillis() - cooldownCounter >= attackCooldown) {
            cooldownCounter = System.currentTimeMillis();
            return true;
        }

        return false;
    }

    protected void setAction(int action) {
        this.aniAction = action;
    }
    
    @Override
    public void resetPositionX(float x) {
        this.x += x * - 1;
        this.entityCenterX += x * -1;
    }

    @Override
    public void resetPositionY(float y) {
        this.y += y * -1;
        this.entityCenterY += y * -1;
    }

    public void pushEntity(float pushDistance, int hitDirection) {
        playing.getMapManager().getCollision().pushEntity(this, hitDirection, pushDistance);
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
