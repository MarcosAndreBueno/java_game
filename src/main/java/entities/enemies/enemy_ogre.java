package entities.enemies;

import entities.EnemyEntity;
import game_states.Playing;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.Scale;
import static utilz.Constants.NpcCsv.*;
import static utilz.Constants.PlayerConstants.STANDING;
import static utilz.Constants.PlayerConstants.WALKING;

public class enemy_ogre extends EnemyEntity {

    protected int maxHP, hp;

    public enemy_ogre(int npcID, String[][] npcInfo, Playing playing) {
        super(npcID, npcInfo, playing);
        initialize();
    }

    private void initialize() {
        loadAnimations();
        setHitbox(aniHeight/6f, aniWidth/4.5f, aniHeight/1.3f, aniWidth/1.3f);
        setEntityInitialCenter();
        aniIndexI = Integer.parseInt(npcInfo[npcID][DIRECTION]);
        maxHP = Integer.parseInt(npcInfo[npcID][MAX_HP]);
        hp = maxHP;
        entityName = npcInfo[npcID][NAME];
    }

    public void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(sprite);
        animations = new BufferedImage[8][9];
        int tempI = 8;
        //walk movement
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                animations[i][j] = img.getSubimage(j * 64, tempI * 64, 64, 64);
            }
            tempI += 1;
        }
        //attack animations
        tempI = 21;
        int tempJ = 0;
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                animations[i][j] = img.getSubimage(tempJ * 64, tempI * 64,64*3, 64*3);
                tempJ += 3;
            }
            tempI += 3;
            tempJ = 0;
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
                    aniDirection = direction;
                    break;
                case WALKING:
                    aniFrame++;
                    aniDirection = direction;
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
        g2.drawImage(animations[aniDirection][aniFrame], (int)npcCenterX,(int)npcCenterY,
                aniWidth, aniHeight, null);

        //HP
        int w1 = (int) (30*Scale);
        g2.setColor(Color.BLACK);
        g2.fillRect((int)(npcCenterX-Scale*1.5f),(int)(npcCenterY-Scale*1.5f),w1,(int) (2*Scale));
        g2.setColor(Color.RED);
        int w2 = (hp * 100 / maxHP);
        g2.fillRect((int)(npcCenterX-Scale*1.5f),(int)(npcCenterY-Scale*1.5f),w1 * w2 / 100, (int) (2*Scale));
    }

    public float getNpcCenterX() {
        return npcCenterX;
    }

    public float getNpcCenterY() {
        return npcCenterY;
    }
}
