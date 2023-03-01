package maps;

import utilz.LoadSaveImage;
import game_states.Playing;
import utilz.CSVHandle;
import tiles.Collision;

import java.awt.image.BufferedImage;

public abstract class MapManager {
    protected Playing playing;
    protected BufferedImage mapSprite;
    protected int mapMaxWidth;
    protected int mapMaxHeight;
    protected int [][] mapHitbox;
    private CSVHandle csvHandle;
    private String mapName;
    private Collision collision;

    public MapManager(Playing playing, String mapName) {
        this.playing = playing;
        this.mapName = mapName;
        this.csvHandle = new CSVHandle();
        this.collision = new Collision(this);
        initialize();
    }

    public void initialize() {
        mapSprite = LoadSaveImage.GetSpriteAtlas(mapName);
        mapMaxWidth = mapSprite.getWidth();
        mapMaxHeight = mapSprite.getHeight();
        System.out.println(mapName);
        System.out.println(mapSprite.getWidth() + " " + mapSprite.getHeight());
        System.out.println(mapSprite.getWidth()/16 + " " + mapSprite.getHeight()/16);
        collision.setMapManager(this);
        mapHitbox = csvHandle.getMapHitbox(mapName, mapMaxWidth, mapMaxHeight);
    }

    public int getMapMaxWidth() {
        return mapMaxWidth;
    }

    public int getMapMaxHeight() {
        return mapMaxHeight;
    }

    public int[][] getMapHitbox() {
        return mapHitbox;
    }

    public Collision getCollision() {
        return collision;
    }

    public Playing getPlaying() {
        return playing;
    }


}
