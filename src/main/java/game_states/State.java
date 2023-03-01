package game_states;

import static utilz.Constants.GameStates.PLAYING;

public class State {
    private int gameState = PLAYING;

    public void changeGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getGameState() {
        return gameState;
    }
}
