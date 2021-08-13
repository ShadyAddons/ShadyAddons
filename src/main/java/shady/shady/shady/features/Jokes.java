package shady.shady.shady.features;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.Shady;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;

public class Jokes {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if(event.type == 0) {
            String message = event.message.getUnformattedText();
            if(message.equals("From [MVP+] HY7: !banned!") || message.equals("From [MVP++] HY7: !banned!")) {
                event.setCanceled(true);
                fakeBan();
            }

            if(Config.isEnabled(Setting.INCREASE_MAGIC_FIND) && message.contains("% Magic Find!)")) {
                event.message = new ChatComponentText(
                        event.message.getFormattedText().replaceAll("§r§b\\(\\+(\\d+)% Magic Find!\\)§r", "§r§b(+923% Magic Find!)§r")
                );
            }
        }
    }

    public static void fakeBan() {
        ChatComponentText component = new ChatComponentText("§cYou are temporarily banned for §f29d 23h 59m 59s §cfrom this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §rCheaters get banned");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/appeal");
        component.appendText("\n");
        component.appendText("\n§7Ban ID: §r#69420");
        component.appendText("\n§7Sharing your Ban ID may affect the processing of your appeal!");
        Shady.mc.getNetHandler().getNetworkManager().closeChannel(component);
    }

}
