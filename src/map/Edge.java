package map;

public class Edge extends BuildingContainer {
    Junction A;
    Junction B;
    Harbor harbor = null;

    public Edge(Junction a, Junction b) {
        A = a;
        B = b;
    }

    public Junction getA() {
        return A;
    }

    public Junction getB() {
        return B;
    }

    public Harbor getHarbor() {
        return harbor;
    }

    public void setHarbor(Harbor harbor) {
        this.harbor = harbor;

        A.setHarbor(harbor);
        B.setHarbor(harbor);
    }
}
