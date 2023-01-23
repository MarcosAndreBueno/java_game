package entities;

import game_states.Playing;
import tiles.Collision;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.Scale;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;

public abstract class Entity {

    protected Playing playing;

    //entity position
    protected float x,y;
    protected float[] hitbox, attackHitBox;

    //entity animation
    protected BufferedImage[][] animations;
    protected String sprite;
    protected int aniAction;
    protected int aniTick, aniFrame, aniMoveSpeed, attackSpeed;
    protected int direction, aniDirection;
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
        aniWidth = (int) (13 * Scale);
        aniHeight = (int) (26 * Scale);
        aniAction = STANDING;
        aniDirection = DOWN;
        aniMoveSpeed = 24;
        attackSpeed = 12;
        entitySpeed = 0.3f * Scale;
    }

    public void setHitbox(float up, float left, float down, float right) {
        hitbox = new float[]{(int) up, (int) left, (int) down, (int) right};
    }

    public void checkCollisionLeft() {
        if (playing.getMapManager().getCollision().isTileSolid(x,y,hitbox, LEFT))
            resetPositionX(-entitySpeed);
        if (playing.getMapManager().getCollision().isEntityHere(this, x, y, hitbox, LEFT))
            resetPositionX(-entitySpeed);
    }
    public void checkCollisionRight() {
        if (playing.getMapManager().getCollision().isTileSolid(x,y,hitbox,RIGHT))
            resetPositionX(entitySpeed);
        if (playing.getMapManager().getCollision().isEntityHere(this, x, y,hitbox,RIGHT))
            resetPositionX(entitySpeed);
    }
    public void checkCollisionUp() {
        if (playing.getMapManager().getCollision().isTileSolid(x,y,hitbox,UP))
            resetPositionY(-entitySpeed);
        if (playing.getMapManager().getCollision().isEntityHere(this, x, y,hitbox,UP))
            resetPositionY(-entitySpeed);
    }
    public void checkCollisionDown() {
        if (playing.getMapManager().getCollision().isTileSolid(x,y,hitbox,DOWN))
            resetPositionY(entitySpeed);
        if (playing.getMapManager().getCollision().isEntityHere(this, x, y,hitbox,DOWN))
            resetPositionY(entitySpeed);
    }

    public int getHp() {
        return 0;
    }

    public void setHp(int hp) {}

    public int getAction() { return aniAction; }

    public float[] getHitbox() {return hitbox;}

    public String getEntityName() {return entityName;}

    public void setPositionX(float x) {
        this.x += x;
    }

    public void setPositionY(float y) {
        this.y += y;
    }

    public float getPositionX() {
        return x;
    }

    public float getPositionY() {
        return y;
    }

    public void resetPositionX(float x) {
        this.x += x * -1;
    }

    public void resetPositionY(float y) {
        this.y += y * -1;
    }
    public void update() {}

    public void draw(Graphics2D g2) {}
}