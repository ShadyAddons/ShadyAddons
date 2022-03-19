package cheaters.get.banned.features.routines.triggers;

import cheaters.get.banned.features.routines.RoutineElementData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class WorldLoadTrigger extends Trigger {

    public WorldLoadTrigger(RoutineElementData data) {
        super(data);
    }

    @Override
    public boolean shouldTrigger(Event event) {
        return event instanceof WorldEvent.Load;
    }

}
