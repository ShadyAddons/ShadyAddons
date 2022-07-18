package cheaters.get.banned.features.autoterminals.solvers;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.autoterminals.ClickQueue;
import cheaters.get.banned.features.autoterminals.SolverBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimingSolver extends SolverBase {
	
    @Override
    public ClickQueue getClicks(List<Slot> inventory, String chestName) {
    	ClickQueue clickQueue = new ClickQueue();
    	Slot validateSlot = null;
    	int line = 1;
    	int column = 1;
    	int indexProgressionPane = -1;
    	for(Slot slot : inventory) {
            if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
            ItemStack itemStack = slot.getStack();
            if(itemStack == null) return null;
            int slotIndex = inventory.indexOf(slot);
            if(slotIndex >= 1 && slotIndex <= 5) {
            	if(Block.getBlockFromItem(itemStack.getItem()) == Blocks.stained_glass_pane && itemStack.getItemDamage() == 10) {
            		column = slotIndex;
            	}
            }
            if(Block.getBlockFromItem(itemStack.getItem()) == Blocks.stained_hardened_clay && itemStack.getItemDamage() == 5) {
            	switch(slotIndex) {
	        		case 16:
	        			line = 1;
	        			validateSlot = slot;
	        			break;
	        		case 25:
	        			line = 2;
	        			validateSlot = slot;
	        			break;
	        		case 34:
	        			line = 3;
	        			validateSlot = slot;
	        			break;
	        		case 43:
	        			line = 4;
	        			validateSlot = slot;
	        			break;
    		}
            }
            if(Block.getBlockFromItem(itemStack.getItem()) == Blocks.stained_glass_pane && itemStack.getItemDamage() == 5) {
            	indexProgressionPane = slotIndex;
            }
    	}
    	if(indexProgressionPane == 9 * line + column) {
    		clickQueue.middleClick(validateSlot);
    	}
        return clickQueue;
    }

}
