package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.BlockChangeEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.ArrayUtils;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import com.mojang.authlib.properties.Property;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class StonklessStonk {
    
    private static HashMap<BlockPos, Block> blockList = new HashMap<>();
    private static BlockPos selectedBlock = null;
    private static BlockPos lastCheckedPosition = null;
    private static HashSet<BlockPos> usedBlocks = new HashSet<>();

    private static float range = 5;

    private static boolean isEnabled() {
        boolean isEnabled = Utils.inDungeon && Shady.mc.thePlayer != null;
        if(!Config.alwaysOn && isEnabled) isEnabled = Config.stonklessStonk && Shady.mc.thePlayer.isSneaking();
        if(Config.disableInBoss && isEnabled) isEnabled = DungeonUtils.dungeonRun != null && !DungeonUtils.dungeonRun.inBoss;
        return isEnabled;
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Shady.mc.thePlayer == null) return;
        BlockPos playerPosition = Shady.mc.thePlayer.getPosition();
        if(isEnabled() && (lastCheckedPosition == null || !lastCheckedPosition.equals(playerPosition))) {
            blockList.clear();
            lastCheckedPosition = playerPosition;

            for(int x = playerPosition.getX()-6; x < playerPosition.getX()+6; x++) {
                for(int y = playerPosition.getY()-6; y < playerPosition.getY()+6; y++) {
                    for(int z = playerPosition.getZ()-6; z < playerPosition.getZ()+6; z++) {

                        BlockPos position = new BlockPos(x, y, z);
                        Block block = Shady.mc.theWorld.getBlockState(position).getBlock();

                        if(shouldEspBlock(block, position)) {
                            blockList.put(position, block);
                        }

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(isEnabled()) {
            for(Map.Entry<BlockPos, Block> block : blockList.entrySet()) {

                if(usedBlocks.contains(block.getKey())) continue;

                if(selectedBlock == null) {
                    if(Utils.facingBlock(block.getKey(), range)) {
                        selectedBlock = block.getKey();
                    }
                } else {
                    if(!Utils.facingBlock(selectedBlock, range)) {
                        selectedBlock = null;
                    }
                }

                Color color = Utils.addAlpha(Color.WHITE, 51); // Normal Chest
                if(block.getValue() instanceof BlockSkull) color = Utils.addAlpha(Color.BLACK, 51); // Wither Essence
                if(block.getValue() instanceof BlockLever) color = Utils.addAlpha(Color.LIGHT_GRAY, 51); // Lever
                if(block.getValue() instanceof BlockChest && ((BlockChest) block.getValue()).chestType == 1) color = Utils.addAlpha(Color.RED, 51); // Trapped Chest
                if(block.getKey().equals(selectedBlock)) color = Utils.addAlpha(Color.GREEN, 51); // Highlighted Block

                RenderUtils.highlightBlock(block.getKey(), color, event.partialTicks);

            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(isEnabled() && selectedBlock != null && !usedBlocks.contains(selectedBlock)) {
            if(Shady.mc.objectMouseOver != null && selectedBlock.equals(Shady.mc.objectMouseOver.getBlockPos())) return;
            if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {

                usedBlocks.add(selectedBlock);
                Shady.mc.thePlayer.setSneaking(false);
                if(Shady.mc.playerController.onPlayerRightClick(
                        Shady.mc.thePlayer,
                        Shady.mc.theWorld,
                        Shady.mc.thePlayer.inventory.getCurrentItem(),
                        selectedBlock,
                        EnumFacing.fromAngle(Shady.mc.thePlayer.rotationYaw),
                        new Vec3(Math.random(), Math.random(), Math.random())
                )) {
                    Shady.mc.thePlayer.swingItem();
                }

            }
        }
    }

    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent event) {
        if(Shady.mc.theWorld == null || Shady.mc.thePlayer == null) return;

        if(event.position.distanceSq(Shady.mc.thePlayer.getPosition()) > range) return;
        if(usedBlocks.contains(event.position)) return;
        if(!shouldEspBlock(event.newBlock.getBlock(), event.position)) return;

        blockList.put(event.position, event.newBlock.getBlock());
    }

    public static boolean shouldEspBlock(Block block, BlockPos position) {
        if(block instanceof BlockChest || block instanceof BlockLever) {
            return true;
        } else if(block instanceof BlockSkull) {
            TileEntitySkull tileEntity = (TileEntitySkull) Shady.mc.theWorld.getTileEntity(position);
            if(tileEntity.getSkullType() == 3) {
                Property property = ArrayUtils.firstOrNull(tileEntity.getPlayerProfile().getProperties().get("textures"));
                return property != null && property.getValue().hashCode() == 241000665;
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        blockList.clear();
        usedBlocks.clear();
        selectedBlock = null;
        lastCheckedPosition = null;
    }

}
