package cheaters.get.banned.features.autoterminals.solvers;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.autoterminals.ClickQueue;
import cheaters.get.banned.features.autoterminals.SolverBase;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

import java.util.List;

public class LetterSolver extends SolverBase {

    @Override
    public ClickQueue getClicks(List<Slot> inventory, String chestName) {
        ClickQueue clickQueue = new ClickQueue();

        if(chestName.length() > chestName.indexOf("'") + 1) {
            char letterNeeded = chestName.charAt(chestName.indexOf("'") + 1);
            for(Slot slot : inventory) {
                if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
                if(slot.slotNumber < 9 || slot.slotNumber > 44 || slot.slotNumber % 9 == 0 || slot.slotNumber % 9 == 8)
                    continue;
                ItemStack itemStack = slot.getStack();
                if(itemStack == null) return null;
                if(itemStack.isItemEnchanted()) continue;
                if(StringUtils.stripControlCodes(itemStack.getDisplayName()).charAt(0) == letterNeeded) {
                    clickQueue.middleClick(slot);
                }
            }
        }

        return clickQueue;
    }

}
