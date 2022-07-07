package cheaters.get.banned.features.autoterminals.solvers;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.autoterminals.ClickQueue;
import cheaters.get.banned.features.autoterminals.SolverBase;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToggleColorSolver extends SolverBase {

    private static final ArrayList<Integer> colorOrder = new ArrayList<>(Arrays.asList(14, 1, 4, 13, 11));
    private static int colorNeededIndex = -2;

    @Override
    public ClickQueue getClicks(List<Slot> inventory, String chestName) {
    	List<Slot> puzzleSlots = getPuzzle(inventory);
    	if(colorNeededIndex == -2) {
    		colorNeededIndex = getTargetColorIndex(puzzleSlots);
    	}
    	int targetColorIndex = colorNeededIndex;
    	ClickQueue clickQueue = new ClickQueue();
    	if(targetColorIndex == -2 || targetColorIndex == -1 || targetColorIndex == 15) return null;
        for(Slot slot : puzzleSlots) {
            if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
            ItemStack itemStack = slot.getStack();
            if(itemStack == null) return null;
            int indexSlot = colorOrder.indexOf(itemStack.getItemDamage());
            if(indexSlot == targetColorIndex) continue;
            int clickNeeded = clickColorNeeded(targetColorIndex , indexSlot);
            for(int i = 0; i < clickNeeded; i++) {
            	clickQueue.middleClick(slot);
            }
        }
        return clickQueue;
    }
    public List<Slot> getPuzzle(List<Slot> inventory){
    	return new ArrayList<Slot>(Arrays.asList(inventory.get(12), inventory.get(13), inventory.get(14), inventory.get(21), inventory.get(22), inventory.get(23), inventory.get(30), inventory.get(31), inventory.get(32)));
    }
    public int getTargetColorIndex(List<Slot> slots) {
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
    
    public int clickColorNeeded(int indexNeededColor, int indexSlotColor) {
    	int clicks = 0;
    	int index = indexSlotColor;
    	while(index != indexNeededColor) {
    		index++;
    		if(index > 4) {
    			index = 0;
    		}
    		clicks++;
    	}
    	return clicks;
    }
    
    public static void resetColorNeeded() {
    	colorNeededIndex = -2;
    }

}
