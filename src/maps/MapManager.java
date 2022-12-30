package maps;

import entities.LoadSaveImage;
import entities.Player;
import main.Game;
import tiles.CSVHandle;
import utilz.Constants.Maps;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.ScreenHeight;
import static main.GameWindow.ScreenSettings.ScreenWidth;

public class MapManager {
    private Game game;
    private BufferedImage mapSprite;
    private int mapMaxWidth;
    private int mapMaxHeight;
    private float playerX;
    private float playerY;
    private CSVHandle csvHandle = new CSVHandle();

    public MapManager(Game game) {
        this.game = game;
        initialize();
    }

    public void initialize() {
        this.mapSprite = LoadSaveImage.GetSpriteAtlas(Maps.SCHOOL_OUTSIDE);
        this.mapMaxWidth = mapSprite.getWidth();
        this.mapMaxHeight = mapSprite.getHeight();
        csvHandle.createMapInfoFromCSV(Maps.SCHOOL_OUTSIDE_INFO, Maps.SCHOOL_OUTSIDE_HEIGHT, Maps.SCHOOL_OUTSIDE_WIDTH);
    }

    public int getMapMaxWidth() {
        return mapMaxWidth;
    }

    public int getMapMaxHeight() {
        return mapMaxHeight;
    }

    public void update() {
        Player pl = game.getPlayer();
        playerX = pl.getPositionX();
        playerY = pl.getPositionY();
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
