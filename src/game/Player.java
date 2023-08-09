package game;

import map.BuildingContainer;

import java.util.LinkedList;

public class Player {
    // Set methods will be in a changing/relative manner
    private int brick, lumber, ore, grain, wool = 0;
    private int settlements_left = 5;
    private int cities_left = 4;
    private int roads_left = 15;
    private int settlements, cities, roads = 0;
    LinkedList<BuildingContainer> buildings = new LinkedList<BuildingContainer>();
    public void addBuildingContainerAndUpdate(BuildingContainer b){
        buildings.add(b);
        switch (b.getBuilding()){
            case Settlement -> {
                settlements_left--;
                settlements++;
            }
            case City -> {
                cities_left--;
                cities++;
            }
            case Road -> {
                roads_left--;
                roads++;
            }
        }
    }

    //region Get and Set
    public int getBrick() {
        return brick;
    }

    public void setBrick(int brick) {
        this.brick += brick;
    }

    public int getLumber() {
        return lumber;
    }

    public void setLumber(int lumber) {
        this.lumber += lumber;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore += ore;
    }

    public int getGrain() {
        return grain;
    }

    public int getSettlements() {
        return settlements;
    }
    public void setSettlements(int settlements) {
        this.settlements = settlements;
    }

    public int getCities() {
        return cities;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }

    public int getRoads() {
        return roads;
    }

    public void setRoads(int roads) {
        this.roads = roads;
    }

    public void setGrain(int grain) {
        this.grain += grain;
    }

    public int getWool() {
        return wool;
    }

    public void setWool(int wool) {
        this.wool += wool;
    }

    public int getSettlements_left() {
        return settlements_left;
    }

    public int getCities_left() {
        return cities_left;
    }

    public int getRoads_left() {
        return roads_left;
    }
    //endregion
}
