package cheaters.get.banned.features.map.elements;

import cheaters.get.banned.features.map.elements.rooms.RoomStatus;

import java.awt.*;

public abstract class MapTile {

    public int color;
    public RoomStatus status = RoomStatus.UNDISCOVERED;

    public MapTile(Color color) {
        this.color = color.getRGB();
    }

}
