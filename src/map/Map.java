package map;

import game.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Map {

    // Used for getting a new tile. Will be 0 at the end of initialization
    private int hills_left, mountains_left = 3;
    private int forests_left, fields_left, pastures_left = 4;
    private int deserts_left = 1;
    // Used for getting a new number for a tile. Will be empty after initialization
    LinkedList<Integer> numbers = (LinkedList<Integer>) Arrays.asList(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);
    // Stores all the tiles, beginning at the center, then the first layer, then the second.
    // Does not include water tiles
    LinkedList<Tile> tiles = new LinkedList<>();

    /**
     * Initializes the tiles of the map, their junctions, paths and numbers, as well as the robber.
     * Shall be called first.
     * */
    public void init() {
        Tile start = getNewTile();
        tiles.add(start);
        // Inserting all the Tiles into a Graph-structure
        // Adding the first layer
        fillSurroundingTiles(start);
        // Adding the second layer
        for (Tile tile : tiles) {
            fillSurroundingTiles(tile);
        }
        // Adding water tiles (third layer)
        for (Tile tile : tiles) {
            fillSurroundingTiles(tile);
        }

        for (Tile tile : tiles) {
            fillJunctions(tile);
        }
        for (Tile tile : tiles) {
            fillEdges(tile);
        }
        for (Tile tile : tiles) {
            fillNumbers(tile);
        }
    }


    /**
     * Fills t.neighbours with new tiles.
     * In the end all neighbours of t shall know their neighbours, also initialized by t.
     * */
    private void fillSurroundingTiles(Tile t) {
        Tile[] neighbours = t.getNeighbours();
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] != null) {
                continue;
            }
            neighbours[i] = getNewTile();
            if (neighbours[i].getResource() != Tile.Resource.WATER){
                tiles.add(neighbours[i]);
            }
            if (neighbours[i].getResource() == Tile.Resource.DESERT){
                neighbours[i].setRaided(true);
            }
            // Determine where t would be indexed at neighbours[i].neighbours and filling the entry with t
            int relPos = (i + 3) % 6;
            neighbours[i].setNeighbour(relPos, t);
        }
        // Set the neighbours' neighbours of t
        fillNeighbours(t);
    }
    /**
     * Builds a new tile with one of the possible resources, limited by the
     * corresponding *_left attributes and adds it to tiles, decrementing the
     * corresponding *_left attribute.
     * If no more possible resources are left, WATER shall be returned.
     */
    private Tile getNewTile() {
        Random random = new Random();
        int total_left = hills_left + mountains_left + forests_left + fields_left + pastures_left + deserts_left;
        if (total_left == 0) {
            return new Tile(Tile.Resource.WATER);
        }
        int tileNumber = random.nextInt(total_left) + 1; // +1 makes it easier to match
        Tile.Resource resource;
        if (tileNumber >= (total_left = total_left - deserts_left)) {
            resource = Tile.Resource.DESERT;
            deserts_left--;
        } else if (tileNumber >= (total_left = total_left - pastures_left)) {
            resource = Tile.Resource.PASTURE;
            pastures_left--;
        } else if (tileNumber >= (total_left = total_left - fields_left)) {
            resource = Tile.Resource.FIELDS;
            fields_left--;
        } else if (tileNumber >= (total_left = total_left - forests_left)) {
            resource = Tile.Resource.FOREST;
            forests_left--;
        } else if (tileNumber >= (total_left = total_left - mountains_left)) {
            resource = Tile.Resource.MOUNTAINS;
            mountains_left--;
        } else {
            resource = Tile.Resource.HILLS;
            hills_left--;
        }
        return new Tile(resource);
    }
    /**
     * Shall be called after t filled its neighbours.
     * For each neighbour i of t, t will tell i their (2) neighbours left and right,
     * which will be set accordingly.
     * */
    private void fillNeighbours(Tile t) {
        Tile[] neighbours = t.getNeighbours();
        for (int i = 0; i < neighbours.length; i++) {
            neighbours[i].getNeighbours()[(i + 4) % 6] = neighbours[(6 + i - 1) % 6]; // +6 makes it positive
            neighbours[i].getNeighbours()[(i + 2) % 6] = neighbours[(i + 1) % 6];
        }
    }
    /**
     * Fills t's junctions and the tell them t's neighbours, so they can set them too.
     * */
    private void fillJunctions(Tile t) {
        Junction[] junctions = t.getJunctions();
        for (int i = 0; i < junctions.length; i++) {
            if (junctions[i] != null){
                continue;
            }
            junctions[i] = new Junction();
            t.getNeighbours()[(6+i-1)%6].getJunctions()[(i+2)%6] = junctions[i];
            t.getNeighbours()[i].getJunctions()[(6+i-2)%6] = junctions[i];
        }
    }
    /**
     * Fills the edges between t's junctions.
     * */
    private void fillEdges(Tile t) {
        Junction[] junctions = t.getJunctions();
        int edgePos = 1;
        int relEdgePos = 2;
        for (int i = 0; i < junctions.length; i++) {
            Edge e = new Edge(junctions[i], junctions[(i+1)%6]);
            junctions[i].getEdges()[edgePos] = e;
            junctions[(i+1)%6].getEdges()[relEdgePos] = e;
            // Needs to be visualized
            if(i%2!=0) {
                edgePos = (edgePos + 1) % 3;
            } else {
                relEdgePos = (relEdgePos +1) % 3;
            }
        }
    }
    private void fillNumbers(Tile t){
        if(t.getResource() == Tile.Resource.DESERT || t.getResource() == Tile.Resource.WATER){
            return;
        }
        Random random = new Random();
        int index = random.nextInt(numbers.size());
        t.setN(numbers.get(index));
        numbers.remove(index);
    }
    /**
     * Updates player resources for every tile and player.
     * */
    public void updateResources(int diceRoll){
        for (Tile t: tiles){
            updateResourceTile(t, diceRoll);
        }
    }
    /**
     * Updates player resources for t.
     * For each Building in t's junctions the owner of said building will get an according amount of resources
     * (brick, lumber, ore, grain or wool) (+1 or +2).
     * */
    private void updateResourceTile(Tile t, int diceRoll){
        if (t.isRaided()||t.getN()==-1||t.getN()!=diceRoll){
            return;
        }
        Junction[] junctions = t.getJunctions();
        for (Junction j: junctions){
            BuildingContainer.Building building = j.getBuilding();
            if(building == null){
                continue;
            }
            Player owner = j.getOwner();
            int amount = 0;
            switch (building){
                case Settlement -> amount = 1;
                case City -> amount = 2;
            }
            Tile.Resource resource = t.getResource();
            switch (resource){
                // Set methods are in a changing/relative manner
                case HILLS -> {
                    owner.setBrick(amount);
                }
                case FOREST -> {
                    owner.setLumber(amount);
                }
                case MOUNTAINS -> {
                    owner.setOre(amount);
                }
                case FIELDS -> {
                    owner.setGrain(amount);
                }
                case PASTURE -> {
                    owner.setWool(amount);
                }
            }
        }
    }
    // TODO Give Control over Resources to game.Game
    public void placeBuilding(BuildingContainer.Building b, BuildingContainer container, Player owner){
        container.setBuilding(b, owner);
        switch (b){
            case City ->{
                owner.setGrain(-2);
                owner.setOre(-3);
            }
            case Settlement -> {
                owner.setWool(-1);
                owner.setGrain(-1);
                owner.setLumber(-1);
                owner.setBrick(-1);
            }
            case Road -> {
                owner.setLumber(-1);
                owner.setBrick(-1);
            }
        }
    }
    public boolean checkValidPlacement(BuildingContainer.Building b, BuildingContainer container, Player owner){
        switch (b){
            case City -> {
                return checkValidCity(container, owner);
            }
            case Settlement -> {
                return checkValidSettlement(container, owner);
            }
            case Road -> {
                return checkValidRoad(container, owner);
            }
        }
        return true;
    }

    private boolean checkValidRoad(BuildingContainer container, Player owner) {
        if(owner.getBrick() < 1 || owner.getLumber()<1){
            return false;
        }
        if(container instanceof Junction){
            return false;
        }
        Edge edge = (Edge) container;
        if(edge.getOwner() != null){
            return false;
        }
        Edge[] temp = edge.getA().getEdges();
        for (Edge e : temp) {
            if (e == edge) {
                continue;
            }
            if (e.getOwner() == owner) {
                return true;
            }
        }
        temp = edge.getB().getEdges();
        for (Edge e : temp) {
            if (e == edge) {
                continue;
            }
            if (e.getOwner() == owner) {
                return true;
            }
        }
        return false;
    }

    private boolean checkValidSettlement(BuildingContainer container, Player owner) {
        if(owner.getBrick() < 1 || owner.getGrain() < 1 || owner.getLumber()<1 || owner.getWool() < 1){
            return false;
        }
        if(container instanceof Edge){
            return false;
        }
        Junction junction = (Junction) container;
        if (junction.getOwner() != null){
            return false;
        }
        Edge[] temp = junction.getEdges();
        for (Edge edge : temp) {
            if (edge.getOwner() == owner) {
                return true;
            }
        }
        return false;
    }

    private boolean checkValidCity(BuildingContainer container, Player owner) {
        if(owner.getGrain() < 2 || owner.getOre()<3){
            return false;
        }
        if(container instanceof Edge){
            return false;
        }
        Junction junction = (Junction) container;
        if (junction.getOwner() != owner){
            return false;
        }
        if(junction.getBuilding() != BuildingContainer.Building.Settlement){
            return false;
        }
        return true;
    }
}
