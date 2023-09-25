package graphics;

import game.Game;
import map.Map;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    Game game;
    Map map;
    public GamePanel(){
        map = game.getMap();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }
}
