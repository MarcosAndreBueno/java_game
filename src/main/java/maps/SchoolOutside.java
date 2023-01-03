package maps;

import entities.Player;
import game_states.Playing;
import main.Game;
import utilz.Constants.Maps;

import java.awt.*;

import static main.GameWindow.ScreenSettings.ScreenHeight;
import static main.GameWindow.ScreenSettings.ScreenWidth;

public class SchoolOutside extends MapManager{
    private float playerX;
    private float playerY;

    public SchoolOutside(Playing playing) {
        super(playing, Maps.SCHOOL_OUTSIDE);
    }

    public void update() {
        playerX = playing.getPlayer().getPositionX();
        playerY = playing.getPlayer().getPositionY();
        //freezes the camera if the screen hits the edge of the map
        if (playerX + ScreenWidth >= mapMaxWidth)
            playerX = mapMaxWidth - ScreenWidth;
        else if (playerX <= 0)
            playerX = 0;
        if (playerY + ScreenHeight >= mapMaxHeight)
            playerY = mapMaxHeight - ScreenHeight;
        else if (playerY <= 0)
            playerY = 0;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(mapSprite.getSubimage((int)playerX, (int)playerY, ScreenWidth, ScreenHeight),
                0,0,ScreenWidth, ScreenHeight,null);
    }
}
