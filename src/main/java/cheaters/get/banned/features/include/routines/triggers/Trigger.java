package cheaters.get.banned.features.include.routines.triggers;

import cheaters.get.banned.features.include.routines.element.RoutineElement;
import cheaters.get.banned.features.include.routines.element.RoutineElementData;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class Trigger<E extends Event> extends RoutineElement {

    protected Class<E> event;

    public Trigger(Class<E> event, RoutineElementData data) {
        super(data);
        this.data = data;
        this.event = event;
    }

    public Class<E> getEvent() {
        return event;
    }

    public abstract boolean shouldTrigger(E event);

}
