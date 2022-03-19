package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class AutoMelody {

    private boolean inHarp = false;
    private ArrayList<Item> lastInventory = new ArrayList<>();

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(event.gui instanceof GuiChest && Utils.inSkyBlock && Config.autoMelody) {
            if(Utils.getGuiName(event.gui).startsWith("Harp -")) {
                lastInventory.clear();
                inHarp = true;
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(!inHarp || !Config.autoMelody || Shady.mc.thePlayer == null) return;
        String inventoryName = Utils.getInventoryName();
        if(inventoryName == null || !inventoryName.startsWith("Harp -")) inHarp = false;

        ArrayList<Item> thisInventory = new ArrayList<>();
        for(Slot slot : Shady.mc.thePlayer.openContainer.inventorySlots) {
            if(slot.getStack() != null) thisInventory.add(slot.getStack().getItem());
        }

        if(!lastInventory.toString().equals(thisInventory.toString())) {
            for(Slot slot : Shady.mc.thePlayer.openContainer.inventorySlots) {
                if(slot.getStack() != null && slot.getStack().getItem() instanceof ItemBlock && ((ItemBlock) slot.getStack().getItem()).getBlock() == Blocks.quartz_block) {
                    Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, slot.slotNumber, 2, 0, Shady.mc.thePlayer);
                    break;
                }
            }
        }

        lastInventory.clear();
        lastInventory.addAll(thisInventory);
    }

}
