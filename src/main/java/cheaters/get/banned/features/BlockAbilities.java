package cheaters.get.banned.features;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.Setting;
import cheaters.get.banned.utils.Utils;

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
