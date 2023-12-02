package game;

import map.Tile;
import map.Terrain;
import static map.Terrain.*;

public class Offer {
    Player from;
    Player to;
    Terrain offered;
    int amountOffered;
    Terrain requested;
    int amountRequested;

    public Offer(Terrain offered, int amountOffered, Terrain requested, int amountRequested) {
        this.offered = offered;
        this.amountOffered = amountOffered;
        this.requested = requested;
        this.amountRequested = amountRequested;
    }
}
