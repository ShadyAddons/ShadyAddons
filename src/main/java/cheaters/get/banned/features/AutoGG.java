package cheaters.get.banned.features;

import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoGG {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(Config.autoGg && !Utils.inSkyBlock && event.type == 0) {
            String message = event.message.getFormattedText();
            if(message.contains("Reward Summary") && !message.contains(":") && !message.contains("]")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac gg");
            }
        }
    }

}
