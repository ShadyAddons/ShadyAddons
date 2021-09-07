package cheaters.get.banned.features;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.Setting;

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
