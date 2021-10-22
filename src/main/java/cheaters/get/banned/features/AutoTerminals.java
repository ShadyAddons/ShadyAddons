package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Contributed by 0Kelvin_
public class AutoTerminals {

    private static final ArrayList<Slot> clickQueue = new ArrayList<>(28);
    private static final int[] mazeDirection = {-9, -1, 1, 9};
    private static TerminalType currentTerminal = TerminalType.NONE;
    private static long lastClickTime;
    private static int windowId;
    private static int windowClicks;
    private static boolean recalculate = false;
    public static boolean testing = false;

    private enum TerminalType {
        MAZE, NUMBERS, CORRECT_ALL, LETTER, COLOR, NONE
    }

    @SubscribeEvent
    public void onGuiDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(!Config.autoTerminals || !Utils.inDungeon) return;

        if(event.gui instanceof GuiChest) {
            Container container = ((GuiChest) event.gui).inventorySlots;
            if(container instanceof ContainerChest) {
                List<Slot> invSlots = container.inventorySlots;
                // Get terminal booleanFieldType from chest name
                if(currentTerminal == TerminalType.NONE) {
                    String chestName = ((ContainerChest) container).getLowerChestInventory().getDisplayName().getUnformattedText();
                    if(chestName.equals("Navigate the maze!")) {
                        currentTerminal = TerminalType.MAZE;
                    } else if(chestName.equals("Click in order!")) {
                        currentTerminal = TerminalType.NUMBERS;
                    } else if(chestName.equals("Correct all the panes!")) {
                        currentTerminal = TerminalType.CORRECT_ALL;
                    } else if(chestName.startsWith("What starts with: '")) {
                        currentTerminal = TerminalType.LETTER;
                    } else if(chestName.startsWith("Select all the")) {
                        currentTerminal = TerminalType.COLOR;
                    }
                }
                if(currentTerminal != TerminalType.NONE) {
                    if(clickQueue.isEmpty() || recalculate) {
                        // Scan chest contents to add to click queue, returns true if it needs to be rescanned.
                        // Note: Sometimes I noticed only half the chest contents loaded on the first frame, so I added this
                        recalculate = getClicks((ContainerChest) container);
                    } else {
                        // Remove clicked items from queue
                        switch(currentTerminal) {
                            case MAZE:
                            case NUMBERS:
                            case CORRECT_ALL:
                                clickQueue.removeIf(slot -> invSlots.get(slot.slotNumber).getHasStack() && invSlots.get(slot.slotNumber).getStack().getItemDamage() == 5);
                                break;
                            case LETTER:
                            case COLOR:
                                clickQueue.removeIf(slot -> invSlots.get(slot.slotNumber).getHasStack() && invSlots.get(slot.slotNumber).getStack().isItemEnchanted());
                                break;
                        }
                    }
                    // Click if delay is up
                    if(!clickQueue.isEmpty()) {
                        if(Config.autoTerminals && System.currentTimeMillis() - lastClickTime > Config.terminalClickDelay) {
                            switch(currentTerminal) {
                                case MAZE:
                                    if(Config.autoMaze) clickSlot(clickQueue.get(0));
                                    break;
                                case NUMBERS:
                                    if(Config.autoNumbers) clickSlot(clickQueue.get(0));
                                    break;
                                case CORRECT_ALL:
                                    if(Config.autoCorrectAll) clickSlot(clickQueue.get(0));
                                    break;
                                case LETTER:
                                    if(Config.autoLetter) clickSlot(clickQueue.get(0));
                                    break;
                                case COLOR:
                                    if(Config.autoColor) clickSlot(clickQueue.get(0));
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(!Config.autoTerminals || !Utils.inDungeon) return;
        if(Shady.mc.thePlayer == null || Shady.mc.theWorld == null) return;

        if(!(Shady.mc.currentScreen instanceof GuiChest)) {
            currentTerminal = TerminalType.NONE;
            clickQueue.clear();
            windowClicks = 0;
        }
    }

    private boolean getClicks(ContainerChest container) {
        List<Slot> invSlots = container.inventorySlots;
        String chestName = container.getLowerChestInventory().getDisplayName().getUnformattedText();
        clickQueue.clear();
        switch(currentTerminal) {
            case MAZE:
                int[] mazeDirection = new int[]{-9, -1, 1, 9};
                boolean[] isStartSlot = new boolean[54];
                int endSlot = -1;
                // Scan chest for start and end
                for(Slot slot : invSlots) {
                    if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
                    ItemStack itemStack = slot.getStack();
                    if(itemStack == null) continue;
                    if(itemStack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane)) {
                        if(itemStack.getItemDamage() == 5) {
                            isStartSlot[slot.slotNumber] = true;
                        } else if(itemStack.getItemDamage() == 14) {
                            endSlot = slot.slotNumber;
                        }
                    }
                }
                // Plan route for maze from start to end
                for (int slot = 0; slot < 54; slot++) {
                    if (isStartSlot[slot]) {
                        boolean[] mazeVisited = new boolean[54];
                        int startSlot = slot;
                        while(startSlot != endSlot) {
                            boolean newSlotChosen = false;
                            for(int i : mazeDirection) {
                                int nextSlot = startSlot + i;
                                if(nextSlot < 0 || nextSlot > 53 || i == -1 && startSlot % 9 == 0 || i == 1 && startSlot % 9 == 8) continue;
                                if (nextSlot == endSlot) return false;
                                if(mazeVisited[nextSlot]) continue;
                                ItemStack itemStack = invSlots.get(nextSlot).getStack();
                                if(itemStack == null) continue;
                                if(itemStack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) && itemStack.getItemDamage() == 0) {
                                    clickQueue.add(invSlots.get(nextSlot));
                                    startSlot = nextSlot;
                                    mazeVisited[nextSlot] = true;
                                    newSlotChosen = true;
                                    break;
                                }
                            }
                            // Prevents infinite loop if there is no adjacent white pane
                            if(!newSlotChosen) {
                                System.out.println("Maze calculation aborted");
                                return true;
                            }
                        }
                    }
                }
                return true;

            case NUMBERS:
                int min = 0;
                Slot[] temp = new Slot[14];
                while(clickQueue.size() < 14) clickQueue.add(null);
                for(int i = 10; i <= 25; i++) {
                    if(i == 17 || i == 18) continue;
                    ItemStack itemStack = invSlots.get(i).getStack();
                    if(itemStack == null) continue;
                    if(itemStack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) && itemStack.stackSize < 15) {
                        if (itemStack.getItemDamage() == 14) {
                            temp[itemStack.stackSize - 1] = invSlots.get(i);
                        } else if (itemStack.getItemDamage() == 5) {
                            if (min < itemStack.stackSize) {
                                min = itemStack.stackSize;
                            }
                        }
                    }
                }
                clickQueue.addAll(Arrays.stream(temp).filter(Objects::nonNull).collect(Collectors.toList()));
                if (clickQueue.size() != 14 - min) return true;
                break;

            case CORRECT_ALL:
                for(Slot slot : invSlots) {
                    if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
                    if(slot.slotNumber < 9 || slot.slotNumber > 35 || slot.slotNumber % 9 <= 1 || slot.slotNumber % 9 >= 7)
                        continue;
                    ItemStack itemStack = slot.getStack();
                    if(itemStack == null) return true;
                    if(itemStack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) && itemStack.getItemDamage() == 14) {
                        clickQueue.add(slot);
                    }
                }
                break;

            case LETTER:
                if (chestName.length() > chestName.indexOf("'") + 1) {
                    char letterNeeded = chestName.charAt(chestName.indexOf("'") + 1);
                    for(Slot slot : invSlots) {
                        if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
                        if(slot.slotNumber < 9 || slot.slotNumber > 44 || slot.slotNumber % 9 == 0 || slot.slotNumber % 9 == 8)
                            continue;
                        ItemStack itemStack = slot.getStack();
                        if(itemStack == null) return true;
                        if(itemStack.isItemEnchanted()) continue;
                        if(StringUtils.stripControlCodes(itemStack.getDisplayName()).charAt(0) == letterNeeded) {
                            clickQueue.add(slot);
                        }
                    }
                }
                break;

            case COLOR:
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
                    for(Slot slot : invSlots) {
                        if(slot.inventory == Shady.mc.thePlayer.inventory) continue;
                        if(slot.slotNumber < 9 || slot.slotNumber > 44 || slot.slotNumber % 9 == 0 || slot.slotNumber % 9 == 8)
                            continue;
                        ItemStack itemStack = slot.getStack();
                        if(itemStack == null) return true;
                        if(itemStack.isItemEnchanted()) continue;
                        if(itemStack.getUnlocalizedName().contains(colorNeeded)) {
                            clickQueue.add(slot);
                        }
                    }
                }
                break;
        }
        return false;
    }

    private void clickSlot(Slot slot) {
        if(windowClicks == 0) windowId = Shady.mc.thePlayer.openContainer.windowId;
        if(testing) {
            Shady.mc.playerController.windowClick(windowId + windowClicks, slot.slotNumber, 0, 1, Shady.mc.thePlayer);
        } else {
            Shady.mc.playerController.windowClick(windowId + windowClicks, slot.slotNumber, 2, 0, Shady.mc.thePlayer);
        }
        lastClickTime = System.currentTimeMillis();
        // Immediately remove from queue before gui updates
        if(Config.terminalPingless) {
            windowClicks++;
            clickQueue.remove(slot);
        }
    }

}
