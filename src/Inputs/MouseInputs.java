package Inputs;

import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    GamePanel gamePanel;
    public boolean inputMouseClicked;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        inputMouseClicked = true;
        System.out.printf("\nplayer position x: %s | y: %s", gamePanel.getGame().getPlayer().getPositionX(), gamePanel.getGame().getPlayer().getPositionY());
        System.out.printf("\nx: %s | y: %s", e.getX(), e.getY());
        System.out.println();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        inputMouseClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
