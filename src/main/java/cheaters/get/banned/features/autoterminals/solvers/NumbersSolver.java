package cheaters.get.banned.features.autoterminals.solvers;

import cheaters.get.banned.features.autoterminals.ClickQueue;
import cheaters.get.banned.features.autoterminals.SolverBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class NumbersSolver extends SolverBase {

    @Override
    public ClickQueue getClicks(List<Slot> inventory, String chestName) {
        ClickQueue clickQueue = new ClickQueue();

        int min = 0;
        Slot[] temp = new Slot[14];
        for(int i = 10; i <= 25; i++) {
            if(i == 17 || i == 18) continue;
            ItemStack itemStack = inventory.get(i).getStack();
            if(itemStack == null) continue;
            if(itemStack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) && itemStack.stackSize < 15) {
                if(itemStack.getItemDamage() == 14) {
                    temp[itemStack.stackSize - 1] = inventory.get(i);
                } else if(itemStack.getItemDamage() == 5) {
                    if(min < itemStack.stackSize) {
                        min = itemStack.stackSize;
                    }
                }
            }
        }

        for(Slot slot : temp) {
            if(slot != null) clickQueue.middleClick(slot);
        }

        if(clickQueue.size() != 14 - min) return null;

        return clickQueue;
    }

}
