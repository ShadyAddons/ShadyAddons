package cheaters.get.banned.features.include.map;

import cheaters.get.banned.features.include.map.elements.MapTile;

import java.util.Arrays;

public class Map {

    public MapTile[][] elements = new MapTile[11][11];
    
    public void clear() {
        for(MapTile[] element : elements) {
            Arrays.fill(element, null);
        }
    }

}
