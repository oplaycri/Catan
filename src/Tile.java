
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
        DESERT
    }
    private Intersection[] Intersections = new Intersection[6]; // Beginning at the top (right) and going clockwise
    private Resource resource;

    public Intersection[] getIntersections() {
        return Intersections;
    }
}
