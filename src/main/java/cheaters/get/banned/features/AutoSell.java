package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class AutoSell {

    private boolean inTradeMenu = false;
    private int tickCount = 0;

    private static final String[] salable = new String[]{
            "Training Weight",
            "Healing Potion VIII",
            "Healing Potion 8",
            "Beating Heart",
            "Premium Flesh",
            "Mimic Fragment",
            "Enchanted Rotten Flesh",
            "Enchanted Bone",
            "Defuse Kit",
            "Enchanted Ice",
            "Optic Lense",
            "Tripwire Hook",
            "Button",
            "Carpet",
            "Lever",
            "Rune",
            "Journal Entry"
    };

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(tickCount % 3 == 0) {
            if(Utils.inSkyBlock && Config.autoSell && inTradeMenu && Shady.mc.currentScreen instanceof GuiChest) {
                List<Slot> chestInventory = ((GuiChest) Shady.mc.currentScreen).inventorySlots.inventorySlots;
                if(chestInventory.get(49).getStack() != null && chestInventory.get(49).getStack().getItem() != Item.getItemFromBlock(Blocks.barrier)) {
                    for(Slot slot : Shady.mc.thePlayer.inventoryContainer.inventorySlots) {
                        if(shouldSell(slot.getStack())) {
                            Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, 45 + slot.slotNumber, 2, 0, Shady.mc.thePlayer);
                            break;
                        }
                    }
                }
            }
        }
        tickCount++;
    }

    @SubscribeEvent
    public void onBackgroundRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        String chestName = Utils.getGuiName(event.gui);
        inTradeMenu = chestName.equals("Trades");
    }

    private boolean shouldSell(ItemStack item) {
        if(item != null) {
            if(Config.autoSellSalvageable && AutoSalvage.shouldSalvage(item)) return true;

            if(Config.autoSellSuperboom && Utils.getSkyBlockID(item).equals("SUPERBOOM_TNT")) return true;

            if(Config.autoSellPotions && item.getDisplayName().contains("Potion")) {
                if(item.getDisplayName().contains("Speed") || item.getDisplayName().contains("Weakness")) {
                    return true;
                }
            }

            if(Config.autoSellDungeonsJunk) {
                for(String name : salable) {
                    if(item.getDisplayName().contains(name)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
