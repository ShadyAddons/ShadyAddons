package cheaters.get.banned.features.include.map.elements.rooms;

import cheaters.get.banned.features.include.map.elements.DungeonColors;
import cheaters.get.banned.features.include.map.elements.MapTile;

public class Separator extends MapTile {

    public static final Separator GENERIC = new Separator();

    public Separator() {
        super(DungeonColors.BROWN);
    }

    @Override
    public String toString() {
        return "Room Separator";
    }

}
