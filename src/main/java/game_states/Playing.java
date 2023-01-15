package game_states;

import entities.Entity;
import entities.Player;
import main.Game;
import maps.TestMap;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static utilz.Constants.Entities.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerConstants.WALKING;

public class Playing implements GameStates{
    private Game game;
    protected Player player;
    protected ArrayList<Entity> entities;
    protected TestMap testMap;


    public Playing(Game game) {
        this.game = game;
        this.testMap = new TestMap(this);
        this.player = new Player(0, 0, PLAYER_ATLAS, this);
        loadMapInfo();
        loadEntities();
    }

    private void loadMapInfo() {
        testMap.loadMapInfo();
    }

    private void loadEntities() {
        entities = testMap.loadEntities();
        entities.add(player);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> { player.setUpPressed(true); player.setAction(WALKING); }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> { player.setDownPressed(true); player.setAction(WALKING); }
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> { player.setLeftPressed(true); player.setAction(WALKING); }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> { player.setRightPressed(true); player.setAction(WALKING); }
            case KeyEvent.VK_J -> player.setAction(ATTACKING_01);
            case KeyEvent.VK_L -> player.setAction(ATTACKING_02);
            case KeyEvent.VK_ESCAPE -> { player.resetDirBooleans(); game.getState().changeGameState(); }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> player.setUpPressed(false);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> player.setDownPressed(false);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> player.setLeftPressed(false);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> player.setRightPressed(false);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        testMap.draw(g2);
        if (entities != null)
            for (Entity npc : entities) if (npc != null) npc.draw(g2);
    }

    @Override
    public void update() {
        testMap.update();
        if (entities != null)
            for (Entity npc : entities) if (npc != null) npc.update();
    }

    public Player getPlayer() {
        return player;
    }

    public TestMap getMapManager() {
        return testMap;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
