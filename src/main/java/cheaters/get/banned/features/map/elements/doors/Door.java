package cheaters.get.banned.features.map.elements.doors;

import cheaters.get.banned.features.map.elements.MapTile;

public class Door extends MapTile {

    public DoorType type;

    public Door(DoorType type) {
        super(type.color);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Door (" + type.name() + ")";
    }

}
