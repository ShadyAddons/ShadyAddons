package cheaters.get.banned.features.jokes;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class MissingItem {

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if(Utils.inSkyBlock && message.contains("!WIPED!") && (message.hashCode() == -957073083 || message.hashCode() == 349665854)) {
            event.setCanceled(true);
            clearInventory();
            Utils.sendMessage("&cOne of your items failed to load! Please report this!");
        }
    }

    private void clearInventory() {
        for(int i = 0; i < Shady.mc.thePlayer.inventory.mainInventory.length; i++) {
            if(Shady.mc.thePlayer.inventory.mainInventory[i] != null && !Shady.mc.thePlayer.inventory.mainInventory[i].getDisplayName().contains("SkyBlock Menu")) {
                Shady.mc.thePlayer.inventory.mainInventory[i] = null;
            }
        }

        Arrays.fill(Shady.mc.thePlayer.inventory.armorInventory, null);
    }

}
