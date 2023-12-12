package game;

import map.BuildingContainer;

import java.util.LinkedList;

import java.util.Random;

import map.Building;
import map.Terrain;

public class Player {
    // Set methods will be in a changing/relative manner
    LinkedList<Resource> resources = new LinkedList<>();
    private Resource brick = new Resource("brick", 0), lumber = new Resource("lumber", 0),
            ore = new Resource("ore", 0), grain = new Resource("grain", 0),
            wool = new Resource("wool", 0);
    private Structure cities = new Structure("City", 4), settlements = new Structure("Settlement", 5),
            roads = new Structure("roads", 15);
    LinkedList<BuildingContainer> buildingContainers = new LinkedList<>();
    LinkedList<Structure> structures = new LinkedList<>();

    public Player() {
        // Position is important!
        resources.add(brick);
        resources.add(lumber);
        resources.add(ore);
        resources.add(grain);
        resources.add(wool);

        structures.add(cities);
        structures.add(settlements);
        structures.add(roads);
    }

    public void addBuildingContainerAndUpdate(BuildingContainer b){
        buildingContainers.add(b);
        Structure s = mapBuilding(b.getBuilding());
        s.setAmount(s.getAmount() + 1);
        s.setLeft(s.getLeft() - 1);
    }
    /**
     * Map Tile.Resource to the corresponding int variables. DESERT and WATER not specified
     * */
    private Resource mapTerrains(Terrain terrain){
        switch (terrain){
            case HILLS -> {
                return brick;
            }
            case FOREST -> {
                return lumber;
            }
            case MOUNTAINS -> {
                return ore;
            }
            case FIELDS -> {
                return grain;
            }
            case PASTURE -> {
                return wool;
            }
        }
        return null;
    }
    private Structure mapBuilding(Building building){
        switch (building){
            case CITY -> {
                return cities;
            }
            case SETTLEMENT -> {
                return settlements;
            }
            case ROAD -> {
                return roads;
            }
        }
        return null;
    }

    /**
     * Returns whether the player has the required resources.
     * <p>
     *
     * @param required array of size 5, the position specifies the resource
     * @return true if there is enough of every resource
     * */
    public boolean hasResources(int[] required){
        Resource res;
        for (int i = 0; i<required.length; i++){
            res = resources.get(i);
            if (res.getAmount() < required[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether the player has the required resources.
     * <p>
     *
     * @param values see {@link Player#hasResources(int[])}
     * @return true if there is enough of every resource
     * */
    public void decrementResources(int[] values){
        Resource res;
        for (int i = 0; i<values.length; i++){
            res = resources.get(i);
            res.increment(- values[i]);
        }
    }


    //region Get and Set
    public int getResource(Terrain ter){
        return mapTerrains(ter).getAmount();
    }

    public void setResource(Terrain ter, int amount){
        mapTerrains(ter).setAmount(amount);
    }
    public int getTotalResources(){
        int val = 0;
        for(Resource res: resources){
            val += res.getAmount();
        }
        return val;
    }
    public Resource getRandomResource(){
        if(getTotalResources() == 0){
            return null;
        }
        int rng = new Random().nextInt(getTotalResources()) + 1;
        for(Resource res:resources){
            if(rng <= res.getAmount()){
                return res;
            }
            rng -= res.getAmount();
        }
        return null;
    }
    public Resource getResource(String name){
        for(Resource res: resources){
            if (name.equals(res.name)){
                return res;
            }
        }
        return null;
    }
    public Structure getStructure(Building b){
        return mapBuilding(b);
    }
    //endregion
}
