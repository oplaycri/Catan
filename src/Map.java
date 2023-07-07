import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Map {
    int hills_left, mountains_left = 3;
    int forests_left, fields_left, pastures_left = 4;
    int deserts_left = 1;
    boolean neigh_filled = false;
    LinkedList<Tile> tiles = new LinkedList<>();

    public void initMap() {
        Tile start = getNewTile();
        tiles.add(start);
        fillSurroundingTiles(start);
        Object[] Iteration = start.getNeighbours();
        for (Object o : Iteration) {
            fillSurroundingTiles((Tile) o);
        }
        Iteration = tiles.toArray();
        for (Object o : Iteration) {
            fillSurroundingTiles((Tile) o);
        }

        while (!neigh_filled) {
            neigh_filled = true;
            for (Tile tile : tiles) {
                fillNeighbours(tile);
            }
        }
        for (Tile tile : tiles) {
            fillJunctions(tile);
        }
        for (Tile tile : tiles) {
            fillEdges(tile);
        }
    }

    private void fillEdges(Tile t) {
        Junction[] junctions = t.getJunctions();
        int edgePos = 1;
        for (int i = 0; i < junctions.length; i++) {
            junctions[i].getEdges()[edgePos] = new Edge(junctions[i], junctions[(i+1)%6]);
            if(i%2!=0) {
                edgePos = (edgePos + 1) % 3;
            }
        }
    }

    private void fillJunctions(Tile t) {
        Junction[] junctions = t.getJunctions();
        for (int i = 0; i < junctions.length; i++) {
            if (junctions[i] != null){
                continue;
            }
            junctions[i] = new Junction();
            t.getNeighbours()[(6+i-1)%6].getJunctions()[(i+2)%6] = junctions[i];
            t.getNeighbours()[i].getJunctions()[(6+i-2)%6] = junctions[i];
        }
    }

    private void fillSurroundingTiles(Tile t) {
        Tile[] neighbours = t.getNeighbours();
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] != null) {
                continue;
            }
            neighbours[i] = getNewTile();
            if (neighbours[i].getResource() != Tile.Resource.WATER){
                tiles.add(neighbours[i]);
            }
            int relPos = (i + 3) % 6;
            neighbours[i].setNeighbour(relPos, t);
        }
    }

    private void fillNeighbours(Tile t) {
        Tile[] neighbours = t.getNeighbours();
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] == null || neighbours[i].getResource() == Tile.Resource.WATER) {
                continue;
            }
            neighbours[(6 + i - 1) % 6] = neighbours[i].getNeighbours()[(i + 4) % 6];
            neighbours[(6 + i + 1) % 6] = neighbours[i].getNeighbours()[(i + 2) % 6];
        }
        if (neigh_filled) {
            for (Tile neighbour : neighbours) {
                if (neighbour == null) {
                    neigh_filled = false;
                    break;
                }
            }
        }
    }

    /**
     * Builds a new tile with one of the possible resources, limited by the
     * corresponding *_left attributes and adds it to tiles, decrementing the
     * corresponding *_left attribute
     */
    private Tile getNewTile() {
        Random random = new Random();
        int total_left = hills_left + mountains_left + forests_left + fields_left + pastures_left + deserts_left;
        if (total_left == 0) {
            return new Tile(Tile.Resource.WATER);
        }
        int tileNumber = random.nextInt(total_left) + 1; // +1 makes it easier to match
        Tile.Resource resource;
        if (tileNumber >= (total_left = total_left - deserts_left)) {
            resource = Tile.Resource.DESERT;
            deserts_left--;
        } else if (tileNumber >= (total_left = total_left - pastures_left)) {
            resource = Tile.Resource.PASTURE;
            pastures_left--;
        } else if (tileNumber >= (total_left = total_left - fields_left)) {
            resource = Tile.Resource.FIELDS;
            fields_left--;
        } else if (tileNumber >= (total_left = total_left - forests_left)) {
            resource = Tile.Resource.FOREST;
            forests_left--;
        } else if (tileNumber >= (total_left = total_left - mountains_left)) {
            resource = Tile.Resource.MOUNTAINS;
            mountains_left--;
        } else {
            resource = Tile.Resource.HILLS;
            hills_left--;
        }
        return new Tile(resource);
    }
}
