package Inputs;

import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.GameStates.*;

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
            case PAUSE -> game.getPause().keyPressed(e);
            case PLAYING -> game.getPlaying().keyPressed(e);
            case GAME_OVER -> game.getGameOver().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (game.getState().getGameState()) {
            case PAUSE -> game.getPause().keyReleased(e);
            case PLAYING -> game.getPlaying().keyReleased(e);
            case GAME_OVER -> game.getGameOver().keyReleased(e);
        }
    }
}