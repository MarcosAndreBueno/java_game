package entities;

import game_states.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.Scale;
import static utilz.Constants.PlayerConstants.*;

public abstract class Entity {

    protected Playing playing;

    //entity position
    protected float x,y;
    //center camera on player
    protected float entityCenterX, entityCenterY;

    protected float[] hitbox;

    //entity animation
    protected BufferedImage[][] animations;
    protected String sprite;
    protected int aniAction, entityStatus;
    protected int aniTick, aniFrame, aniMoveSpeed;
    protected int direction;
    protected int aniWidth, aniHeight;
    protected float entitySpeed;
    protected String entityName;

    public Entity(float x, float y, String sprite, Playing playing) {
        this.x = x;
        this.y = y;
        this.playing = playing;
        this.sprite = sprite;
        setEntityDefaultValues();
    }

    //values used for player
    private void setEntityDefaultValues() {
        aniWidth = (int) (32*Scale);
        aniHeight= (int) (32*Scale);
        aniAction = STANDING;
        aniMoveSpeed = 24;
        entitySpeed = 0.3f * Scale;
    }

    public boolean checkCollision() {
        if (playing.getMapManager().getCollision().isTileSolid(this))
            return true;
        if (playing.getMapManager().getCollision().isEntityHere(this))
            return true;

        return false;
    }

    public void setHitbox(float up, float left, float down, float right) {
        hitbox = new float[]{(int) up, (int) left, (int) down, (int) right};
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getHp() {
        return 0;
    }

    public void setHp(int hp) {}

    public int getAction() { return aniAction; }

    public float[] getHitbox() {return hitbox;}

    public String getEntityName() {return entityName;}

    public void increasePositionX(float x) {
        this.x += x;
    }
    public void increasePositionY(float y) {
        this.y += y;
    }
    public void decreasePositionX(float x) {
        this.x += x * -1;
    }
    public void decreasePositionY(float y) {
        this.y += y * -1;
    }
    public void setPositionX(float x) {
        this.x = x;
    }
    public void setPositionY(float y) {
        this.y = y;
    }
    public float getPositionX() {
        return x;
    }
    public float getPositionY() {
        return y;
    }
    public float getEntityCenterX() {
        return entityCenterX;
    }
    public float getEntityCenterY() {
        return entityCenterY;
    }

    public void setEntityStatus(int entityStatus) {
        this.entityStatus = entityStatus;
    }

    public int getEntityStatus() {
        return entityStatus;
    }

    protected void resetAniFrame() { this.aniFrame = 0; }
    public void update() {}
    public void draw(Graphics2D g2) {}
}