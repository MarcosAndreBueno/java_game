package tiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.duke.*;
import org.apache.commons.csv.*;


import static tiles.Tiles.imgInfo;

public class CSVHandle {
    Tiles tiles;
    public static int [][] mapInfo;

    public void csvModifyValues() {
    }

    public void createMapInfoFromCSV() {
        FileResource fr = new FileResource("school_outside.csv");
        int count = 1;
        int i = 0;
        int j = 0;
        int maxI = 99;
        int maxJ = 69;
        mapInfo = new int[maxI][maxJ];

        for (CSVRecord rec : fr.getCSVParser(false)) {
            mapInfo[i][j] = Integer.parseInt(rec.get(j));
            j += 1;
            if (j == maxJ-1) {
                i += 1;
                j = 0;
            }
            if (i == maxI)
                break;
        }

        for (CSVRecord rec : fr.getCSVParser(false)) {
            System.out.println(rec);
        }
    }

    public CSVHandle(Tiles tiles) {
        this.tiles = tiles;
    }

    String[][] employees = {
            {"Jordan", "God", "jordan.com", "Killer"},
            {"Curry", "Legend", "curry.com", "Sniper"},
            {"Lebron", "MVP", "lebron.com", "DreamDestroyer"},
            {"Zelda", "Dwarf", "zelda.com", "Guard"},
            {"Ramza", "Wold", "ramza.com", "Billionaire"}
    };

    //to prepare csv that will be used at runtime
    public void csvWriter(Tiles tiles) throws IOException {

        File csvFile = new File("school_outside.csv");
        FileWriter fileWriter = new FileWriter(csvFile);

        for (int[] data : imgInfo) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                String x = Integer.toString(data[i]);
                line.append(x);
                if (i != data.length - 1) {
                    line.append(',');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();
    }

    public void csvReader() {
        /*
         *   [ true, false, true, false, false
         *     false, false, true, false, false]
         *
         *  { i= 0 , j= 4 } = false
         *    i = rec.get(0)
         *    j = count
         *  */

        FileResource fr = new FileResource("school_outside.csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            System.out.println(rec);
        }

        System.out.println();
        long startNan = System.nanoTime();
        int count = 1;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (count == 98)
                System.out.println("answer first method: " + rec.get(1));
            count += 1;
        }
        long elapsedTimeNan = System.nanoTime() - startNan;
        System.out.println("time to get from csv: " + elapsedTimeNan + "nan");

        System.out.println();
        long startNan2 = System.nanoTime();
        System.out.println("answer second method: " + imgInfo[97][1]);
        long elapsedTimeNan2 = System.nanoTime() - startNan2;
        System.out.println("time to get from array: " + elapsedTimeNan2 + "nan");

        System.out.println();
        System.out.println("first method time expended " + (elapsedTimeNan / 1000000));
        System.out.println("second method time expended " + (elapsedTimeNan2 / 1000000));
    }
}
