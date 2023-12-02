package map;

import java.util.LinkedList;
import java.util.Random;
import static map.Terrain.*;

public class SpecialHarbor extends Harbor{
    private static LinkedList<Terrain> resources_left = availableResources();
    private static LinkedList<Terrain> availableResources(){
        LinkedList<Terrain> resources_left = new LinkedList<>();
        resources_left.add(HILLS);
        resources_left.add(MOUNTAINS);
        resources_left.add(FOREST);
        resources_left.add(FIELDS);
        resources_left.add(PASTURE);
        return resources_left;
    }
    private Terrain resource;
    public SpecialHarbor(){
        int rng = new Random().nextInt(resources_left.size());
        resource = resources_left.get(rng);
        resources_left.remove(rng);
    }
}
