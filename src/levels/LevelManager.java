package levels;

import entities.LoadSaveImage;
import entities.Player;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import static main.GameWindow.ScreenSettings.ScreenHeight;
import static main.GameWindow.ScreenSettings.ScreenWidth;

public class LevelManager {
    private Game game;
    private BufferedImage levelSprite;
    private int levelMaxWidth;
    private int levelMaxHeight;
    private float playerX;
    private float playerY;

    public LevelManager(Game game) {
        this.game = game;
        initialize();
    }

    public void initialize() {
        this.levelSprite = LoadSaveImage.GetSpriteAtlas(LoadSaveImage.SCHOOL_OUTSIDE);
        this.levelMaxWidth = levelSprite.getWidth();
        this.levelMaxHeight = levelSprite.getHeight();
    }

    public int getLevelMaxWidth() {
        return levelMaxWidth;
    }

    public int getLevelMaxHeight() {
        return levelMaxHeight;
    }

    public void update() {
        Player pl = game.getPlayer();
        playerX = pl.getPositionX();
        playerY = pl.getPositionY();
        if (playerX + ScreenWidth >= levelSprite.getWidth())
            playerX = levelSprite.getWidth() - ScreenWidth;
        else if (playerX <= 0)
            playerX = 0;
        if (playerY + ScreenHeight >= levelSprite.getHeight())
            playerY = levelSprite.getHeight() - ScreenHeight;
        else if (playerY <= 0)
            playerY = 0;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(levelSprite.getSubimage((int)playerX, (int)playerY, ScreenWidth, ScreenHeight),0,0,null);
    }
}
