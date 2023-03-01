package utilz;

import org.apache.commons.csv.*;

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

    Exception exception = new Exception();

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

        String[][] mapInfo =
        {
            {"0,1,40,240,1,1,enemy_Ogre,4,50,characters/enemy_ogre"},
            {"1,1,80,110,1,1,npc_Chemist,4,50,characters/npc_01_chemist"}
        };

        BufferedWriter writer = Files.newBufferedWriter(Paths.get
                ("src/main/resources/maps/testMap_npcs.csv"));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("ID,OnMap,PositionX,PositionY,Direction,CanMove," +
                        "Name,WalkingFrames,MaxHp,SpriteAddress"));

        System.out.println(Arrays.deepToString(mapInfo));

        for (String[] array : mapInfo) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                String aux = array[i];
                line.append(aux);
                csvPrinter.printRecord(aux);
                if (i != array.length-1) {
                    line.append(',');
                }
            }
        }
        csvPrinter.flush();
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
}
