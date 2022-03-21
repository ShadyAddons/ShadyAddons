package cheaters.get.banned.features.map.elements.rooms;

public class Room {

    public RoomType type;
    public String name;
    public int secrets;

    public Room(RoomType type, String name, int secrets) {
        this.type = type;
        this.name = name;
        this.secrets = secrets;
    }

    @Override
    public String toString() {
        return name;
    }

}
