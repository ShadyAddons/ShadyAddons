package cheaters.get.banned.features.include.routines.triggers;

import cheaters.get.banned.events.MinuteEvent;
import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineRuntimeException;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TimeOfDayTrigger extends Trigger {

    private int hour;
    private int minute;

    public TimeOfDayTrigger(RoutineElementData data) throws RoutineRuntimeException {
        super(data);

        hour = data.keyAsInt("hour");
        minute = data.keyAsInt("minute");

        if(hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new RoutineRuntimeException("Hour or minute is out of range");
        }
    }

    @Override
    public boolean shouldTrigger(Event event) {
        return event instanceof MinuteEvent
                && ((MinuteEvent) event).dateTime.getHour() == hour
                && ((MinuteEvent) event).dateTime.getMinute() == minute;
    }

}
