package entities;

import game_states.Playing;
import utilz.Constants.Entities;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.STANDING;
import static utilz.Constants.PlayerConstants.WALKING;

public class NPC_Test extends Entity implements GameEntity{

    protected int aniTick, aniIndexI;
    private float oldX = playing.getPlayer().getPositionX();
    private float oldY = playing.getPlayer().getPositionY();
    int count = 0;
    int pressedButton = -1;
    float npcCenterX, npcCenterY;
    float px, py;

    public NPC_Test(float x, float y, Playing playing) {
        super(x, y, playing);
        initialize();
    }

    public void initialize() {
        loadAnimations();
        setHitbox(0, aniHeight, aniHeight / 2, aniWidth);
        setEntityInitialCenter();
        aniIndexI = 0;
    }

    @Override
    public void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(Entities.PLAYER_ATLAS);
        animations = new BufferedImage[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                animations[i][j] = img.getSubimage(j*16,i*32,16,32);
            }
        }
    }

    @Override
    public void setEntityInitialCenter() {
        x -= 450;
        y += 100;
        npcCenterX = x + 450;
        npcCenterY = y - 100;
    }

    @Override
    public void update() {
        px = playing.getPlayer().getPositionX();
        py = playing.getPlayer().getPositionY();

        //random NPC movement
        Random random = new Random();
        int number = random.nextInt(4);
        if (count >= 120*2) {
            switch (number) {
                case 0 -> { pressedButton = LEFT; aniDirection = LEFT; aniAction = WALKING; }
                case 1 -> { pressedButton = DOWN; aniDirection = DOWN; aniAction = WALKING; }
                case 2 -> { pressedButton = UP; aniDirection = UP; aniAction = WALKING; }
                case 3 -> { pressedButton = RIGHT; aniDirection = RIGHT; aniAction = WALKING; }
            }
//            count = 0;
        }

        updateAnimationTick();

        //updates NPC position when game screen moves
        if (px > 0 && px + ScreenWidth < playing.getMapManager().getMapMaxWidth())
            npcCenterX = npcCenterX - (px - oldX);
        oldX = px;
        if (py > 0 && py + ScreenHeight < playing.getMapManager().getMapMaxHeight())
            npcCenterY = npcCenterY - (py - oldY);
        oldY = py;

        //updates NPC position after it moves and checks for collisions
        if (pressedButton > -1)
            switch (pressedButton) {
                case 0 -> { x += -entitySpeed; npcCenterX += -entitySpeed; checkCollisionLeft(); }
                case 1 -> { y += entitySpeed; npcCenterY += entitySpeed; checkCollisionDown(); }
                case 2 -> { y += -entitySpeed; npcCenterY += -entitySpeed; checkCollisionUp(); }
                case 3 -> { x += entitySpeed; npcCenterX += entitySpeed; checkCollisionRight(); }
            }

        if (count >= 120*2) {
            System.out.println(x + " " + y + " " + px + " " + py);
            System.out.println( (x - (px - oldX)) + " | " + (y - (py - oldY)));
            count = 0;
        }

        count += 1;
    }

    //change image after few frames
    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            if (aniAction == STANDING)
                aniIndexI = 0;
            else
                aniIndexI++;
            if ((aniDirection == LEFT || aniDirection == RIGHT) && aniIndexI >= WALKING)
                aniIndexI = 0;
            else if (aniIndexI > WALKING)
                aniIndexI = 1;
        }
    }

    private void checkCollisionLeft() {
        int direction = ((int) x + (int)ScreenCenterX + hitbox[LEFT]) / BaseTileSize;
        int cornerOne = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
        int cornerTwo = ((int) y + (int)ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
        if (playing.getMapManager().getCollision().isTileSolid(cornerOne, direction) ||
                playing.getMapManager().getCollision().isTileSolid(cornerTwo, direction))
            resetPositionX(-entitySpeed);
    }
    private void checkCollisionRight() {
        int direction = ((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;
        int cornerOne = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
        int cornerTwo = ((int) y + (int)ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
        if (playing.getMapManager().getCollision().isTileSolid(cornerOne, direction) ||
                playing.getMapManager().getCollision().isTileSolid(cornerTwo, direction))
            resetPositionX(entitySpeed);
    }
    private void checkCollisionUp() {
        int direction = ((int) y + (int)ScreenCenterY + hitbox[UP]) / BaseTileSize;
        int cornerOne = ((int) x + (int)ScreenCenterX + hitbox[LEFT]) / BaseTileSize;   //upleft
        int cornerTwo = ((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;  //upright
        if (playing.getMapManager().getCollision().isTileSolid(direction, cornerOne) ||
                playing.getMapManager().getCollision().isTileSolid(direction, cornerTwo))
            resetPositionY(-entitySpeed);
    }
    private void checkCollisionDown() {
        int direction = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;
        int cornerOne = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;  //downleft
        int cornerTwo = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize; //downright
        if (playing.getMapManager().getCollision().isTileSolid(direction, cornerOne) ||
                playing.getMapManager().getCollision().isTileSolid(direction, cornerTwo))
            resetPositionY(entitySpeed);
    }

    public void resetPositionX(float x) {
        this.x += x * -1;
        this.npcCenterX += x * -1;
    }

    public void resetPositionY(float y) {
        this.y += y * -1;
        this.npcCenterY += y * -1;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(animations[aniIndexI][aniDirection], (int)npcCenterX,(int)npcCenterY,
                aniWidth, aniHeight, null);
    }
}
