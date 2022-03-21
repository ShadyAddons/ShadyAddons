package cheaters.get.banned.features.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class RoomLists {

    public static final HashMap<String, String> shortNames = new HashMap<String, String>(){{
        put("Old Trap", "Old");
        put("New Trap", "New");
        put("Boulder", "Box");
        put("Creeper Beams", "Beams");
        put("Teleport Maze", "Maze");
        put("Ice Path", "S.Fish");
        put("Ice Fill", "Fill");
        put("Tic Tac Toe", "TTT");
        put("Water Board", "Water");
        put("Bomb Defuse", "Bomb");
        put("Three Weirdos", "3 Men");
        put("King Midas", "Midas");
        put("Shadow Assassin", "SA");
    }};

    public static final ArrayList<String> slowRooms = new ArrayList<>(Arrays.asList(
            "Mines",
            "Spider",
            "Pit",
            "Crypt",
            "Wizard",
            "Cathedral"
    ));

}