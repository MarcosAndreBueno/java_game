package entities;

import game_states.Playing;
import tiles.Collision;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.Scale;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.STANDING;

public abstract class Entity {

    protected Playing playing;

    //entity position
    protected float x,y;
    protected int[] hitbox;

    //entity animation
    protected BufferedImage[][] animations;
    protected int aniAction;
    protected int aniTick, aniIndexI;
    protected int aniSpeed;
    protected int aniDirection;
    protected int aniWidth;
    protected int aniHeight;

    //entity movement
    protected float entitySpeed;


    public Entity(float x, float y, Playing playing) {
        this.x = x;
        this.y = y;
        this.playing = playing;
        setEntityDefaultValues();
    }

    private void setEntityDefaultValues() {
        aniWidth = (int) (13 * Scale);
        aniHeight = (int) (26 * Scale);
        aniAction = STANDING;
        aniDirection = DOWN;
        aniSpeed = (int) (12 * Scale);
        entitySpeed = 0.4f * Scale;
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

    public void update() {}

    public void draw(Graphics2D g2) {}
}