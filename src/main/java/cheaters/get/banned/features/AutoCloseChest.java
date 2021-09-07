package cheaters.get.banned.features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.Setting;
import cheaters.get.banned.utils.Utils;

public class AutoCloseChest {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(event.gui instanceof GuiChest && Utils.inSkyBlock) {
            String chestName = ((ContainerChest) ((GuiChest)event.gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();

            if(Utils.inDungeon && Config.isEnabled(Setting.CLOSE_SECRET_CHESTS) && chestName.equals("Chest")) {
                closeChest();
            }

            if(Utils.inSkyBlock && Config.isEnabled(Setting.CLOSE_CRYSTAL_HOLLOWS_CHESTS) && (chestName.contains("Loot Chest") || chestName.contains("Treasure Chest"))) {
                closeChest();
            }
        }
    }

    public static void closeChest() {
        Minecraft.getMinecraft().thePlayer.closeScreen();
    }

}
