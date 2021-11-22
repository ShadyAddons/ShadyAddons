package cheaters.get.banned.events;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class DrawSlotEvent extends Event {

    public Container container;
    public Slot slot;

    public DrawSlotEvent(Container container, Slot slot) {
        this.container = container;
        this.slot = slot;
    }

}
