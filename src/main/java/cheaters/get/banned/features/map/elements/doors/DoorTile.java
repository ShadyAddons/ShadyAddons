package cheaters.get.banned.features.map.elements.doors;

import cheaters.get.banned.features.map.elements.MapTile;

public class DoorTile extends MapTile {

    public DoorType type;

    public DoorTile(DoorType type) {
        super(type.color);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Door (" + type.name() + ")";
    }

}
