package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoWardrobe {
    private static int page = 1;
    private static int slot = 0;
    private static boolean openWardrobe;

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
        if (!openWardrobe) return;
        if (event.gui instanceof GuiChest) {
            Container container = ((GuiChest) event.gui).inventorySlots;
            if (container instanceof ContainerChest) {
                String chestName = ((ContainerChest) container).getLowerChestInventory().getDisplayName().getUnformattedText();
                if (chestName.equals("Pets")) {
                    clickSlot(48, 0, 0);
                    clickSlot(32, 0, 1);
                    if (page == 1) {
                        if (slot > 0 && slot < 10) {
                            clickSlot(slot + 35, 0, 2);
                            Shady.mc.thePlayer.closeScreen();
                        }
                        openWardrobe = false;
                    } else if (page == 2) {
                        clickSlot(53, 2, 2);
                        if (slot > 0 && slot < 10) {
                            clickSlot(slot + 35, 0, 3);
                            Shady.mc.thePlayer.closeScreen();
                        }
                        openWardrobe = false;
                    }
                }
            }
        }
    }

    private void clickSlot(int slot, int type, int windowAdd) {
        Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId + windowAdd, slot, type, 0, Shady.mc.thePlayer);
    }
}
