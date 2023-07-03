public class Intersection extends BuildingContainer{
    private Tile[] tiles = new Tile[3]; // Beginning at the top (right) and going clockwise
    private Edge[] edges = new Edge[3]; // Beginning at the top (right) and going clockwise

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTile(int i, Tile t) {
        this.tiles[i] = t;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public void setEdge(int i, Edge e) {
        this.edges[i] = e;
    }
}
