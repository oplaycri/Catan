public class BuildingContainer {
    public enum Building{
        Settlement,
        City,
        Road
    }
    private Building building = null;
    private Player owner = null;

    public void setBuilding(Building b, Player p){
        building = b;
        owner = p;
    }

    public Building getBuilding() {
        return building;
    }

    public Player getOwner() {
        return owner;
    }
}
