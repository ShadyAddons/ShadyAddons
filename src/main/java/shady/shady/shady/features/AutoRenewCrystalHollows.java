package shady.shady.shady.features;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;

public class AutoRenewCrystalHollows {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(Config.isEnabled(Setting.AUTO_RENEW_CRYSTAL_HOLLOWS_PASS) && event.type == 0) {
            if(event.message.getUnformattedText().equals("Your pass to the Crystal Hollows will expire in 1 minute")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/purchasecrystallhollowspass");
            }
        }
    }

}
