package map;

public class Tile {
    private int n = -1;
    private boolean raided = false;
    public enum Resource {
        HILLS,
        FOREST,
        MOUNTAINS,
        FIELDS,
        PASTURE,
        DESERT,
        WATER
    }
    public Tile(Resource resource) {
        this.resource = resource;
    }


    private final Tile[] neighbours = new Tile[6]; // Beginning at the top (right) and going clockwise
    private final Junction[] junctions = new Junction[6]; // Beginning at the top (right) and going clockwise
    private final Resource resource;

    public Junction[] getJunctions() {
        return junctions;
    }

    public Tile[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbour(int i, Tile t){
        neighbours[i] = t;
    }

    public Tile getNeighbour(int i){
        return neighbours[i];
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

    public boolean isRaided() {
        return raided;
    }

    public void setRaided(boolean raided) {
        this.raided = raided;
    }
}
