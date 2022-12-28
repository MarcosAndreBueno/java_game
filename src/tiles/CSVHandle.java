package tiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.duke.*;
import org.apache.commons.csv.*;

import static main.GameWindow.ScreenSettings.BaseTileSize;

public class CSVHandle {

    public static int [][] mapInfo;

    public void createMapInfoFromCSV(String mapCSV, int mapHeight, int mapWidth) {
        FileResource fr = new FileResource("src/" + mapCSV);
        int i = 0;
        mapInfo = new int[(mapHeight/BaseTileSize)][mapWidth/BaseTileSize];
        System.out.println((mapHeight/BaseTileSize) + " " + (mapWidth/BaseTileSize));
        for (CSVRecord rec : fr.getCSVParser(false)) {
            for (int k = 0; k < mapWidth/BaseTileSize; k++)
                mapInfo[i][k] = Integer.parseInt(rec.get(k));
            i += 1;
        }
    }

    public void csvModifyValues() {
    }
}
