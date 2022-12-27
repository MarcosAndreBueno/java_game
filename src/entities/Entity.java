package entities;

import main.Collision;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x,y;
    protected Rectangle2D.Float hitbox;
    protected float leftHit;
    protected float rightHit;
    protected float upHit;
    protected float downHit;

    protected Collision collision;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
        collision = new Collision(this);
    }

    public void drawHitBox(Graphics2D g2) {
        g2.drawRect((int)hitbox.x,(int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
    }

    public void updateHitBox(float x, float y) {
        hitbox.x = x+5;
        hitbox.y = y+35;
    }

    public void setHitbox(float playerCenterX, float playerCenterY, float playerWidth, float playerHeight) {
        hitbox = new Rectangle2D.Float(playerCenterX+5, playerCenterY+35, playerWidth-10, playerHeight-35);
        leftHit = 0;
        rightHit = 14*2;
        upHit = 16*2;
        downHit = 32*2;
    }

    public void setPositionX(float x) {
        this.x += x;
    }

    public void setPositionY(float y) {
        this.y += y;
    }

    public void resetPositionX(float x) {
        this.x += x * -1;
    }

    public void resetPositionY(float y) {
        this.y += y * -1;
    }
}