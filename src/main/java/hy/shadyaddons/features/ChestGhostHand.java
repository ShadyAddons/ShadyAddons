package hy.shadyaddons.features;

import hy.shadyaddons.config.Config;
import hy.shadyaddons.events.BlockChangeEvent;
import hy.shadyaddons.utils.RenderUtils;
import hy.shadyaddons.utils.Utils;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashSet;

public class ChestGhostHand {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static HashSet<BlockPos> chests = new HashSet<>();
    private static BlockPos selectedChest = null;

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if(Config.isEnabled(Config.Setting.OPEN_CHESTS_THROUGH_WALLS) && Utils.inDungeon && mc.thePlayer != null && mc.thePlayer.isSneaking()) {
            for(BlockPos chest : chests) {
                if(chest.distanceSq(mc.thePlayer.getPosition()) < 5*5) {
                    if(Utils.facingBlock(chest, 5)) {
                        RenderUtils.highlightBlock(chest, new Color(0, 255, 0, 50), event.partialTicks);
                        selectedChest = chest;
                    } else {
                        RenderUtils.highlightBlock(chest, new Color(0, 158, 0, 50), event.partialTicks);
                        selectedChest = null;
                    }
                }
            }
        } else {
            selectedChest = null;
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(Config.isEnabled(Config.Setting.OPEN_CHESTS_THROUGH_WALLS) && Utils.inDungeon) {
            if(selectedChest != null && (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)) {
                if(mc.theWorld.getBlockState(selectedChest).getBlock() instanceof BlockChest) {
                    mc.thePlayer.setSneaking(false);
                    mc.playerController.onPlayerRightClick(
                            mc.thePlayer,
                            mc.theWorld,
                            mc.thePlayer.inventory.getCurrentItem(),
                            selectedChest,
                            EnumFacing.fromAngle((double)mc.thePlayer.rotationYaw),
                            new Vec3(Math.random(), Math.random(), Math.random()));
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent event) {
        if(Utils.inDungeon) {
            if(event.oldBlock.getBlock() instanceof BlockChest) {
                chests.remove(event.position);
            }

            if(event.newBlock.getBlock() instanceof BlockChest) {
                chests.add(event.position);
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        chests.clear();
        selectedChest = null;
    }

}
