package hy.shadyaddons.features;

import hy.shadyaddons.config.Config;
import hy.shadyaddons.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockAbilities {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            String skyBlockID = Utils.getSkyBlockID(mc.thePlayer.getHeldItem());
            if(skyBlockID != null) {
                event.setCanceled(Config.blockCellsAlignment && skyBlockID.equals("GYROKINETIC_WAND"));
            }
        }
    }

}
