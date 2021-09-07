package cheaters.get.banned.features;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.Setting;
import cheaters.get.banned.utils.Utils;

public class RoyalPigeonMacro {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(Utils.inSkyBlock && Config.isEnabled(Setting.ROYAL_PIGEON_PICKAXE_MACRO) && event.gui instanceof GuiChest) {
            String chestName = ((ContainerChest) ((GuiChest)event.gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();

            if(chestName.contains("Commissions")) {
                if(Shady.mc.thePlayer.getHeldItem().getDisplayName().contains("Royal Pigeon")) {
                    for(int i = 0; i < 9; i++) {
                        ItemStack item = Shady.mc.thePlayer.inventory.getStackInSlot(i);
                        if(item != null) {
                            if(item.getDisplayName().contains("Refined")) {
                                Shady.mc.thePlayer.inventory.currentItem = i;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}
