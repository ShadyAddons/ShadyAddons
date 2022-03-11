package cheaters.get.banned.features.include.map.elements;

import java.awt.*;

public abstract class MapTile {

    public int color;

    public MapTile(Color color) {
        this.color = color.getRGB();
    }

}
