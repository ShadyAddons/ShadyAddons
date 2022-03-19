package cheaters.get.banned.features.map.elements.rooms;

import cheaters.get.banned.features.map.elements.MapTile;

public class Room extends MapTile {

    public RoomType type;
    public String name;
    public int secrets;

    public Room(RoomType type, String name, int secrets) {
        super(type.color);
        this.type = type;
        this.name = name;
        this.secrets = secrets;
    }

    @Override
    public String toString() {
        return name;
    }

}
