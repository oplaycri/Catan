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
}
