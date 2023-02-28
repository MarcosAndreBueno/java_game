package entities.enemies;

import entities.EnemyEntity;
import entities.Entity;
import entities.Player;
import game_states.Playing;
import utilz.Constants;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.NpcAndEnemiesCsv.*;

public class Ogre extends EnemyEntity {

    public Ogre(int npcID, String[][] npcInfo, Playing playing) {
        super(npcID, npcInfo, playing);
        initialize();
    }

    private void initialize() {
        loadAnimations();
        setHitbox(aniHeight/4f, aniWidth/4f, aniHeight, aniWidth/1.33f);
        setEntityInitialCenter();
        pushBack = (int) (64 * Scale);
        aniIndexI = Integer.parseInt(npcInfo[npcID][DIRECTION]);
        maxHP = Integer.parseInt(npcInfo[npcID][MAX_HP]);
        hp = maxHP;
        entityName = npcInfo[npcID][NAME];
        sightRange = (int) (64 * Scale);
        attackRange = (int) (aniHeight / 1.23f);
        aniAttackSpeed = 16;
        attackCooldown = 1500;
        attack1Damage = 15;
        attack1PushBack = 10 * BaseTileSize;
        hitDamage = 10;
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
        //NPC movement
        if (npcInfo[npcID][CAN_MOVE].equals("1") && !following)
            randomMovement();

        //change image after few frames
        updateAnimationTick();

        //updates NPC position when game screen moves
        updateEnemyCenter();

        //updates NPC position after it moves and checks for collisions
        checkCollision();

        //check enemy action based on distance from enemy to player
        checkEnemyAction();

        if (aniAction == ATTACKING_01)
            if (canPerformAttack) {
                canPerformAttack = false;
                performAttack();
            }
    }

    protected void checkEnemyAction() {
        Player player = playing.getPlayer();

        int playerX = (int) (player.getPositionX() + ScreenCenterX);
        int playerY = (int) (player.getPositionY() + ScreenCenterY);
        int ogreX = (int) (x + ScreenCenterX);
        int ogreY = (int) (y + ScreenCenterY);
        int distanceX = Math.abs(ogreX - playerX);
        int distanceY = Math.abs(ogreY - playerY);


        //if the player is in enemy sight
        if (distanceX <= sightRange && distanceY <= sightRange) {
            //attack player
            if (distanceX <= attackRange && distanceY <= attackRange) {
                if (canAttack()) {
                    resetPressedButtons();
                    setAction(ATTACKING_01);
                }
            }
            //follow player
            else {
                following = true;
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

    public void performAttack() {
        int weaponRange;
        int weaponCornerOne;
        int weaponCornerTwo;

        if (direction == LEFT || direction == RIGHT) {
            weaponRange = (int) (aniHeight / 1.15f);
            weaponCornerOne = (int) (aniHeight / 0.91f);
            weaponCornerTwo = (int) (aniHeight / 3.2f);
        } else {
            weaponRange = (int) (aniHeight / 1.23f);
            weaponCornerOne = (int) (aniWidth / 0.85f);
            weaponCornerTwo = (int) (aniWidth / 6.4f);
        }

        HashSet<Entity> enemiesHit = playing.getMapManager().getCollision().checkCollisionAttack
                (this, weaponRange, weaponCornerOne, weaponCornerTwo);

        if (!enemiesHit.isEmpty()) {
            for (Entity entity : enemiesHit) {
                entity.setHp(hitDamage);
                ((Player) entity).pushEntity(pushBack, direction);
            }
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
                    attackFrame++;
                    if (attackFrame >= ATTACKING_01_FRAMES) {
                        attackFrame = 1;
                        setAction(STANDING);
                    }
                    if (attackFrame >= ATTACKING_01_FRAMES - 1)
                        canPerformAttack = true;
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
    public void setHp(int value) {
        this.hp += value;
        if (this.hp > maxHP)
            this.hp = maxHP;
        else if (this.hp < 0)
            this.hp = 0;
    }

    @Override
    public void draw(Graphics2D g2) {
        //animations
        if (aniAction == ATTACKING_01)
            g2.drawImage(animations[aniAction+direction][attackFrame],
                    (int) (entityCenterX-aniWidth), (int) (entityCenterY-aniHeight),
                    aniWidth*3, aniHeight*3, null);
        else
            g2.drawImage(animations[direction][aniFrame], (int)entityCenterX,(int)entityCenterY,
                    aniWidth, aniHeight, null);

        //hitbox
        g2.drawRect((int) (entityCenterX+hitbox[LEFT]), (int) (entityCenterY+hitbox[UP]),
                (int) (hitbox[RIGHT]-hitbox[LEFT]), (int) (hitbox[DOWN]-hitbox[UP]));

        //HP
        int w1 = (int) (30*Scale);
        g2.setColor(Color.BLACK);
        g2.fillRect((int)(entityCenterX-Scale),(int)(entityCenterY-Scale*1.5f),w1,(int) (2*Scale));
        g2.setColor(Color.RED);
        int w2 = (hp * 100 / maxHP);
        g2.fillRect((int)(entityCenterX-Scale),(int)(entityCenterY-Scale*1.5f),w1 * w2 / 100, (int) (2*Scale));
    }
}
