package cheaters.get.banned.features.jokes;

import cheaters.get.banned.Shady;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class FakeBan {

    public static boolean permBan = false;
    public static long banStart = 0;
    public static boolean usernameBan = false;

    /*@SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(event.type != 0) return;
        String message = event.message.getUnformattedText();
        if(message.contains("!BANNED!") || message.contains("!PERMBAN!") || message.contains("!BADNAME!")) {
            switch(message.hashCode()) {
                case 870570787:
                case -1778728484:
                    permBan = true;

                case -1292812670:
                case 561388713:
                    banStart = System.currentTimeMillis() - 1000;
                    event.setCanceled(true);
                    fakeGenericBan(Shady.mc.getNetHandler().getNetworkManager());
                    break;

                case -1708887982:
                case -63219957:
                    usernameBan = true;
                    event.setCanceled(true);
                    fakeUsernameBan(Shady.mc.getNetHandler().getNetworkManager());
                    break;

                default:
                    Utils.sendMessageAsPlayer("/r Nice try bozo");
            }
        }
    }*/

    @SubscribeEvent
    public void onJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if(Shady.mc.getCurrentServerData() != null && Shady.mc.getCurrentServerData().serverIP.contains("hypixel.net") && !Shady.mc.getCurrentServerData().serverIP.contains("letmein")) {
            if(usernameBan) fakeUsernameBan(event.manager);
            if(permBan) fakeGenericBan(event.manager);
        }
    }

    public static void fakeGenericBan(NetworkManager manager) {
        long banDuration = banStart + 2592000000L - System.currentTimeMillis();
        String formattedDuration = DurationFormatUtils.formatDuration(banDuration, "d'd' H'h' m'm' s's'", false);

        ChatComponentText component = new ChatComponentText("§cYou are temporarily banned for §f" + formattedDuration + " §cfrom this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §rCheating through the use of unfair game advantages.");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/appeal");
        component.appendText("\n");
        component.appendText("\n§7Ban ID: §r#"+Math.abs(Shady.mc.getSession().getProfile().getId().toString().hashCode()));
        component.appendText("\n§7Sharing your Ban ID may affect the processing of your appeal!");
        manager.closeChannel(component);
    }

    public static void fakeUsernameBan(NetworkManager manager) {
        ChatComponentText component = new ChatComponentText("\n§cYou are currently blocked from joining this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §fYour username, " + Shady.mc.getSession().getUsername() + ", is not allowed on the server and is breaking our rules.");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/rules");
        component.appendText("\n");
        component.appendText("\n§cPlease change your Minecraft username before trying to join again.");
        manager.closeChannel(component);
        // Shady.guiToOpen = new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "Failed to connect to the server", component);
    }

}
