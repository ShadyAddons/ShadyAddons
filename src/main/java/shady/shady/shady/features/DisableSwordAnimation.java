package shady.shady.shady.features;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.Utils;

import java.util.ArrayList;

public class DisableSwordAnimation {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private final ArrayList<String> swords = new ArrayList<String>(){{
        add("HYPERION");
        add("VALKYRIE");
        add("SCYLLA");
        add("ASTRAEA");
        add("ASPECT_OF_THE_END");
        add("ROGUE_SWORD");
    }};

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if(Config.isEnabled(Setting.DISABLE_BLOCK_ANIMATION) && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if(mc.thePlayer.getHeldItem() != null) {
                String itemID = Utils.getSkyBlockID(mc.thePlayer.getHeldItem());
                if(swords.contains(itemID)) {
                    event.setCanceled(true);
                    if(mc.gameSettings.keyBindUseItem.isKeyDown()) {
                        Utils.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                    }
                }
            }
        }
    }

}
