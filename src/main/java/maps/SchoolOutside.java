package maps;

import entities.Entity;
import entities.npcs.*;
import game_states.Playing;
import utilz.CSVHandle;
import utilz.Constants.Maps;

import java.awt.*;
import java.util.ArrayList;

import static main.GameWindow.ScreenSettings.ScreenHeight;
import static main.GameWindow.ScreenSettings.ScreenWidth;
import static utilz.Constants.Maps.SCHOOL_OUTSIDE;

public class SchoolOutside extends MapManager{
    private float playerX;
    private float playerY;
    private String[][] npcInfo;

    public SchoolOutside(Playing playing) {
        super(playing, Maps.SCHOOL_OUTSIDE);
    }

    public void loadMapInfo() {
        npcInfo = new CSVHandle().getNPCInformation(SCHOOL_OUTSIDE);
    }

    public ArrayList<Entity> loadEntities(ArrayList<Entity> entities) {
        entities.add(new npc_Tony(0, npcInfo, playing));
        entities.add(new npc_Klay(1, npcInfo, playing));
        entities.add(new npc_John(2, npcInfo, playing));
        entities.add(new npc_Samantha(3, npcInfo, playing));
        entities.add(new npc_Jay(4, npcInfo, playing));
        return entities;
    }

    public void update() {
        playerX = playing.getPlayer().getPositionX();
        playerY = playing.getPlayer().getPositionY();
        //freezes the camera if the screen hits the edge of the map
        if (playerX + ScreenWidth >= mapMaxWidth)
            playerX = mapMaxWidth - ScreenWidth;
        else if (playerX <= 0)
            playerX = 0;
        if (playerY + ScreenHeight >= mapMaxHeight)
            playerY = mapMaxHeight - ScreenHeight;
        else if (playerY <= 0)
            playerY = 0;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(mapSprite.getSubimage((int)playerX, (int)playerY, ScreenWidth, ScreenHeight),
                0,0,ScreenWidth, ScreenHeight,null);
    }
}
