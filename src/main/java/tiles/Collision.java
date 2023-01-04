package tiles;


import entities.Entity;
import entities.Player;
import maps.MapManager;

import static main.GameWindow.ScreenSettings.*;
import static main.GameWindow.ScreenSettings.BaseTileSize;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.Directions.RIGHT;

public class Collision {

    private MapManager mapManager;

    public Collision(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public boolean isTileSolid(float x, float y, int[] hitbox, int direction) {
        int checkCornerOne;
        int checkCornerTwo;
        switch (direction) {
            case (LEFT):
                direction = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;
                checkCornerOne = ((int) y + (int) ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
                checkCornerTwo = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
                if (mapManager.getMapHitbox()[checkCornerOne][direction] == 1 ||
                        mapManager.getMapHitbox()[checkCornerTwo][direction] == 1)
                    return true;
            case (RIGHT):
                direction = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;
                checkCornerOne = ((int) y + (int) ScreenCenterY + hitbox[UP]) / BaseTileSize;     //head
                checkCornerTwo = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;   //feet
                if (mapManager.getMapHitbox()[checkCornerOne][direction] == 1 ||
                        mapManager.getMapHitbox()[checkCornerTwo][direction] == 1)
                    return true;
            case (UP):
                direction = ((int) y + (int) ScreenCenterY + hitbox[UP]) / BaseTileSize;
                checkCornerOne = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;   //upleft
                checkCornerTwo = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize;  //upright
                if (mapManager.getMapHitbox()[direction][checkCornerOne] == 1 ||
                        mapManager.getMapHitbox()[direction][checkCornerTwo] == 1)
                    return true;
            case (DOWN):
                direction = ((int) y + (int) ScreenCenterY + hitbox[DOWN]) / BaseTileSize;
                checkCornerOne = ((int) x + (int) ScreenCenterX + hitbox[LEFT]) / BaseTileSize;  //downleft
                checkCornerTwo = ((int) x + (int) ScreenCenterX + hitbox[RIGHT]) / BaseTileSize; //downright
                if (mapManager.getMapHitbox()[direction][checkCornerOne] == 1 ||
                        mapManager.getMapHitbox()[direction][checkCornerTwo] == 1)
                    return true;
        }
        return false;
    }

    public boolean isEntityHere(float entityX, float entityY, int[] entityHitbox, int entityDirection) {
        if (entityDirection == UP || entityDirection == DOWN) {
            //entities position already considering the hitbox
            float movingEntityDireciton = entityY + entityHitbox[entityDirection];
            float movingEntityCornerOne = entityX + entityHitbox[LEFT];
            float movingEntityCornerTwo = entityX + entityHitbox[RIGHT];

            int oppositeDirection;
            if (entityDirection == UP)
                oppositeDirection = DOWN;
            else
                oppositeDirection = UP;

            return (checkCollisionBetweenEntities(movingEntityDireciton, movingEntityCornerOne,
                    movingEntityCornerTwo, entityDirection, oppositeDirection));
        }else {
            //entities position already considering the hitbox
            float movingEntityDireciton = entityX + entityHitbox[entityDirection];
            float movingEntityCornerOne = entityY + entityHitbox[UP];
            float movingEntityCornerTwo = entityY + entityHitbox[DOWN];

            int oppositeDirection;
            if (entityDirection == LEFT)
                oppositeDirection = RIGHT;
            else
                oppositeDirection = LEFT;

            return (checkCollisionBetweenEntities(movingEntityDireciton, movingEntityCornerOne,
                    movingEntityCornerTwo, entityDirection, oppositeDirection));
        }
    }

    // checks the 4 corners of each entity's hitbox on the map
    public boolean checkCollisionBetweenEntities(float movingEntityDirection, float movingEntityCornerOne, float movingEntityCornerTwo, int entityDirection, int oppositeDirection) {
        Entity[] entities = mapManager.getPlaying().getEntities();
        boolean collision = false;

        float actualEntityDirection = -1;
        float actualEntityCornerOne = -1;
        float actualEntityCornerTwo = -1;

        for (Entity actualEntity : entities) {
            int[] actualEntityHitbox = actualEntity.getHitbox();

            if (entityDirection == UP || entityDirection == DOWN) {
                actualEntityDirection = actualEntity.getPositionY() + actualEntityHitbox[oppositeDirection];
                actualEntityCornerOne = actualEntity.getPositionX() + actualEntityHitbox[LEFT];
                actualEntityCornerTwo = actualEntity.getPositionX() + actualEntityHitbox[RIGHT];
            } else {
                actualEntityDirection = actualEntity.getPositionX() + actualEntityHitbox[oppositeDirection];
                actualEntityCornerOne = actualEntity.getPositionY() + actualEntityHitbox[UP];
                actualEntityCornerTwo = actualEntity.getPositionY() + actualEntityHitbox[DOWN];
            }

            if ((int) movingEntityDirection == (int) actualEntityDirection ) {

                //for entities with hitbox larger than the entity that is moving
                if (((int) movingEntityCornerOne >= (int) actualEntityCornerOne &&
                        (int) movingEntityCornerOne <= (int) actualEntityCornerTwo)
                                ||
                    ((int) movingEntityCornerTwo >= (int) actualEntityCornerOne &&
                            (int) movingEntityCornerTwo <= (int) actualEntityCornerTwo))
                    collision = true;

                //for entities with hitbox smaller than the entity that is moving
                else if ((int) actualEntityCornerOne >= (int) movingEntityCornerOne &&
                        (int) actualEntityCornerTwo <= (int) movingEntityCornerTwo)
                    collision = true;
            }
        }

        return collision;
    }

    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }
}