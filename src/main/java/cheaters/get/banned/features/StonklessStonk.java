package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
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
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class StonklessStonk {
    
    private static HashMap<BlockPos, Block> blockList = new HashMap<>();
    private static BlockPos selectedBlock = null;
    private static BlockPos lastCheckedBlock = null;
    private static HashSet<BlockPos> usedBlocks = new HashSet<>();

    private static float reachDistance = 5f;
    private static final String witherEssenceSkin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=";

    private static boolean isEnabled() {
        boolean isEnabled = Utils.inDungeon && Shady.mc.thePlayer != null;
        if(!Config.alwaysOn && isEnabled) isEnabled = Config.stonklessStonk && Shady.mc.thePlayer.isSneaking();
        return isEnabled;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(Shady.mc.thePlayer == null) return;
        BlockPos playerPosition = Shady.mc.thePlayer.getPosition();
        if(isEnabled() && (Config.alwaysOn || lastCheckedBlock == null || !lastCheckedBlock.equals(playerPosition))) {
            blockList.clear();
            lastCheckedBlock = playerPosition;

            for(int x = playerPosition.getX()-6; x < playerPosition.getX()+6; x++) {
                for(int y = playerPosition.getY()-6; y < playerPosition.getY()+6; y++) {
                    for(int z = playerPosition.getZ()-6; z < playerPosition.getZ()+6; z++) {

                        BlockPos position = new BlockPos(x, y, z);
                        Block block = Shady.mc.theWorld.getBlockState(position).getBlock();

                        if(block instanceof BlockChest || block instanceof BlockLever) {
                            blockList.put(position, block);
                        } else if(block instanceof BlockSkull) {
                            TileEntitySkull tileEntity = (TileEntitySkull) Shady.mc.theWorld.getTileEntity(position);
                            if(tileEntity.getSkullType() == 3) {
                                Property property = Utils.firstOrNull(tileEntity.getPlayerProfile().getProperties().get("textures"));
                                if(property != null && property.getValue().equals(witherEssenceSkin)) {
                                    blockList.put(position, block);
                                }
                            }
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
                    if(Utils.facingBlock(block.getKey(), reachDistance)) {
                        selectedBlock = block.getKey();
                    }
                } else {
                    if(!Utils.facingBlock(selectedBlock, reachDistance)) {
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
                        EnumFacing.fromAngle((double) Shady.mc.thePlayer.rotationYaw),
                        new Vec3(Math.random(), Math.random(), Math.random())
                )) {
                    Shady.mc.thePlayer.swingItem();
                };

            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        blockList.clear();
        usedBlocks.clear();
        selectedBlock = null;
        lastCheckedBlock = null;
    }

}
