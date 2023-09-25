package game;

import map.Tile;

public class Offer {
    Player from;
    Player to;
    Tile.Terrain offered;
    int amountOffered;
    Tile.Terrain requested;
    int amountRequested;

    public Offer(Tile.Terrain offered, int amountOffered, Tile.Terrain requested, int amountRequested) {
        this.offered = offered;
        this.amountOffered = amountOffered;
        this.requested = requested;
        this.amountRequested = amountRequested;
    }
}
