package cheaters.get.banned.features.map.elements.rooms;

import cheaters.get.banned.features.map.elements.MapTile;

public class RoomTile extends MapTile {

    public Room room;

    public RoomTile(Room room) {
        super(room.type.color);
        this.room = room;
    }

}
