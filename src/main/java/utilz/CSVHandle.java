package utilz;

import org.apache.commons.csv.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import static main.GameWindow.ScreenSettings.BaseTileSize;
import static utilz.Constants.Entities.*;
import static utilz.Constants.Maps.*;


public class CSVHandle {

    public int[][] imgInfo;

    public int[][] getMapHitbox(String mapCSV, int mapWidth, int mapHeight) {
        CSVParser csvParser = loadCSV("src/main/resources/" + mapCSV + "_collision.csv");

        int i = 0;
        int[][] mapHitbox = new int[(mapHeight / BaseTileSize)][mapWidth / BaseTileSize];
        for (CSVRecord rec : csvParser) {
            for (int k = 0; k < mapWidth / BaseTileSize; k++)
                mapHitbox[i][k] = Integer.parseInt(rec.get(k));
            i += 1;
        }

        closeCSV(csvParser);
        
        return mapHitbox;
    }

    public String[][] getNPCInformation(String mapCSV) throws IOException {

        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/" + mapCSV + "_npcs.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());

        //amount of npc's in the map
        int rows = csvParser.getRecords().size();
        //amount of npc information
        int columns = csvParser.getHeaderMap().size();

        reader = Files.newBufferedReader(Paths.get("src/main/resources/" + mapCSV + "_npcs.csv"));
        csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());

        SortedMap<String,Integer> headerMap = (SortedMap<String, Integer>) csvParser.getHeaderMap();
        int i = 0;
        String[][] npcInfo = new String[rows][columns];
        for (CSVRecord rec : csvParser) {
            for (Map.Entry<String, Integer> columnName : headerMap.entrySet()) {
                npcInfo[i][columnName.getValue()] = rec.get(columnName.getKey());
            }
            i++;
        }
        closeCSV(csvParser);
        closeReader(reader);

        return npcInfo;
    }

    public void csvModifyValues() {
    }

    //to prepare csv that will be used at runtime
    public void csvWriter() throws IOException {

        File csvFile = new File("new file.csv");
        FileWriter fileWriter = new FileWriter(csvFile);

        for (int[] array : imgInfo) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                String x = Integer.toString(array[i]);
                line.append(x);
                if (i != array.length - 1) {
                    line.append(',');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();
    }

    public CSVParser loadCSV(String mapCSV) {
        CSVParser csvParser;
        BufferedReader file;

        try {
            file = new BufferedReader(new FileReader(mapCSV));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            csvParser = new CSVParser(file, CSVFormat.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return csvParser;
    }

    private void closeCSV(CSVParser csvParser) {
        try {
            csvParser.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeReader(Reader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void findSolids() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(LEVEL_ONE);

        imgInfo = new int[img.getHeight() / BaseTileSize][img.getWidth() / BaseTileSize];

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {

                //if tile is already identified as solid, jump to the next tile
                if (imgInfo[i / BaseTileSize][j / BaseTileSize] == 1) {
                    j = ((j + BaseTileSize) / BaseTileSize) * BaseTileSize;
                }
                else {
                    Color color = new Color(img.getRGB(j, i));

                    //compare pixel color by color number
                    if (color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 153) {
                        imgInfo[i / BaseTileSize][j / BaseTileSize] = 1;
                        j = ((j + BaseTileSize) / BaseTileSize) * BaseTileSize;
                    }
                }
            }
        }
    }
}
