package maps;

import entities.Entity;
import entities.enemies.EnemyOgre;
import entities.npcs.NpcChemist;
import game_states.Playing;

import java.util.ArrayList;

import static utilz.Constants.NpcAndEnemiesCsv.NAME;

public class EntityFactory {

    public ArrayList<Entity> testMap(String[][] npcInfo, Playing playing) {
        ArrayList<Entity> entities = new ArrayList<>();

        for (int i = 0; i < npcInfo.length; i++) {
            if (npcInfo[i][NAME].equals("npc_Chemist"))
                entities.add(new NpcChemist(i, npcInfo, playing));
            else
                entities.add(new EnemyOgre(i, npcInfo, playing));
        }

        return entities;
    }

}
