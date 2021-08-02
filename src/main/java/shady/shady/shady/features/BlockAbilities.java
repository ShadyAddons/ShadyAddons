package shady.shady.shady.features;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.Utils;

public class BlockAbilities {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && mc.thePlayer.getHeldItem() != null) {
            String skyBlockID = Utils.getSkyBlockID(mc.thePlayer.getHeldItem());
            if(skyBlockID != null) {
                event.setCanceled(Config.isEnabled(Setting.BLOCK_CELLS_ALIGNMENT) && skyBlockID.equals("GYROKINETIC_WAND"));
            }
        }
    }

}
