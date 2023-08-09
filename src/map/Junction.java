package map;

public class Junction extends BuildingContainer {
    private final Junction[] neighbours = new Junction[3];
    private final Edge[] edges = new Edge[3]; // Beginning at the top (right) and going clockwise
    Harbor harbor = null;

    public Junction[] getNeighbours() {
        return neighbours;
    }

    public Harbor getHarbor() {
        return harbor;
    }

    public void setHarbor(Harbor harbor) {
        this.harbor = harbor;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public void setEdge(int i, Edge e) {
        this.edges[i] = e;
    }
}
