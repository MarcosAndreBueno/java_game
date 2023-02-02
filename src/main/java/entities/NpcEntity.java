package entities;

import game_states.Playing;

import java.util.Random;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.NpcAndEnemiesCsv.*;
import static utilz.Constants.PlayerConstants.WALKING;

public abstract class NpcEntity extends Entity implements GameEntity {

    protected int aniTick, aniIndexI;

    protected float oldX = playing.getPlayer().getPositionX();
    protected float oldY = playing.getPlayer().getPositionY();
    protected float npcCenterX, npcCenterY;
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
                case LEFT -> { x += -entitySpeed; npcCenterX += -entitySpeed; checkCollisionLeft(entitySpeed); }
                case DOWN -> { y += entitySpeed; npcCenterY += entitySpeed; checkCollisionDown(entitySpeed); }
                case UP -> { y += -entitySpeed; npcCenterY += -entitySpeed; checkCollisionUp(entitySpeed); }
                case RIGHT -> { x += entitySpeed; npcCenterX += entitySpeed; checkCollisionRight(entitySpeed); }
            }
    }

    protected void updatePosition() {
        if (px > 0 && px + ScreenWidth < playing.getMapManager().getMapMaxWidth())
            npcCenterX = npcCenterX - (px - oldX);
        oldX = px;
        if (py > 0 && py + ScreenHeight < playing.getMapManager().getMapMaxHeight())
            npcCenterY = npcCenterY - (py - oldY);
        oldY = py;
    }

    @Override
    public void resetPositionX(float x) {
        this.x += x * -1;
        this.npcCenterX += x * -1;
    }

    protected void randomMovement() {
        Random random = new Random();
        int number = random.nextInt(4);
        long currentTime = playing.getGame().getGameTime();
        if (currentTime - previousTime >= 1) {
            switch (number) {
                case LEFT -> { pressedButton = LEFT; direction = LEFT; aniAction = WALKING; }
                case DOWN -> { pressedButton = DOWN; direction = DOWN; aniAction = WALKING; }
                case UP -> { pressedButton = UP; direction = UP; aniAction = WALKING; }
                case RIGHT -> { pressedButton = RIGHT; direction = RIGHT; aniAction = WALKING; }
            }
            previousTime = currentTime;
        }
    }

    @Override
    public void resetPositionY(float y) {
        this.y += y * -1;
        this.npcCenterY += y * -1;
    }

    public String getName() {
        return npcInfo[npcID][NAME];
    }
}
