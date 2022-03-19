package cheaters.get.banned.features.autoterminals.solvers;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.autoterminals.ClickQueue;
import cheaters.get.banned.features.autoterminals.SolverBase;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ColorSolver extends SolverBase {

    @Override
    public ClickQueue getClicks(List<Slot> inventory, String chestName) {
        ClickQueue clickQueue = new ClickQueue();

        // Get color from chest name
        String colorNeeded = null;
        for(EnumDyeColor color : EnumDyeColor.values()) {
            String colorName = color.getName().replaceAll("_", " ").toUpperCase();
            if(chestName.contains(colorName)) {
                colorNeeded = color.getUnlocalizedName();
                break;
            }
        }

        if(colorNeeded != null) {
            for(Slot slot : inventory) {
                if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
                if(slot.slotNumber < 9 || slot.slotNumber > 44 || slot.slotNumber % 9 == 0 || slot.slotNumber % 9 == 8)
                    continue;
                ItemStack itemStack = slot.getStack();
                if(itemStack == null) return null;
                if(itemStack.isItemEnchanted()) continue;
                if(itemStack.getUnlocalizedName().contains(colorNeeded)) {
                    clickQueue.middleClick(slot);
                }
            }
        }

        return clickQueue;
    }

}
