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
    BufferedImage images[] = new BufferedImage[Tile.Terrain.values().length];
    public GamePanel(){
        setBounds(40, 40, 800, 800);
        setBackground(new Color(0, 0, 0));
        game = new Game(3);
        map = game.getMap();
        initImages();
    }

    private void initImages() {
        try {
            images[Tile.Terrain.DESERT.ordinal()] = ImageIO.read(new File("src/resources/Desert.png"));
            images[Tile.Terrain.FIELDS.ordinal()] = ImageIO.read(new File("src/resources/Fields.png"));
            images[Tile.Terrain.FOREST.ordinal()] = ImageIO.read(new File("src/resources/Forest.png"));
            images[Tile.Terrain.HILLS.ordinal()] = ImageIO.read(new File("src/resources/Hills.png"));
            images[Tile.Terrain.MOUNTAINS.ordinal()] = ImageIO.read(new File("src/resources/Mountains.png"));
            images[Tile.Terrain.PASTURE.ordinal()] = ImageIO.read(new File("src/resources/Pasture.png"));
            images[Tile.Terrain.WATER.ordinal()] = ImageIO.read(new File("src/resources/Water.png"));
        } catch (Exception e){

        }
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
        Image image;
        for (Tile t: map.getTiles()){
            x = t.x * widthTile + t.y * widthTile/2 + offsetX;
            y = t.y * (heightTile - alignY) + offsetY;
            g2D.setClip(getHexagon(x, y, widthTile, heightTile));
            image = images[t.terrain.ordinal()];
            g2D.drawImage(image, x, y, widthTile, heightTile, null);
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
