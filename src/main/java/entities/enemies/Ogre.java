package entities.enemies;

import entities.EnemyEntity;
import entities.Entity;
import entities.Player;
import game_states.Playing;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EntityStatus.*;
import static utilz.Constants.NpcAndEnemiesCsv.*;
import static utilz.Constants.PlayerConstants.*;

public class Ogre extends EnemyEntity {

    protected int maxHP, hp;
    protected float pushBack;
    protected int sight;
    protected boolean following, canPerformAttack = true;
    protected HashSet<Entity> enemiesHit = new HashSet<>();

    public Ogre(int npcID, String[][] npcInfo, Playing playing) {
        super(npcID, npcInfo, playing);
        initialize();
    }

    private void initialize() {
        loadAnimations();
        setHitbox(0, aniWidth/4.5f, aniHeight/1.3f, aniWidth/1.3f);
        setEntityInitialCenter();
        pushBack = 64 * Scale;
        aniIndexI = Integer.parseInt(npcInfo[npcID][DIRECTION]);
        maxHP = Integer.parseInt(npcInfo[npcID][MAX_HP]);
        hp = maxHP;
        entityName = npcInfo[npcID][NAME];
        sight = 10;
    }

    public void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(sprite);
        animations = new BufferedImage[8][9];
        int tempI = 8;
        //walk movement
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++)
                animations[i][j] = img.getSubimage(j * 64, tempI * 64, 64, 64);
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
        if (npcInfo[npcID][CAN_MOVE].equals("1") && !following)
            randomMovement();

        //change image after few frames
        updateAnimationTick();

        //updates NPC position when game screen moves
        updatePosition();

        //updates NPC position after it moves and checks for collisions
        checkCollision();

        //updates NPC status, like attacked
        checkStatus();

        //check enemy action based on distance from enemy to player
        checkEnemyAction();

        if (aniAction == ATTACKING_01 && canPerformAttack) {
            checkCollisionAttack();
            canPerformAttack = false;
        }
    }

    private void checkEnemyAction() {
        Player player = playing.getPlayer();

        int playerX = (int) (player.getPositionX() + ScreenCenterX) / BaseTileSize;
        int playerY = (int) (player.getPositionY() + ScreenCenterY) / BaseTileSize;
        int ogreX = (int) (x + ScreenCenterX) / BaseTileSize;
        int ogreY = (int) (y + ScreenCenterY) / BaseTileSize;

        //if the player is in enemy sight
        if (Math.abs(ogreX - playerX) <= sight && Math.abs(ogreY - playerY) <= sight) {
            following = true;
            //attack player
            if (Math.abs(ogreX - playerX) < 5 && Math.abs(ogreY - playerY) < 5) {
                setAction(ATTACKING_01);
                resetPressedButtons();
            }
            //follow player
            else {
                setAction(WALKING);
                if (playerX < ogreX) {
                    setPressedButton(LEFT); setDirection(LEFT);  }
                if (playerX > ogreX) {
                    setPressedButton(RIGHT); setDirection(RIGHT); }
                if (playerY < ogreY) {
                    setPressedButton(UP); setDirection(UP); }
                if (playerY > ogreY) {
                    setPressedButton(DOWN); setDirection(DOWN); }
                }
        }
        //if the player isn't in enemy sight
        else {
            following = false;
        }
    }

    //checks if enemy suffered damage
    private void checkStatus() {
        if (entityStatus != -1) {
            switch (entityStatus) {
                case ATTACKED_UP -> {
                    y += -entitySpeed * pushBack;
                    npcCenterY += -entitySpeed * pushBack;
                    if (checkIfOutOfMap(UP, x, y - BaseTileSize)) {
                        resetPositionY(-entitySpeed * pushBack);
                    }
                }
                case ATTACKED_LEFT -> {
                    x += -entitySpeed * pushBack;
                    npcCenterX += -entitySpeed * pushBack;
                    if (checkIfOutOfMap(LEFT, x - BaseTileSize, y)) {
                        resetPositionX(-entitySpeed * pushBack);
                    }
                }
                case ATTACKED_DOWN -> {
                    y += entitySpeed * pushBack;
                    npcCenterY += entitySpeed * pushBack;
                    if (checkIfOutOfMap(DOWN, x, y + BaseTileSize)) {
                        resetPositionY(entitySpeed * pushBack);
                    }
                }
                case ATTACKED_RIGHT -> {
                    x += entitySpeed * pushBack;
                    npcCenterX += entitySpeed * pushBack;
                    if (checkIfOutOfMap(RIGHT, x + BaseTileSize, y)) {
                        resetPositionX(entitySpeed * pushBack);
                    }
                }
            }
            entityStatus = -1; //reset status
        }
    }

    private void updateAnimationTick() {
        switch (aniAction) {
            case STANDING:
                aniFrame = 0;
                break;
            case WALKING:
                if (checkAniTickFrame(aniMoveSpeed)) {
                    aniFrame++;
                    if (aniFrame >= WALKING_FRAMES)
                        aniFrame = 1;
                }
                break;
            case ATTACKING_01:
                if (checkAniTickFrame(aniAttackSpeed)) {
                    aniFrame++;
                    if (aniFrame >= ATTACKING_01_FRAMES-2) {
                        aniFrame = 1;
                        setAction(STANDING);
                        canPerformAttack = true;
                    }
                }
                break;
        }
    }

    private boolean checkAniTickFrame(int aniSpeed) {
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            return true;
        }
        else
            aniTick++;

        return false;
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
        if (aniAction == ATTACKING_01)
            g2.drawImage(animations[direction+ATTACKING_01][aniFrame],
                    (int) (npcCenterX-aniWidth), (int) (npcCenterY-aniHeight),
                    aniWidth*3, aniHeight*3, null);
        else
            g2.drawImage(animations[direction][aniFrame], (int)npcCenterX,(int)npcCenterY,
                    aniWidth, aniHeight, null);

        //HP
        int w1 = (int) (30*Scale);
        g2.setColor(Color.BLACK);
        g2.fillRect((int)(npcCenterX-Scale),(int)(npcCenterY-Scale*1.5f),w1,(int) (2*Scale));
        g2.setColor(Color.RED);
        int w2 = (hp * 100 / maxHP);
        g2.fillRect((int)(npcCenterX-Scale),(int)(npcCenterY-Scale*1.5f),w1 * w2 / 100, (int) (2*Scale));
    }
}
