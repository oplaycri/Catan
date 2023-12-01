package map;

/**
 * Base component of the datastructure to construct a hexagonal Catan grid.
 * A Tile stores Information about its Resource, number, position, and whether it has been raided or not.
 * Additionally, a Tile knows all of its neighbouring Tiles, Junctions and Edges.
 * Tiles are managed by Map.
 */
public class Tile {
    public final int x; // Horizontal position. Increases to the right
    public final int y; // "Vertical" position. Increases to the right-down
    private final Tile[] neighbours = new Tile[6]; // Beginning at the top (right) and going clockwise
    private final Junction[] junctions = new Junction[6]; // Beginning at the top (right) and going clockwise
    private final Edge[] edges = new Edge[6]; // Beginning at the top (right) and going clockwise
    public final Terrain terrain;
    private int n = -1;
    private boolean hasRobber = false;
    public Tile(Terrain terrain, int x, int y) {
        this.terrain = terrain;
        this.x = x;
        this.y = y;
    }

    public Junction[] getJunctions() {
        return junctions;
    }

    public Tile[] getNeighbours() {
        return neighbours;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public boolean HasRobber() {
        return hasRobber;
    }

    public void setHasRobber(boolean hasRobber) {
        this.hasRobber = hasRobber;
    }


}
