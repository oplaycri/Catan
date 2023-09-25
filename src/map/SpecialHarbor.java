package map;

import java.util.LinkedList;
import java.util.Random;

public class SpecialHarbor extends Harbor{
    private static LinkedList<Tile.Terrain> resources_left = availableResources();
    private static LinkedList<Tile.Terrain> availableResources(){
        LinkedList<Tile.Terrain> resources_left = new LinkedList<>();
        resources_left.add(Tile.Terrain.HILLS);
        resources_left.add(Tile.Terrain.MOUNTAINS);
        resources_left.add(Tile.Terrain.FOREST);
        resources_left.add(Tile.Terrain.FIELDS);
        resources_left.add(Tile.Terrain.PASTURE);
        return resources_left;
    }
    private Tile.Terrain resource;
    public SpecialHarbor(){
        int rng = new Random().nextInt(resources_left.size());
        resource = resources_left.get(rng);
        resources_left.remove(rng);
    }
}
