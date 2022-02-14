package cheaters.get.banned.features.include.routines.triggers;

import cheaters.get.banned.features.include.routines.RoutineElementData;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class Trigger {

    public RoutineElementData data;
    public boolean shouldCancelEvent = false;

    public Trigger(RoutineElementData data) {
        this.data = data;
    }

    public abstract boolean shouldTrigger(Event event);

}