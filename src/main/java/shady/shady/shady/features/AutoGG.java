package shady.shady.shady.features;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.Utils;

public class AutoGG {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(Config.isEnabled(Setting.AUTO_GG) && !Utils.inSkyBlock && event.type == 0) {
            String message = event.message.getFormattedText();
            if(message.contains("Reward Summary") && !message.contains(":") && !message.contains("]")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac gg");
            }
        }
    }

}
