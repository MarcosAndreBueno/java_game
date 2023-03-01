package main;

import game_states.Pause;
import game_states.Playing;
import game_states.State;

import java.awt.*;

import static utilz.Constants.GameStates.*;

public class Game implements Runnable{

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private Playing playing;
    private Pause menu;
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
        state = new State();
        menu = new Pause(this);
        playing = new Playing(this);
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // go to run method
    }

    public void update() {
        switch (state.getGameState()) {
            case MENU -> menu.update();
            case PLAYING -> {
                playing.update();
            }
        }
    }

    public void draw(Graphics2D g2) {
        playing.draw(g2);
        if (state.getGameState() == MENU)
            menu.draw(g2);
    }

    public Playing getPlaying() {
        return playing;
    }

    public Pause getMenu() {
        return menu;
    }

    public State getState() {
        return state;
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
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
