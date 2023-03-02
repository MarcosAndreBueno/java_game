package maps;

import entities.Entity;
import entities.enemies.Ogre;
import entities.npcs.Chemist;
import game_states.Playing;

import java.util.ArrayList;

import static utilz.Constants.NpcAndEnemiesCsv.NAME;

public class EntityFactory {

    public ArrayList<Entity> testMap(String[][] npcInfo, Playing playing) {
        ArrayList<Entity> entities = new ArrayList<>();

        for (int i = 0; i < npcInfo.length; i++) {
            if (npcInfo[i][NAME].startsWith("npc"))
                entities.add(new Chemist(i, npcInfo, playing));
            else
                entities.add(new Ogre(i, npcInfo, playing));
        }

        return entities;
    }

}
