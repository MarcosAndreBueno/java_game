package game_states;

import static utilz.Constants.GameStates.MENU;
import static utilz.Constants.GameStates.PLAYING;

public class State {
    private int gameState = PLAYING;

    public void changeGameState() {
        if (gameState == PLAYING)
            gameState = MENU;
        else
            gameState = PLAYING;
    }

    public int getGameState() {
        return gameState;
    }
}
