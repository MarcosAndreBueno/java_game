package tiles;


import entities.EnemyEntity;
import entities.Entity;
import entities.Player;
import maps.MapManager;

import java.util.ArrayList;

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

    public boolean isTileSolid(float x, float y, float[] hitbox, int direction) {
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

    public boolean isEntityHere(Entity movingEntity, float entityX, float entityY, float[] entityHitbox, int entityDirection) {
        //prepare movingEntity variables
        float movingEntityDirection,movingEntityOppositeDirection,movingEntityCornerOne,movingEntityCornerTwo;
        int oppositeDirection, movingEntityAction = movingEntity.getAction();

        if (entityDirection == UP || entityDirection == DOWN) {
            if (entityDirection == UP)
                oppositeDirection = DOWN;
            else
                oppositeDirection = UP;
            movingEntityDirection = entityY + entityHitbox[entityDirection];
            movingEntityOppositeDirection = entityY + entityHitbox[oppositeDirection];
            movingEntityCornerOne = entityX + entityHitbox[LEFT];
            movingEntityCornerTwo = entityX + entityHitbox[RIGHT];
        }else {
            if (entityDirection == LEFT)
                oppositeDirection = RIGHT;
            else
                oppositeDirection = LEFT;
            movingEntityDirection = entityX + entityHitbox[entityDirection];
            movingEntityOppositeDirection = entityX + entityHitbox[oppositeDirection];
            movingEntityCornerOne = entityY + entityHitbox[UP];
            movingEntityCornerTwo = entityY + entityHitbox[DOWN];
        }

    // checks the 4 corners of each entity's hitbox on the map
        ArrayList<Entity> entities = mapManager.getPlaying().getEntities();

        float actualEntityOppositeDirection = -1;
        float actualEntityDirection = -1;
        float actualEntityCornerOne = -1;
        float actualEntityCornerTwo = -1;

        boolean movingEntityIsPlayer = movingEntity.getEntityName().equals("Player");

        for (Entity actualEntity : entities) {
            boolean collision = false;
            boolean attack = false;
            boolean walking = false;
            if (!actualEntity.equals(movingEntity)) {
                float[] actualEntityHitbox = actualEntity.getHitbox();

                if (entityDirection == UP || entityDirection == DOWN) {
                    actualEntityOppositeDirection = actualEntity.getPositionY() + actualEntityHitbox[oppositeDirection];
                    actualEntityDirection = actualEntity.getPositionY() + actualEntityHitbox[entityDirection];
                    actualEntityCornerOne = actualEntity.getPositionX() + actualEntityHitbox[LEFT];
                    actualEntityCornerTwo = actualEntity.getPositionX() + actualEntityHitbox[RIGHT];
                } else {
                    actualEntityOppositeDirection = actualEntity.getPositionX() + actualEntityHitbox[oppositeDirection];
                    actualEntityDirection = actualEntity.getPositionX() + actualEntityHitbox[entityDirection];
                    actualEntityCornerOne = actualEntity.getPositionY() + actualEntityHitbox[UP];
                    actualEntityCornerTwo = actualEntity.getPositionY() + actualEntityHitbox[DOWN];
                }

                //first check: are entities close enough?
                if ((movingEntityAction == ATTACKING_01 ||
                        movingEntityAction == ATTACKING_02)) {
                    if (((entityDirection == LEFT || entityDirection == UP) &&
                        ((int) movingEntityDirection / BaseTileSize <= (int) actualEntityOppositeDirection / BaseTileSize) &&
                        ((int) movingEntityOppositeDirection / BaseTileSize >= (int) actualEntityOppositeDirection / BaseTileSize))
                            ||
                        ((entityDirection == RIGHT || entityDirection == DOWN) &&
                        ((int) movingEntityDirection / BaseTileSize >= (int) actualEntityOppositeDirection / BaseTileSize) &&
                        ((int) movingEntityOppositeDirection / BaseTileSize <= (int) actualEntityOppositeDirection / BaseTileSize)))
                        attack = true;
                }
                else if (movingEntityAction == WALKING) {
                    if ((int) movingEntityDirection / BaseTileSize  == (int) actualEntityOppositeDirection / BaseTileSize)
                        walking = true;
                }

                //second check: are entities colliding?
                if (walking || attack) {
                    //if moving entity has a hitbox smaller than the actual entity
                    if ((int) movingEntityCornerOne / BaseTileSize <= (int) actualEntityCornerTwo / BaseTileSize
                            &&
                        (int) movingEntityCornerTwo / BaseTileSize >= (int) actualEntityCornerOne / BaseTileSize)
                        collision = true;

                        //if moving entity has a hitbox larger than the actual entity
                    else if ((int) actualEntityCornerOne / BaseTileSize <= (int) movingEntityCornerTwo / BaseTileSize
                            &&
                            (int) actualEntityCornerTwo / BaseTileSize >= (int) movingEntityCornerOne / BaseTileSize)
                        collision = true;
                }

                if (walking && collision)
                    return true;
                else if (attack && collision)
                    if (movingEntityIsPlayer)
                        ((Player) movingEntity).setEnemiesHit(actualEntity);
                    else
                        ((EnemyEntity) movingEntity).setPlayerHit(true);
            }
        }
        return false;
    }

    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }
}