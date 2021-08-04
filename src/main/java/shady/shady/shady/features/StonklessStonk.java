package shady.shady.shady.features;

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
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.RenderUtils;
import shady.shady.shady.utils.Utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StonklessStonk {
    
    private static HashMap<Block, BlockPos> blockList = new HashMap<>();
    private static BlockPos selectedBlock = null;

    private static float reachDistance = 5f;
    private static final String witherEssenceSkin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=";

    private static boolean isEnabled() {
        return Config.isEnabled(Setting.STONKLESS_STONK) && Utils.inDungeon && Shady.mc.thePlayer != null && Shady.mc.thePlayer.isSneaking();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(isEnabled()) {
            blockList.clear();
            BlockPos playerPosition = Shady.mc.thePlayer.getPosition();
            for(int x = playerPosition.getX()-7; x < playerPosition.getX()+7; x++) {
                for(int y = playerPosition.getY()-7; y < playerPosition.getY()+7; y++) {
                    for(int z = playerPosition.getZ()-7; z < playerPosition.getZ()+7; z++) {
                        BlockPos position = new BlockPos(x, y, z);
                        Block block = Shady.mc.theWorld.getBlockState(position).getBlock();

                        if(block instanceof BlockChest || block instanceof BlockLever) {
                            blockList.put(block, position);
                            break;
                        }

                        if(block instanceof BlockSkull) {
                            TileEntitySkull tileEntity = (TileEntitySkull) Shady.mc.theWorld.getTileEntity(position);
                            if(tileEntity.getSkullType() == 3) {
                                Property property = Utils.firstOrNull(tileEntity.getPlayerProfile().getProperties().get("textures"));
                                if(property != null && property.getValue().equals(witherEssenceSkin)) {
                                    blockList.put(block, position);
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
            for(Map.Entry<Block, BlockPos> block : blockList.entrySet()) {

                if(selectedBlock == null) {
                    if(Utils.facingBlock(block.getValue(), reachDistance)) {
                        selectedBlock = block.getValue();
                    }
                } else {
                    if(!Utils.facingBlock(selectedBlock, reachDistance)) {
                        if(Utils.facingBlock(block.getValue(), reachDistance)) {
                            selectedBlock = block.getValue();
                        } else {
                            selectedBlock = null;
                        }
                    }
                }

                Color color = Utils.addAlpha(Color.WHITE, 51); // Normal Chest
                if(block.getKey() instanceof BlockSkull) color = Utils.addAlpha(Color.BLACK, 51); // Wither Essence
                if(block.getKey() instanceof BlockLever) color = Utils.addAlpha(Color.LIGHT_GRAY, 51); // Lever
                if(block.getKey() instanceof BlockChest && ((BlockChest) block.getKey()).chestType == 1) color = Utils.addAlpha(Color.RED, 51); // Trapped Chest
                if(block.getValue().equals(selectedBlock)) color = Utils.addAlpha(Color.GREEN, 51); // Highlighted Block

                RenderUtils.highlightBlock(block.getValue(), color, event.partialTicks);

            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(isEnabled() && selectedBlock != null) {
            if(Shady.mc.objectMouseOver != null && Shady.mc.objectMouseOver.getBlockPos().equals(selectedBlock)) return;
            if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                Shady.mc.thePlayer.setSneaking(false);
                Shady.mc.playerController.onPlayerRightClick(
                        Shady.mc.thePlayer,
                        Shady.mc.theWorld,
                        Shady.mc.thePlayer.inventory.getCurrentItem(),
                        selectedBlock,
                        EnumFacing.fromAngle((double) Shady.mc.thePlayer.rotationYaw),
                        new Vec3(Math.random(), Math.random(), Math.random())
                );
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        blockList.clear();
        selectedBlock = null;
    }

}
