package game_states;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

import static utilz.Constants.GameStates.PLAYING;

public interface GameStates {
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
    public void draw(Graphics2D g2);
    public void update();
}
