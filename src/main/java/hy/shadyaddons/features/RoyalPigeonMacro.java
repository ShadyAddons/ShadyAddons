package hy.shadyaddons.features;

import hy.shadyaddons.config.Config;
import hy.shadyaddons.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class RoyalPigeonMacro {

    private static Minecraft mc = Minecraft.getMinecraft();
    private static ArrayList<String> allowedPickaxes = new ArrayList<>(Arrays.asList(
            "ALPHA_PICK",
            "STONK_PICKAXE"
    ));

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(Utils.inSkyBlock && Config.isEnabled(Config.Setting.ROYAL_PIGEON_PICKAXE_MACRO) && event.gui instanceof GuiChest) {
            String chestName = ((ContainerChest) ((GuiChest)event.gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();

            if(chestName.contains("Commissions")) {
                if(!allowedPickaxes.contains(Utils.getSkyBlockID(mc.thePlayer.getHeldItem()))) {
                    for(int i = 0; i < 9; i++) {
                        ItemStack item = mc.thePlayer.inventory.getStackInSlot(i);
                        if(item != null) {
                            if(allowedPickaxes.contains(Utils.getSkyBlockID(item))) {
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
