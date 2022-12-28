package tiles;
import static tiles.CSVHandle.mapInfo;

public class Collision {
    public static boolean isTileSolid(int y, int x) {
        return mapInfo[y][x] == 1;
    }
}