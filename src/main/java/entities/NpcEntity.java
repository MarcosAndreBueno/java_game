package entities;

import game_states.Playing;

import java.util.Random;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.NpcAndEnemiesCsv.*;
import static utilz.Constants.PlayerConstants.STANDING;
import static utilz.Constants.PlayerConstants.WALKING;

public abstract class NpcEntity extends Entity implements GameEntity {

    protected int aniTick, aniIndexI;

    protected float oldX = playing.getPlayer().getPositionX();
    protected float oldY = playing.getPlayer().getPositionY();
    protected float px, py;

    protected long previousTime = playing.getGame().getGameTime();
    protected int pressedButton = -1;

    protected String[][] npcInfo;
    protected final int npcID;

    public NpcEntity(int npcID, String[][]npcInfo, Playing playing) {
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
    protected void checkMoveCollision() {
        if (pressedButton > -1)
            switch (pressedButton) {
                case UP ->    { y += -entitySpeed; entityCenterY += -entitySpeed; if (checkCollision()) decreasePositionY(-entitySpeed); }
                case LEFT ->  { x += -entitySpeed; entityCenterX += -entitySpeed; if (checkCollision()) decreasePositionX(-entitySpeed); }
                case DOWN ->  { y +=  entitySpeed; entityCenterY +=  entitySpeed; if (checkCollision()) decreasePositionY(entitySpeed); }
                case RIGHT -> { x +=  entitySpeed; entityCenterX +=  entitySpeed; if (checkCollision()) decreasePositionX(entitySpeed); }
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
                case LEFT  -> { pressedButton = LEFT;  direction = LEFT;  aniAction = WALKING; }
                case DOWN  -> { pressedButton = DOWN;  direction = DOWN;  aniAction = WALKING; }
                case UP    -> { pressedButton = UP;    direction = UP;    aniAction = WALKING; }
                case RIGHT -> { pressedButton = RIGHT; direction = RIGHT; aniAction = WALKING; }
                case 4 -> { resetPressedButtons(); aniAction = STANDING; }
            }
            previousTime = currentTime;
        }
    }

    public void updateStatusCooldown() {
    }
    public void setEntityCooldown(int entityStatus){}

    protected void resetPressedButtons() {
        this.pressedButton = -1;
    }

    public String getName() {
        return npcInfo[npcID][NAME];
    }
}
