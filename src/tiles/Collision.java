package tiles;


import maps.MapManager;

public class Collision {

    private MapManager mapManager;

    public boolean isTileSolid(int y, int x) {
        return mapManager.getMapHitbox()[y][x] == 1;
    }

    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }
}