package entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSaveImage {

    public static final String PLAYER_ATLAS = "res/characters/sample_character_02.png";
    public static final String SCHOOL_OUTSIDE = "res/levels/school_outside.png";

    public static BufferedImage GetSpriteAtlas(String filename) {
        BufferedImage img = null;
        InputStream is = LoadSaveImage.class.getResourceAsStream("/" + filename);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
