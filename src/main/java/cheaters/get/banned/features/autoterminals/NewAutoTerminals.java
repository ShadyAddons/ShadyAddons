package cheaters.get.banned.features.autoterminals;

import cheaters.get.banned.features.autoterminals.solvers.*;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewAutoTerminals {

    private static long lastClick = 0;
    public static int windowClicks = 0;
    
    
    @SubscribeEvent
    public void onGuiDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(!Config.autoTerminals || !Utils.inDungeon || !(event.gui instanceof GuiChest)) return;

        Container container = ((GuiChest) event.gui).inventorySlots;
        if(!(container instanceof ContainerChest)) return;
        List<Slot> slots = container.inventorySlots;
        if(slots.isEmpty()) return;
        String chestName = Utils.getGuiName(event.gui);
        Utils.log("Chest Name: " + chestName);

        ClickQueue clickQueue;

        if(chestName.equals("Navigate the maze!")) {
            clickQueue = new MazeSolver().getClicks(slots, chestName);
        } else if(chestName.equals("Click in order!")) {
            clickQueue = new NumbersSolver().getClicks(slots, chestName);
        } else if(chestName.equals("Correct all the panes!")) {
            clickQueue = new CorrectPanesSolver().getClicks(slots, chestName);
        } else if(chestName.startsWith("What starts with: '")) {
            clickQueue = new LetterSolver().getClicks(slots, chestName);
        } else if(chestName.startsWith("Select all the")) {
            clickQueue = new ColorSolver().getClicks(slots, chestName);
        } else if(chestName.equals("Change all to same color!")) {
            clickQueue = new ToggleColorSolver().getClicks(slots, chestName);
        } else if(chestName.equals("Click the button on time!")) {
        	clickQueue = new TimingSolver().getClicks(slots, chestName);
        } else {
            return;
        }

        if(clickQueue == null) {
            Utils.log("Something is possibly wrong with the click queue");
            return;
        }
        if(clickQueue.size() != 0) {
	        if(System.currentTimeMillis() - lastClick >= Config.terminalClickDelay) {
	            windowClicks = clickQueue.clickFirst(windowClicks);
	            lastClick = System.currentTimeMillis();
	            if(clickQueue.size() == 0) {
	            	 ToggleColorSolver.resetColorNeeded();
	            }
	        }
	        
        }
    }

}
