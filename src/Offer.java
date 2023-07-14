public class Offer {
    Player from;
    Player to;
    Tile.Resource offered;
    int amountOffered;
    Tile.Resource requested;
    int amountRequested;

    public Offer(Tile.Resource offered, int amountOffered, Tile.Resource requested, int amountRequested) {
        this.offered = offered;
        this.amountOffered = amountOffered;
        this.requested = requested;
        this.amountRequested = amountRequested;
    }
}
