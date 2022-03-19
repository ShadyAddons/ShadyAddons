package cheaters.get.banned.features.map;

import cheaters.get.banned.features.map.elements.MapTile;
import cheaters.get.banned.features.map.elements.rooms.Room;

import java.util.ArrayList;
import java.util.Arrays;

public class MapModel {

    public MapTile[][] elements = new MapTile[11][11];
    public ArrayList<Room> rooms = new ArrayList<>();
    public boolean allLoaded = true;
    public int totalSecrets = 0;
    
    public void clear() {
        rooms.clear();
        totalSecrets = 0;
        allLoaded = true;

        for(MapTile[] element : elements) {
            Arrays.fill(element, null);
        }
    }

}
