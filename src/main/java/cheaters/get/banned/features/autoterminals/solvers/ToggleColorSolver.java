package cheaters.get.banned.features.autoterminals.solvers;

import cheaters.get.banned.features.autoterminals.ClickQueue;
import cheaters.get.banned.features.autoterminals.SolverBase;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToggleColorSolver extends SolverBase {

    private static final ArrayList<Integer> colorOrder = new ArrayList<>(Arrays.asList(14, 1, 4, 13, 11));

    @Override
    public ClickQueue getClicks(List<Slot> inventory, String chestName) {
        return null;
    }

    private static int getTargetColorIndex(List<Slot> slots) {
        if(slots.isEmpty()) return 15;

        float sum = 0;
        for(Slot slot : slots) {
            if(slot == null) continue;
            ItemStack stack = slot.getStack();
            if(stack == null) continue;
            sum += colorOrder.indexOf(stack.getItemDamage());
        }

        int index = Math.round(sum / slots.size());
        return colorOrder.size() > index ? index : -1;
    }

}
