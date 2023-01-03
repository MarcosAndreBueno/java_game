package tiles;


import entities.Entity;
import entities.Player;
import maps.MapManager;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.Directions.RIGHT;

public class Collision {

    private MapManager mapManager;
    private int[] playerHitbox;
    private Entity[] npcs;

    public Collision(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public boolean isTileSolid(int y, int x) {
        return mapManager.getMapHitbox()[y][x] == 1;
    }

    public boolean isEntityHereX(float playerX, float playerY, int[] playerHitbox, int playerDirection) {
        npcs = mapManager.getPlaying().getNpcs();

        int npcDirection;
        if (playerDirection == LEFT)
            npcDirection = RIGHT;
        else
            npcDirection = LEFT;


        for (Entity npc : npcs) {
            int[] npcHitbox = npc.getHitbox();
            if (
                    (int) playerX + playerHitbox[playerDirection] == (int) npc.getPositionX() + npcHitbox[npcDirection]
                        &&
                        (
                            (
                                (int) playerY + playerHitbox[UP] <= (int) npc.getPositionY() + npcHitbox[DOWN] &&
                                (int) playerY + playerHitbox[UP] >= (int) npc.getPositionY() + npcHitbox[UP]
                            )
                                    ||
                            (
                                (int) playerY + playerHitbox[DOWN] >= (int) npc.getPositionY() + npcHitbox[UP] &&
                                (int) playerY + playerHitbox[DOWN] <= (int) npc.getPositionY() + npcHitbox[DOWN]
                            )
                        )
                )
                return true;
        }

        return false;
    }

    public boolean isEntityHereY(float playerX, float playerY, int[] playerHitbox, int playerDirection) {
        npcs = mapManager.getPlaying().getNpcs();

        int npcDirection;
        if (playerDirection == UP)
            npcDirection = DOWN;
        else
            npcDirection = UP;

        for (Entity npc : npcs) {
            int[] npcHitbox = npc.getHitbox();
            if (
                    (int) playerY + playerHitbox[playerDirection] == (int) npc.getPositionY() + npcHitbox[npcDirection]
                            &&
                        (
                            (
                                (int) playerX + playerHitbox[LEFT] <= (int) npc.getPositionX() + npcHitbox[RIGHT] &&
                                (int) playerX + playerHitbox[LEFT] >= (int) npc.getPositionX() + npcHitbox[LEFT]
                            )
                                    ||
                            (
                                (int) playerX + playerHitbox[RIGHT] >= (int) npc.getPositionX() + npcHitbox[LEFT] &&
                                (int) playerX + playerHitbox[RIGHT] <= (int) npc.getPositionX() + npcHitbox[RIGHT]
                            )
                        )
                )
                return true;
        }
        return false;
    }

    public boolean isPlayerHereY(float npcX, float npcY, int[] npcHitbox, int npcDirection) {
        playerHitbox = mapManager.getPlaying().getPlayer().getHitbox();
        Player player = mapManager.getPlaying().getPlayer();

        int playerDirection;
        if (npcDirection == UP)
            playerDirection = DOWN;
        else
            playerDirection = UP;

        if (
                (int) npcY + npcHitbox[npcDirection] == (int) player.getPositionY() + playerHitbox[playerDirection]
                        &&
                    (
                        (
                            (int) npcX + npcHitbox[LEFT] <= (int) player.getPositionX() + playerHitbox[RIGHT] &&
                            (int) npcX + npcHitbox[LEFT] >= (int) player.getPositionX() + playerHitbox[LEFT]
                        )
                                    ||
                        (
                            (int) npcX + npcHitbox[RIGHT] >= (int) player.getPositionX() + playerHitbox[LEFT] &&
                            (int) npcX + npcHitbox[RIGHT] <= (int) player.getPositionX() + playerHitbox[RIGHT]
                        )
                    )
            )
                return true;
        return false;
    }

    public boolean isPlayerHereX(float npcX, float npcY, int[] npcHitbox, int npcDirection) {
        playerHitbox = mapManager.getPlaying().getPlayer().getHitbox();
        Player player = mapManager.getPlaying().getPlayer();

        int playerDirection;
        if (npcDirection == LEFT)
            playerDirection = RIGHT;
        else
            playerDirection = LEFT;

        if (
                (int) npcX + npcHitbox[npcDirection] == (int) player.getPositionX() + playerHitbox[playerDirection]
                        &&
                    (
                        (
                            (int) npcY + npcHitbox[LEFT] <= (int) player.getPositionY() + playerHitbox[RIGHT] &&
                            (int) npcY + npcHitbox[LEFT] >= (int) player.getPositionY() + playerHitbox[LEFT]
                        )
                                ||
                        (
                            (int) npcY + npcHitbox[RIGHT] >= (int) player.getPositionY() + playerHitbox[LEFT] &&
                            (int) npcY + npcHitbox[RIGHT] <= (int) player.getPositionY() + playerHitbox[RIGHT]
                        )
                    )
            )
            return true;
        return false;
    }

    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }
}