package entities;

import game_states.Playing;
import utilz.Constants.*;

import java.util.Random;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.NpcAndEnemiesCsv.*;
import static utilz.Constants.PlayerConstants.*;

public abstract class EnemyEntity extends Entity implements GameEntity {

    protected int aniTick, aniIndexI;

    protected long previousTime, attackTimer;
    protected int pressedButton = -1;

    protected String[][] npcInfo;
    protected final int npcID;

    protected int aniAttackSpeed, aniDyingSpeed, aniPerishingSpeed;
    protected int maxHP, hp;
    protected int sightRange, attackRange;
    protected int pushBack, attackingDamage;
    protected boolean following, canPerformAttack, attackCooldown;

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

        if (playing.getPlayer().getPositionX() > this.x)
            entityCenterX = playing.getPlayer().getEntityCenterX() - screenDifferenceX;
        else
            entityCenterX = playing.getPlayer().getEntityCenterX() + screenDifferenceX;
        if (playing.getPlayer().getPositionY() > this.y)
            entityCenterY = playing.getPlayer().getEntityCenterY() - screenDifferenceY;
        else
            entityCenterY = playing.getPlayer().getEntityCenterY() + screenDifferenceY;
    }

    protected void randomMovement() {
        Random random = new Random();
        int number = random.nextInt(5);
        long currentTime = System.currentTimeMillis();

        if (currentTime - previousTime >= 1700) {
            switch (number) {
                case UP -> { setPressedButton(UP); setDirection(UP); setAction(EnemyConstants.WALKING); }
                case LEFT -> { setPressedButton(LEFT); setDirection(LEFT); setAction(EnemyConstants.WALKING); }
                case DOWN -> { setPressedButton(DOWN); setDirection(DOWN); setAction(EnemyConstants.WALKING); }
                case RIGHT -> { setPressedButton(RIGHT); setDirection(RIGHT); setAction(EnemyConstants.WALKING); }
                case 4 -> { resetPressedButtons(); setAction(EnemyConstants.STANDING); }
            }
            previousTime = currentTime;
        }
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

    public void setEntityCooldown(int status) {
        switch (status) {
            case EnemyConstants.ATTACKING_01 -> {
                attackCooldown = true;
                attackTimer = System.currentTimeMillis();
            }
        }
    }

    public void updateStatusCooldown() {
        if (System.currentTimeMillis() - attackTimer >= 1000)
            attackCooldown = false;
    }

    protected void setPressedButton(int pressedButton) {
        this.pressedButton = pressedButton;
    }

    protected void resetPressedButtons() {
        this.pressedButton = -1;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int value) {
        this.hp += value;
        if (this.hp > maxHP)
            this.hp = maxHP;
        else if (this.hp < 0) {
            setAction(EnemyConstants.DYING);
            resetAniFrame();
        }
    }

    public int getId() {
        return npcID;
    }

    public String getName() {
        return npcInfo[npcID][NAME];
    }
}
