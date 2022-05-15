package cheaters.get.banned.features.routines.triggers;

import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import cheaters.get.banned.utils.ReflectionUtils;
import jdk.internal.reflect.Reflection;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ChestOpenTrigger extends Trigger {

    private String nameContains;

    public ChestOpenTrigger(RoutineElementData data) throws RoutineRuntimeException {
        super(data);

        nameContains = data.keyAsString(data.keyAsString("name_contains"), "");
    }

    @Override
    public boolean shouldTrigger(Event event) {
        if (!(event instanceof GuiScreenEvent.InitGuiEvent.Post && ((GuiScreenEvent.InitGuiEvent.Post) event).gui instanceof GuiChest)) return false;
        GuiChest chest = ((GuiChest) ((GuiScreenEvent.InitGuiEvent.Post) event).gui);
        String upperString = ((IInventory) ReflectionUtils.field(chest, "upperChestInventory")).getDisplayName().getUnformattedText();
        String lowerString = ((IInventory) ReflectionUtils.field(chest, "lowerChestInventory")).getDisplayName().getUnformattedText();
        return upperString.contains(nameContains) || lowerString.contains(nameContains);
    }

}