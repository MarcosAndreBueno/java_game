package maps;

import entities.Entity;
import entities.enemies.enemy_ogre;
import entities.npcs.npc_chemist;
import game_states.Playing;

import java.util.ArrayList;

import static utilz.Constants.NpcCsv.NAME;

public class EntityFactory {

    public ArrayList<Entity> testMap(String[][] npcInfo, Playing playing) {
        ArrayList<Entity> entities = new ArrayList<>();

        for (int i = 0; i < npcInfo.length; i++) {
            if (npcInfo[i][NAME].equals("Chemist"))
                entities.add(new npc_chemist(i, npcInfo, playing));
            else
                entities.add(new enemy_ogre(i, npcInfo, playing));
        }

        return entities;
    }

}
