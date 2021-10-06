package cheaters.get.banned.features.dungeonmap;

import net.minecraft.util.EnumFacing;

import java.awt.*;
import java.util.ArrayList;

public class DungeonLayout {

    public ArrayList<RoomTile> roomTiles = new ArrayList<>();
    public ArrayList<ConnectorTile> connectorTiles = new ArrayList<>();
    public int totalSecrets;
    public int totalCrypts;
    public int totalPuzzles;
    public boolean uncertainCrypts = false;
    public TrapType trapType;
    public boolean sent300ScoreMessage = false;

    enum TrapType {
        OLD, NEW
    }

    public static class ConnectorTile {
        public int x;
        public int z;
        public ConnectorTile.Type type;
        public boolean doorOpen = false;
        public EnumFacing direction;

        public ConnectorTile(int x, int z, ConnectorTile.Type type, EnumFacing direction) {
            this.x = x;
            this.z = z;
            this.type = type;
            this.direction = direction;
        }

        enum Type {
            WITHER_DOOR(Color.BLACK),
            BLOOD_DOOR(Room.Type.BLOOD.color),
            ENTRANCE_DOOR(Room.Type.ENTRANCE.color),
            NORMAL_DOOR(Room.Type.NORMAL.color),
            CONNECTOR(Room.Type.NORMAL.color),
            DEBUG(Color.MAGENTA);

            Color color;
            Type(Color color) {
                this.color = color;
            }
        }
    }

    public static class RoomTile {
        public int x;
        public int z;
        public Room room;
        public boolean isLarge;
        public boolean explored;
        public boolean greenCheck;

        public RoomTile(int x, int z, Room room) {
            this.x = x;
            this.z = z;
            this.room = room;
        }
    }

}
