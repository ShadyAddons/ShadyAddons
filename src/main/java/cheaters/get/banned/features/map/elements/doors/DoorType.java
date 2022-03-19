package cheaters.get.banned.features.map.elements.doors;

import cheaters.get.banned.features.map.elements.DungeonColors;

import java.awt.*;

public enum DoorType {
    WITHER(DungeonColors.WITHER_DOOR),
    BLOOD(DungeonColors.RED),
    ENTRANCE(DungeonColors.GREEN),
    NORMAL(DungeonColors.BROWN);

    public final Color color;
    DoorType(Color color) {
        this.color = color;
    }
}
