package game_states;

import entities.EnemyEntity;
import entities.Entity;
import entities.Player;

import main.Game;
import maps.StoneCave;
import utilz.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static utilz.Constants.Entities.*;
import static utilz.Constants.EntityConstants.DEAD;
import static utilz.Constants.EntityConstants.GAME_OVER;
import static utilz.Constants.GameStates.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerConstants.WALKING;

public class Playing implements GameStates{
    private Game game;
    protected Player player;
    protected ArrayList<Entity> entities;
    protected StoneCave testMap;


    public Playing(Game game) {
        this.game = game;
        this.testMap = new StoneCave(this);
        this.player = new Player(800, 10, PLAYER_ATLAS, this);
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
        ArrayList<Entity> entities1 = getEntities();
        EnemyEntity enemy = (EnemyEntity) entities1.get(0);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> { player.setUpPressed(true); player.setAction(WALKING); }
            case KeyEvent.VK_S -> { player.setDownPressed(true); player.setAction(WALKING); }
            case KeyEvent.VK_A -> { player.setLeftPressed(true); player.setAction(WALKING); }
            case KeyEvent.VK_D -> { player.setRightPressed(true); player.setAction(WALKING); }
            case KeyEvent.VK_J -> player.setAction(ATTACKING_01);
            case KeyEvent.VK_L -> player.setAction(ATTACKING_02);
            case KeyEvent.VK_ESCAPE -> {
                player.resetDirBooleans();
                game.getState().changeGameState(MENU);
            }

            case KeyEvent.VK_ENTER -> {
                System.out.println("informations ");
                System.out.println("player x:  " + player.getPositionX()     + " y:  " + player.getPositionY());
                System.out.println("player cx: " + player.getEntityCenterX() + " cy: " + player.getEntityCenterY());
                System.out.println("enemy  x:  " + enemy.getPositionX()      + " y:  " + enemy.getPositionY());
                System.out.println("enemy  cx: " + enemy.getEntityCenterX()  + " cy: " + enemy.getEntityCenterY());
                System.out.println("-------------------------------------------------------------------");
            }
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
        for (Entity npc : entities) if (npc != null && npc.getEntityStatus() != DEAD) npc.draw(g2);
    }

    @Override
    public void update() {
        testMap.update();
        for (Entity npc : entities) if (npc != null && npc.getEntityStatus() != DEAD) npc.update();
        entities.removeIf(npc -> npc.getEntityStatus() == DEAD);
        if (entities.get(entities.size()-1).getEntityStatus() == GAME_OVER)
            game.getState().changeGameState(Constants.GameStates.GAME_OVER);
    }

    public Player getPlayer() {
        return player;
    }

    public StoneCave getMapManager() {
        return testMap;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
