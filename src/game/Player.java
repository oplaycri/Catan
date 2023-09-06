package game;

import map.Tile;

import java.util.Random;

public class Player {
    // Set methods will be in a changing/relative manner
    private int brick, lumber, ore, grain, wool = 0;
    private int settlements_left = 5;
    private int cities_left = 4;
    private int roads_left = 15;

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
    public int getTotalResources(){
        return brick + lumber + ore + grain + wool;
    }
    public Tile.Resource getRandomResource(){
        int totalResources = getTotalResources();
        int rng = new Random().nextInt(totalResources)+1;
        if(rng>(totalResources-=wool)){
            return Tile.Resource.PASTURE;
        }
        if(rng>(totalResources-=grain)){
            return Tile.Resource.FIELDS;
        }
        if(rng>(totalResources-=ore)){
            return Tile.Resource.MOUNTAINS;
        }
        if(rng>(totalResources-=lumber)){
            return Tile.Resource.FOREST;
        }
        return Tile.Resource.HILLS;
    }
}
