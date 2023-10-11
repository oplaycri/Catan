package map;

import game.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Map {

    // Used for getting a new Tile/Harbor. Will be 0 at the end of initialization
    private int specialHarbors_left = 5;
    private int genericHarbors_left = 4;
    private int hills_left = 3, mountains_left = 3;
    private int forests_left = 4, fields_left = 4, pastures_left = 4;
    private int deserts_left = 1;
    // Used for getting a new number for a tile. Will be empty after initialization
    LinkedList<Integer> numbers = new LinkedList<>();
    // Stores all the tiles, beginning at the center, then the first layer, then the second.
    // Does not include water tiles
    LinkedList<Tile> tiles = new LinkedList<>();
    private Random random = new Random();

    /**
     * Initializes the tiles of the map, their junctions, paths and numbers, as well as the robber.
     * Shall be called first.
     * */
    public void init() {
        Collections.addAll(numbers,2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);
        Tile start = new Tile(getNewTerrain(), 0, 0);
        tiles.add(start);
        // Inserting all the Tiles into a Graph-structure
        // Adding the first layer
        fillSurroundingTiles(start);
        LinkedList<Tile> temp = (LinkedList<Tile>) tiles.clone();
        // Adding the second layer
        for (Tile tile : temp) {
            fillSurroundingTiles(tile);
        }
        temp = (LinkedList<Tile>) tiles.clone();
        // Adding water tiles (third layer)
        for (Tile tile : temp) {
            fillSurroundingTiles(tile);
        }
        temp = (LinkedList<Tile>) tiles.clone();
        for (Tile tile : temp) {
            fillJunctions(tile);
        }
        temp = (LinkedList<Tile>) tiles.clone();
        for (Tile tile : temp) {
            fillEdges(tile);
        }
        temp = (LinkedList<Tile>) tiles.clone();
        for (Tile tile : temp) {
            fillNumbers(tile);
        }
        fillHarbors();
    }

    private void fillHarbors() {
        LinkedList<Edge> coastalEdges = new LinkedList<>();
        for(Tile t:tiles) {
            Tile[] neighbours = t.getNeighbours();
            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i].terrain == Tile.Terrain.WATER) {
                    coastalEdges.add(t.getEdges()[i]);
                }
            }
        }
        Random random = new Random();
        while (specialHarbors_left != 0 && genericHarbors_left != 0 && coastalEdges.size() != 0){
            int i = random.nextInt(coastalEdges.size());
            Edge e = coastalEdges.get(i);
            if(e.getA().getHarbor() != null || e.getB().getHarbor() != null){
                coastalEdges.remove(i);
                continue;
            }
            int rng = random.nextInt(specialHarbors_left + genericHarbors_left) + 1;
            Harbor harbor;
            if (rng <= specialHarbors_left){
                harbor = new SpecialHarbor();
                specialHarbors_left--;
            } else {
                harbor = new GenericHarbor();
                genericHarbors_left--;
            }
            e.setHarbor(harbor);
            coastalEdges.remove(i);
        }
    }

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Fills t.neighbours with new tiles.
     * In the end all neighbours of t shall know their neighbours, also initialized by t.
     * */
    private void fillSurroundingTiles(Tile t) {
        Tile[] neighbours = t.getNeighbours();
        int x, y;
        for (int i = 0; i < neighbours.length; i++) {
            x = t.x;
            y = t.y;
            if (neighbours[i] != null) {
                continue;
            }
            switch (i){
                case 0 -> {
                    x++;
                    y--;
                }
                case 1 -> {
                    x++;
                }
                case 2 -> {
                    y++;
                }
                case 3 -> {
                    x--;
                    y++;
                }
                case 4 -> {
                    x--;
                }
                case 5 -> {
                    y--;
                }
            }
            neighbours[i] = new Tile(getNewTerrain(), x, y);
            if (neighbours[i].terrain!= Tile.Terrain.WATER){
                tiles.add(neighbours[i]);
            }
            if (neighbours[i].terrain == Tile.Terrain.DESERT){
                neighbours[i].setHasRobber(true);
            }
            // Determine where t would be indexed at neighbours[i].neighbours and filling the entry with t
            int relPos = (i + 3) % 6;
            neighbours[i].getNeighbours()[relPos] = t;
        }
        // Set the neighbours' neighbours of t
        fillNeighbours(t);
    }
    /**
     * Returns a Resource for a new Tile, limited by the
     * corresponding *_left attributes and adds it to tiles, decrementing the
     * corresponding *_left attribute.
     * If no more possible resources are left, WATER shall be returned.
     */
    private Tile.Terrain getNewTerrain() {
        Random random = new Random();
        int total_left = hills_left + mountains_left + forests_left + fields_left + pastures_left + deserts_left;
        if (total_left == 0) {
            return Tile.Terrain.WATER;
        }
        int tileNumber = random.nextInt(total_left);
        Tile.Terrain resource;
        if (tileNumber >= (total_left = total_left - deserts_left)) {
            resource = Tile.Terrain.DESERT;
            deserts_left--;
        } else if (tileNumber >= (total_left = total_left - pastures_left)) {
            resource = Tile.Terrain.PASTURE;
            pastures_left--;
        } else if (tileNumber >= (total_left = total_left - fields_left)) {
            resource = Tile.Terrain.FIELDS;
            fields_left--;
        } else if (tileNumber >= (total_left = total_left - forests_left)) {
            resource = Tile.Terrain.FOREST;
            forests_left--;
        } else if (tileNumber >= (total_left = total_left - mountains_left)) {
            resource = Tile.Terrain.MOUNTAINS;
            mountains_left--;
        } else {
            resource = Tile.Terrain.HILLS;
            hills_left--;
        }
        return resource;
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
     * Fills the edges in t and between t's junctions.
     * Also fills neighbours of each Junction.
     * */
    private void fillEdges(Tile t) {
        Junction[] junctions = t.getJunctions();
        int edgePos = 1;
        int relEdgePos = 2;
        for (int i = 0; i < junctions.length; i++) {
            if (t.getEdges()[i]!=null){
                continue;
            }
            Junction a = junctions[i];
            Junction b = junctions[(i+1)%6];
            Edge e = new Edge(a, b);
            // Fill Edge in Tiles
            t.getEdges()[i] = e;
            t.getEdges()[(i+3)%6] = e;
            // Fill Edge in Junctions
            a.getEdges()[edgePos] = e;
            b.getEdges()[relEdgePos] = e;
            // Fill Neighbour in Junctions
            a.getNeighbours()[edgePos] = b;
            b.getNeighbours()[relEdgePos] = a;
            // Needs to be visualized
            if(i%2!=0) {
                edgePos = (edgePos + 1) % 3;
            } else {
                relEdgePos = (relEdgePos +1) % 3;
            }
        }
    }
    private void fillNumbers(Tile t){
        if(t.terrain == Tile.Terrain.DESERT || t.terrain == Tile.Terrain.WATER){
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
        if (t.HasRobber()||t.getN()==-1||t.getN()!=diceRoll){
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
            Tile.Terrain resource = t.terrain;
            owner.setResource(resource, owner.getResource(resource) + amount);
        }
    }
    // TODO Give Control over Resources to game.Game
    public void placeBuilding(BuildingContainer.Building b, BuildingContainer container, Player owner){
        container.placeBuilding(b, owner);
    }
    public boolean checkValidPlacement(BuildingContainer.Building b, BuildingContainer container, Player owner, boolean isInit){
        switch (b){
            case City -> {
                return checkValidCity(container, owner);
            }
            case Settlement -> {
                return checkValidSettlement(container, owner, isInit);
            }
            case Road -> {
                return checkValidRoad(container, owner);
            }
        }
        return false;
    }

    private boolean checkValidRoad(BuildingContainer container, Player owner) {
        // Basic check
        if(container instanceof Junction){
            return false;
        }
        Edge edge = (Edge) container;
        if(edge.getOwner() != null){
            return false;
        }

        Junction A = edge.getA();
        Junction B = edge.getB();
        // Check for adjacent Settlement or City
        if(A.getOwner() == owner || B.getOwner() == owner){
            return true;
        }
        // Check or adjacent road
        Edge[] temp = A.getEdges();
        for (Edge e : temp) {
            if(A.getOwner() != owner){
                break;
            }
            if (e == edge) {
                continue;
            }
            if (e.getOwner() == owner) {
                return true;
            }
        }
        temp = edge.getB().getEdges();
        for (Edge e : temp) {
            if(B.getOwner() != owner){
                break;
            }
            if (e == edge) {
                continue;
            }
            if (e.getOwner() == owner) {
                return true;
            }
        }
        return false;
    }

    private boolean checkValidSettlement(BuildingContainer container, Player owner, boolean isInit) {
        // Basic check
        if(container instanceof Edge){
            return false;
        }
        Junction junction = (Junction) container;
        if (junction.getOwner() != null){
            return false;
        }

        Edge[] temp = junction.getEdges();
        // Check for adjacent Settlement
        for(Edge edge : temp){
            Junction A = edge.getA();
            Junction B = edge.getB();
            if(A!=junction&&A.getOwner()!=null){
                return false;
            }
            if(B!=junction&&B.getOwner()!=null){
                return false;
            }
        }

        if(isInit){
            return true;
        }

        // Check for adjacent roads of owner
        for (Edge edge : temp) {
            if (edge.getOwner() == owner) {
                return true;
            }
        }

        return false;
    }

    private boolean checkValidCity(BuildingContainer container, Player owner) {
        // Basic check
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

    public Tile findRobber(){
        for (Tile tile: tiles){
            if (tile.HasRobber()){
                return tile;
            }
        }
        return null;
    }

    public void moveRobber(Tile tile){
        findRobber().setHasRobber(false);
        tile.setHasRobber(true);
    }

    public boolean canSteal(Player target) {
        Tile robbberTile = findRobber();
        for (Junction junction: robbberTile.getJunctions()) {
            if (junction.getBuilding() != null && junction.getOwner() == target && target.getTotalResources() > 0) {
                return true;
            }
        }
        return false;
    }
}
