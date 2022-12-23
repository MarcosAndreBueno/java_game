package test;

import entities.LoadSaveImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
TestOne method takes two steps to draw images:
    first it uses an image that serves as a sketch for the drawing (level_one_data).
    second it uses an image that makes a better drawing from the sketch (outside_sprites_png).

Divide outside_sprites in 48 parts of 32x32, and saves to an array.
Then get the value of red in each pixel from level_one_data and use that value as an index.
Finally, draw each tile using the array extracted from outside_sprites and index extracted from level_one_data
*/

public class PaintTest extends JPanel {

    public PaintTest() {
        this.setPreferredSize(new Dimension(1040, 560));
        JFrame jFrame = new JFrame();
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(this);
        jFrame.setResizable(false);
        jFrame.pack(); //apply screen options to window
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    public void paintTestOne(Graphics2D g2) {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas("test/level_one_data.png");

        for (int i = 0; i < img.getHeight(); i++)
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                System.out.println("| i " + i + " j " + j + " | " + color.toString());
            }

        BufferedImage img2 = LoadSaveImage.GetSpriteAtlas("test/outside_sprites.png");
        BufferedImage[] imgs = new BufferedImage[48];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 12; j++) {
                int index = i*12+j;
                imgs[index] = img2.getSubimage(j*32,i*32,32,32);
            }

        int distanceY = 0;
        for (int i = 0; i < 4; i++) {
            distanceY += 2;
            int distanceX = 0;
            for (int j = 0; j < 12; j++) {
                g2.drawImage(imgs[i*12+j], j*32*2+distanceX, i*32*2+distanceY, 32*2, 32*2, null);
                distanceX += 2;
            }
        }
    }

    //drawn my own level image
    public void paintTestTwo(Graphics2D g2) {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(LoadSaveImage.SCHOOL_OUTSIDE);
        g2.drawImage(img,0,0,null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
//        paintTestOne(g2);
        paintTestTwo(g2);
    }
}
