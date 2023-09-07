package map;

/**
 * Base component of the datastructure to construct a hexagonal Catan grid.
 * A Tile stores Information about its Resource, number, and whether it has been raided or not.
 * Additionally, a Tile knows all of its neighbouring Tiles, Junctions and Edges.
 * Tiles are managed by Map.
 */
public class Tile {
    private final Tile[] neighbours = new Tile[6]; // Beginning at the top (right) and going clockwise
    private final Junction[] junctions = new Junction[6]; // Beginning at the top (right) and going clockwise
    private final Edge[] edges = new Edge[6]; // Beginning at the top (right) and going clockwise
    private final Resource resource;
    private int n = -1;
    private boolean hasRobber = false;
    public Tile(Resource resource) {
        this.resource = resource;
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

    public Resource getResource() {
        return resource;
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

    public enum Resource {
        HILLS,
        FOREST,
        MOUNTAINS,
        FIELDS,
        PASTURE,
        DESERT,
        WATER
    }
}
