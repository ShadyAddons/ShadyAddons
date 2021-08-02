package shady.shady.shady.features;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Modified from AlonAddons
 * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
 */
public class DisableSwordAnimation {

    private static final Minecraft mc = Minecraft.getMinecraft();
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
        isRightClickKeyDown = mc.gameSettings.keyBindUseItem.isKeyDown();
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(Config.isEnabled(Setting.DISABLE_BLOCK_ANIMATION) && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if(mc.thePlayer.getHeldItem() != null) {
                String itemID = Utils.getSkyBlockID(mc.thePlayer.getHeldItem());
                if(swords.contains(itemID)) {
                    event.setCanceled(true);
                    if(!isRightClickKeyDown) {
                        Utils.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                    }
                }
            }
        }
    }

}
