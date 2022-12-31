package game_states;

import entities.Player;
import main.Game;
import maps.SchoolOutside;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Playing implements GameStates{
    private Game game;
    protected Player player;
    protected SchoolOutside mapManager;


    public Playing(Game game) {
        this.game = game;
        this.mapManager = new SchoolOutside(this);
        this.player = new Player(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> game.getPlaying().getPlayer().setUpPressed(true);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> game.getPlaying().getPlayer().setDownPressed(true);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> game.getPlaying().getPlayer().setLeftPressed(true);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> game.getPlaying().getPlayer().setRightPressed(true);
            case KeyEvent.VK_ESCAPE -> { player.resetDirBooleans(); game.getState().changeGameState(); }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> game.getPlaying().getPlayer().setUpPressed(false);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> game.getPlaying().getPlayer().setDownPressed(false);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> game.getPlaying().getPlayer().setLeftPressed(false);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> game.getPlaying().getPlayer().setRightPressed(false);
        }
    }

    public void draw(Graphics2D g2) {
        mapManager.draw(g2);
        player.render(g2);
    }

    public void update() {
        player.update();
        mapManager.update();
    }

    public Player getPlayer() {
        return player;
    }

    public SchoolOutside getMapManager() {
        return mapManager;
    }

    public Game getGame() {
        return game;
    }
}
