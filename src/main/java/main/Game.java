package main;

import game_states.GameOver;
import game_states.Pause;
import game_states.Playing;
import game_states.State;

import java.awt.*;
import java.awt.event.KeyListener;

import static utilz.Constants.GameStates.*;

public class Game implements Runnable{

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private Playing playing;
    private Pause pause;
    private GameOver gameOver;
    private State state;

    // FPS
    final int FPS = 120;
    final int UPS = 200; //control update
    private long gameTime = System.currentTimeMillis();

    private Thread gameThread;


    public Game() {
        initialize();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(); //make inputs go to this panel

        startGameThread();
    }

    private void initialize() {
        state = new State(this);
        pause = new Pause(this);
        playing = new Playing(this);
        gameOver = new GameOver(this);
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // go to run method
    }

    public void update() {
        switch (state.getGameState()) {
            case PAUSE -> pause.update();
            case PLAYING -> { playing.update();}
            case GAME_OVER -> { gameOver.update();}
        }
    }

    public void draw(Graphics2D g2) {
        switch (state.getGameState()) {
            case PAUSE -> { playing.draw(g2); pause.draw(g2); }
            case PLAYING -> { playing.draw(g2);}
            case GAME_OVER -> { playing.draw(g2); gameOver.draw(g2);}
        }
    }

    public void restartPlaying() {
        playing = new Playing(this);
        getState().changeGameState(PLAYING);
    }

    public void exit() { System.exit(0); }

    public Playing getPlaying() {
        return playing;
    }

    public Pause getPause() {
        return pause;
    }

    public State getState() {
        return state;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public void windowsFocusLost() {
        playing.getPlayer().resetDirBooleans();
    }

    public long getGameTime() {
        return gameTime;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double controlInterval = 1000000000.0 / UPS;

        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        int updates = 0;
        int frames = 0;
        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / controlInterval;
            deltaF += (currentTime - previousTime) / drawInterval;

            previousTime = currentTime;

            // if more than 0.005 seconds has passed
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            // if more than 0.008333 seconds has passed
            if (deltaF >= 1) {
                gamePanel.repaint(); //go to paintComponent method
                frames++;
                deltaF--;
            }

            //print fps and ups
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
