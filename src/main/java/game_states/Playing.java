package game_states;

import entities.Entity;
import entities.Player;
import main.Game;
import maps.SchoolOutside;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static utilz.Constants.Entities.*;

public class Playing implements GameStates{
    private Game game;
    protected Player player;
    protected ArrayList<Entity> entities;
    protected SchoolOutside mapManager;


    public Playing(Game game) {
        this.game = game;
        this.mapManager = new SchoolOutside(this);
        this.player = new Player(-50, 500, PLAYER_ATLAS, this);
        this.entities = new ArrayList<>();
        loadMapInfo();
        loadEntities();
    }

    private void loadMapInfo() {
        mapManager.loadMapInfo();
    }

    private void loadEntities() {
        entities = mapManager.loadEntities(entities);
        entities.add(player);
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

    @Override
    public void draw(Graphics2D g2) {
        mapManager.draw(g2);
        if (entities != null)
            for (Entity npc : entities) if (npc != null) npc.draw(g2);
    }

    @Override
    public void update() {
        mapManager.update();
        if (entities != null)
            for (Entity npc : entities) if (npc != null) npc.update();
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

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
