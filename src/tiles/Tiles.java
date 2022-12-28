package tiles;

import entities.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.BaseTileSize;

/**
 * This method gets map information that will be saved in a csv file.
 * It will not be used while running the game,
 * instead we gonna use CSVHandle to write and get the map information.
 * */

public class Tiles {
    public static int[][] imgInfo;

    public void findSolids() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(LoadSaveImage.TEST_SHORT_IMAGE);

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

    public static int[][] getCountOne() {
        return imgInfo;
    }

}