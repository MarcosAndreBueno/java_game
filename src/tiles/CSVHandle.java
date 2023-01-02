package tiles;

import edu.duke.*;
import org.apache.commons.csv.*;

import static main.GameWindow.ScreenSettings.BaseTileSize;

public class CSVHandle {

    private int [][] mapHibox;

    public int[][] getMapHitbox(String mapCSV, int mapWidth, int mapHeight) {
        FileResource fr = new FileResource("src/" + mapCSV + ".csv");
        int i = 0;
        mapHibox = new int[(mapHeight/BaseTileSize)][mapWidth/BaseTileSize];
        System.out.println((mapHeight/BaseTileSize) + " " + (mapWidth/BaseTileSize));
        for (CSVRecord rec : fr.getCSVParser(false)) {
            for (int k = 0; k < mapWidth/BaseTileSize; k++)
                mapHibox[i][k] = Integer.parseInt(rec.get(k));
            i += 1;
        }
        return mapHibox;
    }

    public void csvModifyValues() {
    }
}
