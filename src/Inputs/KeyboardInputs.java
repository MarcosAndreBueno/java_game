package Inputs;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.STANDING;
import static utilz.Constants.PlayerConstants.WALKING;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //number of the key pressed
        switch (code) {
            case KeyEvent.VK_W -> gamePanel.getGame().getPlayer().setUpPressed(true);
            case KeyEvent.VK_S -> gamePanel.getGame().getPlayer().setDownPressed(true);
            case KeyEvent.VK_A -> gamePanel.getGame().getPlayer().setLeftPressed(true);
            case KeyEvent.VK_D -> gamePanel.getGame().getPlayer().setRightPressed(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); //number of the key released
        switch (code) {
            case KeyEvent.VK_W -> gamePanel.getGame().getPlayer().setUpPressed(false);
            case KeyEvent.VK_S -> gamePanel.getGame().getPlayer().setDownPressed(false);
            case KeyEvent.VK_A -> gamePanel.getGame().getPlayer().setLeftPressed(false);
            case KeyEvent.VK_D -> gamePanel.getGame().getPlayer().setRightPressed(false);
        }
    }
}