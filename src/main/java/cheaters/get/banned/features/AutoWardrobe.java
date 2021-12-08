package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// Contributed by RoseGold
public class AutoWardrobe {

    private static int page = 0;
    private static int slot = 0;
    private static boolean shouldOpenWardrobe = false;

    public static void open(int page, int slot) {
        AutoWardrobe.page = page;
        AutoWardrobe.slot = slot;
        shouldOpenWardrobe = true;
        Utils.executeCommand("/pets");
    }

    @SubscribeEvent
    public void onDrawGuiBackground(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(Utils.inSkyBlock && shouldOpenWardrobe && event.gui instanceof GuiChest) {

            Container container = ((GuiChest) event.gui).inventorySlots;
            if(container instanceof ContainerChest) {

                String chestName = Utils.getGuiName(event.gui);
                if(chestName.endsWith("Pets")) {

                    clickSlot(48, 0, 0);
                    clickSlot(32, 0, 1);

                    if(page == 1) {
                        if(slot > 0 && slot < 10) {
                            clickSlot(slot + 35, 0, 2);
                            Shady.mc.thePlayer.closeScreen();
                        }
                    } else if(page == 2) {
                        clickSlot(53, 2, 2);
                        if(slot > 0 && slot < 10) {
                            clickSlot(slot + 35, 0, 3);
                            Shady.mc.thePlayer.closeScreen();
                        }
                    }

                    shouldOpenWardrobe = false;
                }
            }
        }
    }

    private void clickSlot(int slot, int type, int incrementWindowId) {
        Shady.mc.playerController.windowClick(
                Shady.mc.thePlayer.openContainer.windowId + incrementWindowId,
                slot,
                type,
                0,
                Shady.mc.thePlayer
        );
    }

}
