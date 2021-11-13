package cheaters.get.banned.features.jokes;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Grapefruit {

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if(Utils.inSkyBlock && message.contains("grapefruit")) {
            if(message.contains(Shady.mc.getSession().getUsername() + ": ")) {
                event.setCanceled(true);
            }

            if(message.hashCode() == 497390995 || message.hashCode() == 1810650470) {
                Utils.sendMessageAsPlayer("/ac grapefruit");
                event.setCanceled(true);
            }
        }
    }

}
