package Inputs;

import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.GameStates.MENU;
import static utilz.Constants.GameStates.PLAYING;

public class KeyboardInputs implements KeyListener {

    private Game game;

    public KeyboardInputs(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (game.getState().getGameState()) {
            case MENU -> game.getMenu().keyPressed(e);
            case PLAYING -> game.getPlaying().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (game.getState().getGameState()) {
            case MENU -> game.getMenu().keyReleased(e);
            case PLAYING -> game.getPlaying().keyReleased(e);
        }
    }
}