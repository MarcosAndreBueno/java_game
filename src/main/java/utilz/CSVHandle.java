package utilz;

import org.apache.commons.csv.*;

import java.io.*;

import static main.GameWindow.ScreenSettings.BaseTileSize;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Entities.*;
import static utilz.Constants.Maps.*;

public class CSVHandle {

    Exception exception = new Exception();

    public int[][] getMapHitbox(String mapCSV, int mapWidth, int mapHeight) {
        CSVParser csvParser = loadCSV("src/main/resources/" + mapCSV + ".csv");

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

    public String[][] getNPCInformation(String mapCSV) {

        CSVParser csvParser = loadCSV("src/main/resources/" + mapCSV + "_npcs.csv");

        CSVRecord csvRecord = getMapInfo(mapCSV);
        int rows = Integer.parseInt(csvRecord.get(0));
        int columns = Integer.parseInt(csvRecord.get(1));

        int i = 0;
        String[][] npcInfo = new String[rows][columns];
        for (CSVRecord rec : csvParser) {
            for (int k = 0; k < columns ; k++)
                npcInfo[i][k] = rec.get(k);
            i += 1;
        }

        closeCSV(csvParser);

        return npcInfo;
    }

    public CSVRecord getMapInfo(String mapCSV) {
        CSVParser csvParser = loadCSV("src/main/resources/" + mapCSV + "_info.csv");
        for (CSVRecord rec : csvParser) {
            if (rec.get(2).equals(mapCSV)) {
                closeCSV(csvParser);
                return rec;
            }
        }
        return null;
    }

    public void csvModifyValues() {
    }

    //to prepare csv that will be used at runtime
    public void csvWriter() throws IOException {

        //create npc information
//        File csvFile = new File("src/main/resources/maps/school_outside_npcs.csv");
//        String[][] npcInfo = npcData();

        //create map information
        File csvFile = new File("src/main/resources/maps/school_outside_info.csv");
        String[][] mapInfo = mapData();

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(csvFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (String[] array : mapInfo) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                String aux = array[i];
                line.append(aux);
                if (i != array.length - 1) {
                    line.append(',');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();
    }

    //all maps informations
    private String[][] mapData() {
        //npc data rows | npc data columns | map name
        return new String[][]{
                {"5,11," + SCHOOL_OUTSIDE}
        };
    }

    public String[][] npcData() {
        return new String[][]{
        //ID | position: x,y | hitbox: left,down,up,right | direction | can move | name | sprite
            {"0," + "-50,620,"  + "8.6,1.05,3.5,1," + "1," + "0," + "Tony,"     + NPC_01},
            {"1," + "250,700,"  + "8.6,1.05,3.5,1," + "1," + "0," + "Klay,"     + NPC_02},
            {"2," + "100,490,"  + "8.6,1.05,3.5,1," + "1," + "0," + "John,"     + NPC_03},
            {"3," + "-200,510," + "8.6,1.05,3.5,1," + "1," + "1," + "Samantha," + NPC_04},
            {"4," + "150,560,"  + "8.6,1.05,3.5,1," + "1," + "1," + "Jay,"      + NPC_05}
        };
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
}
