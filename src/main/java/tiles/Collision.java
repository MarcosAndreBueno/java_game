package tiles;


import entities.EnemyEntity;
import entities.Entity;
import entities.Player;
import maps.MapManager;

import java.util.ArrayList;
import java.util.HashSet;

import static main.GameWindow.ScreenSettings.*;
import static main.GameWindow.ScreenSettings.BaseTileSize;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.PlayerConstants.*;

public class Collision {

    private MapManager mapManager;

    public Collision(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void pushEntity(Entity entity, int hitDirection, float pushDistance) {
        float tempX = entity.getPositionX();
        float tempY = entity.getPositionY();
        try {
            switch (hitDirection) {
                case UP -> {
                    entity.increasePositionY(-pushDistance);
                    entity.checkCollisionUp(pushDistance);
                    isEntityHere(entity);
                }
                case LEFT -> {
                    entity.increasePositionX(-pushDistance);
                    entity.checkCollisionLeft(pushDistance);
                    isEntityHere(entity);
                }
                case DOWN -> {
                    entity.increasePositionY(pushDistance);
                    entity.checkCollisionDown(pushDistance);
                    isEntityHere(entity);
                }
                case RIGHT -> {
                    entity.increasePositionX(pushDistance);
                    entity.checkCollisionRight(pushDistance);
                    isEntityHere(entity);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            entity.setPositionX(tempX);
            entity.setPositionY(tempY);
        }
    }

    public boolean isTileSolid(Entity entity) {
        float x = entity.getPositionX();
        float y = entity.getPositionY();
        float[] hitbox = entity.getHitbox();
        int direction = entity.getDirection();

        float entityDirection;
        float checkCornerOne;
        float checkCornerTwo;
        switch (direction) {
            case (LEFT):
                entityDirection = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;
                checkCornerOne = ((int) y + (int) ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
                checkCornerTwo = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
                if (mapManager.getMapHitbox()[(int)checkCornerOne][(int)entityDirection] == 1 ||
                        mapManager.getMapHitbox()[(int)checkCornerTwo][(int)entityDirection] == 1)
                    return true;
            case (RIGHT):
                entityDirection = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;
                checkCornerOne = ((int) y + (int) ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
                checkCornerTwo = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
                if (mapManager.getMapHitbox()[(int)checkCornerOne][(int)entityDirection] == 1 ||
                        mapManager.getMapHitbox()[(int)checkCornerTwo][(int)entityDirection] == 1)
                    return true;
            case (UP):
                entityDirection = ((int) y + (int) ScreenCenterY + hitbox[UP]) / BaseTileSize;
                checkCornerOne = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;   //upleft
                checkCornerTwo = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;  //upright
                if (mapManager.getMapHitbox()[(int)entityDirection][(int)checkCornerOne] == 1 ||
                        mapManager.getMapHitbox()[(int)entityDirection][(int)checkCornerTwo] == 1)
                    return true;
            case (DOWN):
                entityDirection = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;
                checkCornerOne = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;  //downleft
                checkCornerTwo = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize; //downright
                if (mapManager.getMapHitbox()[(int)entityDirection][(int)checkCornerOne] == 1 ||
                        mapManager.getMapHitbox()[(int)entityDirection][(int)checkCornerTwo] == 1)
                    return true;
        }
        return false;
    }

    public boolean isEntityHere(Entity movingEntity) {
        float entityX = movingEntity.getPositionX();
        float entityY = movingEntity.getPositionY();
        float[] entityHitbox = movingEntity.getHitbox();
        int entityDirection = movingEntity.getDirection();

        //prepare movingEntity variables
        float movingEntityDirection,movingEntityOppositeDirection,movingEntityCornerOne,movingEntityCornerTwo;
        int oppositeDirection, movingEntityAction = movingEntity.getAction();

        //hitbox values
        if (entityDirection == UP || entityDirection == DOWN) {
            if (entityDirection == UP)
                oppositeDirection = DOWN;
            else
                oppositeDirection = UP;
            movingEntityDirection = entityY + entityHitbox[entityDirection];
            movingEntityCornerOne = entityX + entityHitbox[LEFT];
            movingEntityCornerTwo = entityX + entityHitbox[RIGHT];
        }else {
            if (entityDirection == LEFT)
                oppositeDirection = RIGHT;
            else
                oppositeDirection = LEFT;
            movingEntityDirection = entityX + entityHitbox[entityDirection];
            movingEntityCornerOne = entityY + entityHitbox[UP];
            movingEntityCornerTwo = entityY + entityHitbox[DOWN];
        }

        ArrayList<Entity> entities = mapManager.getPlaying().getEntities();

        float actualEntityOppositeDirection = -1;
        float actualEntityDirection = -1;
        float actualEntityCornerOne = -1;
        float actualEntityCornerTwo = -1;

        // checks the 4 corners of each entity's hitbox on the map
        for (Entity actualEntity : entities) {
            if (!actualEntity.equals(movingEntity)) {
                float[] actualEntityHitbox = actualEntity.getHitbox();

                //hitbox values
                if (entityDirection == UP || entityDirection == DOWN) {
                    actualEntityOppositeDirection = actualEntity.getPositionY() + actualEntityHitbox[oppositeDirection];
                    actualEntityCornerOne = actualEntity.getPositionX() + actualEntityHitbox[LEFT];
                    actualEntityCornerTwo = actualEntity.getPositionX() + actualEntityHitbox[RIGHT];
                } else {
                    actualEntityOppositeDirection = actualEntity.getPositionX() + actualEntityHitbox[oppositeDirection];
                    actualEntityCornerOne = actualEntity.getPositionY() + actualEntityHitbox[UP];
                    actualEntityCornerTwo = actualEntity.getPositionY() + actualEntityHitbox[DOWN];
                }

                //first check: are entities in the same line/column?
                if ((int) movingEntityDirection  == (int) actualEntityOppositeDirection) {

                //second check: are entities colliding?
                    //if moving entity has a hitbox smaller than the actual entity
                    if ((int) movingEntityCornerOne <= (int) actualEntityCornerTwo
                            &&
                        (int) movingEntityCornerTwo >= (int) actualEntityCornerOne)
                        return true;

                        //if moving entity has a hitbox larger than the actual entity
                    else if ((int) actualEntityCornerOne <= (int) movingEntityCornerTwo
                            &&
                            (int) actualEntityCornerTwo >= (int) movingEntityCornerOne)
                        return true;
                }
            }
        }
        return false;
    }

    public HashSet<Entity> checkCollisionAttack(Entity movingEntity, int weaponRange, int weaponCornerOne, int weaponCornerTwo) {
        ArrayList<Entity> entities = mapManager.getPlaying().getEntities();
        HashSet<Entity> enemiesHit = new HashSet<>();

        int playerX = (int) (movingEntity.getPositionX());
        int playerY = (int) (movingEntity.getPositionY());

        int movingEntityDirection = movingEntity.getDirection();

        for (Entity entity : entities) {
            if (!entity.equals(movingEntity)) {
                int enemyX = (int) (entity.getPositionX());
                int enemyY = (int) (entity.getPositionY());

                if (movingEntityDirection == LEFT || movingEntityDirection == RIGHT) {
                int distanceX = Math.abs(playerX - enemyX);
                if (distanceX <= weaponRange &&
                        playerY + weaponCornerOne >= enemyY + entity.getHitbox()[UP] &&
                        playerY + weaponCornerTwo <= enemyY + entity.getHitbox()[DOWN])
                    enemiesHit.add(entity);
                }

                else if (movingEntityDirection == UP || movingEntityDirection == DOWN) {
                    int distanceY = Math.abs(playerY - enemyY);
                    if (distanceY <= weaponRange &&
                            playerX + weaponCornerOne >= enemyX + entity.getHitbox()[LEFT] &&
                            playerX + weaponCornerTwo <= enemyX + entity.getHitbox()[RIGHT])
                        enemiesHit.add(entity);
                }
            }
        }
        return enemiesHit;
    }


    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }
}