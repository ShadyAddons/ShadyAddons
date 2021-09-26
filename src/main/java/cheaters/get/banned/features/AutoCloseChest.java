package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoCloseChest {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(event.gui instanceof GuiChest && Utils.inSkyBlock) {
            String chestName = ((ContainerChest) ((GuiChest)event.gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();

            if(Utils.inDungeon && Config.closeSecretChests && chestName.equals("Chest")) {
                closeChest();
            }

            if(Utils.inSkyBlock && Config.closeCrystalHollowsChests && (chestName.contains("Loot Chest") || chestName.contains("Treasure Chest"))) {
                closeChest();
            }
        }
    }

    public static void closeChest() {
        Shady.mc.thePlayer.closeScreen();
    }

}
