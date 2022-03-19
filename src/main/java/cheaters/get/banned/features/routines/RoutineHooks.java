package cheaters.get.banned.features.routines;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.MinuteEvent;
import cheaters.get.banned.events.PacketEvent;
import cheaters.get.banned.events.SendChatMessageEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.features.routines.triggers.*;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.Map;

public class RoutineHooks {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        runTrigger(KeybindTrigger.class, event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(SendChatMessageEvent event) {
        runTrigger(CommandTrigger.class, event);
    }

    @SubscribeEvent
    public void onChatReceived(PacketEvent.ReceiveEvent event) {
        if(event.packet instanceof S02PacketChat) {
            runTrigger(ChatReceivedTrigger.class, event);
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        runTrigger(WorldLoadTrigger.class, event);
    }

    @SubscribeEvent
    public void onMinute(MinuteEvent event) {
        runTrigger(TimeOfDayTrigger.class, event);
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(event.every(3)) runTrigger(PositionTrigger.class, event);
        if(event.every(20)) runTrigger(SecondIntervalTrigger.class, event);
    }

    private static void runTrigger(Class<? extends Trigger> triggerClass, Event event) {
        if(Shady.mc.theWorld == null /*|| !Utils.inSkyBlock*/) return;

        for(Map.Entry<Trigger, Routine> routine : Routines.routines.entrySet()) {
            if(routine.getKey().getClass() == triggerClass && routine.getKey().shouldTrigger(event)) {
                routine.getValue().doActions();
                if(routine.getKey().shouldCancelEvent) event.setCanceled(true);
            }
        }
    }

}
