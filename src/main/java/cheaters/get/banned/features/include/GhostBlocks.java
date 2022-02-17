package cheaters.get.banned.features.include;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.events.ClickEvent;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class GhostBlocks {

    public GhostBlocks() {
        KeybindUtils.register("Create Ghost Block", Keyboard.KEY_G);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(Config.ghostBlockKeybind && KeybindUtils.get("Create Ghost Block").isKeyDown()) {
            MovingObjectPosition object = Shady.mc.thePlayer.rayTrace(Shady.mc.playerController.getBlockReachDistance(), 1);
            if(object != null) {
                if(object.getBlockPos() != null) {
                    Block lookingAtblock = Shady.mc.theWorld.getBlockState(object.getBlockPos()).getBlock();
                    if(!Utils.isInteractable(lookingAtblock) && lookingAtblock != Blocks.air) {
                        Shady.mc.theWorld.setBlockToAir(object.getBlockPos());
                        MiscStats.add(MiscStats.Metric.BLOCKS_GHOSTED);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(ClickEvent.Right event) {
        if(Utils.inSkyBlock && Config.stonkGhostBlock && Shady.mc.objectMouseOver != null && Shady.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !Utils.isInteractable(Shady.mc.theWorld.getBlockState(Shady.mc.objectMouseOver.getBlockPos()).getBlock())) {
            String itemId = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
            if(itemId.equals("STONK_PICKAXE") || itemId.equals("GOLD_PICKAXE")) {
                Shady.mc.theWorld.setBlockToAir(Shady.mc.objectMouseOver.getBlockPos());
                event.setCanceled(true);
            }
        }
    }

}
