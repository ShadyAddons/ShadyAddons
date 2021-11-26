package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class AutoWardrobe {
    private static int page = 1;
    private static int slot = 0;
    private static boolean openWardrobe;
    private Thread thread;

    public static void execute() {
        page = 1;
        slot = 0;
        openWardrobe = true;
        Utils.sendMessageAsPlayer("/pets");
    }

    public static void execute(int num) {
        page = 1;
        slot = num;
        openWardrobe = true;
        Utils.sendMessageAsPlayer("/pets");
    }

    public static void execute(int num1, int num2) {
        page = num1;
        slot = num2;
        openWardrobe = true;
        Utils.sendMessageAsPlayer("/pets");
    }

    @SubscribeEvent
    public void GuiDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(!openWardrobe) return;
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                try {
                    if(event.gui instanceof GuiChest) {
                        Container container = ((GuiChest) event.gui).inventorySlots;
                        if (container instanceof ContainerChest) {
                            List<Slot> invSlots = container.inventorySlots;
                            String chestName = ((ContainerChest) container).getLowerChestInventory().getDisplayName().getUnformattedText();
                            switch (chestName) {
                                case "Pets":
                                    //not 48 due to issues with over-clicking
                                    for(Slot slot : invSlots) {
                                        if (!slot.getHasStack()) continue;
                                        if(slot.getStack().getDisplayName().equals("§aGo Back")) {
                                            clickSlot(slot.slotNumber, 2);
                                            break;
                                        }
                                    }
                                    break;

                                case "SkyBlock Menu":
                                    for(Slot slot : invSlots) {
                                        if (!slot.getHasStack()) continue;
                                        if(slot.getStack().getDisplayName().equals("§aWardrobe")) {
                                            clickSlot(slot.slotNumber, 2);
                                            break;
                                        }
                                    }
                                    break;

                                case "Wardrobe (1/2)":
                                    if(page == 1) {
                                        if(slot > 0 && slot < 10) {
                                            clickSlot(slot + 35, 0);
                                            Shady.mc.thePlayer.closeScreen();
                                        }
                                        openWardrobe = false;
                                    }
                                    else if(page == 2) {
                                        clickSlot(53, 2);
                                    }
                                    break;

                                case "Wardrobe (2/2)":
                                    if(slot > 0 && slot < 10) {
                                        clickSlot(slot + 35, 0);
                                        Shady.mc.thePlayer.closeScreen();
                                    }
                                    openWardrobe = false;
                                    break;
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }, "Auto Wardrobe");
            thread.start();
        }
    }

    private void clickSlot(int slot, int type) {
        Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, slot, type, 0, Shady.mc.thePlayer);
    }
}
