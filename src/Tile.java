
public class Tile {
    public enum Resource {
        HILLS,
        FOREST,
        MOUNTAINS,
        FIELDS,
        PASTURE,
        DESERT
    }

    private Intersection[] Intersections = new Intersection[6]; // Beginning at the top and going clockwise
    private Resource resource;

    public Intersection[] getIntersections() {
        return Intersections;
    }
}
