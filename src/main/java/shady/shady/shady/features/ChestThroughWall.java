package shady.shady.shady.features;

import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.RenderUtils;
import shady.shady.shady.utils.Utils;

import java.awt.*;
import java.util.HashSet;

public class ChestThroughWall {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static HashSet<BlockPos> chests = new HashSet<>();
    private static BlockPos selectedChest = null;

    private static boolean isEnabled() {
        return Config.isEnabled(Setting.OPEN_CHESTS_THROUGH_WALLS) && Utils.inDungeon && mc.thePlayer != null && mc.thePlayer.isSneaking();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(isEnabled()) {
            chests.clear();
            BlockPos playerPosition = mc.thePlayer.getPosition();
            for(int x = playerPosition.getX()-5; x < playerPosition.getX()+5; x++) {
                for(int y = playerPosition.getY()-5; y < playerPosition.getY()+5; y++) {
                    for(int z = playerPosition.getZ()-5; z < playerPosition.getZ()+5; z++) {
                        BlockPos position = new BlockPos(x, y, z);
                        if(mc.theWorld.getBlockState(position).getBlock() instanceof BlockChest) {
                            chests.add(position);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(isEnabled()) {
            for(BlockPos chest : chests) {

                if(selectedChest == null) {
                    if(Utils.facingBlock(chest, 5)) {
                        selectedChest = chest;
                    }
                } else {
                    if(!Utils.facingBlock(selectedChest, 5)) {
                        if(Utils.facingBlock(chest, 5)) {
                            selectedChest = chest;
                        } else {
                            selectedChest = null;
                        }
                    }
                }

                if(chest.equals(selectedChest)) {
                    RenderUtils.highlightBlock(chest, new Color(0, 255, 0, 51), event.partialTicks);
                } else {
                    RenderUtils.highlightBlock(chest, new Color(255, 255, 255, 51), event.partialTicks);
                }

            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(isEnabled() && selectedChest != null) {
            if(!mc.objectMouseOver.getBlockPos().equals(selectedChest)) {
                if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                    mc.thePlayer.setSneaking(false);
                    mc.playerController.onPlayerRightClick(
                            mc.thePlayer,
                            mc.theWorld,
                            mc.thePlayer.inventory.getCurrentItem(),
                            selectedChest,
                            EnumFacing.fromAngle((double)mc.thePlayer.rotationYaw),
                            new Vec3(Math.random(), Math.random(), Math.random())
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        chests.clear();
        selectedChest = null;
    }

}
