package cheaters.get.banned.features.include.map.elements.rooms;

import cheaters.get.banned.features.include.map.elements.DungeonColors;

import java.awt.*;

public enum RoomType {
    NORMAL(DungeonColors.BROWN),
    BLOOD(DungeonColors.RED),
    ENTRANCE(DungeonColors.GREEN),
    FAIRY(DungeonColors.PINK),
    YELLOW(DungeonColors.YELLOW),
    RARE(DungeonColors.RARE_ROOM),
    TRAP(DungeonColors.ORANGE),
    PUZZLE(DungeonColors.PURPLE);

    public final Color color;
    RoomType(Color color) {
        this.color = color;
    }
}
