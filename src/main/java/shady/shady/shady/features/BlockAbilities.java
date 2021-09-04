package shady.shady.shady.features;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.Utils;

public class BlockAbilities {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && Shady.mc.thePlayer.getHeldItem() != null) {
            String skyBlockID = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
            event.setCanceled(Config.isEnabled(Setting.BLOCK_CELLS_ALIGNMENT) && skyBlockID.equals("GYROKINETIC_WAND"));
            event.setCanceled(Config.isEnabled(Setting.BLOCK_GIANTS_SLAM) && skyBlockID.equals("GIANTS_SWORD"));
            event.setCanceled(Config.isEnabled(Setting.BLOCK_VALKYRIE_ABILITY) && Utils.inDungeon && skyBlockID.equals("VALKYRIE"));
        }
    }

}
