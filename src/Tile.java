
public class Tile {
    public Tile(Resource resource) {
        this.resource = resource;
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
    private Tile[] neighbours = new Tile[6]; // Beginning at the top (right) and going clockwise
    private Junction[] junctions = new Junction[6]; // Beginning at the top (right) and going clockwise
    private Resource resource;

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
}
