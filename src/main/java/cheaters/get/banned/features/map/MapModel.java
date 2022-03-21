package cheaters.get.banned.features.map;

import cheaters.get.banned.features.map.elements.MapTile;
import cheaters.get.banned.features.map.elements.rooms.Room;
import cheaters.get.banned.features.map.elements.rooms.RoomTile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MapModel {

    public MapTile[][] elements = new MapTile[11][11];
    public ArrayList<RoomTile> roomTiles = new ArrayList<>();
    public HashSet<Room> uniqueRooms = new HashSet<>();
    public boolean allLoaded = true;
    public int totalSecrets = 0;
    
    public void clear() {
        roomTiles.clear();
        totalSecrets = 0;
        allLoaded = true;

        for(MapTile[] element : elements) {
            Arrays.fill(element, null);
        }
    }

}
