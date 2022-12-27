package entities;

import tiles.Collision;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x,y;
    protected int[] hitbox;
    protected float leftHit;
    protected float rightHit;
    protected float upHit;
    protected float downHit;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setHitbox(float left, float down, float up, float right) {
        //left, down, up, right
        hitbox = new int[]{(int) left, (int) down, (int) up, (int) right};
    }

    public int[] getHitbox() {
        return hitbox;
    }

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
}