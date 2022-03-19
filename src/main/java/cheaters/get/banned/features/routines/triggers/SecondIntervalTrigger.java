package cheaters.get.banned.features.routines.triggers;

import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SecondIntervalTrigger extends Trigger {

    private int seconds;

    public SecondIntervalTrigger(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        seconds = data.keyAsInt("seconds");
    }

    @Override
    public boolean shouldTrigger(Event event) {
        return event instanceof TickEndEvent &&
                ((TickEndEvent) event).every(seconds * 20); // Also checked in RoutineHooks
    }

}
