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

    protected float x,y;
    protected int[] hitbox;
    protected Playing playing;

    protected BufferedImage[][] animations;
    protected int aniTick, aniIndexI, aniSpeed = (int) (12 * Scale);
    protected int playerWidth = (int) (13 * Scale), playerHeight = (int) (26 * Scale);

    protected int playerAction = STANDING, playerDirection = DOWN;
    protected float playerSpeed = 0.4f * Scale;


    public Entity(float x, float y, Playing playing) {
        this.x = x;
        this.y = y;
        this.playing = playing;
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