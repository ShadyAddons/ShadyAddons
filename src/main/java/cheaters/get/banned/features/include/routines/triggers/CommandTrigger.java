package cheaters.get.banned.features.include.routines.triggers;

import cheaters.get.banned.events.SendChatMessageEvent;
import cheaters.get.banned.features.include.routines.RoutineElementData;
import cheaters.get.banned.features.include.routines.RoutineException;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CommandTrigger extends Trigger {

    private String name;

    public CommandTrigger(RoutineElementData data) throws RoutineException {
        super(data);
        super.shouldCancelEvent = true;
        name = data.keyAsString("name");
    }

    @Override
    public boolean shouldTrigger(Event event) {
        return
            event instanceof SendChatMessageEvent &&
            ((SendChatMessageEvent) event).message.equals("/" + name);
    }

}
