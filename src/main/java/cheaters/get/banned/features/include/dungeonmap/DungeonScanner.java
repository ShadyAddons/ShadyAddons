package cheaters.get.banned.features.include.dungeonmap;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.BlockChangeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class DungeonScanner {

    public static DungeonLayout scan(int xCorner, int zCorner) {
        DungeonLayout newDungeonLayout = new DungeonLayout();

        for(int x = 15; x < xCorner + 1; x += 32) {
            for(int z = 15; z < zCorner + 1; z += 32) {
                boolean isColumnEmpty = isColumnNotEmpty(x, z);

                if(isColumnEmpty) {
                    for(int y : RoomLoader.yLevels) {
                        BlockList blockList = new BlockList(x, y, z);
                        for(Room room : RoomLoader.rooms) {
                            if(room.matches(blockList)) {
                                newDungeonLayout.totalSecrets += room.secrets;

                                if(room.crypts == null) {
                                    newDungeonLayout.uncertainCrypts = true;
                                } else {
                                    newDungeonLayout.totalCrypts += room.crypts;
                                }

                                if(room.type == Room.Type.PUZZLE) {
                                    newDungeonLayout.totalPuzzles++;
                                }

                                if(room.type == Room.Type.TRAP) {
                                    if(room.name.contains("Old")) {
                                        newDungeonLayout.trapType = DungeonLayout.TrapType.OLD;
                                    } else if(room.name.contains("New")) {
                                        newDungeonLayout.trapType = DungeonLayout.TrapType.NEW;
                                    }
                                }

                                newDungeonLayout.roomTiles.add(new DungeonLayout.RoomTile(x, z, room));
                                break;
                            }
                        }
                    }
                }

                newDungeonLayout.connectorTiles.add(getConnectorAt(x, z - 16, EnumFacing.NORTH)); // North
                newDungeonLayout.connectorTiles.add(getConnectorAt(x + 16, z, EnumFacing.EAST)); // East
                newDungeonLayout.connectorTiles.add(getConnectorAt(x, z + 16, EnumFacing.SOUTH)); // South
                newDungeonLayout.connectorTiles.add(getConnectorAt(x - 16, z, EnumFacing.WEST)); // West
            }
        }

        ArrayList<DungeonLayout.RoomTile> roomTilesToAdd = new ArrayList<>();
        for(int x = 15; x < xCorner + 1; x += 32) {
            for(int z = 15; z < zCorner + 1; z += 32) {
                if(!layoutContainsRoomAtPosition(newDungeonLayout, x, z) && isColumnNotEmpty(x, z)) {
                    roomTilesToAdd.add(new DungeonLayout.RoomTile(x, z, Room.GENERIC_NORMAL_ROOM));
                }
            }
        }
        newDungeonLayout.roomTiles.addAll(roomTilesToAdd);

        return newDungeonLayout;
    }

    private static DungeonLayout.ConnectorTile getConnectorAt(int x, int z, EnumFacing direction) {
        BlockPos doorBlock = new BlockPos(x, 69, z);
        IBlockState blockState = Shady.mc.theWorld.getBlockState(doorBlock);
        Block block = blockState.getBlock();
        DungeonLayout.ConnectorTile.Type type = DungeonLayout.ConnectorTile.Type.CONNECTOR;

        if(block == Blocks.coal_block) type = DungeonLayout.ConnectorTile.Type.WITHER_DOOR;
        if(block == Blocks.stained_hardened_clay && blockState.getValue(BlockColored.COLOR) == EnumDyeColor.RED) type = DungeonLayout.ConnectorTile.Type.BLOOD_DOOR;
        if(block == Blocks.monster_egg && blockState.getValue(BlockSilverfish.VARIANT) == BlockSilverfish.EnumType.CHISELED_STONEBRICK) type = DungeonLayout.ConnectorTile.Type.ENTRANCE_DOOR;
        if(block == Blocks.air) {
            if(Shady.mc.theWorld.getBlockState(doorBlock.down(2)).getBlock() == Blocks.bedrock && Shady.mc.theWorld.getBlockState(doorBlock.up(4)).getBlock() != Blocks.air) {
                type = DungeonLayout.ConnectorTile.Type.NORMAL_DOOR;
            } else if(isColumnNotEmpty(x, z)) {
                type = DungeonLayout.ConnectorTile.Type.CONNECTOR;
            } else {
                type = null;
            }
        }

        return new DungeonLayout.ConnectorTile(x, z, type, direction);
    }

    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent event) {
        if(DungeonMap.activeDungeonLayout != null && (event.oldBlock.getBlock() == Blocks.coal_block || event.oldBlock.getBlock() == Blocks.stained_hardened_clay) && event.newBlock.getBlock() == Blocks.air) {
            for(DungeonLayout.ConnectorTile door : DungeonMap.activeDungeonLayout.connectorTiles) {
                if(!door.isOpen && (event.oldBlock.getBlock() == Blocks.coal_block && door.type == DungeonLayout.ConnectorTile.Type.WITHER_DOOR) || (event.oldBlock.getBlock() == Blocks.stained_hardened_clay && door.type == DungeonLayout.ConnectorTile.Type.BLOOD_DOOR)) {
                    if(event.position.equals(door.getPosition(69))) {
                        door.isOpen = true;
                    }
                }
            }
        }
    }

    private static boolean isColumnNotEmpty(int x, int z) {
        for(int y = 256; y > 0; y--) { // set to 68 in IllegalMap
            if(Shady.mc.theWorld != null && !Shady.mc.theWorld.isAirBlock(new BlockPos(x, y, z))) {
                return true;
            }
        }
        return false;
    }

    private static boolean layoutContainsRoomAtPosition(DungeonLayout layout, int x, int z) {
        for(DungeonLayout.RoomTile room : layout.roomTiles) {
            if(room.x == x && room.z == z) {
                return true;
            }
        }
        return false;
    }

}
