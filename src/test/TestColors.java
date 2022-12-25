package test;

import entities.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TestColors {
    public static int[][] countOne;
    public void getColors() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas("test/school_outside.png");
        //LEFT COLLISION COLOR: #FF0099 / 255x0x153
        //RIGHT COLLISION COLOR: #07ED0E / 7x237x14
        //UP COLLISION COLOR: #461003 / 70x16x3
        //DOWN COLLISION COLOR: #0715ED / 7x21x237

        BufferedImage colorImg1 = LoadSaveImage.GetSpriteAtlas("test/pink color.png");
        BufferedImage colorImg2 = LoadSaveImage.GetSpriteAtlas("test/blue color.png");
        Color colorTrackX = new Color(colorImg1.getRGB(0,0));
        Color colorTrackY = new Color(colorImg2.getRGB(0,0));

        int count1 = 0;
        int count2 = 0;

        countOne = new int[img.getHeight()][img.getWidth()];

        for (int i = 0; i < img.getHeight(); i++)
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));

                if (color.equals(colorTrackX)) {
                    count1 += 1;
                    countOne[i][j] = 1;
                } else if (color.equals(colorTrackY)) {
                    count1 += 1;
                    countOne[i][j] = 2;
                }

                if (color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 153)
                    count2 += 1;
                else if (color.getRed() == 7 && color.getGreen() == 21 && color.getBlue() == 237)
                    count2 += 1;

            }

        System.out.println("Quantidade de cores");
        System.out.println(count1);
        System.out.println(count2);
    }
}
