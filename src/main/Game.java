package main;

import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable{

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    // FPS
    final int FPS = 120;
    final int UPS = 200; //control update

    Thread gameThread;

    private Player player;
    private LevelManager levelManager;

    public Game() {
        initialize();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(); //make inputs go to this panel

        startGameThread();
    }

    private void initialize() {
        player = new Player(this);
        levelManager = new LevelManager(this);
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // go to run method
    }

    public void update() {
        levelManager.update();
        player.update();
    }

    public void render(Graphics2D g2) {
        levelManager.draw(g2);
        player.render(g2);
    }

    public void windowsFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
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
