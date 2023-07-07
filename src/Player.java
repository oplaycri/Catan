public class Player {
    private int brick, lumber, ore, grain, wool = 0;
    private int settlements_left = 5;
    private int cities_left = 4;
    private int roads_left = 15;

    public int getBrick() {
        return brick;
    }

    public void setBrick(int brick) {
        this.brick = brick;
    }

    public int getLumber() {
        return lumber;
    }

    public void setLumber(int lumber) {
        this.lumber = lumber;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public int getGrain() {
        return grain;
    }

    public void setGrain(int grain) {
        this.grain = grain;
    }

    public int getWool() {
        return wool;
    }

    public void setWool(int wool) {
        this.wool = wool;
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
}
