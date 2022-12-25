package main;

import entities.Entity;
import entities.Player;

import java.awt.geom.Rectangle2D;

import static test.TestColors.countOne;
import static utilz.Constants.Directions.*;

public class Collision {

    public static boolean checkCollision(Player player, float x, float y) {
        if (player.isUpPressed() || (player.isUpPressed() && (player.isRightPressed() || player.isLeftPressed()))) {
            for (int i = 0; i < 30; i++)
                if (countOne[(int) y + 200 + 35][(int) x + 340 + i] == 2)
                    return true;
        }
        else if (player.isDownPressed() || (player.isDownPressed() && (player.isLeftPressed() || player.isRightPressed())))
            for (int i = 0; i < 30; i++)
                if (countOne[(int) y + 200 + 60][(int) x + 340 + i] == 2)
                    return true;

        if (player.isLeftPressed()) {
            for (int i = 16; i < 60; i++)
                if (countOne[(int) y + 200 + i][(int) x + 340 + 5] == 1)
                    return true;
        }
        else if (player.isRightPressed())
            for (int i = 16; i < 60; i++)
                if (countOne[(int) y + 200 + i][(int) x + 340 + 20] == 1)
                    return true;

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

