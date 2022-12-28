package tiles;

import java.io.IOException;

public class test {
    public static void main(String[] args) {
        Tiles tiles = new Tiles();

        long startNan = System.nanoTime();
        tiles.findSolids();
        long elapsedTimeNan = System.nanoTime() - startNan;
        System.out.println("time to prepare array from image: " + elapsedTimeNan + "nan");

        long startNan2 = System.nanoTime();
        CSVHandle csvHandle = new CSVHandle(tiles);
        try {
            csvHandle.csvWriter(tiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long elapsedTimeNan2 = System.nanoTime() - startNan2;
        System.out.println("time to prepare array from excel: " + elapsedTimeNan2 + "nan");

        System.out.println("first method time expended " + (elapsedTimeNan / 1000000));
        System.out.println("second method time expended " + (elapsedTimeNan2 / 1000000));
        System.out.println();

//        csvHandle.csvReader();

        System.out.println();
        csvHandle.createMapInfoFromCSV();
    }
}
