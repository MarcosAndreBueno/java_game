package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {
    // Screen settings
    final int baseTileSize = 16; //16x16 pixels
    final int scale = 3;

    final int tileSize = baseTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    KeyboardInputs keyboardInputs;
    MouseInputs mouseInputs;

    private Game game;

    public GamePanel(Game game) {
        setPanelConfigurations();
        initializeInputs();
        this.game = game;
    }

    private void initializeInputs() {
        keyboardInputs = new KeyboardInputs(this);
        mouseInputs = new MouseInputs(this);
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true); // starts with focus on window
    }

    private void setPanelConfigurations() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //preferred size does not include borders if called by gamePanel
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // optimize game paint
    }

    public void updateGame() {
    }

    public Game getGame() {
        return game;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //prepare panel
        Graphics2D g2 = (Graphics2D) g;
        game.render(g2);
        g2.dispose(); //it saves some memory
    }
}
