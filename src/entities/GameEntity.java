package entities;

import java.awt.*;

public interface GameEntity {
    //in parent class
    public void setHitbox(float left, float down, float up, float right);
    public int[] getHitbox();
    public void setPositionX(float x);
    public void setPositionY(float y);
    public float getPositionX();
    public float getPositionY();
    public void resetPositionX(float x);
    public void resetPositionY(float y);

    //in child class
    public void loadAnimations();
    public void setEntityInitialCenter();
    public void update();
    public void draw(Graphics2D g2);
}
