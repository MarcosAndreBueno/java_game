package entities.npcs;

import entities.NpcEntity;
import game_states.Playing;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.Scale;
import static utilz.Constants.NpcAndEnemiesCsv.*;
import static utilz.Constants.PlayerConstants.STANDING;
import static utilz.Constants.PlayerConstants.WALKING;

public class Chemist extends NpcEntity {

    protected int maxHP, hp;

    public Chemist(int npcID, String[][] npcInfo, Playing playing) {
        super(npcID, npcInfo, playing);
        initialize();
    }

    private void initialize() {
        loadAnimations();
        aniWidth = (int) (20*Scale);
        aniHeight= (int) (30*Scale);
        setHitbox(aniHeight/-6f, aniWidth/6f, aniHeight/1.05f, aniWidth/1.05f);
        setEntityInitialCenter();
        aniIndexI = Integer.parseInt(npcInfo[npcID][DIRECTION]);
        maxHP = Integer.parseInt(npcInfo[npcID][MAX_HP]);
        hp = maxHP;
        entityName = npcInfo[npcID][NAME];
    }

    public void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(sprite);
        animations = new BufferedImage[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                animations[i][j] = img.getSubimage(j*32,i*48,32,48);
            }
        }
    }

    @Override
    public void update() {
        px = playing.getPlayer().getPositionX();
        py = playing.getPlayer().getPositionY();

        //NPC movement
        if (npcInfo[npcID][CAN_MOVE].equals("1"))
            randomMovement();

        //change image after few frames
        updateAnimationTick();

        //updates NPC position when game screen moves
        updatePosition();

        //updates NPC position after it moves and checks for collisions
        checkCollision();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniMoveSpeed) {
            aniTick = 0;
            switch (aniAction) {
                case STANDING:
                    aniFrame = 0;
                    break;
                case WALKING:
                    aniFrame++;
                    if (aniFrame >= Integer.parseInt(npcInfo[npcID][ANI_FRAME]))
                        aniFrame = 1;
                    break;
            }
        }
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp += hp;
        if (this.hp > maxHP)
            this.hp = maxHP;
        else if (this.hp < 0)
            this.hp = 0;
    }

    @Override
    public void draw(Graphics2D g2) {
        //animations
        g2.drawImage(animations[direction][aniFrame], (int)entityCenterX,(int)entityCenterY,
                aniWidth, aniHeight, null);

        //HP
        int w1 = (int) (25*Scale);
        g2.setColor(Color.BLACK);
        g2.fillRect((int)(entityCenterX-Scale*2),(int)(entityCenterY-Scale*5),w1,(int) (2*Scale));
        g2.setColor(Color.RED);
        int w2 = (hp * 100 / maxHP);
        g2.fillRect((int)(entityCenterX-Scale*2),(int)(entityCenterY-Scale*5),w1 * w2 / 100, (int) (2*Scale));
    }
}
