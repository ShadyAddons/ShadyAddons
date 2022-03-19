package cheaters.get.banned.features.routines.triggers;

import cheaters.get.banned.events.SendChatMessageEvent;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CommandTrigger extends Trigger {

    private String name;

    public CommandTrigger(RoutineElementData data) throws RoutineRuntimeException {
        super(data);
        super.shouldCancelEvent = true;
        name = data.keyAsString("command");
    }

    @Override
    public boolean shouldTrigger(Event event) {
        return
            event instanceof SendChatMessageEvent &&
            ((SendChatMessageEvent) event).message.equals("/" + name);
    }

}
