package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.Utils;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockAbilities {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && Shady.mc.thePlayer.getHeldItem() != null) {
            String skyBlockID = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
            if(

                    (Config.blockCellsAlignment && skyBlockID.equals("GYROKINETIC_WAND")) ||
                    (Config.blockGiantsSlam && skyBlockID.equals("GIANTS_SWORD")) ||
                    (Config.blockValkyrie && Utils.inDungeon && skyBlockID.equals("VALKYRIE"))

            ) { event.setCanceled(true); }
        }
    }

}
