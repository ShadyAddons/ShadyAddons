package cheaters.get.banned.features.map.elements.rooms;

import cheaters.get.banned.features.map.elements.DungeonColors;

import java.awt.*;

public enum RoomType {
    NORMAL(DungeonColors.BROWN),
    BLOOD(DungeonColors.RED),
    ENTRANCE(DungeonColors.GREEN),
    FAIRY(DungeonColors.FAIRY_ROOM),
    YELLOW(DungeonColors.YELLOW),
    RARE(DungeonColors.RARE_ROOM),
    TRAP(DungeonColors.ORANGE),
    PUZZLE(DungeonColors.PUZZLE_ROOM);

    public final Color color;
    RoomType(Color color) {
        this.color = color;
    }
}
