package entities;

import game_states.Playing;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.UP;

import static utilz.Constants.EntityStatusConstants.*;
import static utilz.Constants.*;
import static utilz.Constants.PlayerConstants.*;

import utilz.Constants.Entities;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class Player extends Entity implements GameEntity{

    //player movement
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    protected int aniAttackSpeed, aniDyingSpeed, aniPerishingSpeed;

    protected int maxHP, hp;
    public int attack1Damage, attack2Damage;
    public boolean canPerformAttack;
    protected float attack1PushBack, attack2PushBack;

    protected long damaged;

    public Player(float x, float y, String sprite, Playing playing) {
        super(x, y, sprite, playing);
        initialize();
    }

    public void initialize() {
        loadAnimations();
        setHitbox(aniHeight/2f, aniWidth/4f, aniHeight, aniWidth/1.33f);
        setEntityInitialCenter();
        maxHP = 50;
        hp = maxHP;
        aniAttackSpeed = 12;
        aniDyingSpeed = 70;
        aniPerishingSpeed = 150;
        attack1Damage = 10;
        attack2Damage = 20;
        entityName = "Player";
        entityStatus = NORMAL;
        attack1PushBack = 8 * BaseTileSize;
        attack2PushBack = 10 * BaseTileSize;
    }

    //when the player gets into a new map
    public void setEntityInitialCenter(){
        entityCenterX = ScreenCenterX;
        entityCenterY = ScreenCenterY;
        updatePlayerCenter();
    }

    public void update() {
        if (aniAction != PlayerConstants.DYING)
            updatePlayerInformations();
        else
            playerDying();
    }

    private void playerDying() {
        if (entityStatus != PERISHING)
            updateAnimationTick();
        else if (checkAniTickFrame(aniPerishingSpeed))
            setEntityStatus(GAME_OVER);
    }

    //update image after few frames
    private void updateAnimationTick() {
        switch (aniAction) {
            case STANDING:
                aniFrame = 0;
                break;
            case WALKING:
                if (checkAniTickFrame(aniMoveSpeed)) {
                    aniFrame++;
                    if (aniFrame > WALKING_FRAMES)
                        aniFrame = 1;
                }
                break;
            case ATTACKING_01:
                if (checkAniTickFrame(aniAttackSpeed)) {
                    aniFrame++;
                    if (aniFrame >= ATTACKING_01_FRAMES) {
                        aniFrame = 1;
                        aniAction = STANDING;
                    }
                    if (aniFrame >= ATTACKING_01_FRAMES-1)
                        canPerformAttack = true;
                }
                break;
            case ATTACKING_02:
                if (checkAniTickFrame(aniAttackSpeed)) {
                    aniFrame++;
                    if (aniFrame >= ATTACKING_02_FRAMES) {
                        aniFrame = 1;
                        aniAction = STANDING;
                    }
                    if (aniFrame >= ATTACKING_02_FRAMES-1)
                        canPerformAttack = true;
                }
                break;
            case PlayerConstants.DYING:
                if (checkAniTickFrame(aniDyingSpeed) && entityStatus != PERISHING) {
                    aniFrame++;
                    if (aniFrame >= PlayerConstants.DYING_FRAMES) {
                        setEntityStatus(PERISHING);
                        aniFrame = PlayerConstants.DYING_FRAMES-1;
                    }
                }
                break;
        }
    }

    //checks the need to change the frame
    private boolean checkAniTickFrame(int aniSpeed) {
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            return true;
        }
        else
            aniTick++;
        return false;
    }

    private void updatePlayerInformations() {
        if (!leftPressed && !rightPressed && !upPressed && !downPressed &&
                aniAction != ATTACKING_01 && aniAction != ATTACKING_02) {
            setAction(STANDING);
        }
        else if (aniAction == WALKING) {
            if (leftPressed && !rightPressed) {
                setDirection(LEFT);
                increasePositionX(-entitySpeed);
                if (checkCollision())
                    decreasePositionX(-entitySpeed);
            } else if (rightPressed && !leftPressed) {
                setDirection(RIGHT);
                increasePositionX(entitySpeed);
                if (checkCollision())
                    decreasePositionX(entitySpeed);
            }
            if (upPressed && !downPressed) {
                setDirection(UP);
                increasePositionY(-entitySpeed);
                if (checkCollision())
                    decreasePositionY(-entitySpeed);
            } else if (downPressed && !upPressed) {
                setDirection(DOWN);
                increasePositionY(entitySpeed);
                if (checkCollision())
                    decreasePositionY(entitySpeed);
            }
        } else if (aniAction == ATTACKING_01 || aniAction == ATTACKING_02){
            if (canPerformAttack) {
                performAttack();
                canPerformAttack = false;
            }
        }
        updatePlayerCenter();
        updateStatusCooldown();
        updateAnimationTick();
    }

    public void performAttack() {
        int weaponRange;
        int weaponCornerOne;
        int weaponCornerTwo;

        if (aniAction == ATTACKING_01) {
            if (direction == LEFT || direction == RIGHT) {
                weaponRange = (int) (aniWidth / 1.14f);
                weaponCornerOne = (int) (aniHeight / 1.42f);
                weaponCornerTwo = (int) (aniHeight / 1.42f);
            }
            else {
                weaponRange = (int) (aniWidth / 1.06f);
                weaponCornerOne = (int) (aniHeight / 2.46f);
                weaponCornerTwo = (int) (aniHeight / 2.46f);
            }
        } else {
            if (direction == LEFT || direction == RIGHT) {
                weaponRange = (int) (aniHeight / 0.85f);
                weaponCornerOne = (int) (aniWidth / 0.94f);
                weaponCornerTwo = (int) (aniWidth / 2.67f);
            }
            else {
                weaponRange = (int) (aniHeight / 0.85f);
                weaponCornerOne = (int) (aniWidth / 0.85f);
                weaponCornerTwo = (int) (aniWidth / 6.4f);
            }
        }

        HashSet<Entity> enemiesHit = playing.getMapManager().getCollision().checkCollisionAttack
                (this, weaponRange, weaponCornerOne, weaponCornerTwo);

        if (!enemiesHit.isEmpty()) {
            int hitDamage = 0;
            float push = 0;
            switch (aniAction) {
                case ATTACKING_01 -> { hitDamage = -attack1Damage; push = attack1PushBack; }
                case ATTACKING_02 -> { hitDamage = -attack2Damage; push = attack2PushBack; }
            }
            for (Entity entity : enemiesHit) {
                if (entity.getAction() != EnemyConstants.DYING) {
                    entity.setHp(hitDamage);
                    ((EnemyEntity) entity).pushEntity(push, direction);
                }
            }
        }
    }

    //player movement if the screen reaches the edge of the map
    public void updatePlayerCenter() {
        //left
        if (x <= 0 || playing.getMapManager().getMapMaxWidth() < ScreenWidth)
            entityCenterX = x + ScreenCenterX;
        //right
        else if (x + ScreenWidth > playing.getMapManager().getMapMaxWidth())
            entityCenterX = x - (playing.getMapManager().getMapMaxWidth() - ScreenWidth - ScreenCenterX);
        else
            entityCenterX = ScreenCenterX;
        //up
        if (y <= 0 || playing.getMapManager().getMapMaxHeight() < ScreenHeight)
            entityCenterY = y + ScreenCenterY;
        //down
        else if (y + ScreenHeight > playing.getMapManager().getMapMaxHeight())
            entityCenterY = y - (playing.getMapManager().getMapMaxHeight() - ScreenHeight - ScreenCenterY);
        else
            entityCenterY = ScreenCenterY;
    }

    public void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(Entities.PLAYER_ATLAS_01);
        animations = new BufferedImage[13][9];
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
        for (int i = 4; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                animations[i][j] = img.getSubimage(tempJ * 64, tempI * 64,64*3, 64*3);
                tempJ += 3;
            }
            tempI += 3;
            tempJ = 0;
        }
        //dying animation
        tempI = 20;
        for (int j = 0; j < 6; j++) {
            animations[12][j] = img.getSubimage(j * 64, tempI * 64, 64, 64);
        }
    }

    public void resetDirBooleans() {
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
    }

    public void setAction(int action) {
        aniAction = action;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setEntityCooldown(int status) {
        switch (status) {
            case DAMAGED -> { damaged = System.currentTimeMillis(); entityStatus = DAMAGED; }
        }
    }

    public void updateStatusCooldown() {
        if (System.currentTimeMillis() - damaged >= 2000)
            entityStatus = NORMAL;
    }

    public void pushEntity(float pushDistance, int hitDirection) {
        playing.getMapManager().getCollision().pushEntity(this, hitDirection, pushDistance);
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
        else if (this.hp < 0) {
            setAction(PlayerConstants.DYING);
            resetAniFrame();
        }
    }

    public void draw(Graphics2D g2) {
        if (aniAction == PlayerConstants.DYING)
            g2.drawImage(animations[PlayerConstants.DYING][aniFrame], (int) entityCenterX,
                    (int) entityCenterY, aniWidth, aniHeight, null);
        else if (entityStatus == NORMAL || entityStatus == DAMAGED && System.currentTimeMillis() % 2 == 0) {
            //animation
            if (aniAction == ATTACKING_01 || aniAction == ATTACKING_02)
                g2.drawImage(animations[aniAction + direction][aniFrame],
                        (int) (entityCenterX - aniWidth), (int) (entityCenterY - aniHeight),
                        aniWidth * 3, aniHeight * 3, null);
            else
                g2.drawImage(animations[direction][aniFrame], (int) entityCenterX,
                        (int) entityCenterY, aniWidth, aniHeight, null);
        }

        //HP
        int w1 = (int) (100 * Scale);
        g2.setColor(Color.BLACK);
        g2.fillRect(30,20, w1, (int) (4 * Scale));
        g2.setColor(Color.RED);
        int w2 = hp * 100 / maxHP;
        g2.fillRect(30,20,w1 * w2 / 100,(int) (4 * Scale));
    }
}