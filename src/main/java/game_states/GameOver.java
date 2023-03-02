package game_states;

import main.Game;
import utilz.Draw;

import java.awt.*;
import java.awt.event.KeyEvent;

import static main.GameWindow.ScreenSettings.*;
import static utilz.Constants.GameStates.PAUSE;

public class GameOver implements GameStates{

    private Game game;
    private Rectangle arrow;
    private int option;

    public GameOver(Game game) {
        this.game = game;
        this.option = 1;
        arrow = new Rectangle();
        arrow.x = ScreenWidth / 12 + (TileSize * 10) - BaseTileSize;
        arrow.width = (int) (ScreenWidth/5f);
        arrow.height = (int) (ScreenHeight/12f);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> { game.getState().changeGameState(PAUSE); }
            case KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                if (option == 1) option = 2; else option = 1; }
            case KeyEvent.VK_ENTER -> {
                if (option == 1) game.restartPlaying(); else game.exit(); }
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        int x = ScreenWidth / 12;
        int y = ScreenHeight / 12;

        //game over window
        Color color = new Color(0,0,0,230);
        g2.setColor(color);
        g2.fillRect(x,y, (int) (ScreenWidth / 1.2f), (int) (ScreenHeight / 1.2f));

        //game over text
        Font font = g2.getFont().deriveFont(Font.BOLD, 48F*Scale);
        String text = "GAME OVER";
        Color textColor = Color.RED;
        Draw.DrawTextWithShadow(g2, textColor, x + TileSize*4, y + TileSize*4, font, text);

        //retry and quit text
        font = g2.getFont().deriveFont(Font.BOLD, 25F*Scale);
        String text2 = "RETRY";
        String text3 = "QUIT";
        Draw.DrawTextWithShadow(g2, textColor, x + TileSize*10,y + TileSize*8, font, text2);
        Draw.DrawTextWithShadow(g2, textColor, x + TileSize*11,y + TileSize*12, font, text3);

        //select arrow
        g2.setColor(new Color(255,255,255,100));
        if (option == 1)
            arrow.y = (int) (y + TileSize*8 - TileSize*1.3f);
        else
            arrow.y = (int) (y + TileSize*12 - TileSize*1.3f);

        g2.draw(arrow);
    }
}
