package graphics;

import game.Game;
import map.Map;
import map.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class GamePanel extends JPanel {
    Game game;
    Map map;
    public GamePanel(){
        setBounds(40, 40, 800, 800);
        setBackground(new Color(0, 0, 0));
        game = new Game(3);
        map = game.getMap();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int widthTile = width/5;
        int heightTile = height/5;
        double angle = (Math.PI - (4.0/6.0 * Math.PI))/2;
        int alignY = (int) ((widthTile/2.0)*Math.tan(angle));
        int offsetX =width/2 - widthTile/2;
        int offsetY =height/2 - heightTile/2;
        int x,y;
        BufferedImage test = null;
        try {
            test = ImageIO.read(new File("src/resources/Pasture.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Tile t: map.getTiles()){
            x = t.x * widthTile + t.y * widthTile/2 + offsetX;
            y = t.y * (heightTile - alignY) + offsetY;
            g2D.setClip(getHexagon(x, y, widthTile, heightTile));
            g2D.drawImage(test, x, y, widthTile, heightTile, null);
            System.out.println("huh");
        }
    }

    private Polygon getHexagon(int x, int y, int width, int height){
        Polygon val = new Polygon();
        double angle = (Math.PI - (4.0/6.0 * Math.PI))/2;
        int alignY = (int) ((width/2.0)*Math.tan(angle));
        // Order is important
        val.addPoint(x + width/2, y);
        val.addPoint(x + width, y + alignY);
        val.addPoint(x + width, y+height-alignY);
        val.addPoint(x + width/2, y+height);
        val.addPoint(x, y+height-alignY);
        val.addPoint(x, y+ alignY);
        return val;
    }
}
