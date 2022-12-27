package tiles;

import entities.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.BaseTileSize;

public class Tiles {
    public static int[][] countOne;

    public void findSolids() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(LoadSaveImage.TEST_SHORT_IMAGE);

        countOne = new int[img.getHeight() / BaseTileSize][img.getWidth() / BaseTileSize];

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {

                //if tile is already identified as solid, jump to the next tile
                if (countOne[i / BaseTileSize][j / BaseTileSize] == 1) {
                    j = ((j + BaseTileSize) / BaseTileSize) * BaseTileSize;
                }
                else {
                    Color color = new Color(img.getRGB(j, i));

                    //compare pixel color by color number
                    if (color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 153) {
                        countOne[i / BaseTileSize][j / BaseTileSize] = 1;
                        j = ((j + BaseTileSize) / BaseTileSize) * BaseTileSize;
                    }
                }
            }
        }
    }
}