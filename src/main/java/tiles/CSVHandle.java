package tiles;

import org.apache.commons.csv.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static main.GameWindow.ScreenSettings.BaseTileSize;

public class CSVHandle {
    private int [][] mapHitbox;

    public int[][] getMapHitbox(String mapCSV, int mapWidth, int mapHeight) {
        CSVParser csvParser;
        BufferedReader file;

        try {
            file = new BufferedReader(new FileReader("src/main/resources/" + mapCSV + ".csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            csvParser = new CSVParser(file, CSVFormat.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int i = 0;
        mapHitbox = new int[(mapHeight / BaseTileSize)][mapWidth / BaseTileSize];
        System.out.println((mapHeight / BaseTileSize) + " " + (mapWidth / BaseTileSize));
        for (CSVRecord rec : csvParser) {
            for (int k = 0; k < mapWidth / BaseTileSize; k++)
                mapHitbox[i][k] = Integer.parseInt(rec.get(k));
            i += 1;
        }
        return mapHitbox;
    }

    public void csvModifyValues() {
    }
}
