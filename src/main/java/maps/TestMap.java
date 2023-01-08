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
import static utilz.Constants.Maps.TEST_MAP;
import static utilz.Constants.NpcCsv.IS_ON_MAP;

public class TestMap extends MapManager{
    private float screenX;
    private float screenY;
    private float screenW;
    private float screenH;

    private String[][] npcInfo;

    public TestMap(Playing playing) {
        super(playing, TEST_MAP);
    }

    public void loadMapInfo() {
        npcInfo = new CSVHandle().getNPCInformation(TEST_MAP);
    }

    public ArrayList<Entity> loadEntities() {
        ArrayList<Entity> entities = new ArrayList<>();
        if (npcInfo[0][IS_ON_MAP].equals("1"))
            entities.add(new npc_Tony(0, npcInfo, playing));
        if (npcInfo[1][IS_ON_MAP].equals("1"))
            entities.add(new npc_Klay(1, npcInfo, playing));
        if (npcInfo[2][IS_ON_MAP].equals("1"))
            entities.add(new npc_John(2, npcInfo, playing));
        if (npcInfo[3][IS_ON_MAP].equals("1"))
            entities.add(new npc_Samantha(3, npcInfo, playing));
        if (npcInfo[4][IS_ON_MAP].equals("1"))
            entities.add(new npc_Jay(4, npcInfo, playing));
        return entities;
    }

    public void update() {
        screenX = playing.getPlayer().getPositionX();
        screenY = playing.getPlayer().getPositionY();
        screenW = ScreenWidth;
        screenH = ScreenHeight;
        //for maps smaller than the screen width
        if (mapMaxWidth < ScreenWidth) {
            screenX = 0;
            screenW = mapMaxWidth;
        }
        //freezes the camera if the screen hits the edge of the map
        else {
            if (screenX <= 0)
                screenX = 0;
            else if (screenX + ScreenWidth >= mapMaxWidth)
                screenX = mapMaxWidth - ScreenWidth;
        }
        //for maps smaller than the screen height
        if (mapMaxHeight < ScreenHeight) {
            screenY = 0;
            screenH = mapMaxHeight;
        }
        //freezes the camera if the screen hits the edge of the map
        else {
            if (screenY <= 0)
                screenY = 0;
            else if (screenY + ScreenHeight >= mapMaxHeight)
                screenY = mapMaxHeight - ScreenHeight;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(mapSprite.getSubimage((int)screenX, (int)screenY, (int)screenW, (int)screenH),
                0,0,null);
    }
}
