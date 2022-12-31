package game_states;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

import static main.GameWindow.ScreenSettings.*;

public class GameMenu implements GameStates{
    private Game game;

    public GameMenu(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> System.out.println("w");
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> System.out.println("s");
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> System.out.println("a");
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> System.out.println("d");
            case KeyEvent.VK_ESCAPE -> game.getState().changeGameState();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> System.out.println("w");
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> System.out.println("s");
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> System.out.println("a");
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> System.out.println("d");
        }
    }

    public void draw(Graphics2D g2) {
        g2.fillRect(ScreenWidth/2-ScreenWidth/4,ScreenHeight/2-ScreenHeight/4-TileSize*4,
                ScreenWidth/2,ScreenHeight-TileSize*2);
    }

    public void update() {}
}
