package test;

import entities.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class TestColors {
    public static int[][] countOne;
    public ArrayList<Integer> one = new ArrayList<>();

    public void getColors() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(LoadSaveImage.TEST_SHORT_IMAGE);
        //LEFT COLLISION COLOR: #FF0099 / 255x0x153
        //RIGHT COLLISION COLOR: #07ED0E / 7x237x14
        //UP COLLISION COLOR: #461003 / 70x16x3
        //DOWN COLLISION COLOR: #0715ED / 7x21x237

        BufferedImage colorImg1 = LoadSaveImage.GetSpriteAtlas("test/pink color.png");
        Color colorTrackX = new Color(colorImg1.getRGB(0, 0));

        int count1 = 0;
        int count2 = 0;

        boolean solid = false;

        countOne = new int[img.getHeight() / 16][img.getWidth() / 16];

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {

                if (i == 0 && j == 48)
                    System.out.println("cheguemo");

                //if tile is already identified as solid, jump to the next tile
                if (countOne[i / 16][j / 16] == 1) {
                    j = ((j + 16) / 16) * 16;
                }
                else {
                    Color color = new Color(img.getRGB(j, i));

                    //compare pixel color by imported image
                    if (color.equals(colorTrackX) && countOne[i / 16][j / 16] != 1) {
                        count1 += 1;
                        countOne[i / 16][j / 16] = 1;
                        j = ((j + 16) / 16) * 16;
                    }

                    //compare pixel color by color number
                    if (color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 153) {
                        count2 += 1;
                    }
                }
            }
        }

        System.out.println("Quantidade de cores");
        System.out.println(count1);
        System.out.println(count2);

        System.out.println(Arrays.deepToString(countOne));


        System.out.println();
        for (int i = 0; i < img.getHeight()/16; i++) {
            for (int j = 0; j < img.getWidth()/16; j++) {
                if (countOne[i][j] == 1)
                    System.out.printf("y: %s (%2s) | x: %s (%2s) ... ", i,i*16, j, j*16);
            }
        }

        System.out.println();
        System.out.println("======================/=====================");
    }
}