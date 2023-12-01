package map;
import game.Player;

public class BuildingContainer {

    private Building building = null;
    private Player owner = null;

    public void placeBuilding(Building b, Player p){}

    public Building getBuilding() {
        return building;
    }

    public Player getOwner() {
        return owner;
    }
}
