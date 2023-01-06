package entities.npcs;

import entities.Entity;
import entities.GameEntity;
import game_states.Playing;

import java.awt.*;
import java.util.Random;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.WALKING;

public abstract class NPCEntity extends Entity implements GameEntity {

    protected int aniTick, aniIndexI;
    protected float oldX = playing.getPlayer().getPositionX();
    protected float oldY = playing.getPlayer().getPositionY();
    protected long previousTime = playing.getGame().getGameTime();
    protected int pressedButton = -1;
    protected float npcCenterX, npcCenterY;
    protected float px, py;

    public NPCEntity(float x, float y, String sprite, Playing playing) {
        super(x, y, sprite, playing);
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
                case 0 -> { x += -entitySpeed; npcCenterX += -entitySpeed; checkCollisionLeft(); }
                case 1 -> { y += entitySpeed; npcCenterY += entitySpeed; checkCollisionDown(); }
                case 2 -> { y += -entitySpeed; npcCenterY += -entitySpeed; checkCollisionUp(); }
                case 3 -> { x += entitySpeed; npcCenterX += entitySpeed; checkCollisionRight(); }
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
        if (currentTime - previousTime >= 3) {
            switch (number) {
                case 0 -> { pressedButton = LEFT; aniDirection = LEFT; aniAction = WALKING; }
                case 1 -> { pressedButton = DOWN; aniDirection = DOWN; aniAction = WALKING; }
                case 2 -> { pressedButton = UP; aniDirection = UP; aniAction = WALKING; }
                case 3 -> { pressedButton = RIGHT; aniDirection = RIGHT; aniAction = WALKING; }
            }
            previousTime = currentTime;
        }
    }

    @Override
    public void resetPositionY(float y) {
        this.y += y * -1;
        this.npcCenterY += y * -1;
    }
}
