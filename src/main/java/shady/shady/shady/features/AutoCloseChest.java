package shady.shady.shady.features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.Utils;

public class AutoCloseChest {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(event.gui != null) {
            if(event.gui instanceof GuiChest) {
                String chestName = ((ContainerChest) ((GuiChest)event.gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();

                if(Utils.inDungeon && Config.isEnabled(Setting.CLOSE_SECRET_CHESTS) && chestName.equals("Chest")) {
                    closeChest();
                }

                if(Utils.inSkyBlock && Config.isEnabled(Setting.CLOSE_CRYSTAL_HOLLOWS_CHESTS) && (chestName.contains("Loot Chest") || chestName.contains("Treasure Chest"))) {
                    closeChest();
                }
            }
        }
    }

    private void closeChest() {
        Minecraft.getMinecraft().thePlayer.closeScreen();
    }

}
