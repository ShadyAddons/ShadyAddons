package cheaters.get.banned.features.dungeonmap;

import cheaters.get.banned.Shady;
import com.google.gson.*;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class RoomLoader {

    public static ArrayList<Room> rooms = new ArrayList<>();
    public static HashSet<Integer> yLevels = new HashSet<>();

    public static void load() {
        try {
            ResourceLocation roomsResource = new ResourceLocation("shadyaddons:dungeonscanner/rooms.json");
            InputStream inputStream = Shady.mc.getResourceManager().getResource(roomsResource).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            JsonParser parser = new JsonParser();
            JsonObject fullJson = parser.parse(reader).getAsJsonObject();
            JsonArray roomsArray = fullJson.getAsJsonArray("dungeonRooms");

            for(JsonElement roomElement : roomsArray) {
                Room room = new Room();
                JsonObject roomObject = roomElement.getAsJsonObject();

                room.name = roomObject.get("roomName").getAsString();
                room.type = getTypeFromString(roomObject.get("roomType").getAsString());
                room.secrets = roomObject.get("secrets").getAsInt();
                room.yLevel = roomObject.get("yLevel").getAsInt();
                room.crypts = null;
                try {
                    room.crypts = Integer.parseInt(roomObject.get("crypts").getAsString());
                } catch(NumberFormatException ignored) {}
                JsonObject blocksObject = roomObject.getAsJsonObject("blocks");
                HashMap<String, Object> stringBlocksMap = new Gson().fromJson(blocksObject, HashMap.class);
                HashMap<Block, String> blockConditions = new HashMap<>();
                for(Map.Entry<String, Object> block : stringBlocksMap.entrySet()) {
                    blockConditions.put(Block.getBlockFromName("minecraft:"+block.getKey()), (String)block.getValue());
                }
                room.blockConditions = blockConditions;

                yLevels.add(room.yLevel);
                rooms.add(room);
            }
        } catch(Exception ignored) {}
    }

    private static Room.Type getTypeFromString(String string) {
        switch(string) {
            case "normal":
                return Room.Type.NORMAL;

            case "blood":
                return Room.Type.BLOOD;

            case "fairy":
                return Room.Type.FAIRY;

            case "green":
                return Room.Type.ENTRANCE;

            case "puzzle":
                return Room.Type.PUZZLE;

            case "trap":
                return Room.Type.TRAP;

            case "yellow":
                return Room.Type.MINIBOSS;

            default:
                return Room.Type.UNKNOWN;
        }
    }

}
