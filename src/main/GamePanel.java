package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static main.GameWindow.ScreenSettings.ScreenWidth;
import static main.GameWindow.ScreenSettings.ScreenHeight;

public class GamePanel extends JPanel {
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
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight)); //preferred size does not include borders if called by gamePanel
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
