package shady.shady.shady.features.dungeonscanner;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.Shady;
import shady.shady.shady.utils.RenderUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class DungeonScanner {

    public static class Tile {
        public enum Type {
            NORMAL(new Color(108, 67, 31)),
            TRAP(new Color(206, 126, 60)),
            PUZZLE(new Color(176, 42, 214)),
            MINIBOSS(new Color(223, 235, 68)),
            BLOOD(new Color(241, 0, 32)),
            FAIRY(new Color(232, 119, 165)),
            START(new Color(32, 144, 15)),
            BAD_ROOM(Color.BLUE),
            UNKNOWN(Color.GRAY);

            public Color color;
            Type(Color color) {
                this.color = color;
            }
        }

        public Type type;
        public BlockPos cornerPos;
        public boolean gapNorth;
        public boolean gapWest;
        private String core = null;
        private Door[] doors = null;

        public Tile() {
        }

        public Tile(BlockPos pos) {
            this.cornerPos = pos;
        }

        public Tile(Type type, BlockPos pos, boolean gapNorth, boolean gapEast, boolean gapSouth, boolean gapWest) {
            this.type = type;
            this.cornerPos = pos;
            this.gapNorth = gapNorth;
            this.gapWest = gapWest;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setPos(BlockPos pos) {
            this.cornerPos = pos;
        }

        public void setGaps(boolean gapNorth, boolean gapWest) {
            this.gapNorth = gapNorth;
            this.gapWest = gapWest;
        }

        public String getCore() {
            if(core != null) return core;
            return core = DungeonScanner.getCore(cornerPos.getX()+15, cornerPos.getZ()+15);
        }

        public Door[] getDoors() {
            if(doors != null) return doors;
            return doors = new Door[]{
                    Door.getDoor(this, 15, -1), // North door
                    Door.getDoor(this, 31, 15), // East door
                    Door.getDoor(this, 15, 31), // South door
                    Door.getDoor(this, -1, 15) // West door
            };
        }
    }
    public static class Door {
        public enum Type {
            NORMAL(Tile.Type.NORMAL.color),
            BLOOD(Tile.Type.BLOOD.color),
            WITHER(Color.BLACK),
            NO_DOOR(Color.GREEN);

            public Color color;
            Type(Color color) {
                this.color = color;
            }
        }

        public BlockPos pos;
        public Type type;

        public Door(BlockPos pos, Type type) {
            this.pos = pos;
            this.type = type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public static Door getDoor(Tile tile, int x, int z) {
            BlockPos pos = new BlockPos(tile.cornerPos.getX()+x, 70, tile.cornerPos.getZ()+z);
            Door door = new Door(pos, Type.NO_DOOR);
            Block block = getBlock(pos);

            if(block == Blocks.coal_block) door.setType(Type.WITHER);

            return door;
        }
    }

    private static final int[] yLevelsToCheck = {99, 100, 129, 98, 105, 90, 133, 83, 159, 119, 114};
    private static final HashMap<String, String> roomLookup = new HashMap<String, String>(){{
        put("771529813511989811111", "START:Enterance");

        put("77319898111711", "TRAP:Old (Stonk) Trap");
        put("77131971981111111111111", "TRAP:New (Spirit Boot) Trap");

        put("77459898989898989811", "BLOOD:Blood Room");
        put("77159898989898989811", "BLOOD:Blood Room");

        put("7777117171716917171717171717111", "FAIRY:Fairy Room");

        put("7711112351", "MINIBOSS:Shadow Assassin Miniboss Room");
        put("771171989898", "MINIBOSS:King Midas");
        put("7711264498444444111111", "MINIBOSS:Banner Miniboss Room");

        put("7711444111111111111111111111111", "PUZZLE:Teleport Maze");
        put("7711111111669898111111111111", "PUZZLE:Creeper Beams");
        put("771354111111111111111111111111", "PUZZLE:Three Weirdos");
        put("771111111147998411", "PUZZLE:Ice Fill"); // Without block
        put("7711111111479198411", "PUZZLE:Ice Fill"); // With block
        put("771149411111111111111484481111", "PUZZLE:Bomb Defuse");
        put("771117410910941111111111111111111111", "PUZZLE:Silverfish Puzzle");
        put("7711293595111111111", "PUZZLE:Water Board");
        // Quiz & Blaze are identified elsewhere
    }};

    private static HashMap<BlockPos, Tile> tiles = new HashMap<>();
    private static HashSet<BlockPos> checkedPos = new HashSet<>();
    private static HashSet<String> puzzles = new HashSet<>();
    private static HashSet<String> badRooms = new HashSet<>();
    private static String trap = null;

    public static HashMap<BlockPos, Tile> scan() {
        if(!tiles.isEmpty()) return tiles;

        for(int y : yLevelsToCheck) {
            for(int x = 0; x < 6; x++) {
                for(int z = 0; z < 6; z++) {
                    BlockPos pos = new BlockPos(x*32, y, z*32);
                    if(checkedPos.contains(pos)) continue;
                    Tile tile = getTile(pos);
                    checkedPos.add(pos);
                    if(tile == null) continue;
                    tiles.put(pos, tile);
                }
            }
        }

        return tiles;
    }

    public static HashMap<BlockPos, Tile> reScan() {
        checkedPos.clear();
        tiles.clear();
        puzzles.clear();
        badRooms.clear();
        trap = null;
        return scan();
    }

    // @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        for(Tile tile : tiles.values()) {
            RenderUtils.highlightBlock(tile.cornerPos, tile.type.color, event.partialTicks);
        }
    }

    public static String getPuzzlesText() {
        return String.join("\n", puzzles);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        checkedPos.clear();
        tiles.clear();
        puzzles.clear();
        badRooms.clear();
        trap = null;
    }

    private static Block getBlock(BlockPos pos) {
        if(Shady.mc.theWorld != null && Shady.mc.theWorld.isBlockLoaded(pos)) {
            return Shady.mc.theWorld.getBlockState(pos).getBlock();
        }
        return null;
    }

    private static Tile getTile(BlockPos pos) {
        Tile tile = new Tile(pos);
        Block corner = getBlock(pos);

        if(corner != Blocks.air && getBlock(pos.up()) == Blocks.air) {
            switch(pos.getY()) {
                case 105: // Quiz
                    puzzles.add("Quiz");
                    tile.setType(Tile.Type.PUZZLE);
                    break;

                case 83: // Top-Door Blaze
                case 133: // Bottom-Door Blaze
                    puzzles.add("Blaze");
                    tile.setType(Tile.Type.PUZZLE);
                    break;

                default:
                    if(tile.getCore() != null) {
                        tile.setType(getCoreType(tile.getCore()));
                    } else {
                        tile.setType(Tile.Type.UNKNOWN);
                    }
                    break;
            }

            tile.setGaps(getBlock(pos.north()) == Blocks.air,
                    getBlock(pos.west()) == Blocks.air);

            return tile;
        }

        return null;
    }

    public static String getCore(int x, int z) {
        StringBuilder result = new StringBuilder();

        for(int y = 0; y < 140; y++) {
            Block block = Shady.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
            if(block == Blocks.air || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.lava || block == Blocks.flowing_lava) continue;
            int blockId = Item.getIdFromItem(Item.getItemFromBlock(block));
            result.append(blockId);
        }

        return result.toString();
    }

    private static Tile.Type getCoreType(String core) {
        String roomName = roomLookup.get(core);
        Tile.Type type = Tile.Type.NORMAL;

        if(roomName != null) {
            String[] splitRoomName = roomName.split(":");
            if(splitRoomName.length == 2) {
                try {
                    type = Tile.Type.valueOf(splitRoomName[0]);
                } catch(IllegalArgumentException ignored) {}

                if(type == Tile.Type.PUZZLE) puzzles.add(splitRoomName[1]);
                if(type == Tile.Type.BAD_ROOM) badRooms.add(splitRoomName[1]);
                if(type == Tile.Type.TRAP) trap = splitRoomName[1];
            }
        }

        return type;
    }

}
