package cheaters.get.banned.features.include.routines;

import cheaters.get.banned.events.SendChatMessageEvent;
import cheaters.get.banned.features.include.routines.triggers.CommandTrigger;
import cheaters.get.banned.features.include.routines.triggers.KeybindTrigger;
import cheaters.get.banned.features.include.routines.triggers.Trigger;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.Map;

public class RoutineHooks {

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        runTrigger(KeybindTrigger.class, event);
    }

    @SubscribeEvent
    public void onChatSent(SendChatMessageEvent event) {
        runTrigger(CommandTrigger.class, event);
    }

    private static void runTrigger(Class<? extends Trigger> triggerClass, Event event) {
        for(Map.Entry<Trigger, Routine> routine : Routines.routines.entrySet()) {
            if(routine.getKey().getClass() == triggerClass && routine.getKey().shouldTrigger(event)) {
                routine.getValue().doActions();
            }
        }
    }

}
