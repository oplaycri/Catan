public class Junction extends BuildingContainer{
    private Edge[] edges = new Edge[3]; // Beginning at the top (right) and going clockwise

    public Edge[] getEdges() {
        return edges;
    }

    public void setEdge(int i, Edge e) {
        this.edges[i] = e;
    }
}
