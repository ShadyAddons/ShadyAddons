package cheaters.get.banned.features.include;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.config.components.ClearButton;
import cheaters.get.banned.events.DrawSlotEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AutoSalvage {

    private static final ClearButton button = new ClearButton(
            0,
            0,
            0,
            71,
            20,
            "§aStart"
    );
    private boolean inSalvageGui = false;
    private boolean salvaging = false;

    private int tickCount = 0;

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(tickCount % 5 == 0) {
            if(Config.autoSalvage && inSalvageGui && salvaging && Shady.mc.currentScreen instanceof GuiChest) {
                List<Slot> chestInventory = ((GuiChest) Shady.mc.currentScreen).inventorySlots.inventorySlots;
                if(chestInventory != null && chestInventory.get(31).getStack() != null && chestInventory.get(31).getStack().getItem() == Items.skull) {
                    // Confirm salvaging legendary items
                    if(chestInventory.get(22) != null && chestInventory.get(22).getStack() != null & chestInventory.get(22).getStack().getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
                        Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, 22, 2, 0, Shady.mc.thePlayer);
                    }

                    if(chestInventory.get(13) != null && chestInventory.get(13).getStack() != null) {
                        if(chestInventory.get(22) != null && chestInventory.get(22).getStack() != null && chestInventory.get(22).getStack().getItem() == Item.getItemFromBlock(Blocks.anvil)) {
                            // Click anvil to salvage the item
                            Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, 22, 2, 0, Shady.mc.thePlayer);
                            MiscStats.add(MiscStats.Metric.ITEMS_SALVAGED);
                        }
                    }
                } else if(chestInventory.get(13) != null && chestInventory.get(13).getStack() == null) { // If no item is waiting to be salvaged
                    ArrayList<Slot> itemsToSalvage = new ArrayList<>(Shady.mc.thePlayer.inventoryContainer.inventorySlots);
                    itemsToSalvage.removeIf(slot -> !shouldSalvage(slot.getStack()));
                    if(itemsToSalvage.isEmpty()) {
                        salvaging = false;
                    } else {
                        // Shift-click an item to be salvaged
                        Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, 45 + itemsToSalvage.get(0).slotNumber, 0, 1, Shady.mc.thePlayer);
                    }
                }
            }
        }
        tickCount++;
    }

    @SubscribeEvent
    public void onBackgroundRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        String chestName = Utils.getGuiName(event.gui);
        inSalvageGui = chestName.equals("Salvage Dungeon Item");
        if(Config.autoSalvage && inSalvageGui) {
            if(Config.automaticallyStartSalvaging) {
                salvaging = true;
            } else {
                ScaledResolution scaledResolution = new ScaledResolution(Shady.mc);
                button.xPosition = (scaledResolution.getScaledWidth() - button.width) / 2;
                button.yPosition = scaledResolution.getScaledHeight() / 2 - 145;
                button.displayString = salvaging ? "§cStop" : "§aStart";
                button.drawButton(event.gui.mc, event.getMouseX(), event.getMouseY());
            }
        } else {
            salvaging = false;
        }
    }

    @SubscribeEvent
    public void onDrawSlot(DrawSlotEvent event) {
        if(Config.autoSalvage && inSalvageGui && button.isMouseOver() && shouldSalvage(event.slot.getStack())) {
            int x = event.slot.xDisplayPosition;
            int y = event.slot.yDisplayPosition;
            Gui.drawRect(x, y, x + 16, y + 16, Utils.addAlpha(Color.RED, 128).getRGB());
        }
    }

    @SubscribeEvent
    public void onMouseInput(GuiScreenEvent.MouseInputEvent.Pre event) {
        if(Utils.inSkyBlock && Config.autoSalvage && inSalvageGui &&
                button.isMouseOver() && !Config.automaticallyStartSalvaging && Mouse.isButtonDown(0)
        ) {
            salvaging = !salvaging;
        }
    }

    public static boolean shouldSalvage(ItemStack item) {
        if(item == null) return false;

        NBTTagCompound attributes = item.getSubCompound("ExtraAttributes", false);
        if(attributes == null) return false;
        if(!attributes.hasKey("baseStatBoostPercentage") || attributes.hasKey("dungeon_item_level")) return false;

        if(Utils.getSkyBlockID(item).equals("ICE_SPRAY_WAND")) return false;

        return true;
    }

}
