package cheaters.get.banned.features.jokes;

import cheaters.get.banned.Shady;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class FakeBan {

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if(message.contains("!BANNED!") && message.hashCode() == -1292812670) {
            event.setCanceled(true);
            fakeGenericBan();
        }
    }

    @SubscribeEvent
    public void onJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if(Shady.mc.getCurrentServerData() != null && Shady.mc.getCurrentServerData().serverIP.contains("hypixel.net") && !Shady.mc.getCurrentServerData().serverIP.contains("letmein")) {
            switch(Shady.mc.getSession().getProfile().getName().hashCode()) {
                case -1152592972:
                case -45179611:
                    fakeUsernameBan(Shady.mc.getSession().getProfile().getName(), event.manager);
            }
        }
    }

    public static void fakeGenericBan() {
        ChatComponentText component = new ChatComponentText("§cYou are temporarily banned for §f29d 23h 59m 59s §cfrom this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §rCheating through the use of unfair game advantages.");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/appeal");
        component.appendText("\n");
        component.appendText("\n§7Ban ID: §r#"+Math.abs(Shady.mc.getSession().getProfile().getId().toString().hashCode()));
        component.appendText("\n§7Sharing your Ban ID may affect the processing of your appeal!");
        Shady.mc.getNetHandler().getNetworkManager().closeChannel(component);
    }

    public static void fakeUsernameBan(String username, NetworkManager manager) {
        ChatComponentText component = new ChatComponentText("\n§cYou are currently blocked from joining this server!");
        component.appendText("\n");
        component.appendText("\n§7Reason: §fYour username, " + username + ", is not allowed on the server and is breaking our rules.");
        component.appendText("\n§7Find out more: §b§nhttps://www.hypixel.net/rules");
        component.appendText("\n");
        component.appendText("\n§cPlease change your Minecraft username before trying to join again.");
        manager.closeChannel(component);
        Shady.guiToOpen = new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "Failed to connect to the server", component);
    }

}
