package entities;

import java.awt.*;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;

public interface GameEntity {
    //in parent class
    public void setHitbox(float left, float down, float up, float right);
    public float[] getHitbox();
    public void setPositionX(float x);
    public void setPositionY(float y);
    public float getPositionX();
    public float getPositionY();
    public float getEntityCenterX();
    public float getEntityCenterY();
    public void increasePositionX(float x);
    public void increasePositionY(float y);
    public void decreasePositionX(float x);
    public void decreasePositionY(float y);
    public int getDirection();
    public void setDirection(int direction);
    public void updateStatusCooldown();
    public void setEntityCooldown(int entityStatus);
    public int getEntityStatus();

    public int getHp();
    public void setHp(int hp);
    public int getAction();
    public String getEntityName();

    public boolean checkCollision();

    //in child class
    public void loadAnimations();
    public void setEntityInitialCenter();
    public void update();
    public void draw(Graphics2D g2);
}
