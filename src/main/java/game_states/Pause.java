package game_states;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

import static main.GameWindow.ScreenSettings.*;

public class Pause implements GameStates{
    private Game game;
    public Pause(Game game) {
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
        //pause screen
        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(ScreenWidth/2-ScreenWidth/4,ScreenHeight/2-ScreenHeight/4-TileSize*4,
                ScreenWidth/2,ScreenHeight-TileSize*2);

        //game time
        g2.setColor(new Color(255,255,255,255));
        long time = System.currentTimeMillis() - game.getGameTime();
        long seconds = time/1000;
        String secondsPrint = Long.toString(seconds % 60);
        String minutes = Long.toString(seconds / 60 % 60);
        String hours = Long.toString(seconds/60/60);
        g2.drawString((hours + " : " + minutes + " : " + secondsPrint),
                ScreenWidth/2-ScreenWidth/4,ScreenHeight/2-ScreenHeight/4-TileSize*4 + 50);
    }

    public void update() {}
}
