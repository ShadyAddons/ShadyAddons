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
        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && mc.thePlayer.getHeldItem() != null) {
            String skyBlockID = Utils.getSkyBlockID(mc.thePlayer.getHeldItem());
            if(skyBlockID != null) {
                event.setCanceled(Config.isEnabled(Config.Setting.BLOCK_CELLS_ALIGNMENT) && skyBlockID.equals("GYROKINETIC_WAND"));
                event.setCanceled(Config.isEnabled(Config.Setting.BLOCK_STONK_ABILITIES) && skyBlockID.equals("STONK_PICKAXE"));
            }
        }
    }

}
