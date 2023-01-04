package game_states;

import entities.Entity;
import entities.NPC_Test;
import entities.Player;
import main.Game;
import maps.SchoolOutside;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Playing implements GameStates{
    private Game game;
    protected Player player;
    protected Entity[] entities;
    protected SchoolOutside mapManager;


    public Playing(Game game) {
        this.game = game;
        this.mapManager = new SchoolOutside(this);
        this.player = new Player(this);
        loadNPCs();
    }

    private void loadNPCs() {
        entities = new Entity[7];
        entities[0] = new NPC_Test(-50,620, this);
        entities[1] = new NPC_Test(250,700, this);
        entities[2] = new NPC_Test(100,490, this);
        entities[3] = new NPC_Test(-200,510, this);
        entities[4] = new NPC_Test(150,460, this);
        entities[5] = new NPC_Test(430,500, this);
        entities[6] = player; //the player will be the last one drawn and updated.
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
        if (entities != null)
            for (Entity npc : entities) if (npc != null) npc.draw(g2);
    }

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

    public Entity[] getEntities() {
        return entities;
    }
}
