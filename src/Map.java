import java.util.LinkedList;
import java.util.Random;

public class Map {
    int hills_left, mountains_left = 3;
    int forests_left, fields_left, pastures_left = 4;
    int deserts_left = 1;
    LinkedList<Tile> tiles;

    public void initMap(){
        Tile start = getNewTile();
        Tile
    }

    private void fillTiles(){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = getNewTile();
            }
        }
    }

    /**
     * Builds a new tile with one of the possible resources, limited by the
     * corresponding *_left attributes and adds it to tiles, decrementing the
     * corresponding *_left attribute
     * */
    private Tile getNewTile() {
        Random random = new Random();
        int total_left = hills_left + mountains_left + forests_left + fields_left + pastures_left + deserts_left;
        if (total_left == 0) {
            return null;
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
