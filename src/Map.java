import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Map {
    int hills_left, mountains_left = 3;
    int forests_left, fields_left, pastures_left = 4;
    int deserts_left = 1;
    LinkedList<Integer> numbers = (LinkedList<Integer>) Arrays.asList(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);
    boolean neigh_filled = false;
    LinkedList<Tile> tiles = new LinkedList<>();

    public void initMap() {
        Tile start = getNewTile();
        tiles.add(start);
        fillSurroundingTiles(start);
        for (Tile tile : tiles) {
            fillSurroundingTiles(tile);
        }
        for (Tile tile : tiles) {
            fillSurroundingTiles(tile);
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
        for (Tile tile : tiles) {
            fillNumbers(tile);
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
            if (neighbours[i].getResource() == Tile.Resource.DESERT){
                neighbours[i].setRaided(true);
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
    private void fillNumbers(Tile t){
        if(t.getResource() == Tile.Resource.DESERT || t.getResource() == Tile.Resource.WATER){
            return;
        }
        Random random = new Random();
        int index = random.nextInt(numbers.size());
        t.setN(numbers.get(index));
        numbers.remove(index);
    }
    private void updateResources(Tile t, int diceRoll){
        if (t.isRaided()||t.getN()==-1){
            return;
        }
        Junction[] junctions = t.getJunctions();
        for (Junction j: junctions){
            BuildingContainer.Building building = j.getBuilding();
            if(building == null){
                continue;
            }
            Player owner = j.getOwner();
            int amount;
            switch (building){
                case Settlement -> amount = 1;
                case City -> amount = 2;
            }
            Tile.Resource resource = t.getResource();
            switch (resource){
                case HILLS -> {

                }
                case FOREST -> {
                }
                case MOUNTAINS -> {
                }
                case FIELDS -> {
                }
                case PASTURE -> {
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
