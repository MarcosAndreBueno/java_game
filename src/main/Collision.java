package main;

import entities.Entity;
import entities.Player;

import java.awt.geom.Rectangle2D;

import static test.TestColors.countOne;
import static utilz.Constants.Directions.*;

public class Collision {

    private Entity entity;
    static int count = 0;

    public Collision(Entity entity) {
        this.entity = entity;
    }

    public boolean checkCollision(Player player, float x, float y) {
        count+= 1;
        if (count >= 240) {
            System.out.printf("\n| x: %s | y: %s |", x, y);
            System.out.printf("\nArray position: [%s][%s] = %s", (((int) y + 200 + 16 * 2) / 16), (((int) x + 340) / 16), (countOne[((int) y + 200 + 16 * 2) / 16][((int) x + 340) / 16]) );
            count = 0;
            System.out.println("\nchecar: " + countOne[(1114) / 16][(780) / 16]);
        }
        if (player.isUpPressed() || (player.isUpPressed() && (player.isRightPressed() || player.isLeftPressed()))) {
            //upleft
            if (countOne[((int) y + 200 + 16 * 2) / 16][((int) x + 340) / 16] == 1)
                return true;
            //upright
            else if (countOne[((int) y + 200 + 16 * 2) / 16][((int) x + 340 + 14 * 2) / 16] == 1)
                return true;
        }
        else if (player.isDownPressed() || (player.isDownPressed() && (player.isLeftPressed() || player.isRightPressed()))) {
            //downleft
            if (countOne[((int) y + 200 + 32 * 2) / 16][((int) x + 340) / 16] == 1)
                return true;
            //downright
            else if (countOne[((int) y + 200 + 32 * 2) / 16][((int) x + 340 + 14 * 2) / 16] == 1)
                return true;
        }
        if (player.isLeftPressed()) {
            //head
            if (countOne[((int) y + 200 + 16 * 2) / 16][((int) x + 340) / 16] == 1)
                return true;
            //feet
            else if (countOne[((int) y + 200 + 32 * 2) / 16][((int) x + 340) / 16] == 1)
                return true;
        }
        else if (player.isRightPressed()) {
            //head
            if (countOne[((int) y + 200 + 16 * 2) / 16][((int) x + 340 + 14 * 2) / 16] == 1)
                return true;
            //feet
            else if (countOne[((int) y + 200 + 32 * 2) / 16][((int) x + 340 + 14 * 2) / 16] == 1)
                return true;
        }

        return false;
    }
}

    /**
    //test collision
        if (countOne[(int) y + 200][(int) x + 340]) {
            System.out.println("Chegamos em um objeto sólido");
            if (leftPressed && !rightPressed)
                setPositionX(playerSpeed);
            else if (rightPressed && !leftPressed)
                setPositionX(-playerSpeed);
            if (upPressed && !downPressed)
                setPositionX(playerSpeed);
            else if (downPressed && !upPressed)
                setPositionX(-playerSpeed);
     **/

