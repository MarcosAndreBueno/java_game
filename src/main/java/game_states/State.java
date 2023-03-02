package game_states;

import main.Game;

import static utilz.Constants.GameStates.PAUSE;
import static utilz.Constants.GameStates.PLAYING;

public class State {
    private int gameState = PLAYING;
    private Game game;

    public State(Game game) {
        this.game = game;
    }

    public void changeGameState(int gameState) {
        if (gameState == PAUSE)
            game.getPause().setPreviousPage(this.gameState);
        this.gameState = gameState;
    }

    public int getGameState() {
        return gameState;
    }
}
