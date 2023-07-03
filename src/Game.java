import java.util.LinkedList;

public class Game {
    int hills_left, mountains_left = 3;
    int forests_left, fields_left, pastures_left = 4;
    int deserts_left = 1;
    LinkedList<Tile> tiles;
    public void initGame(){
        Tile start = insertNewTile();
        fillSurroundingTiles(start);
        Tile[] tiles_of_first_iteration = new Tile[7];
        tiles.toArray(tiles_of_first_iteration);
        for (Tile t : tiles_of_first_iteration) {
            fillSurroundingTiles(t);
        }
    }

    private void fillSurroundingTiles(Tile t){
        Intersection[] intersections_of_t = t.getIntersections();
        for (int i = 0; i < intersections_of_t.length; i=i+2) {
            Intersection curr_intersection = intersections_of_t[i];
            Tile[] tiles_of_curr = curr_intersection.getTiles();
            for (int j = 0; j < tiles_of_curr.length; j++) {
                Tile curr_tile = tiles_of_curr[j];
                if(curr_tile==null){
                    curr_tile = insertNewTile();
                    curr_intersection.setTile(j, curr_tile);
                }
            }
        }
    }

    /**
     * Builds a new tile with one of the possible resources, limited by the
     * corresponding *_left attributes and adds it to tiles, decrementing the
     * corresponding *_left attribute
     * */
    private Tile insertNewTile() {

    }
}
