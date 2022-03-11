package cheaters.get.banned.features.include.map;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.map.elements.rooms.Room;
import cheaters.get.banned.features.include.map.elements.rooms.RoomType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class MapManager {

    public static HashMap<Integer, Room> rooms = new HashMap<>();
    public static HashSet<Room> uniqueRooms = new HashSet<>();
    public static Map map;

    public static void loadRooms() {
        try {
            ResourceLocation roomsResource = new ResourceLocation("shadyaddons:dungeonscanner/new-rooms.json");
            InputStream inputStream = Shady.mc.getResourceManager().getResource(roomsResource).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            JsonArray roomsArray = json.getAsJsonArray("rooms");

            for(JsonElement roomElement : roomsArray) {
                JsonObject roomObject = roomElement.getAsJsonObject();

                String type = roomObject.get("type").getAsString().toUpperCase();
                String name = roomObject.get("name").getAsString();
                int secrets = roomObject.get("secrets").getAsInt();
                JsonArray cores = roomObject.get("cores").getAsJsonArray();

                Room room = new Room(RoomType.valueOf(type), name, secrets);

                for(JsonElement core : cores) {
                    int coreNumber = core.getAsInt();
                    rooms.put(coreNumber, room);
                }

                uniqueRooms.add(room);
            }
        } catch(Exception exception) {
            System.out.println("Error loading dungeon rooms");
            exception.printStackTrace();
        }
    }

    /**
     * Prints all the loaded rooms to the console for debugging.
     */
    public static void printRooms() {
        for(Room room : uniqueRooms) {
            System.out.println("Name: " + room.name);
            System.out.println("Secrets: " + room.secrets);
            System.out.println("Type: " + room.type.name());
            System.out.println();
        }
    }

    public static void scan() {
        try {
            map = MapScanner.getScan();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

}
