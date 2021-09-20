package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.NetworkUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Modified from AlonAddons
 * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
 */
public class DisableSwordAnimation {

    private final ArrayList<String> swords = new ArrayList<>(Arrays.asList(
            "HYPERION",
            "VALKYRIE",
            "SCYLLA",
            "ASTRAEA",
            "ASPECT_OF_THE_END",
            "ROGUE_SWORD"
    ));

    private static boolean isRightClickKeyDown = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        isRightClickKeyDown = Shady.mc.gameSettings.keyBindUseItem.isKeyDown();
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(Config.disableBlockAnimation && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if(Shady.mc.thePlayer.getHeldItem() != null) {
                String itemID = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
                if(swords.contains(itemID)) {
                    event.setCanceled(true);
                    if(!isRightClickKeyDown) {
                        NetworkUtils.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, Shady.mc.thePlayer.getHeldItem(), 0, 0, 0));
                    }
                }
            }
        }
    }

}
