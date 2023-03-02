package utilz;

import java.awt.*;

import static main.GameWindow.ScreenSettings.TileSize;

public class Draw {

    public static void DrawTextWithShadow(Graphics2D g2, Color textColor, int x, int y,
                                          Font font, String text) {
        g2.setFont(font);
        g2.setColor(textColor);
        g2.drawString(text,x, y);
        g2.setColor(new Color(textColor.getRed(), textColor.getGreen(),
                textColor.getBlue(), textColor.getAlpha()-170));
        g2.drawString(text,x + 5, y + 5);
    }
}
