public class Edge extends BuildingContainer{
    Junction A;
    Junction B;

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
}
