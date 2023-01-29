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
    public void resetPositionX(float x);
    public void resetPositionY(float y);
    public int getHp();
    public void setHp(int hp);
    public int getAction();
    public String getEntityName();

    public void checkCollisionLeft(float reset);
    public void checkCollisionRight(float reset);
    public void checkCollisionUp(float reset);
    public void checkCollisionDown(float reset);

    //in child class
    public void loadAnimations();
    public void setEntityInitialCenter();
    public void update();
    public void draw(Graphics2D g2);
}
