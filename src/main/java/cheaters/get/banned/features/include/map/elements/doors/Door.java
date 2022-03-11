package cheaters.get.banned.features.include.map.elements.doors;

import cheaters.get.banned.features.include.map.elements.MapTile;
import org.apache.commons.lang3.NotImplementedException;

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
