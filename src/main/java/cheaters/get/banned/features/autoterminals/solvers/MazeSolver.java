package cheaters.get.banned.features.autoterminals.solvers;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.autoterminals.ClickQueue;
import cheaters.get.banned.features.autoterminals.SolverBase;
import cheaters.get.banned.utils.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

// kelvin wtf
public class MazeSolver extends SolverBase {

    @Override
    public ClickQueue getClicks(List<Slot> inventory, String chestName) {
        ClickQueue clickQueue = new ClickQueue();

        for(Slot slot : inventory) {
            if(slot == null || slot.getStack() == null) continue;
            if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
            if(slot.getStack().getItemDamage() == 5) continue;

            int[] mazeDirection = new int[]{-9, -1, 1, 9};
            boolean[] isStartSlot = new boolean[54];
            int endSlot = -1;

            // Scan chest for start and end
            if(slot.getStack().getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane)) {
                if(slot.getStack().getItemDamage() == 5) {
                    isStartSlot[slot.slotNumber] = true;
                } else if(slot.getStack().getItemDamage() == 14) {
                    endSlot = slot.slotNumber;
                }
            }

            // Plan route for maze from start to end
            for(int slotIndex = 0; slotIndex < 54; slotIndex++) {
                if(isStartSlot[slotIndex]) {
                    boolean[] mazeVisited = new boolean[54];
                    int startSlot = slotIndex;
                    while(startSlot != endSlot) {
                        boolean newSlotChosen = false;
                        for(int i : mazeDirection) {
                            int nextSlot = startSlot + i;
                            if(nextSlot < 0 || nextSlot > 53 || i == -1 && startSlot % 9 == 0 || i == 1 && startSlot % 9 == 8) continue;
                            if(nextSlot == endSlot) return null;
                            if(mazeVisited[nextSlot]) continue;
                            ItemStack itemStack = inventory.get(nextSlot).getStack();
                            if(itemStack == null) continue;
                            if(itemStack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) && itemStack.getItemDamage() == 0) {
                                clickQueue.middleClick(inventory.get(nextSlot));
                                startSlot = nextSlot;
                                mazeVisited[nextSlot] = true;
                                newSlotChosen = true;
                                break;
                            }
                        }

                        // Prevents infinite loop if there is no adjacent white pane
                        if(!newSlotChosen) {
                            Utils.log("Maze calculation aborted");
                        }
                    }
                }
            }
        }

        return clickQueue;
    }

}
