package map;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class SpecialHarbor extends Harbor{
    private static LinkedList<Tile.Resource> resources_left = availableResources();
    private static LinkedList<Tile.Resource> availableResources(){
        LinkedList<Tile.Resource> resources_left = new LinkedList<>();
        resources_left.add(Tile.Resource.HILLS);
        resources_left.add(Tile.Resource.MOUNTAINS);
        resources_left.add(Tile.Resource.FOREST);
        resources_left.add(Tile.Resource.FIELDS);
        resources_left.add(Tile.Resource.PASTURE);
        return resources_left;
    }
    private Tile.Resource resource;
    public SpecialHarbor(){
        int rng = new Random().nextInt(resources_left.size());
        resource = resources_left.get(rng);
        resources_left.remove(rng);
    }
}
