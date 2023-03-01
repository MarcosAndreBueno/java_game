package main;

import utilz.CSVHandle;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Game game = new Game();

        //create csv from map image before game run
        CSVHandle csvHandle = new CSVHandle();
        csvHandle.findSolids();
        try {
            csvHandle.csvWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}