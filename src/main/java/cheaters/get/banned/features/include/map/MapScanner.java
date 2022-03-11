package cheaters.get.banned.features.include.map;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.include.map.elements.MapTile;
import cheaters.get.banned.features.include.map.elements.doors.Door;
import cheaters.get.banned.features.include.map.elements.doors.DoorType;
import cheaters.get.banned.features.include.map.elements.rooms.Room;
import cheaters.get.banned.features.include.map.elements.rooms.RoomType;
import cheaters.get.banned.features.include.map.elements.rooms.Separator;
import cheaters.get.banned.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class MapScanner {

    // https://i.imgur.com/vKgDOSv.png
    public static final int xCorner = -200;
    public static final int zCorner = -200;

    private static boolean isScanning = false;
    private static boolean allLoaded = true;

    public static Map getScan() {
        Map map = new Map();
        long startTime = System.currentTimeMillis();

        scanLoop: // 11 rows and columns (6 rooms, 5 doors)
        for(int col = 0; col <= 10; col++) {
            for(int row = 0; row <= 10; row++) {
                int x = 15 + xCorner + col * 16;
                int z = 15 + zCorner + row * 16;

                // TODO: Resolve...issues
                /*if(!Shady.mc.theWorld.getChunkFromChunkCoords(x >> 4, z >> 4).isLoaded()) {
                    allLoaded = false;
                    break scanLoop;
                }*/

                if(isColumnAir(x, z)) continue;

                boolean rowEven = row % 2 == 0;
                boolean colEven = col % 2 == 0;

                if(rowEven && colEven) {
                    // Add rooms
                    map.elements[row][col] = getRoom(x, z);
                } else if(!rowEven && !colEven) {
                    // Fills holes in 2x2 rooms (https://i.imgur.com/Tx0xD43.png)
                    if(map.elements[row - 1][col - 1] instanceof Room) {
                        map.elements[row][col] = Separator.GENERIC;
                    }
                } else if(isDoor(x, z)) {
                    // Add doors
                    map.elements[row][col] = getDoor(x, z);
                } else {
                    // Add separators (https://i.imgur.com/E67GWl0.png)
                    MapTile tileToCheck = map.elements[rowEven ? row : row - 1][rowEven ? col - 1 : col];
                    if(tileToCheck instanceof Room) {
                        if(((Room) tileToCheck).type == RoomType.ENTRANCE) {
                            map.elements[row][col] = new Door(DoorType.ENTRANCE);
                        } else {
                            map.elements[row][col] = Separator.GENERIC;
                        }
                    }
                }

                if(map.elements[row][col] == null) {
                    setBlock(Blocks.redstone_block, x, z);
                    Utils.sendModMessage("&cx"+x+" z"+z);
                } else {
                    setBlock(Blocks.emerald_block, x, z);
                    Utils.sendModMessage("&ax"+x+" z"+z);
                }
            }
        }

        Utils.sendModMessage("Scanned in &a" + (System.currentTimeMillis() - startTime) + "ms");
        return allLoaded ? map : null;
    }

    @Nullable private static Room getRoom(int x, int z) {
        int core = getCore(x, z);
        return MapManager.rooms.get(core);
    }

    /**
     * Determines if a x/z coordinate is a door or a room.
     *
     * @return true if it is a door, false if it is part of a room
     */
    private static boolean isDoor(int x, int z) {
        boolean xPlus4 = isColumnAir(x + 4, z);
        boolean xMinus4 = isColumnAir(x - 4, z);
        boolean zPlus4 = isColumnAir(x, z + 4);
        boolean zMinus4 = isColumnAir(x, z - 4);
        return xPlus4 && xMinus4 && !zPlus4 && !zMinus4 || !xPlus4 && !xMinus4 && zPlus4 && zMinus4;
    }

    private static Door getDoor(int x, int z) {
        IBlockState blockState = Shady.mc.theWorld.getBlockState(new BlockPos(x, 69, z));
        Block block = blockState.getBlock();
        DoorType type = null;

        if(block == Blocks.coal_block) type = DoorType.WITHER;
        if(block == Blocks.monster_egg) type = DoorType.ENTRANCE;
        if(block == Blocks.stained_hardened_clay && Blocks.stained_hardened_clay.getMetaFromState(blockState) == 14) type = DoorType.BLOOD;
        if(type == null) type = DoorType.NORMAL;

        return new Door(type);
    }

    /**
     * Gets the "core" of a column of blocks at a given x and y
     * position. A core is the hashcode of a string of the block
     * IDs for every block in the column. Calculated from the
     * bottom up. It corresponds to a room in the file created
     * by UnclaimedBloom6.
     *
     * @return The calculated core/hash
     */
    public static int getCore(int x, int z) {
        ArrayList<Integer> blocks = new ArrayList<>();

        for(int y = 140; y >= 12; y--) {
            int id = Block.getIdFromBlock(Shady.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock());
            if(id != 5 && id != 54) { // Wood Planks, Chest
                blocks.add(id);
            }
        }

        return StringUtils.join(blocks.toArray()).hashCode();
    }

    /**
     * Checks if a column of blocks is entirely air blocks or
     * not. Scans from the bottom up and exits on the first
     * non-air block encountered.
     */
    private static boolean isColumnAir(int x, int z) {
        for(int y = 12; y < 140; y++) {
            if(!Shady.mc.theWorld.isAirBlock(new BlockPos(x, y, z))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Place a block client-side at y=100 for debugging purposes
     */
    private static void setBlock(Block block, int x, int z) {
        Shady.mc.theWorld.setBlockState(new BlockPos(x, 255, z), block.getDefaultState());
    }

}
