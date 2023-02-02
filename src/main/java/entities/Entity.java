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
    protected int aniTick, aniFrame, aniMoveSpeed, aniAttackSpeed;
    protected int direction;
    protected int aniWidth, aniHeight;
    protected float entitySpeed;
    protected String entityName;
    protected int entityStatus = -1;

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
        aniAttackSpeed = 12;
        entitySpeed = 0.3f * Scale;
    }

    public void setHitbox(float up, float left, float down, float right) {
        hitbox = new float[]{(int) up, (int) left, (int) down, (int) right};
    }

    public void checkCollisionLeft(float reset) {
        if (playing.getMapManager().getCollision().isTileSolid(x,y,hitbox, LEFT))
            resetPositionX(-reset);
        if (playing.getMapManager().getCollision().isEntityHere(this, x, y, hitbox, LEFT))
            resetPositionX(-reset);
    }
    public void checkCollisionRight(float reset) {
        if (playing.getMapManager().getCollision().isTileSolid(x,y,hitbox,RIGHT))
            resetPositionX(reset);
        if (playing.getMapManager().getCollision().isEntityHere(this, x, y,hitbox,RIGHT))
            resetPositionX(reset);
    }
    public void checkCollisionUp(float reset) {
        if (playing.getMapManager().getCollision().isTileSolid(x,y,hitbox,UP))
            resetPositionY(-reset);
        if (playing.getMapManager().getCollision().isEntityHere(this, x, y,hitbox,UP))
            resetPositionY(-reset);
    }
    public void checkCollisionDown(float reset) {
        if (playing.getMapManager().getCollision().isTileSolid(x,y,hitbox,DOWN))
            resetPositionY(reset);
        if (playing.getMapManager().getCollision().isEntityHere(this, x, y,hitbox,DOWN))
            resetPositionY(reset);
    }

    protected void setDirection(int direction) {
        this.direction = direction;
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

    protected void setEntityStatus(int status) {this.entityStatus = status;}
}