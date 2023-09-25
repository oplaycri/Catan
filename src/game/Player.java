package game;

import map.BuildingContainer;

import java.util.LinkedList;

import map.Tile;

import java.util.Random;

public class Player {
    // Set methods will be in a changing/relative manner
    LinkedList<Resource> resources = new LinkedList<>();
    private Resource brick = new Resource("brick", 0), lumber = new Resource("lumber", 0),
            ore = new Resource("ore", 0), grain = new Resource("grain", 0),
            wool = new Resource("wool", 0);
    private Structure cities = new Structure("City", 4), settlements = new Structure("Settlement", 5),
            roads = new Structure("roads", 15);
    LinkedList<BuildingContainer> buildings = new LinkedList<>();

    public Player() {
        resources.add(brick);
        resources.add(lumber);
        resources.add(ore);
        resources.add(grain);
        resources.add(wool);
    }

    public void addBuildingContainerAndUpdate(BuildingContainer b){
        buildings.add(b);
        Structure s = mapBuilding(b.getBuilding());
        s.setAmount(s.getAmount() + 1);
        s.setLeft(s.getLeft() - 1);
    }
    /**
     * Map Tile.Resource to the corresponding int variables. DESERT and WATER not specified
     * */
    private Resource mapTerrains(Tile.Terrain terrain){
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
    private Structure mapBuilding(BuildingContainer.Building building){
        switch (building){
            case City -> {
                return cities;
            }
            case Settlement -> {
                return settlements;
            }
            case Road -> {
                return roads;
            }
        }
        return null;
    }


    //region Get and Set
    public int getResource(Tile.Terrain ter){
        return mapTerrains(ter).getAmount();
    }

    public void setResource(Tile.Terrain ter, int amount){
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
    //endregion
}
