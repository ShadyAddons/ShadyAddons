package cheaters.get.banned.features.map;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.features.map.elements.MapTile;
import cheaters.get.banned.features.map.elements.rooms.Room;
import cheaters.get.banned.features.map.elements.rooms.RoomStatus;
import cheaters.get.banned.features.map.elements.rooms.RoomType;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MapController {

    public static HashMap<Integer, Room> rooms = new HashMap<>();
    public static HashSet<Room> uniqueRooms = new HashSet<>();
    public static MapModel scannedMap;

    public static boolean isScanning = false;
    private static long lastScan = 0;

    public static int[] startCorner = new int[]{5, 5};
    public static float multiplier = 1.6f;
    public static int roomSize = 16;

    public static void calibrate() {
        Utils.log("Beginning calibration");
        switch(DungeonUtils.floor) {
            case FLOOR_1:
                startCorner[0] = 22;
                startCorner[1] = 11;
                break;

            case FLOOR_2:
            case FLOOR_3:
                startCorner[0] = 11;
                startCorner[1] = 11;
                break;

            case FLOOR_4:
                if(scannedMap.rooms.size() > 25) {
                    startCorner[0] = 5;
                    startCorner[1] = 16;
                }
                break;

            default:
                if(scannedMap.rooms.size() == 30) {
                    startCorner[0] = 16;
                    startCorner[1] = 5;
                } else if(scannedMap.rooms.size() == 25) {
                    startCorner[0] = 11;
                    startCorner[1] = 11;
                } else {
                    startCorner[0] = 5;
                    startCorner[1] = 5;
                }
        }

        if(DungeonUtils.inFloor(
                DungeonUtils.Floor.FLOOR_1,
                DungeonUtils.Floor.FLOOR_2,
                DungeonUtils.Floor.FLOOR_3,
                DungeonUtils.Floor.MASTER_1,
                DungeonUtils.Floor.MASTER_2,
                DungeonUtils.Floor.MASTER_3
        ) || scannedMap.rooms.size() == 24) {
            roomSize = 18;
        } else {
            roomSize = 16;
        }

        multiplier = 32 / (roomSize + 4f);

        Utils.log("Calibration finished");
        Utils.log("  startCorner: " + Arrays.toString(startCorner));
        Utils.log("  roomSize: " + roomSize);
        Utils.log("  multiplier: " + multiplier);
    }

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
        Utils.log("Beginning scan");
        try {
            lastScan = System.currentTimeMillis();
            new Thread(() -> {
                isScanning = true;
                scannedMap = MapScanner.getScan();
                if(scannedMap.allLoaded) calibrate();
                isScanning = false;
            }, "ShadyAddons-DungeonScan").start();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    private static boolean shouldScan() {
        return
            Utils.inDungeon &&
            !isScanning &&
            System.currentTimeMillis() - lastScan >= 250 &&
            DungeonUtils.floor != null &&
            (scannedMap == null || !scannedMap.allLoaded);
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(shouldScan()) scan();

        if(event.every(10) && scannedMap != null && scannedMap.allLoaded)  {
            new Thread(MapController::updateRoomStatuses).start();
        }
    }

    /**
     * Updates the the {@link RoomStatus} for {@link Room}s using the colors
     * from the handheld map. Leaves the statuses unchanged on failure.
     */
    public static void updateRoomStatuses() {
        MapData mapData = getMapData();
        if(mapData == null) return;
        byte[] mapColors = mapData.colors;

        Utils.log("Updating room statuses");

        int startX = startCorner[0] + Math.floorDiv(roomSize, 2);
        int startZ = startCorner[1] + Math.floorDiv(roomSize, 2);
        int increment = Math.floorDiv(roomSize, 2) + 2;

        for(int x = 0; x <= 10; x++) {
            for(int y = 0; y <= 10; y++) {
                int mapX = startX + x * increment;
                int mapY = startZ + y * increment;

                if(mapX >= 128 || mapY >= 128) continue;

                MapTile tile = scannedMap.elements[y][x];
                if(tile == null) continue;

                int color = Byte.toUnsignedInt(mapColors[(mapY << 7) + mapX]);
                if(color != 0) Utils.log("Color is " + color);

                switch(color) {
                    case 0: // Transparent
                    case 85: // Gray (question mark rooms)
                    case 119: // Black (wither doors)
                        tile.status = RoomStatus.UNDISCOVERED;
                        break;

                    case 18: // Red
                        if(tile instanceof Room) {
                            if(((Room) tile).type == RoomType.BLOOD) {
                                tile.status = RoomStatus.DISCOVERED;
                            } else if(((Room) tile).type == RoomType.PUZZLE) {
                                tile.status = RoomStatus.FAILED;
                            }
                            break;
                        }
                        tile.status = RoomStatus.DISCOVERED;
                        break;

                    case 30: // Green
                        if(tile instanceof Room) {
                            if(((Room) tile).type == RoomType.ENTRANCE) {
                                tile.status = RoomStatus.DISCOVERED;
                            } else {
                                tile.status = RoomStatus.GREEN;
                            }
                        }
                        break;

                    case 34: // White
                        tile.status = RoomStatus.CLEARED;
                        break;

                    case 63: // Brown
                    default:
                        tile.status = RoomStatus.DISCOVERED;
                }
            }
        }
    }

    private static MapData getMapData() {
        if(Shady.mc.thePlayer == null) return null;
        if(Shady.mc.thePlayer.inventory == null) return null;
        ItemStack handheldMap = Shady.mc.thePlayer.inventory.getStackInSlot(8);
        if(handheldMap == null || !(handheldMap.getItem() instanceof ItemMap) || !handheldMap.getDisplayName().contains("Magical Map")) return null;
        return ((ItemMap) handheldMap.getItem()).getMapData(handheldMap, Shady.mc.theWorld);
    }

}
