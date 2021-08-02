package shady.shady.shady.features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.Utils;

public class RoyalPigeonMacro {

    private static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(Utils.inSkyBlock && Config.isEnabled(Setting.ROYAL_PIGEON_PICKAXE_MACRO) && event.gui instanceof GuiChest) {
            String chestName = ((ContainerChest) ((GuiChest)event.gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();

            if(chestName.contains("Commissions")) {
                if(mc.thePlayer.getHeldItem().getDisplayName().contains("Royal Pigeon")) {
                    for(int i = 0; i < 9; i++) {
                        ItemStack item = mc.thePlayer.inventory.getStackInSlot(i);
                        if(item != null) {
                            if(item.getDisplayName().contains("Refined")) {
                                mc.thePlayer.inventory.currentItem = i;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}
