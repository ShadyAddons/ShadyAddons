package cheaters.get.banned.features.autoterminals.solvers;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.autoterminals.ClickQueue;
import cheaters.get.banned.features.autoterminals.SolverBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CorrectPanesSolver extends SolverBase {

    @Override
    public ClickQueue getClicks(List<Slot> inventory, String chestName) {
        ClickQueue clickQueue = new ClickQueue();

        for(Slot slot : inventory) {
            if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
            if(slot.slotNumber < 9 || slot.slotNumber > 35 || slot.slotNumber % 9 <= 1 || slot.slotNumber % 9 >= 7) continue;
            ItemStack itemStack = slot.getStack();
            if(itemStack == null) return null;
            if(itemStack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) && itemStack.getItemDamage() == 14) {
                clickQueue.middleClick(slot);
            }
        }

        return clickQueue;
    }

}
