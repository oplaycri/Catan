package graphics;

import game.Game;
import map.Map;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class GamePanel extends JPanel {
    Game game;
    Map map;
    public GamePanel(){
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
        LinkedList<Polygon> polygons = new LinkedList<>();

        for (Polygon hex: polygons){
            g2D.clip(hex);
            switch ()
            g2D.drawImage(s);
        }
    }

    private void fillPolygons(LinkedList<Polygon> polygons, int widthTile, int heightTile){
        double angle = Math.PI - (1.0/6.0 * 2 * Math.PI);
        double alignYPercentage =  1/Math.tan(angle);
        int alignY = (int) (alignYPercentage * heightTile);
        int x = 3 * widthTile / 2;
        int y = 0;
        for (int i = 0; i < 3; i++) {
            polygons.add(getHexagon(x, y, widthTile, heightTile));
            x += widthTile;
        }
        x = widthTile/2;
        y += heightTile - alignY;
        for (int i = 0; i < 4; i++) {
            polygons.add(getHexagon(x, y, widthTile, heightTile));
            x += widthTile;
        }
        x = 0;
        y += heightTile - alignY;
        for (int i = 0; i < 5; i++) {
            polygons.add(getHexagon(x, y, widthTile, heightTile));
            x += widthTile;
        }
        x = widthTile/2;
        y += heightTile - alignY;
        for (int i = 0; i < 4; i++) {
            polygons.add(getHexagon(x, y, widthTile, heightTile));
            x += widthTile;
        }
        x = 3 * widthTile / 2;
        y += heightTile - alignY;
        for (int i = 0; i < 3; i++) {
            polygons.add(getHexagon(x, y, widthTile, heightTile));
            x += widthTile;
        }
    }

    private Polygon getHexagon(int x, int y, int width, int height){
        Polygon val = new Polygon();
        double angle = Math.PI - (1.0/6.0 * 2 * Math.PI);
        double alignYPercentage =  1/Math.tan(angle);
        int alignY = (int) (alignYPercentage * height);
        val.addPoint(x + width/2, y);
        val.addPoint(x, y+ alignY);
        val.addPoint(x + width, y + alignY);
        val.addPoint(x, y+height-alignY);
        val.addPoint(x + width, y+height-alignY);
        val.addPoint(x + width/2, y+height);
        return val;
    }
}
