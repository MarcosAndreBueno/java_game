package tiles;

import entities.Entity;
import entities.Player;

import static entities.Player.ScreenCenterX;
import static entities.Player.ScreenCenterY;
import static main.GameWindow.ScreenSettings.BaseTileSize;
import static tiles.Tiles.countOne;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.Directions.DOWN;

public class Collision {

    public static boolean checkPlayerCollision(Entity entity, int direction) {
        int[] hitbox = entity.getHitbox();
        float y = entity.getPositionY();
        float x = entity.getPositionX();

        if (direction == UP) {
            //upleft
            if (countOne[((int) y + (int)ScreenCenterY + hitbox[UP]) /
                    BaseTileSize][((int) x + (int)ScreenCenterX) / BaseTileSize] == 1)
                return true;
            //upright
            else if (countOne[((int) y + (int)ScreenCenterY + hitbox[UP]) /
                    BaseTileSize][((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize] == 1)
                return true;
        }
        else if (direction == DOWN) {
            //downleft
            if (countOne[((int) y + (int)ScreenCenterY + hitbox[DOWN]) /
                    BaseTileSize][((int) x + (int)ScreenCenterX) / BaseTileSize] == 1)
                return true;
            //downright
            else if (countOne[((int) y + (int)ScreenCenterY + hitbox[DOWN]) /
                    BaseTileSize][((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize] == 1)
                return true;
        }
        if (direction == LEFT) {
            //head
            if (countOne[((int) y + (int)ScreenCenterY + hitbox[UP]) /
                    BaseTileSize][((int) x + (int)ScreenCenterX) / BaseTileSize] == 1)
                return true;
            //feet
            else if (countOne[((int) y + (int)ScreenCenterY + hitbox[DOWN]) /
                    BaseTileSize][((int) x + (int)ScreenCenterX) / BaseTileSize] == 1)
                return true;
        }
        else if (direction == RIGHT) {
            //head
            if (countOne[((int) y + (int)ScreenCenterY + hitbox[UP]) /
                    BaseTileSize][((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize] == 1)
                return true;
            //feet
            else if (countOne[((int) y + (int)ScreenCenterY + hitbox[DOWN]) /
                    BaseTileSize][((int) x + (int)ScreenCenterX + hitbox[RIGHT]) / BaseTileSize] == 1)
                return true;
        }

        return false;
    }
}
